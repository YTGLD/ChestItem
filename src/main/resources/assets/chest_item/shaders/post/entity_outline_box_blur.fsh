#version 150

uniform sampler2D InSampler;

in vec2 texCoord;
in vec2 sampleStep;

out vec4 fragColor;


void main() {
    vec4 blurred = vec4(0.0);
    float radius = 10.0;
    float sigma = 5.0; // 标准差，可根据需要调整以改变模糊效果
    float total = 0.0;

    // 计算高斯权重
    for (float a = -radius; a <= radius; a += 1.0) {
        float weight = exp(-a * a / (2.0 * sigma * sigma));
        blurred += texture(InSampler, texCoord + sampleStep * a) * weight;
        total += weight;
    }

    // 将结果除以权重总和，确保颜色值的正确性
    vec4 color = blurred / total;

    // 调整亮度 (假设增加亮度)
    float brightness = 0.4; // 增加0.1的亮度
    color.rgb += brightness;

    // 调整对比度 (假设增加对比度)
    float contrast = 2.5 ;// 增加1.5的对比度
    color.rgb = ((color.rgb - 0.5) * contrast) + 0.5;

    // 确保颜色值在0到1之间
    color.rgb = clamp(color.rgb, 0.0, 1.0);

    fragColor = color;
}