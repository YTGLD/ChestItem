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

    // 引入时间变量，假设时间变量是从外部传入的uniform变量
    float time = (GameTime * 3333); // Time是一个外部传入的uniform变量，你需要在你的程序中定义并更新它

    float sigma = 8.0;
    float rcpFactor = 1.0 / (sqrt(2.0 * 3.141592653589793) * sigma);

    vec4 blurred = vec4(0.0);
    float weightSum = 0.0;
    float radius = 10.0;
    for (float a = -radius; a <= radius; a += 1) {
        float weight = rcpFactor * exp(-(a * a) / (2.0 * sigma * sigma));
        // 在这里修改texCoord以实现扭曲效果
        vec2 offsetTexCoord = texCoord + vec2(sin(texCoord.y * 10.0 + time) * 0.01, cos(texCoord.x * 10.0 + time) * 0.01);
        blurred += texture(InSampler, offsetTexCoord + sampleStep * a) * weight;
        weightSum += weight;
    }

    // 处理边缘情况时也应用同样的扭曲
    vec2 offsetTexCoord = texCoord + vec2(sin(texCoord.y * 10.0 + time) * 0.01, cos(texCoord.x * 10.0 + time) * 0.01);
    blurred += texture(InSampler, offsetTexCoord + sampleStep * radius) * rcpFactor * exp(-(radius * radius) / (2.0 * sigma * sigma)) / 2.0;
    weightSum += rcpFactor * exp(-(radius * radius) / (2.0 * sigma * sigma)) / 2.0;

    float aaa = blurred.a * 500;
    float r = (blurred / weightSum).r * 1.5;
    float g = (blurred / weightSum).g * 1.5;
    float b = (blurred / weightSum).b * 1.5;
    fragColor = vec4(vec3(r,g-(blurred.a/2),b),  aaa);
}
