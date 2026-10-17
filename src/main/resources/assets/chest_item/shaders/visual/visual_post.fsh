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


// ------------------------------------------------------------
// 随机噪声
// ------------------------------------------------------------

float hash21(vec2 p) {
    return fract(
        sin(dot(p, vec2(127.1, 311.7)))
        * 43758.5453
    );
}

float noise(vec2 p) {
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


// ------------------------------------------------------------
// 分形噪声
// ------------------------------------------------------------

float fbm(vec2 p) {
    float result = 0.0;
    float amplitude = 0.5;

    for (int i = 0; i < 4; i++) {
        result += noise(p) * amplitude;

        p = p * 2.02 + vec2(13.1, 7.7);
        amplitude *= 0.5;
    }

    return result;
}


// ------------------------------------------------------------
// 将视图空间位置投影到屏幕 UV
// ------------------------------------------------------------
bool projectViewToUV(
    vec3 viewPos,
out vec2 uv,
out float viewDepth,
out float effectDepth
) {
    vec4 clipPos = ProjMat * vec4(viewPos, 1.0);

    if (clipPos.w <= 0.0001) {
        return false;
    }

    vec3 ndc = clipPos.xyz / clipPos.w;

    uv = ndc.xy * 0.5 + 0.5;

    viewDepth = -viewPos.z;

    // Minecraft 26.3 反向 Z，深度范围为 0~1
    effectDepth = ndc.z;

    return viewDepth > 0.0;
}

float evilSteam(
    vec2 p,
    float t,
    float seed,
    float n1,
    float n2,
    float width
) {
    // ========================================================
    // 1. 轮廓扰动：让烟雾不再像固定的圆柱
    // ========================================================

    float vertical = max(p.y, 0.0);

    // 缓慢摆动，避免整个烟雾同步抖动
    float sway =
    sin(p.y * 2.0 - t * 0.8 + seed) * 0.075
    + sin(p.y * 4.3 - t * 1.15 + seed * 0.7) * 0.025;

    // 使用已有噪声扰动边缘，不额外采样
    float turbulence = (n1 - 0.5) * 0.20
    + (n2 - 0.5) * 0.10;

    // 高处逐渐变得更加松散
    float loosen = 1.0 + vertical * 0.12;

    float dx = abs(
        p.x - sway - turbulence * loosen
    );

    // ========================================================
    // 2. 不规则轮廓与左右渐隐
    // ========================================================

    float localWidth = width * (
    0.88
    + 0.12 * sin(p.y * 3.1 - t * 0.45 + seed)
    );

    // 主体轮廓
    float body = 1.0 - smoothstep(
        localWidth * 0.60,
        localWidth,
        dx
    );

    // 左右渐隐
    float sideFade = 1.0 - smoothstep(
        localWidth * 0.20,
        localWidth,
        dx
    );

    // ========================================================
    // 3. 上升烟丝
    // ========================================================

    // 纵向拉伸的条纹，模拟烟雾向上卷动
    float streak = sin(
        p.x * 9.0
        + sin(p.y * 2.0 - t * 0.7 + seed) * 2.0
        - t * 0.9
        + n1 * 3.0
    );

    // 不规则的烟丝密度
    float wisps = mix(
        0.45,
        1.0,
        smoothstep(-0.35, 0.65, streak)
    );

    // 细小噪声打破过于平滑的内部
    float detail = mix(
        0.72,
        1.0,
        smoothstep(0.20, 0.80, n2)
    );

    // ========================================================
    // 4. 上升与消散
    // ========================================================

    float bottomFade = smoothstep(
        -0.28,
        0.05,
        p.y
    );

    // 越往上越透明
    float riseFade = exp(-vertical * 0.65);

    // 高处逐渐消散
    float topFade = 1.0 - smoothstep(
        3.2,
        3.8,
        p.y
    );

    // ========================================================
    // 5. 密度变化
    // ========================================================

    float density = 0.72 + n1 * 0.28;

    // 轻微的时间脉动，避免整片烟雾过于静止
    float motion = 0.92 + 0.08 * sin(
        t * 1.1 + p.y * 2.5 + seed
    );

    return body
    * sideFade
    * bottomFade
    * riseFade
    * topFade
    * wisps
    * detail
    * density
    * motion;
}

void main() {
    float t = GameTime * 1500;
    float sceneDepth = texture(
        DepthSampler,
        texCoord
    ).r;
    float aspect =
    ScreenSize.x / max(ScreenSize.y, 1.0);

    // 以世界单位表示的基础作用半径
    const float WORLD_RADIUS = 1.5;

    vec2 warpUV = vec2(0.0);

    float smokeAccum = 0.0;
    float haloAccum = 0.0;

    vec3 smokeColorAccum = vec3(0.0);
    vec3 haloColorAccum = vec3(0.0);


    // ========================================================
    // 遍历实体
    // ========================================================

    for (int i = 0; i < 32; i++) {
        vec4 position = VisualData.VisualPosition[i];
        vec4 color = VisualData.VisualColors[i];

        // xyz 是相机视图空间坐标，w 是强度
        vec3 viewPos = position.xyz;

        float strength = clamp(position.w, 0.0, 2.5)
        * clamp(color.a, 0.0, 1.0);

        if (strength <= 0.001) {
            continue;
        }

        // 投影到屏幕
        vec2 centerUV;
        float viewDepth;
        float effectDepth;

        if (!projectViewToUV(
            viewPos,
            centerUV,
            viewDepth,
            effectDepth
        )) {
            continue;
        }

        // 前方存在更近的物体时，不绘制当前实体的光晕
        const float DEPTH_BIAS = 0.0002;

        if (sceneDepth > effectDepth + DEPTH_BIAS) {
            continue;
        }
        // 根据透视关系计算屏幕空间半径
        // 距离越远，屏幕上的光晕越小
        float radiusUV =
        0.5 * abs(ProjMat[1][1])
        * WORLD_RADIUS
        / max(viewDepth, 0.001);

        // 太小的效果没有继续计算的必要
        if (radiusUV * ScreenSize.y < 0.75) {
            continue;
        }

        // 局部坐标：水平与垂直方向采用近似一致的尺度
        // 此处假设 texCoord.y 向上增加
        vec2 p = vec2(
        (texCoord.x - centerUV.x) * aspect,
        texCoord.y - centerUV.y
        ) / radiusUV;

        // 蒸汽覆盖区域以外直接跳过噪声计算
        if (
        p.y < -1.25 ||
        p.y > 3.9 ||
        abs(p.x) > 1.75
        ) {
            continue;
        }

        float dist = length(p);
        float seed = float(i) * 13.71;

        // ----------------------------------------------------
        // 1. 动态噪声
        // 每个实体只计算一次，供蒸汽和扭曲共同使用
        // ----------------------------------------------------

        float n1 = fbm(
            vec2(
            p.x * 1.65
            + sin(p.y * 1.5 - t * 0.3 + seed) * 0.12,
            p.y * 0.85 - t * 0.30
            )
            + vec2(seed, seed * 0.63)
        );

        float n2 = noise(
            vec2(
            p.x * 4.8
            + sin(p.y * 2.0 - t * 0.65 + seed) * 0.18,
            p.y * 2.4 - t * 1.0
            )
            + vec2(seed * 1.7, seed)
        );

        // 微弱的整体能量脉动
        float pulse = 0.88 + 0.12 * sin(
            t * 1.25 + float(i) * 1.7
        );

        float power = strength * pulse;


        // ----------------------------------------------------
        // 2. 邪恶蒸汽
        // ----------------------------------------------------

        float smoke = evilSteam(
            p,
            t,
            seed,
            n1,
            n2,
            position.w
        );

        float smokePart = smoke * power;

        smokeAccum += smokePart;
        smokeColorAccum += color.rgb * smokePart;


        // ----------------------------------------------------
        // 3. 实体周围的光晕与能量环
        // ----------------------------------------------------

        float halo = exp(-dist * dist * 1.75);

        float ringRadius =
        0.55 + 0.07 * sin(
            t * 1.8 + float(i) * 2.1
        );

        float ring = exp(
            -pow(dist - ringRadius, 2.0) * 12.0
        );

        float haloPart =
        (halo * 0.24 + ring * 0.12)
        * power;

        haloAccum += haloPart;
        haloColorAccum += color.rgb * haloPart;


        // ----------------------------------------------------
        // 4. 附近空间扰动
        // 使用同一组动态噪声，避免重复进行 FBM 计算
        // ----------------------------------------------------

        vec2 tangent = vec2(-p.y, p.x)
        / max(dist, 0.18);

        // 随时间移动的波纹
        float wave = sin(
            dist * 12.0
            - t * 2.6
            + n1 * 2.5
        );

        vec2 flow =
        tangent * wave * 0.65
        + vec2(n1 - 0.5, n2 - 0.5) * 0.8;

        float warpMask =
        exp(-dist * 0.85)
        * (0.45 + n1 * 0.55);

        // 转回屏幕 UV，并限制初始扭曲强度
        warpUV += vec2(
        flow.x / aspect,
        flow.y
        ) * radiusUV
        * 0.045
        * power
        * warpMask;
    }


    // ========================================================
    // 限制总扭曲量，避免多个实体同时影响时效果过强
    // ========================================================

    float warpLength = length(warpUV);

    if (warpLength > 0.012) {
        warpUV *= 0.012 / warpLength;
    }


    // ========================================================
    // 5. 利用场景深度边缘，减轻物体边缘处的扭曲伪影
    // ========================================================

    vec2 pixel = 1.0 / max(
        ScreenSize,
        vec2(1.0)
    );

    float depthCenter = texture(
        DepthSampler,
        texCoord
    ).r;

    float depthL = texture(
        DepthSampler,
        clamp(
            texCoord - vec2(pixel.x, 0.0),
            vec2(0.0),
            vec2(1.0)
        )
    ).r;

    float depthR = texture(
        DepthSampler,
        clamp(
            texCoord + vec2(pixel.x, 0.0),
            vec2(0.0),
            vec2(1.0)
        )
    ).r;

    float depthU = texture(
        DepthSampler,
        clamp(
            texCoord + vec2(0.0, pixel.y),
            vec2(0.0),
            vec2(1.0)
        )
    ).r;

    float depthD = texture(
        DepthSampler,
        clamp(
            texCoord - vec2(0.0, pixel.y),
            vec2(0.0),
            vec2(1.0)
        )
    ).r;

    float depthEdge = max(
        max(
            abs(depthL - depthCenter),
            abs(depthR - depthCenter)
        ),
        max(
            abs(depthU - depthCenter),
            abs(depthD - depthCenter)
        )
    );

    float edgeFade = smoothstep(
        0.001,
        0.025,
        depthEdge
    );

    warpUV *= 1.0 - edgeFade;


    // ========================================================
    // 6. 采样受扰动的画面
    // ========================================================

    vec4 baseColor = texture(
        MainSampler,
        clamp(
            texCoord + warpUV,
            vec2(0.0),
            vec2(1.0)
        )
    );


    // ========================================================
    // 7. 合成烟雾颜色和中心光晕
    // ========================================================

    vec3 smokeColor =
    smokeColorAccum / max(smokeAccum, 0.0001);

    vec3 haloColor =
    haloColorAccum / max(haloAccum, 0.0001);

    // 半透明烟雾，保留原始画面的可见性
    float smokeAlpha = clamp(
        smokeAccum * 0.40,
        0.0,
        0.58
    );

    vec3 result = mix(
        baseColor.rgb,
        smokeColor,
        smokeAlpha * 0.42
    );

    // 烟雾内部微弱发光
    result += smokeColor * clamp(
        smokeAccum * 0.10,
        0.0,
        0.32
    );

    // 中心光晕与环形能量
    result += haloColor * clamp(
        haloAccum * 0.14,
        0.0,
        0.20
    );

    fragColor = vec4(result, baseColor.a);
}