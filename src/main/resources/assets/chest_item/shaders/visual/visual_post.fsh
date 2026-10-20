
#version 330
#extension GL_ARB_separate_shader_objects : require

layout(location = 0) out vec4 fragColor;
layout(location = 1) in vec2 texCoord;

uniform sampler2D MainSampler;
uniform sampler2D DepthSampler;

layout(std140) uniform Globals {
    ivec3 CameraBlockPos;
    float GlintAlpha;
    vec3 CameraOffset;
    float GameTime;
    vec2 ScreenSize;
    int MenuBlurRadius;
    int UseRgss;
};

layout(std140) uniform Projection {
    mat4 ProjMat;
};

layout(std140) uniform VisualColor {
    vec4 VisualPosition[32];
    vec4 VisualColors[32];
} VisualData;


// ============================================================
// Flame settings
// ============================================================

const int MAX_FLAMES = 32;

const float FLAME_HEIGHT = 3.8;
const float FLAME_WIDTH = 0.48;

const float MIN_HEIGHT_PIXELS = 12.0;
const float MAX_HEIGHT_PIXELS = 420.0;

const float DEPTH_BIAS = 0.0005;
const float DEPTH_FADE_END = 0.004;


// ============================================================
// Noise
// ============================================================

float hash21(vec2 p) {
    p = fract(p * vec2(123.34, 456.21));
    p += dot(p, p + 45.32);
    return fract(p.x * p.y);
}

float noise21(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);

    f = f * f * (3.0 - 2.0 * f);

    float a = hash21(i);
    float b = hash21(i + vec2(1.0, 0.0));
    float c = hash21(i + vec2(0.0, 1.0));
    float d = hash21(i + vec2(1.0, 1.0));

    return mix(
        mix(a, b, f.x),
        mix(c, d, f.x),
        f.y
    );
}


// ============================================================
// Occlusion
//
// Reverse-Z:
// sceneDepth > flameDepth 表示场景表面可能在火焰前方。
// ============================================================

float getFlameOcclusion(
    float sceneDepth,
    float flameDepth
) {
    float depthDifference = sceneDepth - flameDepth;

    return 1.0 - smoothstep(
        DEPTH_BIAS,
        DEPTH_FADE_END,
        depthDifference
    );
}


// ============================================================
// Flame shape
//
// p.x: 横向坐标，中心约为 0
// p.y: 高度，底部 0，顶部 1
// ============================================================

float flameShape(
    vec2 p,
    float time,
    float seed
) {
    float h = p.y;

    if (h < 0.0 || h > 1.0) {
        return 0.0;
    }

    // 底部粗壮，顶部尖锐
    float width = mix(
        0.62,
        0.012,
        h
    );

    // 火舌不断收缩和扩张
    width *= 1.0 + 0.22 * sin(
        h * 18.0 - time * 5.0 + seed
    );

    // 多频率摆动
    float sway =
    sin(
        h * 8.0 - time * 4.5 + seed
    ) * 0.13
    +
    sin(
        h * 17.0 - time * 7.0 + seed * 2.1
    ) * 0.075
    +
    sin(
        h * 29.0 - time * 9.0 + seed * 0.7
    ) * 0.035;

    // 低频扰动
    float n1 = noise21(vec2(
                       p.x * 5.0 + seed * 3.0,
                       h * 5.0 - time * 2.5
                       ));

    // 高频扰动
    float n2 = noise21(vec2(
                       p.x * 11.0 - seed,
                       h * 15.0 - time * 5.5
                       ));

    sway += (n1 - 0.5) * 0.20 * h;
    sway += (n2 - 0.5) * 0.10 * h;

    float localX = abs(p.x - sway);

    // 柔化火焰轮廓，避免边缘过硬
    float body = 1.0 - smoothstep(
        width * 0.25,
        width * 1.15,
        localX
    );

    // 提前开始顶部渐隐
    float fadeTop = 1.0 - smoothstep(
        0.70,
        1.0,
        h
    );

    // 底部保持连续
    float fadeBottom = smoothstep(
        0.0,
        0.045,
        h
    );

    // 火焰内部动态纹理
    float turbulence = noise21(vec2(
                               p.x * 7.0 + seed * 2.0,
                               h * 18.0 - time * 6.0
                               ));

    float wisps = smoothstep(
        0.12,
        0.78,
        turbulence
    );

    float flame = body * mix(
        0.60,
        1.0,
        wisps
    );

    return flame * fadeTop * fadeBottom;
}


// ============================================================
// Flame halo
// ============================================================

float flameHalo(vec2 p) {
    float outerHalo = exp(
        -p.x * p.x * 3.8
        - (p.y - 0.28) *
        (p.y - 0.28) * 2.5
    );

    float innerHalo = exp(
        -p.x * p.x * 12.0
        - (p.y - 0.22) *
        (p.y - 0.22) * 5.0
    );

    return outerHalo * 0.20
    + innerHalo * 0.28;
}


// ============================================================
// Main
// ============================================================

void main() {
    vec4 baseColor = texture(
        MainSampler,
        texCoord
    );

    vec2 screenSize = max(
        ScreenSize,
        vec2(1.0)
    );

    float sceneDepth = texture(
        DepthSampler,
        texCoord
    ).r;

    vec3 glowColor = vec3(0.0);

    float time = GameTime * 1000.0;

    // --------------------------------------------------------
    // Flame loop
    // --------------------------------------------------------

    for (int i = 0; i < MAX_FLAMES; i++) {
        vec4 source = VisualData.VisualPosition[i];
        vec4 color = VisualData.VisualColors[i];

        float strength = source.w;

        if (strength <= 0.0 || color.a <= 0.0) {
            continue;
        }

        // Camera-space position
        vec3 viewPos = source.xyz;
        float viewDepth = -viewPos.z;

        if (viewDepth <= 0.05) {
            continue;
        }

        // View -> Clip
        vec4 clipPos = ProjMat * vec4(
        viewPos,
        1.0
        );

        if (clipPos.w <= 0.0) {
            continue;
        }

        vec3 ndc = clipPos.xyz / clipPos.w;
        vec2 sourceUV = ndc.xy * 0.5 + 0.5;

        // 火焰源点不在屏幕内时跳过
        if (
        any(lessThan(sourceUV, vec2(0.0))) ||
        any(greaterThan(sourceUV, vec2(1.0)))
        ) {
            continue;
        }

        // ----------------------------------------------------
        // Distance falloff
        // ----------------------------------------------------

        float cameraDistance = length(viewPos);

        float distanceFactor = 1.0 - smoothstep(
            8.0,
            32.0,
            cameraDistance
        );

        distanceFactor *= distanceFactor;

        if (distanceFactor <= 0.001) {
            continue;
        }

        // ----------------------------------------------------
        // Screen-space flame dimensions
        // ----------------------------------------------------

        float heightPixels =
        abs(ProjMat[1][1]) *
        FLAME_HEIGHT /
        viewDepth *
        screenSize.y * 0.5;

        heightPixels = clamp(
            heightPixels,
            MIN_HEIGHT_PIXELS,
            MAX_HEIGHT_PIXELS
        );

        float widthPixels = heightPixels * FLAME_WIDTH;

        // Pixel offset from source
        vec2 deltaPixels =
        (texCoord - sourceUV) * screenSize;

        // p.y = 0 at source, 1 at flame top
        vec2 p;

        p.x = deltaPixels.x / max(widthPixels, 1.0);
        p.y = deltaPixels.y / max(heightPixels, 1.0);

        // 扩大计算区域，避免光晕在边缘突然消失
        if (
        p.y < -0.15 ||
        p.y > 1.12 ||
        abs(p.x) > 1.15
        ) {
            continue;
        }

        // ----------------------------------------------------
        // Soft boundary fade
        // ----------------------------------------------------

        float boundsFadeX = 1.0 - smoothstep(
            0.78,
            1.12,
            abs(p.x)
        );

        float boundsFadeY =
        smoothstep(
            -0.15,
            0.02,
            p.y
        ) *
        (
        1.0 - smoothstep(
            0.90,
            1.12,
            p.y
        )
        );

        // ----------------------------------------------------
        // Flame shape and halo
        // ----------------------------------------------------

        float flame = flameShape(
            p,
            time,
            float(i) * 1.71
        );

        float halo = flameHalo(p);

        // 光晕独立柔化，避免矩形边缘
        halo *= boundsFadeX;
        halo *= boundsFadeY;

        // ----------------------------------------------------
        // Depth occlusion
        //
        // 仍使用火焰源点深度近似整个火焰的深度。
        // ----------------------------------------------------

        float flameDepth = ndc.z;

        float occlusion = getFlameOcclusion(
            sceneDepth,
            flameDepth
        );

        // ----------------------------------------------------
        // Alpha and color
        // ----------------------------------------------------

        // 本体和光晕分别控制
        float flameAlpha = flame;
        float haloAlpha = halo;

        // 本体顶部进一步柔化
        float topFade = 1.0 - smoothstep(
            0.72,
            1.0,
            p.y
        );

        // 左右轮廓渐隐
        float edgeFadeX = 1.0 - smoothstep(
            0.68,
            0.85,
            abs(p.x)
        );

        // 光晕顶部渐隐
        float haloTopFade = 1.0 - smoothstep(
            0.55,
            1.0,
            p.y
        );

        // 合并透明度
        float alpha =
        flameAlpha * topFade +
        haloAlpha * haloTopFade;

        // 避免硬边
        alpha *= edgeFadeX;

        // 遮挡和距离衰减
        alpha *= occlusion;
        alpha *= distanceFactor;

        // 透明度上限
        alpha = clamp(
            alpha,
            0.0,
            0.45
        );

        // 下方略亮，上方略暗
        vec3 flameColor = mix(
            color.rgb * 1.10,
            color.rgb * 0.75,
            smoothstep(0.0, 1.0, clamp(p.y, 0.0, 1.0))
        );

        // 降低整体能量
        float intensity = min(
            strength * 0.85,
            1.5
        );

        glowColor +=
        flameColor *
        alpha *
        intensity *
        color.a;
    }

    // --------------------------------------------------------
    // Composite
    // --------------------------------------------------------

    // 控制多层火焰叠加后的整体亮度
    glowColor *= 0.85;

    // 软限制高亮，避免突然硬截断
    vec3 finalColor =
    baseColor.rgb +
    glowColor / (vec3(1.0) + glowColor * 0.35);

    fragColor = vec4(
    finalColor,
    baseColor.a
    );
}
