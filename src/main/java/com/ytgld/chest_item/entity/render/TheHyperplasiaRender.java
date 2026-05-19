package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.TheHyperplasia;
import com.ytgld.chest_item.entity.state.TheHyperplasiaRenderState;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TheHyperplasiaRender extends EntityRenderer<TheHyperplasia, TheHyperplasiaRenderState> {
    public TheHyperplasiaRender(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public boolean shouldRender(TheHyperplasia livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public @NotNull TheHyperplasiaRenderState createRenderState() {
        return new TheHyperplasiaRenderState();
    }
    @Override
    public void extractRenderState(TheHyperplasia entity, TheHyperplasiaRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.entity = entity;
        reusedState.partialTick = partialTick;
    }


    @Override
    public void submit(TheHyperplasiaRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, net.minecraft.client.renderer.state.level.CameraRenderState camera) {
        HandlerClient.showOutline = true;
        HandlerClient.doPass = true;

        TheHyperplasia entity = renderState.entity;
        double x = Mth.lerp(renderState.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(renderState.partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(renderState.partialTick, entity.zOld, entity.getZ());
        poseStack.pushPose();
        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);

        collector.submitCustomGeometry(poseStack, MRender.colorOutlineLines(true), (pose, bufferSource) -> {
            setT2(pose, entity, bufferSource);
        });
        collector.submitCustomGeometry(poseStack, MRender.colorOutlineLines(false), (pose, bufferSource) -> {
            setT2(pose, entity, bufferSource);
        });

        poseStack.popPose();
    }
    private void setT2(PoseStack.Pose matrices,
                       TheHyperplasia entity,
                       VertexConsumer vertexConsumers)
    {
        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.getX(), prevPos.y - entity.getY(), prevPos.z - entity.getZ());
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.getX(), currPos.y - entity.getY(), currPos.z - entity.getZ());
            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());
            alpha *= 255;
            addSquare(vertexConsumers,matrices,adjustedCurrPos, adjustedPrevPos, Light.ARGB.color((int) alpha,255,0,255 - (int) alpha));
        }
    }

    private static void addSquare(VertexConsumer vertexConsumer, PoseStack.Pose poseStack, Vec3 s, Vec3 e, int colorS) {
        vertexConsumer.addVertex(poseStack, (float) s.x, (float) s.y, (float) s.z)
                .setColor(colorS)
                .setUv2(255, 255)
                .setNormal(1,0,0)
                .setLight(255)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLineWidth(20);

        vertexConsumer.addVertex(poseStack, (float) e.x, (float) e.y, (float) e.z)
                .setColor(colorS)
                .setUv2(255, 255)
                .setNormal(1,0,0)
                .setLight(255)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLineWidth(20);
    }
}




