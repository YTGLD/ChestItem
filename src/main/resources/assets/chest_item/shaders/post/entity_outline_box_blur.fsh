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

    for (float a = -radius; a <= radius; a += 1.0) {
        vec2 sampleCoord = texCoord + sampleStep * a;

        float weight = rcpFactor * exp(-(a * a) / (2.0 * sigma * sigma));

        blurred += texture(InSampler, sampleCoord) * weight;
        weightSum += weight;
    }

    vec4 result = blurred / weightSum;

    float aaa = result.a * 666.0;

    fragColor = vec4(
    result.rgb * 1.25,
    aaa
    );
}