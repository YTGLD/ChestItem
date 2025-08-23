package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.entity.EndSpiral;
import com.ytgld.chest_item.entity.state.EndSpiralRenderState;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Spiral;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EndSpiralRender <T extends EndSpiral> extends net.minecraft.client.renderer.entity.EntityRenderer<T, EndSpiralRenderState> {
    public EndSpiralRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(EndSpiralRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        float partialTick = renderState.partialTick;
        double x = Mth.lerp(partialTick, renderState.entity.xOld, renderState.entity.getX());
        double y = Mth.lerp(partialTick, renderState.entity.yOld, renderState.entity.getY());
        double z = Mth.lerp(partialTick, renderState.entity.zOld, renderState.entity.getZ());
        poseStack.pushPose();
        poseStack.translate(renderState.entity.getX()-x, renderState.entity.getY()-y,renderState.entity.getZ() -z);
        poseStack.mulPose(Axis.YN.rotationDegrees(-renderState.entity.tickCount*1.555f));
        float s = 0.4f;
        float l = renderState.entity.tickCount / 20f;
        if (l > s) {
            l = s;
        }
        poseStack.scale(l,l,l);
        for (int i = 0; i < 4; i++) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YN.rotationDegrees(90*i));
            new Spiral(poseStack,new Vec3(0,0,0),bufferSource.getBuffer(MRender.ENTITY_SHADOW_Outline.apply(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                    "textures/withe.png"))));
            poseStack.popPose();
        }


        poseStack.popPose();

    }

    @Override
    public @NotNull EndSpiralRenderState createRenderState() {
        return new EndSpiralRenderState();
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void extractRenderState(T p_entity, EndSpiralRenderState reusedState, float partialTick) {
        reusedState.entity = p_entity;
        reusedState.partialTick = partialTick;
    }
}


