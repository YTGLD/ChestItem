#version 330
#moj_import <minecraft:globals.glsl>

layout(std140) uniform SamplerInfo {
    vec2 OutSize;
    vec2 InSize;
};

layout(std140) uniform BlurConfig {
    vec2 BlurDir;
    float Radius;
};

uniform sampler2D InSampler;

in vec2 texCoord;

out vec4 fragColor;
void main() {
    vec2 oneTexel = 1.0 / InSize;
    vec2 sampleStep = oneTexel * BlurDir;

    float sigma = 10.0;
    float rcpFactor = 1.0 / (sqrt(2.0 * 3.141592653589793) * sigma);

    vec4 blurred = vec4(0.0);
    float weightSum = 0.0;
    float radius = 8.0;

    // 对每个模糊采样点应用扭曲
    for (float a = -radius; a <= radius; a += 1) {
        // 计算模糊采样位置
        vec2 sampleCoord = texCoord + sampleStep * a;

        // 扭曲处理
        vec2 center = vec2(0.5, 0.5);
        vec2 diff = sampleCoord - center;
        float distance = length(diff);

        // 扭曲因子
        float twistFactor = sin((GameTime * 3333) + distance * 30.0) * 0.0033; // 扭曲因子

        float angle = atan(diff.y, diff.x);
        float newAngle = angle + twistFactor;
        vec2 deformedTexCoord = center + distance * vec2(cos(newAngle), sin(newAngle));

        // 使用扭曲后的坐标进行模糊采样
        float weight = rcpFactor * exp(-(a * a) / (2.0 * sigma * sigma));
        blurred += texture(InSampler, deformedTexCoord) * weight;
        weightSum += weight;
    }

    // 最后的一次特殊处理，减少权重的一部分
    blurred += texture(InSampler, texCoord + sampleStep * radius) * rcpFactor * exp(-(radius * radius) / (2.0 * sigma * sigma)) / 2.0;
    weightSum += rcpFactor * exp(-(radius * radius) / (2.0 * sigma * sigma)) / 2.0;

    // 计算最终的颜色（这里可以根据需求调整颜色增强）
    float aaa = blurred.a * 500;
    float r = (blurred / weightSum).r * 1.25;
    float g = (blurred / weightSum).g * 1.25;
    float b = (blurred / weightSum).b * 1.25;

    fragColor = vec4(vec3(r, g, b), aaa);
}
