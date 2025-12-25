package com.ytgld.chest_item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class HandlerClient {
    public static boolean showOutline = false;
    public static boolean doPass = false;


    public static boolean showRenderWarped = false;
    public static boolean doPassWarped = false;






    public static void renderBlood(PoseStack.Pose matrices,
                                   float speed,
                                   Vec3 vec3,
                                   float size,
                                   VertexConsumer vertexConsumers){

        matrices.translate((float) 0, (float) (speed+vec3.y),0);

        renderSphere1(matrices,vertexConsumers, size);

    }
    public static void renderSphere1(@NotNull PoseStack.Pose matrices, @NotNull VertexConsumer vertexConsumer, float a ) {
        int stacks = 20; // 垂直方向的分割数
        int slices = 20; // 水平方向的分割数
        for (int i = 0; i < stacks; ++i) {
            float phi0 = (float) Math.PI * ((i + 0) / (float) stacks);
            float phi1 = (float) Math.PI * ((i + 1) / (float) stacks);

            for (int j = 0; j < slices; ++j) {
                float theta0 = (float) (2 * Math.PI) * ((j + 0) / (float) slices);
                float theta1 = (float) (2 * Math.PI) * ((j + 1) / (float) slices);

                float x0 = a * (float) Math.sin(phi0) * (float) Math.cos(theta0);
                float y0 = a * (float) Math.cos(phi0);
                float z0 = a * (float) Math.sin(phi0) * (float) Math.sin(theta0);
                float x1 = a * (float) Math.sin(phi0) * (float) Math.cos(theta1);
                float y1 = a * (float) Math.cos(phi0);
                float z1 = a * (float) Math.sin(phi0) * (float) Math.sin(theta1);
                float x2 = a * (float) Math.sin(phi1) * (float) Math.cos(theta1);
                float y2 = a * (float) Math.cos(phi1);
                float z2 = a * (float) Math.sin(phi1) * (float) Math.sin(theta1);
                float x3 = a * (float) Math.sin(phi1) * (float) Math.cos(theta0);
                float y3 = a * (float) Math.cos(phi1);
                float z3 = a * (float) Math.sin(phi1) * (float) Math.sin(theta0);

                vertexConsumer.addVertex(matrices, x0, y0, z0).setColor(1.0f, 0,0, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255,255).setNormal(matrices,1, 0, 0);
                vertexConsumer.addVertex(matrices, x1, y1, z1).setColor(1.0f, 0,0, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255,255).setNormal(matrices,1, 0, 0);
                vertexConsumer.addVertex(matrices, x2, y2, z2).setColor(1.0f, 0,0, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255,255).setNormal(matrices,1, 0, 0);
                vertexConsumer.addVertex(matrices, x3, y3, z3).setColor(1.0f, 0,0, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255,255).setNormal(matrices,1, 0, 0);
            }
        }
    }
}
