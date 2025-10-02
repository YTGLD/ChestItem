package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.chest_item.entity.BlackVex;
import com.ytgld.chest_item.entity.render.layer.GlowingLayer;
import com.ytgld.chest_item.entity.render.model.BlackVexModel;
import com.ytgld.chest_item.entity.state.BlackVexRenderState;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.ResourceLocation;


public class BlackVexRenderer  extends MobRenderer<BlackVex, BlackVexRenderState , BlackVexModel> {
    private static final ResourceLocation VEX_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/illager/vex.png");
    private static final ResourceLocation VEX_CHARGING_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/illager/vex_charging.png");

    public BlackVexRenderer(EntityRendererProvider.Context p_174435_) {
        super(p_174435_, new BlackVexModel(p_174435_.bakeLayer(ModelLayers.VEX)), 0.3F);
        this.addLayer(new ItemInHandLayer<>(this));
        this.addLayer(new GlowingLayer<>(this,getTextureLocation(this.createRenderState())));
    }

    @Override
    public void render(BlackVexRenderState blackVexRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int p_115313_) {
        super.render(blackVexRenderState, poseStack, multiBufferSource, p_115313_);
    }
    @Override
    public ResourceLocation getTextureLocation(BlackVexRenderState p_364652_) {
        return p_364652_.isCharging ? VEX_CHARGING_LOCATION : VEX_LOCATION;
    }
    @Override
    public BlackVexRenderState  createRenderState() {
        return new BlackVexRenderState ();
    }

    @Override
    public void extractRenderState(BlackVex p_360574_, BlackVexRenderState  p_364312_, float p_362582_) {
        super.extractRenderState(p_360574_, p_364312_, p_362582_);
        ArmedEntityRenderState.extractArmedEntityRenderState(p_360574_, p_364312_, this.itemModelResolver);
        p_364312_.isCharging = p_360574_.isCharging();
        p_364312_.entity = p_360574_;
        p_364312_.partialTick = p_362582_;
    }
}

