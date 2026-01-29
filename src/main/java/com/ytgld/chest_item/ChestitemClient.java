package com.ytgld.chest_item;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestMenuScreen;
import com.ytgld.chest_item.other.ChestMenuTypes;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.model.IAvatarRenderState;
import com.ytgld.chest_item.renderer.particle.ColorPart;
import com.ytgld.chest_item.renderer.particle.IParticleEngine;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Map;
import java.util.Queue;

@Mod(value = Chestitem.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Chestitem.MODID, value = Dist.CLIENT)
public class ChestitemClient{
    public ChestitemClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    public static void registerFactories(ViewportEvent.RenderFog event) {
        if (event.getCamera().entity() instanceof Player player){
            float number = (float) player.getAttributeValue(AttReg.chaos_consciousness);
            if (number > 0) {
                event.setFarPlaneDistance(event.getFarPlaneDistance() * number);
                event.setNearPlaneDistance(event.getNearPlaneDistance() * number);
            }
        }
    }
    @SubscribeEvent
    public static void regMenu(RegisterMenuScreensEvent event){
        event.register(ChestMenuTypes.GENERIC_12.get(), ChestMenuScreen::new);
    }
    @SubscribeEvent
    public static <T extends Avatar & ClientAvatarEntity> void regMenu(RenderPlayerEvent.Pre<T> event) {
        if (Minecraft.getInstance().player instanceof Player living) {
            int value = (int) ((float) living.getData(AttReg.attachmentTypeBLOOD_Model));
            if (value>0) {
                HandlerClient.doPass = true;
                HandlerClient.showOutline = true;
                event.getSubmitNodeCollector().submitCustomGeometry(event.getPoseStack(), MRender.colorOutline(false), (pose, var) -> {
                            float s = value * 0.1f;
                            HandlerClient.renderBlood(pose, (float) Math.sin(living.tickCount / 10f) / 7f,
                                    new Vec3(0, 2.25 + s, 0),
                                    s, var);
                        }
                );
                event.getSubmitNodeCollector().submitCustomGeometry(event.getPoseStack(), MRender.colorOutline(true), (pose, var) -> {
                    float s = value * 0.1f;
                    HandlerClient.renderBlood(pose, (float) Math.sin(living.tickCount / 10f) / 7f,
                            new Vec3(0, 2.25 + s, 0),
                            s, var);
                });
            }
        }
    }
    public static Queue<?> iterateParticles(Map<ParticleRenderType, ParticleGroup<?>> map) {
        for (ParticleRenderType renderType : map.keySet()){
            if (renderType != ParticleRenderType.NO_RENDER){
                ParticleGroup<?> particleGroup = map.get(renderType);
                return particleGroup.getAll();
            }
        }
        return null;
    }
    @SubscribeEvent
    public static void AfterParticles(RenderLevelStageEvent.AfterParticles event){
        if (Minecraft.getInstance().particleEngine instanceof IParticleEngine iParticleEngine) {
            for (ParticleRenderType particlerendertype : iParticleEngine.cI1_21_11$particles().keySet()) {
                if (particlerendertype != ParticleRenderType.NO_RENDER) {
                    Queue<?> q = iterateParticles(iParticleEngine.cI1_21_11$particles());
                    if (q != null) {
                        for (Object o : q){
                            if (o instanceof ColorPart colorPart) {
                                var offset = colorPart.getPos().subtract(event.getLevelRenderState().cameraRenderState.pos);
                                event.getPoseStack().pushPose();
                                RenderType renderType = MRender.colorOutline(false);
                                VertexConsumer consumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
                                event.getPoseStack().translate(offset.x, offset.y, offset.z);

                                colorPart.setT(event.getPoseStack(),colorPart, consumer);

                                Minecraft.getInstance().renderBuffers().bufferSource().endBatch(renderType);
                                event.getPoseStack().popPose();
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(Particles.colorPart.get(), ColorPart.Provider::new);
    }
    @SubscribeEvent
    public static void RegisterKeyMappingsEvent(RegisterKeyMappingsEvent event){
        event.registerCategory(Keys.chest);
    }
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        event.createProvider(InitItems.TagsProvider::new);
    }
}
