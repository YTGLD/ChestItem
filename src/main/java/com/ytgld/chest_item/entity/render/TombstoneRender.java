package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.Tombstone;
import com.ytgld.chest_item.entity.state.TombstoneRenderState;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.light.LightRenders;
import com.ytgld.chest_item.renderer.outline.ISubmitReactorGlow;
import com.ytgld.chest_item.renderer.visual.Visual;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class TombstoneRender extends EntityRenderer<Tombstone, TombstoneRenderState> {
    private final ItemModelResolver itemModelResolver;
    public TombstoneRender(EntityRendererProvider.Context context) {
        super(context);
        itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public boolean shouldRender(Tombstone livingEntity, Frustum camera, double camX, double camY, double camZ, float partialTicks) {
        return true;
    }

    @Override
    public @NotNull TombstoneRenderState createRenderState() {
        return new TombstoneRenderState();
    }

    @Override
    public void extractRenderState(Tombstone entity, TombstoneRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.entity = entity;
        reusedState.partialTick = partialTick;
        this.itemModelResolver.updateForNonLiving(reusedState.item,
                InitItems.Tombstone_client.asItem().getDefaultInstance(), ItemDisplayContext.FIXED, entity);
    }


    @Override
    public void submit(TombstoneRenderState renderState, PoseStack poseStack, SubmitNodeCollector collector, net.minecraft.client.renderer.state.level.CameraRenderState camera) {
        HandlerClient.setShowRenderLight(true);
        HandlerClient.setShowRenderVisualFire(true);
        HandlerClient.setShowOutline(true);
        HandlerClient.setDoPass(true);

        Tombstone entity = renderState.entity;
        LightRenders.addLight(
                entity.position().x,entity.position().y,entity.position().z,
                36,
                0.8f,0.1f,1,
                1);

        Visual.putVisualVec3(new Visual.VisualVec3(new Vec3(entity.position().x,entity.position().y,entity.position().z)
        ,1,235/200f,100/200f,245/200f));

        double x = Mth.lerp(renderState.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(renderState.partialTick, entity.yOld , entity.getY() );
        double z = Mth.lerp(renderState.partialTick, entity.zOld, entity.getZ());


        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);

        ((ISubmitReactorGlow)collector).chest_item$submitReactorGlow(poseStack, MRender.colorOutline, (pose, bufferSource) -> {
            int a = Math.max(0,255 - entity.onGroundTime * 15);
            if (a > 0) {
                renderSphere1(pose, bufferSource, Light.ARGB.color(a,235,100,245),
                        (float) Math.sqrt(entity.onGroundTime * 3f));
            }
        });


        poseStack.pushPose();
        poseStack.rotate(Axis.YP.rotation(entity.getId()));
        poseStack.rotate(Axis.XP.rotationDegrees(6.75f));
        poseStack.rotate(Axis.ZP.rotationDegrees(6.75f));
        {
            poseStack.pushPose();
            poseStack.scale(3, 3, 3);
            poseStack.translate(0, 0.5f, 0);
            renderState.item.submit(poseStack, collector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
            poseStack.popPose();
        }
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
}






