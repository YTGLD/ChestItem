package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.EndComing;
import com.ytgld.chest_item.entity.state.EndComingRenderState;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EndComingRender extends EntityRenderer<@NotNull EndComing, EndComingRenderState> {
    public EndComingRender(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public boolean shouldRender(EndComing livingEntity, Frustum camera, double camX, double camY, double camZ, float partialTicks) {
        return true;
    }

    @Override
    public @NotNull EndComingRenderState createRenderState() {
        return new EndComingRenderState();
    }

    @Override
    public void submit(EndComingRenderState renderState, PoseStack modelView , SubmitNodeCollector collector, CameraRenderState camera) {
        EndComing entity = renderState.entity;

        submitOther(renderState, modelView, collector, camera);
    }
    public void submitOther(EndComingRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        EndComing entity = renderState.entity;
        double x = Mth.lerp(renderState.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(renderState.partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(renderState.partialTick, entity.zOld, entity.getZ());
        poseStack.pushPose();
        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);

        if (entity.getOwner() instanceof Player player) {
            int alpha = (int) ((EvilMother.getSanValueBase(player)- EvilMother.getSanValue(player)) * 25);
            collector.submitCustomGeometry(poseStack, RenderTypes.lightning(), (pose, bufferSource) -> {
                renderSphere1(pose, bufferSource, Light.ARGB.color(
                        Math.clamp(alpha, 0, 255),
                        80,
                        255,
                        180
                ), 0.45f);
            });
        }

        collector.submitCustomGeometry(poseStack, MRender.endBlack, (pose, bufferSource) -> {
            renderSphere1(pose, bufferSource, 0xffffffff, 0.44f);
        });


        poseStack.popPose();
    }

    public void renderSphere1(@NotNull PoseStack.Pose matrices, @NotNull VertexConsumer vertexConsumer, int color, float s ) {

        int stacks = 20; // 垂直方向的分割数
        int slices = 20; // 水平方向的分割数
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

                vertexConsumer.addVertex(matrices, x0, y0, z0).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255,255).setNormal(matrices,1, 0, 0);
                vertexConsumer.addVertex(matrices, x1, y1, z1).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255,255).setNormal(matrices,1, 0, 0);
                vertexConsumer.addVertex(matrices, x2, y2, z2).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255,255).setNormal(matrices,1, 0, 0);
                vertexConsumer.addVertex(matrices, x3, y3, z3).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(255,255).setNormal(matrices,1, 0, 0);
            }
        }
    }
    @Override
    public void extractRenderState(EndComing entity, EndComingRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.entity = entity;
        reusedState.partialTick = partialTick;
    }
}



