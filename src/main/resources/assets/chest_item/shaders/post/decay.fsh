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

    vec4 blurred = vec4(0.0);
    float radius = 3.0;

    for (float a = -radius + radius / 4; a <= radius; a += 1.0) {
        blurred += texture(InSampler, texCoord + sampleStep * a);
    }

    blurred += texture(InSampler, texCoord + sampleStep * radius) / 2.0;

    blurred /= (radius + 0.5);

    vec2 pixel = texCoord * InSize;

    float noise = fract(
        sin(dot(pixel, vec2(12.9898,78.233))) * 43758.5453
    );

    if (noise > blurred.a) {
        discard;
    }


    fragColor = vec4(blurred.rgb, blurred.a);
}