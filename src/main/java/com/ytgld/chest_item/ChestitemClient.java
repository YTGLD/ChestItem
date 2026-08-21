package com.ytgld.chest_item;

import com.ytgld.chest_item.crafting.ModRecipeCache;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestMenuScreen;
import com.ytgld.chest_item.other.ChestMenuTypes;
import com.ytgld.chest_item.renderer.BlackShieldRenderHandler;
import com.ytgld.chest_item.renderer.ShieldRenderHandler;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import com.ytgld.chest_item.renderer.model.BigGlowingModel;
import com.ytgld.chest_item.renderer.model.CIItemFeatureRenderer;
import com.ytgld.chest_item.renderer.model.WarpModel;
import com.ytgld.chest_item.renderer.particle.ColorPart;
import com.ytgld.chest_item.renderer.particle.FireBlock;
import com.ytgld.chest_item.renderer.particle.OrbPart;
import com.ytgld.chest_item.renderer.particle.SwordEnergy;
import com.ytgld.chest_item.renderer.particle.evilmother.ColorPartEvil;
import com.ytgld.chest_item.renderer.particle.evilmother.CubeEvil;
import com.ytgld.chest_item.renderer.particle.evilmother.EvilTailing;
import com.ytgld.chest_item.renderer.particle.evilmother.OrbPartEvil;
import com.ytgld.chest_item.renderer.particle.has_opt.ChestParticle;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import com.ytgld.chest_item.renderer.particle.sword.SwordShadow1;
import com.ytgld.chest_item.renderer.particle.sword.SwordShadow2;
import com.ytgld.chest_item.renderer.particle.sword.SwordShadow3;
import com.ytgld.chest_item.renderer.particle.sword.SwordShadow4;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(value = Chestitem.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Chestitem.MODID, value = Dist.CLIENT)
public class ChestitemClient{
    public ChestitemClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
    @SubscribeEvent
    public static void ItemTooltipEvent(ClientTickEvent.Pre event){
        EventMain.time++;
    }
    @SubscribeEvent
    public static void registerFactories(ViewportEvent.RenderFog event) {
        if (event.getCamera().entity() instanceof Player player){
            float number = (float) player.getAttributeValue(AttReg.chaos_consciousness);
            if (number > 0) {
                event.setFarPlaneDistance(event.getFarPlaneDistance() * number);
            }
        }
    }
    @SubscribeEvent
    public static void clientTickEvent(ClientTickEvent.Pre event) {
        ShieldRenderHandler.tick(event);
        BlackShieldRenderHandler.tick(event);
        BlackParticlesAdd.tick();
    }
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, Identifier.fromNamespaceAndPath(Chestitem.MODID,"pain_shield"),
                (guiGraphics,tracker)->ShieldRenderHandler.renderShield(guiGraphics));
        event.registerAbove(VanillaGuiLayers.FOOD_LEVEL, Identifier.fromNamespaceAndPath(Chestitem.MODID,"black_shield"),
                (guiGraphics,tracker)-> BlackShieldRenderHandler.renderShield(guiGraphics));
    }
    @SubscribeEvent
    public static void regMenu(RegisterMenuScreensEvent event){
        event.register(ChestMenuTypes.GENERIC_12.get(), ChestMenuScreen::new);
    }
    @SubscribeEvent
    public static void registerItemModels(RegisterItemModelsEvent event) {
        event.register(Identifier.fromNamespaceAndPath(Chestitem.MODID,"model"), BigGlowingModel.Unbaked.MAP_CODEC);
        event.register(Identifier.fromNamespaceAndPath(Chestitem.MODID,"warp"), WarpModel.Unbaked.MAP_CODEC);
    }
    @SubscribeEvent
    public static void RegisterFeatureRenderersEvent(RegisterFeatureRenderersEvent event) {
        event.register(CIItemFeatureRenderer.TYPE,new CIItemFeatureRenderer());
    }


//    private static final ContextKey<Integer> integerContextKey = new ContextKey<>(Identifier.fromNamespaceAndPath(Chestitem.MODID, "slime"));
//    @SubscribeEvent
//    public static void RegisterRenderStateModifiersEvent(RegisterRenderStateModifiersEvent event ){
//        event.registerAvatarEntityModifier(new AvatarRenderStateModifier() {
//            @Override
//            public <T extends Avatar & ClientAvatarEntity> void accept(T avatar, AvatarRenderState renderState) {
//                renderState.setRenderData(integerContextKey,avatar.getData(AttReg.slime.get()));
//            }
//        });
//    }
//    @SubscribeEvent
//    public static <T extends Avatar & ClientAvatarEntity> void regRenderPlayerEvent(RenderPlayerEvent.Pre<T> event) {
//        var renderData = event.getRenderState().getRenderData(integerContextKey);
//        float size = event.getRenderState().scale;
//        if (renderData != null) {
//            int slime = renderData;
//            if (slime > 0) {
//                PoseStack stack = event.getPoseStack();
//                stack.pushPose();
//                addPlayer(size,stack,new Vec3(1,-1.5f,1),event);
//                stack.popPose();
//                if (slime > 1) {
//                    stack.pushPose();
//                    addPlayer(size,stack,new Vec3(1,-1.5f,-1),event);
//                    stack.popPose();
//                }
//                if (slime > 2) {
//                    stack.pushPose();
//                    addPlayer(size,stack,new Vec3(-1,-1.5f,1),event);
//                    stack.popPose();
//                }
//                if (slime > 3) {
//                    stack.pushPose();
//                    addPlayer(size,stack,new Vec3(-1,-1.5f,-1),event);
//                    stack.popPose();
//                }
//            }
//        }
//    }
//    private static<T extends Avatar & ClientAvatarEntity>  void addPlayer(float size,PoseStack stack,
//                                                                          Vec3 vec3 ,
//                                                                          RenderPlayerEvent.Pre<T> event){
//
//        stack.mulPose(Axis.ZN.rotationDegrees(180));
//        stack.scale(size,size,size);
//        stack.translate(vec3.x * size, vec3.y, vec3.z * size);
//        stack.mulPose(Axis.YP.rotationDegrees(180 + event.getRenderState().yRot));
//
//        event.getSubmitNodeCollector().submitModel(event.getRenderer().getModel(),
//                event.getRenderState(),
//                event.getPoseStack(), RenderTypes.entityCutout(event.getRenderer().getTextureLocation(event.getRenderState())),
//                255, OverlayTexture.NO_OVERLAY, 0x00000000, null);
//
//    }
    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(Particles.COLOR_PART.get(), ColorPart.Provider::new);
        event.registerSpriteSet(Particles.FireBlock_.get(), FireBlock.Provider::new);
        event.registerSpriteSet(Particles.orbAPart.get(), OrbPart.Provider::new);

        event.registerSpriteSet(Particles.orbAPart_evil.get(), OrbPartEvil.Provider::new);
        event.registerSpriteSet(Particles.colorPart_evil.get(), ColorPartEvil.Provider::new);
        event.registerSpriteSet(Particles.cube_evil.get(), CubeEvil.Provider::new);
        event.registerSpriteSet(Particles.evil_tailing.get(), EvilTailing.Provider::new);

        event.registerSpriteSet(Particles.SwordEnergyOption_.get(), SwordEnergy.Provider::new);
        event.registerSpriteSet(Particles.colorOption.get(), ChestParticle.Provider::new);

        event.registerSpriteSet(Particles.sword_shadow_1.get(), SwordShadow1.Provider::new);
        event.registerSpriteSet(Particles.sword_shadow_2.get(), SwordShadow2.Provider::new);
        event.registerSpriteSet(Particles.sword_shadow_3.get(), SwordShadow3.Provider::new);
        event.registerSpriteSet(Particles.sword_shadow_4.get(), SwordShadow4.Provider::new);

    }
    @SubscribeEvent
    public static void RegisterKeyMappingsEvent(RegisterKeyMappingsEvent event){
        event.registerCategory(Keys.chest);
    }
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        event.createProvider(InitItems.TagsProvider::new);
    }


    @SubscribeEvent
    public static void event(RecipesReceivedEvent event) {
        ModRecipeCache.event(event);
    }

}
