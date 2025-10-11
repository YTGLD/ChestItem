#version 330

uniform sampler2D MainSampler;

layout(std140) uniform LightmapInfo {
    uniform mat4 u_INVMVP;
    uniform vec3 lightPos;
} lightmapInfo;

in vec2 texCoord;
out vec4 fragColor;

vec3 getFragPos(sampler2D depthMap) {
    float zBuffer = texture(depthMap, texCoord).r;
    float fragDepth = zBuffer * 2.0F - 1.0F;
    vec4 fragRelPos = vec4(texCoord.xy * 2.0F - 1.0F, fragDepth, 1.0F) * lightmapInfo.u_INVMVP;
    fragRelPos.xyz /= fragRelPos.w;
    return fragRelPos.xyz;
}

void main() {
    vec3 fragPos = getFragPos(MainSampler);
    vec4 color = vec4(0.0F, 0.0F, 0.0F, 0.0F);
    float distortionMultiplier = 0.0F;
    for (int i = 0; i < 32; i++) {
        vec3 lightPos = vec3(lightmapInfo.lightPos.x,lightmapInfo.lightPos.y,lightmapInfo.lightPos.z);
        float dist = distance(lightPos, fragPos);
        float radius = 16;
        if (dist < radius) {
            vec3 lightColor = vec3(1.,0,1);
            if (lightColor.r == -1 && lightColor.g == -1 && lightColor.b == -1) {
                if (distortionMultiplier < 0.6F) {
                    distortionMultiplier += max(distortionMultiplier, 1.0F - pow(dist / radius, 4));
                }
            }
        }
    }

    vec4 sourceColor;
    if (distortionMultiplier <= 0.0F) {
        sourceColor = texture2D(MainSampler, texCoord);
        color += sourceColor;
    } else {
        float fragDistortion = (fragPos.y + 0 + (cos(fragPos.x + 0) * sin(fragPos.z + 0))) * 5.0F;
        sourceColor = texture2D(MainSampler, texCoord + vec2(sin(fragDistortion + 0 * 50.0F / 300.0F) / 800.0F, 0.0F) * distortionMultiplier);
        color += sourceColor;
    }
    float lightingFogMultiplier = 1.0F;
    for (int i = 0; i < 32; i++) {
        vec3 lightPos = vec3(lightmapInfo.lightPos.x,lightmapInfo.lightPos.y,lightmapInfo.lightPos.z);
        float dist = distance(lightPos, fragPos);
        float radius = 16;
        vec3 lightColor = vec3(1.,0,1);

        if (dist < radius) {
            if (lightColor.r != -1 || lightColor.g != -1 || lightColor.b != -1) {
                color += (sourceColor * (vec4(lightColor * pow(1.0F - dist / radius, 2), 0.0F) * lightingFogMultiplier));
            }
        }
    }

    fragColor = color;
}
