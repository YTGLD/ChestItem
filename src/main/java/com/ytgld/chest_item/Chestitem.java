package com.ytgld.chest_item;

import com.ytgld.chest_item.event.EventHandler;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.key.ClientEvent;
import com.ytgld.chest_item.event.key.SINetworkHandler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(Chestitem.MODID)
public class Chestitem {
    public static final String MODID = "chest_item";
    public static final ResourceLocation POST = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
            "entity_outline");
    public Chestitem(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerPayloadHandler);


        InitItems.ITEMS.register(modEventBus);
        InitItems.TabChestItem.CREATIVE_MODE_TABS.register(modEventBus);
        DataReg.REGISTRY.register(modEventBus);

        NeoForge.EVENT_BUS.register(new EventHandler());



        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    private void registerPayloadHandler(final RegisterPayloadHandlersEvent evt) {
        SINetworkHandler.register(evt.registrar("1.0"));
    }
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
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
