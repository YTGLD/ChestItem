package com.ytgld.chest_item;

import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.render.AbyssOrbRenderer;
import com.ytgld.chest_item.event.activated.EventHandler;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.key.ClientEvent;
import com.ytgld.chest_item.event.key.SINetworkHandler;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Chestitem.MODID)
public class Chestitem {
    public static final String MODID = "chest_item";
    public static final ResourceLocation POST = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
            "entity_outline");
    public Chestitem(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerPayloadHandler);

        Entitys.REGISTRY.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitItems.TabChestItem.CREATIVE_MODE_TABS.register(modEventBus);
        DataReg.REGISTRY.register(modEventBus);

        NeoForge.EVENT_BUS.register(new EventHandler());
        NeoForge.EVENT_BUS.register(new EventMain());

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void registerPayloadHandler(final RegisterPayloadHandlersEvent evt) {
        SINetworkHandler.register(evt.registrar("1.0"));
    }
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void RegisterRenderPipelinesEvent(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(Entitys.Abyss_Orb.get(), AbyssOrbRenderer::new);
        }
        @SubscribeEvent
        public static void setupClient(FMLClientSetupEvent evt) {
            NeoForge.EVENT_BUS.register(new ClientEvent());
        }
        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(Keys.KEY_MAPPING_LAZY_R);
        }
    }
}
