package com.ytgld.chest_item;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.c.AttackEndComingRenderer;
import com.ytgld.chest_item.entity.c.EndComingRenderer;
import com.ytgld.chest_item.other.ChestMenuScreen;
import com.ytgld.chest_item.other.ChestMenuTypes;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.particle.ColorPart;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.io.IOException;
import java.util.Queue;

@Mod(value = Chestitem.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Chestitem.MODID, value = Dist.CLIENT)
public class ChestitemClient{

    public static final ResourceLocation Warped = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
            "shaders/post/warped.json");
    public static final ResourceLocation POST_Blood = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
            "shaders/post/entity_outline_blood.json");
    public ChestitemClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    public static void regMenu(RegisterMenuScreensEvent event){
        event.register(ChestMenuTypes.GENERIC_12.get(), ChestMenuScreen::new);
    }
    @SubscribeEvent
    public static void RegisterRenderPipelinesEvent(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(Entitys.AttackEndComing_.get(), AttackEndComingRenderer::new);
        event.registerEntityRenderer(Entitys.EndComing_.get(), EndComingRenderer::new);
    }
    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(Particles.colorPart.get(), ColorPart.Provider::new);
    }
    @SubscribeEvent
    public static void AfterParticles(RenderLevelStageEvent event){
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            var camPos = event.getCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();
            poseStack.pushPose();
            RenderType renderType = MRender.LIGHTNING;
            VertexConsumer consumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType);
            Minecraft.getInstance().particleEngine.iterateParticles(particle -> {
                if (particle instanceof ColorPart colorPart) {
                    poseStack.pushPose();
                    var offset = particle.getPos().subtract(camPos);
                    event.getPoseStack().translate(offset.x, offset.y, offset.z);
                    colorPart.setT(event.getPoseStack(),colorPart, consumer);
                    poseStack.popPose();
                }
            });
            Minecraft.getInstance().renderBuffers().bufferSource().endBatch(renderType);
            poseStack.popPose();
        }
    }

    @SubscribeEvent
    public static void EntityRenderersEvent(RegisterShadersEvent event) {
        try {

            event.registerShader(new ShaderInstance(event.getResourceProvider(),
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"position_tex_color"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), MRender::setShaderInstance_liveShaderInstance);

            event.registerShader(new ShaderInstance(event.getResourceProvider(),
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"position_tex_color_slowness"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), MRender::setLiveShaderInstance_slowness);
            event.registerShader(new ShaderInstance(event.getResourceProvider(),

                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"whirlpool"),
                    DefaultVertexFormat.POSITION_TEX_COLOR), MRender::setWhirlpool);

        }catch (IOException exception){
            exception.printStackTrace();
        }
    }



}
