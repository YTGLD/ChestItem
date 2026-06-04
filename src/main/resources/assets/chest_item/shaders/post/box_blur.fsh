#version 330
//#moj_import <minecraft:globals.glsl>

uniform sampler2D InSampler;

layout(std140) uniform WarpedInfo {
    float stronger;
    vec2 posClasses;
    int time;
} warpedInfo;

layout(std140) uniform SamplerInfo {
    vec2 OutSize;
    vec2 InSize;
};

layout(std140) uniform BlurConfig {
    vec2 BlurDir;
    float Radius;
};

in vec2 texCoord;

out vec4 fragColor;

void main() {
    // =========================
    // 扭曲参数
    // =========================
    vec2 pos = vec2(texture(InSampler,texCoord).x,texture(InSampler,texCoord).y);
    vec2 center = pos;


    vec2 diff = texCoord - center;
    float distCenter = length(diff);

    // 扭曲半径（20%屏幕）
    float twistRadius = 0.2;

    // 中心最强，边缘逐渐减弱
    float twistStrength = smoothstep(
        twistRadius,
        0.0,
        distCenter
    );

    float angle = atan(diff.y, diff.x);

    // 动态旋涡
    float twistFactor =
    sin(GameTime * 3333.0 + distCenter * 30.0)
    * twistStrength
    * 0.5;

    float newAngle = angle + twistFactor;

    vec2 twistedTexCoord =
    center +
    distCenter *
    vec2(
    cos(newAngle),
    sin(newAngle)
    );

    // 防止UV越界
    twistedTexCoord = clamp(twistedTexCoord, vec2(0.0), vec2(1.0));
    // =========================
    // 模糊参数
    // =========================
    vec2 posScreen = pos;

    vec2 oneTexel = 1.0 / InSize;
    vec2 sampleStep = oneTexel * BlurDir;

    float distBlur = distance(gl_FragCoord.xy / InSize, posScreen);

    float radius = warpedInfo.stronger;

    // 模糊区域半径
    float blurRadiusLimit = 0.2;

    float blurFactor = smoothstep(blurRadiusLimit, 0.0, distBlur);
    // =========================
    // 模糊采样
    // =========================
    vec4 blurred = vec4(0.0);

    for (float a = -radius + 0.5;a <= radius; a += 2.0) {
        blurred += texture(InSampler, twistedTexCoord + sampleStep * a);
    }

    blurred += texture(InSampler, twistedTexCoord + sampleStep * radius) * 0.5;

    blurred /= (radius + 0.5);

    // =========================
    // 原图（已扭曲）
    // =========================
    vec4 original = texture(InSampler, twistedTexCoord);

    // =========================
    // 最终输出
    // =========================
    fragColor = mix(original, blurred,blurFactor);
}