package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.LaserColumn;
import com.ytgld.chest_item.entity.state.LaserColumnRenderState;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class LaserColumnRender extends EntityRenderer<LaserColumn, LaserColumnRenderState> {
    public LaserColumnRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRender(LaserColumn livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
    @Override
    public void submit(LaserColumnRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
        LaserColumn entity = renderState.entity;
        HandlerClient.showOutline = true;
        HandlerClient.doPass = true;
        int posOffset  = 0;
        float s =600 -  entity.tickCount;
        float ss = Math.min(40,s);
        ss /= 40f;
        float timeC = ss;


        float a =entity.tickCount;
        float c = Math.min(10,a);
        c /= 10f;
        float time=  c;
        double x = Mth.lerp(renderState.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(renderState.partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(renderState.partialTick, entity.zOld, entity.getZ());
        nodeCollector.submitCustomGeometry(poseStack, MRender.colorOutline(true), (pose, bufferSource) -> {
            setT2(pose, entity,bufferSource);
        });
        if (entity.canSee) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees((entity.tickCount + (renderState.partialTick * 4) * 4)));
            poseStack.translate(entity.getX() - x, entity.getY() - y, entity.getZ() - z);
            nodeCollector.submitCustomGeometry(poseStack, MRender.colorOutline(true), (pose, bufferSource) -> {
                pose.translate(0, 40 + posOffset, 0);
                pose.scale(1 * time * timeC, 20, 1 * time * timeC);
                renderSphere1(pose, bufferSource, 100, 3.25f, Light.ARGB.color(255, 100, 50, 255));
            });
            nodeCollector.submitCustomGeometry(poseStack, MRender.colorOutline(true), (pose, bufferSource) -> {
                pose.scale(2 * time * timeC, 0.2f, 2 * time * timeC);
                renderSphere1(pose, bufferSource, 100, 3, Light.ARGB.color(100, 255, 100, 0));
            });
            poseStack.popPose();
        }
        if (entity.canSee) {
            for (int i = 0; i < 8; i++) {
                poseStack.pushPose();
                poseStack.mulPose(Axis.YP.rotationDegrees(entity.tickCount * 4 + (i * 45)));
                poseStack.translate(entity.getX() - x, entity.getY() - y, entity.getZ() - z);
                nodeCollector.submitCustomGeometry(poseStack, MRender.colorOutline(true), (pose, bufferSource) -> {
                    pose.translate(4, 25 + posOffset, 0);
                    pose.scale(1 * time * timeC, 50, 1 * time * timeC);
                    renderSphere1(pose, bufferSource, 100, 0.8f, Light.ARGB.color(255, 15, 35, 200));
                });
                nodeCollector.submitCustomGeometry(poseStack, MRender.colorOutline(true), (pose, bufferSource) -> {
                    pose.translate(4, 0, 0);
                    pose.scale(2 * time * timeC, 1, 2 * time * timeC);
                    renderSphere1(pose, bufferSource, 100, 1, Light.ARGB.color(255, 150, 0, 150));
                });
                poseStack.popPose();
            }
        }
    }
    private void setT2(PoseStack.Pose matrices,
                       LaserColumn entity,
                       VertexConsumer vertexConsumers)
    {
        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.getX(), prevPos.y - entity.getY() , prevPos.z - entity.getZ());
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.getX(), currPos.y - entity.getY(), currPos.z - entity.getZ());
            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());
            renderBlood(Light.ARGB.color((int) (255*alpha),255, (int) (100*alpha),0),matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, Math.max(255,alpha),6 * alpha);
            renderBlood(Light.ARGB.color((int) (255*alpha),255, (int) (100*alpha),0),matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, Math.max(255,alpha),6 * alpha);
        }
    }

    public static void renderBlood(int color, PoseStack.Pose poseStack, VertexConsumer vertexConsumer, Vec3 start, Vec3 end, float alpha, float r) {
        int segmentCount = 16;

        for (int i = 0; i < segmentCount; i++) {
            double angle1 = (2 * Math.PI * i) / segmentCount;
            double angle2 = (2 * Math.PI * (i + 1)) / segmentCount;

            double x1 = Math.cos(angle1) * r;
            double z1 = Math.sin(angle1) * r;
            double x2 = Math.cos(angle2) * r;
            double z2 = Math.sin(angle2) * r;

            Vec3 up1 = start.add(x1, 0, z1);
            Vec3 up2 = start.add(x2, 0, z2);

            Vec3 down1 = end.add(x1, 0, z1);
            Vec3 down2 = end.add(x2, 0, z2);

            addSquare(color, vertexConsumer, poseStack, up1, up2, down1, down2, alpha);
        }
    }

    private static void addSquare(int color ,VertexConsumer vertexConsumer, PoseStack.Pose poseStack, Vec3 up1, Vec3 up2, Vec3 down1, Vec3 down2, float alpha) {
        // 添加四个顶点来绘制一个矩形
        vertexConsumer.addVertex(poseStack, (float) up1.x, (float) up1.y, (float) up1.z)
                .setColor(color)
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) down1.x, (float) down1.y, (float) down1.z)
                .setColor(color)
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) down2.x, (float) down2.y, (float) down2.z)
                .setColor(color)
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) up2.x, (float) up2.y, (float) up2.z)
                .setColor(color)
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);
    }

    public void renderSphere1(@NotNull PoseStack.Pose matrices, @NotNull VertexConsumer vertexConsumer, int light, float s ,int color) {
        int stacks = 25; // 垂直方向的分割数
        int slices = 25; // 水平方向的分割数
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

                vertexConsumer.addVertex(matrices, x0, y0, z0).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(light, light);
                vertexConsumer.addVertex(matrices, x1, y1, z1).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(light, light);
                vertexConsumer.addVertex(matrices, x2, y2, z2).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(light, light);
                vertexConsumer.addVertex(matrices, x3, y3, z3).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(light, light);
            }
        }
    }
    @Override
    public void extractRenderState(LaserColumn entity, LaserColumnRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.entity = entity;
        reusedState.partialTick = partialTick;
    }
    @Override
    public LaserColumnRenderState createRenderState() {
        return new LaserColumnRenderState();
    }

}
