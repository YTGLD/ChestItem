package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.Tombstone;
import com.ytgld.chest_item.entity.state.TombstoneRenderState;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.light.LightRenders;
import com.ytgld.chest_item.renderer.warp.ChestItemFrameGraph;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

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
        Tombstone entity = renderState.entity;

        LightRenders.addLight(
                entity.position().x,entity.position().y,entity.position().z,
                24,
                235/255f,100/255f,245/255f,
                1);

        double x = Mth.lerp(renderState.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(renderState.partialTick, entity.yOld , entity.getY() );
        double z = Mth.lerp(renderState.partialTick, entity.zOld, entity.getZ());
        poseStack.pushPose();
        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);
        poseStack.rotate(Axis.YP.rotation(entity.getId()));
        {
            poseStack.pushPose();
            poseStack.scale(2,2,2);
            poseStack.translate(0,0.5f,0);
            renderState.item.submit(poseStack, collector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
            poseStack.popPose();
        }
        poseStack.popPose();
    }

}





