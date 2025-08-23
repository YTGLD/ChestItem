package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.AttackEndComing;
import com.ytgld.chest_item.entity.state.AttackEndComingRenderState;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class AttackEndComingRender extends EntityRenderer<AttackEndComing, AttackEndComingRenderState> {
    public AttackEndComingRender(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public boolean shouldRender(AttackEndComing livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public @NotNull AttackEndComingRenderState createRenderState() {
        return new AttackEndComingRenderState();
    }
    @Override
    public void extractRenderState(AttackEndComing entity, AttackEndComingRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.entity = entity;
        reusedState.partialTick = partialTick;
    }
    @Override
    public void render(AttackEndComingRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        AttackEndComing entity = renderState.entity;
        double x = Mth.lerp(renderState.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(renderState.partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(renderState.partialTick, entity.zOld, entity.getZ());
        poseStack.pushPose();
        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);
        if (entity.follow) {
            setT(poseStack, entity, bufferSource);
        }else {
            setT2(poseStack, entity, bufferSource);

        }
        if (entity.canSee) {
            renderSphere1(poseStack, bufferSource, 0, MRender.end(true),0.15f);
            renderSphere1(poseStack, bufferSource, 255, MRender.end(false),0.15f);
        }
        poseStack.popPose();
    }
    private void setT2(PoseStack matrices,
                      AttackEndComing entity,
                      MultiBufferSource vertexConsumers)
    {
        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.getX(), prevPos.y - entity.getY(), prevPos.z - entity.getZ());
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.getX(), currPos.y - entity.getY(), currPos.z - entity.getZ());
            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());
            matrices.pushPose();
            Handler.renderBlood(matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, alpha, MRender.endBlack(true),alpha/10f);
            Handler.renderBlood(matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, alpha, MRender.endBlack(false),alpha/10f);
            matrices.popPose();

        }
    }

    private void setT(PoseStack matrices,
                      AttackEndComing entity,
                      MultiBufferSource vertexConsumers)
    {
        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.getX(), prevPos.y - entity.getY(), prevPos.z - entity.getZ());
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.getX(), currPos.y - entity.getY(), currPos.z - entity.getZ());
            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());
            matrices.pushPose();
            Handler.renderBlood(matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, alpha, MRender.end(true),alpha/10f);
            Handler.renderBlood(matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, alpha, MRender.end(false),alpha/10f);
            matrices.popPose();

        }
    }

    public void renderSphere1(@NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, RenderType renderType, float a ) {
        int stacks = 10; // 垂直方向的分割数
        int slices = 10; // 水平方向的分割数
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderType);
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

                vertexConsumer.addVertex(matrices.last().pose(), x0, y0, z0).setColor(1.0f, 1.0f, 1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x1, y1, z1).setColor(1.0f, 1.0f, 1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x2, y2, z2).setColor(1.0f, 1.0f, 1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x3, y3, z3).setColor(1.0f, 1.0f, 1.0f, 1.0f).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
            }
        }
    }
}



