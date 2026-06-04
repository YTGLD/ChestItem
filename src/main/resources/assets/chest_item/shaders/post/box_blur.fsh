#version 330
#moj_import <minecraft:globals.glsl>

uniform sampler2D InSampler;

layout(std140) uniform WarpedInfo {
    float stronger;
    vec2 pos;
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
    vec2 center = warpedInfo.pos;;

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
    twistedTexCoord = clamp(
        twistedTexCoord,
        vec2(0.0),
        vec2(1.0)
    );
    vec4 original =
    texture(
        InSampler,
        twistedTexCoord
    );

    // =========================
    // 最终输出
    // =========================
    fragColor =original;
}