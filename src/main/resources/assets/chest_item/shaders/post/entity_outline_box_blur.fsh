#version 150

uniform sampler2D InSampler;

in vec2 texCoord;
in vec2 sampleStep;

out vec4 fragColor;

void main() {
    vec4 blurred = vec4(0.0);
    float radius = 5.0;
    for (float a = -radius + 1; a <= radius; a += 1) {
        blurred += texture(InSampler, texCoord + sampleStep * a);
    }
    blurred += texture(InSampler, texCoord + sampleStep * radius) / 2.0;
    fragColor = vec4((blurred / (radius + 1.25)).rgb, blurred.a/2.5);
}
