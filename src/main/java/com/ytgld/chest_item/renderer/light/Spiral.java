package com.ytgld.chest_item.renderer.light;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class Spiral {
   public Spiral(
            PoseStack stack,
            Vec3 pos,
            VertexConsumer consumer
    ){
        int numberOfOrbs = 100;
        float baseRadius = 1.5f; // 基础半径
        float angleStep = (float) (3 * Math.PI) / numberOfOrbs; // 每个球的角度步长

        for (int i = 0; i < numberOfOrbs; i++) {
            float angle = i * angleStep;
            float radius = baseRadius + (i / (float) numberOfOrbs) * 3.5f *(i/20f) ; // 半径随角度增加而增加
            float x = (float) pos.x + radius * (float) Math.cos(angle);
            float y = (float) pos.y;
            float z = (float) pos.z + radius * (float) Math.sin(angle);
            float scale = (numberOfOrbs - i)/100f/2f ;

            int color = getColorForOrb(i, numberOfOrbs); // 获取每个球的颜色
            stack.pushPose();
            stack.translate(x, y, z);
            renderOrb(stack, consumer, color, scale);
            stack.popPose();
        }
    }


    public void renderOrb(@NotNull PoseStack matrices, @NotNull VertexConsumer vertexConsumer, int color, float s) {
        int stacks = 4;
        int slices = 4;
        for (int i = 0; i < stacks; ++i) {
            float phi0 = (float) Math.PI * ((i + 0) / (float) stacks);
            float phi1 = (float) Math.PI * ((i + 1) / (float) stacks);

            for (int j = 0; j < slices; ++j) {
                float theta0 = (float) (2 * Math.PI) * ((j + 0) / (float) slices);
                float theta1 = (float) (2 * Math.PI) * ((j + 1) / (float) slices);

                float x0 = s * (float) Math.sin(phi0) * (float) Math.cos(theta0);
                float y0 = s * (float) Math.cos(phi0);
                float z0 = s * (float) Math.sin(phi0) * (float) Math.sin(theta0);

                float x1 = s * (float) Math.sin(phi0) * (float) Math.cos(theta1);
                float y1 = s * (float) Math.cos(phi0);
                float z1 = s * (float) Math.sin(phi0) * (float) Math.sin(theta1);

                float x2 = s * (float) Math.sin(phi1) * (float) Math.cos(theta1);
                float y2 = s * (float) Math.cos(phi1);
                float z2 = s * (float) Math.sin(phi1) * (float) Math.sin(theta1);

                float x3 = s * (float) Math.sin(phi1) * (float) Math.cos(theta0);
                float y3 = s * (float) Math.cos(phi1);
                float z3 = s * (float) Math.sin(phi1) * (float) Math.sin(theta0);

                vertexConsumer.addVertex(matrices.last().pose(), x0, y0, z0).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255, 255).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x1, y1, z1).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255, 255).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x2, y2, z2).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255, 255).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x3, y3, z3).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255, 255).setNormal(1, 0, 0);
            }
        }
    }

    private int getColorForOrb(int index, int total) {
        // 计算颜色渐变
        float t = index / (float) total;
        int r = (int) (255 * t);
        int g = (int) (255 * (1 - t));
        int b = 255;
        int a = 255;
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
