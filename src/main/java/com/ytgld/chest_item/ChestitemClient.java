package com.ytgld.chest_item;

import com.ibm.icu.text.MessagePattern;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestMenuScreen;
import com.ytgld.chest_item.other.ChestMenuTypes;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.RendererFarm;
import com.ytgld.chest_item.renderer.particle.ColorPart;
import com.ytgld.chest_item.renderer.particle.IParticleEngine;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

@Mod(value = Chestitem.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Chestitem.MODID, value = Dist.CLIENT)
public class ChestitemClient{
    public ChestitemClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    public static void regMenu(RegisterMenuScreensEvent event){
        event.register(ChestMenuTypes.GENERIC_12.get(), ChestMenuScreen::new);
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
                                HandlerClient.showOutline = true;
                                HandlerClient.doPass = true;
                                var offset = colorPart.getPos().subtract(event.getLevelRenderState().cameraRenderState.pos);
                                event.getPoseStack().pushPose();
                                RenderType renderType = MRender.colorOutline(true);
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
