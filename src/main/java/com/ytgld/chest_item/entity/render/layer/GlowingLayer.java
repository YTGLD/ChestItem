package com.ytgld.chest_item.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class GlowingLayer <S extends EntityRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {

    private final ResourceLocation location;

    public GlowingLayer(RenderLayerParent<S, M> renderer, ResourceLocation location) {
        super(renderer);
        this.location = location;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, S s, float v, float v1) {
        this.getParentModel().renderToBuffer(poseStack, multiBufferSource.getBuffer(MRender.ENTITY_SHADOW_Outline.apply(
                        location)), 20, OverlayTexture.NO_OVERLAY);;

    }
}
