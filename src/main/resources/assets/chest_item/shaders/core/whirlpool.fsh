#version 330

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:matrix.glsl>
#moj_import <minecraft:globals.glsl>

uniform sampler2D Sampler0;

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;



void main() {
    // 计算旋转中心
    vec2 center = vec2(0.5, 0.5);

    // 将纹理坐标转换为围绕中心的相对坐标
    vec2 rotatedTexCoord = texCoord0 - center;

    // 计算纹理坐标到中心的距离
    float distanceToCenter = length(rotatedTexCoord);

    // 根据距离调整旋转角度
    float baseRotationSpeed = 150.0; // 基础旋转速度
    float rotationFactor = 0.25; // 控制旋涡旋臂精细度的因子
    float adjustedRotationSpeed = baseRotationSpeed * (1.0 + rotationFactor * distanceToCenter);

    // 计算旋转矩阵
    float cosTheta = cos(GameTime * adjustedRotationSpeed);
    float sinTheta = sin(GameTime * adjustedRotationSpeed);
    mat2 rotationMatrix = mat2(cosTheta, -sinTheta, sinTheta, cosTheta);

    // 应用旋转矩阵到相对坐标
    rotatedTexCoord = rotationMatrix * rotatedTexCoord;

    // 将旋转后的相对坐标转换回绝对坐标
    rotatedTexCoord += center;

    // 使用旋转后的纹理坐标采样纹理
    vec4 color = texture(Sampler0, rotatedTexCoord) * vertexColor;

    if (color.a == 0.0) {
        discard;
    }
    fragColor = color;
}
