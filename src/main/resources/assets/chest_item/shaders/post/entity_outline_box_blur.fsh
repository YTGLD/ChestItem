#version 330

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

    for (float a = -radius; a <= radius; a += 1) {
        vec2 sampleCoord = texCoord + sampleStep * a;

        vec2 center = vec2(0.5, 0.5);
        vec2 diff = sampleCoord - center;
        float distance = length(diff);

        float twistFactor = sin((1 * 3333) + distance * 30.0) * 0.0033; // 扭曲因子

        float angle = atan(diff.y, diff.x);
        float newAngle = angle + twistFactor;
        vec2 deformedTexCoord = center + distance * vec2(cos(newAngle), sin(newAngle));

        float weight = rcpFactor * exp(-(a * a) / (2.0 * sigma * sigma));
        blurred += texture(InSampler, deformedTexCoord) * weight;
        weightSum += weight;
    }

    blurred += texture(InSampler, texCoord + sampleStep * radius) * rcpFactor * exp(-(radius * radius) / (2.0 * sigma * sigma)) / 2.0;
    weightSum += rcpFactor * exp(-(radius * radius) / (2.0 * sigma * sigma)) / 2.0;

    float aaa = blurred.a * 666;
    float r = (blurred / weightSum).r * 1.25;
    float g = (blurred / weightSum).g * 1.25;
    float b = (blurred / weightSum).b * 1.25;

    fragColor = vec4(vec3(r, g, b), aaa);
}
