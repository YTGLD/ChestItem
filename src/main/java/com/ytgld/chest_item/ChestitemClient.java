package com.ytgld.chest_item;

import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestMenuScreen;
import com.ytgld.chest_item.other.ChestMenuTypes;
import com.ytgld.chest_item.renderer.BlackShieldRenderHandler;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.ShieldRenderHandler;
import com.ytgld.chest_item.renderer.particle.ColorPart;
import com.ytgld.chest_item.renderer.particle.FireBlock;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
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

    @SubscribeEvent
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(Particles.colorPart.get(), ColorPart.Provider::new);
        event.registerSpriteSet(Particles.FireBlock_.get(), FireBlock.Provider::new);
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
