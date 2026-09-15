package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.Reactor;
import com.ytgld.chest_item.entity.EndComing;
import com.ytgld.chest_item.entity.state.ReactorRenderState;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class ReactorRender extends EntityRenderer<Reactor, ReactorRenderState> {
    private final ItemModelResolver itemModelResolver;
    public ReactorRender(EntityRendererProvider.Context context) {
        super(context);
        itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public boolean shouldRender(Reactor livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public @NotNull ReactorRenderState createRenderState() {
        return new ReactorRenderState();
    }

    @Override
    public void extractRenderState(Reactor entity, ReactorRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.entity = entity;
        reusedState.partialTick = partialTick;
        this.itemModelResolver.updateForNonLiving(reusedState.item,
                InitItems.Reactor_.asItem().getDefaultInstance(), ItemDisplayContext.FIXED, entity);
    }


    @Override
    public void submit(ReactorRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, net.minecraft.client.renderer.state.level.CameraRenderState camera) {
        HandlerClient.showOutline = true;
        HandlerClient.doPass = true;

        Reactor entity = renderState.entity;
        double x = Mth.lerp(renderState.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(renderState.partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(renderState.partialTick, entity.zOld, entity.getZ());
        poseStack.pushPose();
        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);
        {
            poseStack.pushPose();
            poseStack.scale(1,1,1);
            poseStack.translate(0,0.66f,0);
            renderState.item.submit(poseStack, collector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
            poseStack.popPose();
        }
        {
            float size = 0.25f;
            poseStack.pushPose();
            poseStack.translate(0,0.5,0);
            {
                poseStack.pushPose();
                poseStack.mulPose(Axis.XP.rotationDegrees(entity.tickCount));
                poseStack.mulPose(Axis.YP.rotationDegrees(entity.tickCount));
                poseStack.mulPose(Axis.ZP.rotationDegrees(entity.tickCount));
                poseStack.translate(-size, -size, -size);

                collector.submitCustomGeometry(poseStack, MRender.colorOutline,
                        (pose, bufferSource) -> {
                            Matrix4f mat = new Matrix4f(pose.pose());
                            renderCubeFace(mat, bufferSource, 6, Light.ARGB.color(255, 100, 255, 50), size);
                        });

                poseStack.popPose();
            }
            poseStack.popPose();
        }

        poseStack.popPose();
    }

    public void renderCubeFace(@NotNull Matrix4f matrix4f, @NotNull VertexConsumer vertexConsumer, int face, int color, float size) {
        float x = size;
        float y = size;
        float z = size;
        if (face > 0) { // 前面
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 1) { // 后面
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 2) { // 左面
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 3) { // 右面
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 4) { // 上面
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 5) { // 下面
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
    }
}




