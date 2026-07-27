package com.ytgld.chest_item;

import com.ytgld.chest_item.config.ModLanguageProvider;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.render.*;
import com.ytgld.chest_item.event.OppressionHandler;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.activated.EventHandler;
import com.ytgld.chest_item.event.key.ChestNetworkHandler;
import com.ytgld.chest_item.event.key.ClientEvent;
import com.ytgld.chest_item.event.key.UseCuriosHandler;
import com.ytgld.chest_item.event.loot.Loots;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.memory.MemoryAttreg;
import com.ytgld.chest_item.event.use.MemoryEvent;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.items.memory.TheMemoryDataHandler;
import com.ytgld.chest_item.items.memory.tooltip.BigTooltip;
import com.ytgld.chest_item.items.memory.tooltip.ImageTooltip;
import com.ytgld.chest_item.items.reinforced.ReinforcedAttreg;
import com.ytgld.chest_item.items.reinforced.ReinforcedDataHandler;
import com.ytgld.chest_item.event.use.ReinforcedEvent;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import com.ytgld.chest_item.other.ChestMenuTypes;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import com.ytgld.chest_item.sounds.CISoundDefinitionsProvider;
import com.ytgld.chest_item.sounds.Sounds;
import com.ytgld.chest_item.tip.SkillEvent;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.function.Function;

@Mod(Chestitem.MODID)
public class Chestitem {
    public static final String MODID = "chest_item";
    public static final Identifier POST_BLACK = Identifier.fromNamespaceAndPath(Chestitem.MODID,
            "black");
    public static final Identifier warpedPOST = Identifier.fromNamespaceAndPath(Chestitem.MODID,
            "warped");
    public static final Identifier decay = Identifier.fromNamespaceAndPath(Chestitem.MODID,
            "decay");

    public Chestitem(IEventBus modEventBus, Dist dist, ModContainer modContainer) {
        modEventBus.addListener(this::registerPayloadHandler);
        AttReg.REGISTRY.register(modEventBus);
        Effects.EFFECT_DEFERRED_REGISTER.register(modEventBus);
        Entitys.REGISTRY.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitItems.TabChestItem.CREATIVE_MODE_TABS.register(modEventBus);
        AttReg.ATTACHMENT_TYPES.register(modEventBus);
        DataReg.REGISTRY.register(modEventBus);
        Loots.LOOT.register(modEventBus);
        NeoForge.EVENT_BUS.register(new EventHandler());
        NeoForge.EVENT_BUS.register(new EventMain());
        ChestMenuTypes.register.register(modEventBus);
        Particles.PARTICLE_TYPES.register(modEventBus);
        Sounds.REGISTRY.register(modEventBus);
        TheMemoryDataHandler.ATTACHMENT_TYPES.register(modEventBus);
        MemoryItems.ITEMS.register(modEventBus);
        NeoForge.EVENT_BUS.register(new MemoryEvent());
        MemoryAttreg.REGISTRY.register(modEventBus);

        ReinforcedDataHandler.ATTACHMENT_TYPES.register(modEventBus);
        ReinforcedAttreg.REGISTRY.register(modEventBus);
        NeoForge.EVENT_BUS.register(new ReinforcedEvent());
        ReinforcedItems.ITEMS.register(modEventBus);

        NeoForge.EVENT_BUS.addListener(PlayerEvent.Clone.class, event -> {
            if (event.isWasDeath() && event.getOriginal().hasData(TheMemoryDataHandler.mStringSetData)) {
                event.getEntity().getData(TheMemoryDataHandler.mStringSetData).clear();
                event.getEntity().getData(TheMemoryDataHandler.mStringSetData)
                        .addAll(event.getOriginal().getData(TheMemoryDataHandler.mStringSetData))
                ;
            }

            if (event.isWasDeath() && event.getOriginal().hasData(AttReg.itemRecord)) {
                event.getEntity().getData(AttReg.itemRecord).clear();
                event.getEntity().getData(AttReg.itemRecord)
                        .addAll(event.getOriginal().getData(AttReg.itemRecord))
                ;
            }
            if (event.isWasDeath() && event.getOriginal().hasData(TheMemoryDataHandler.notActivated)) {
                event.getEntity().getData(TheMemoryDataHandler.notActivated).clear();
                event.getEntity().getData(TheMemoryDataHandler.notActivated)
                        .addAll(event.getOriginal().getData(TheMemoryDataHandler.notActivated))
                ;
            }
            if (event.isWasDeath() && event.getOriginal().hasData(TheMemoryDataHandler.counter)) {
                event.getEntity().setData(TheMemoryDataHandler.counter,event.getOriginal().getData(TheMemoryDataHandler.counter));
            }
            if (event.isWasDeath() && event.getOriginal().hasData(ReinforcedDataHandler.reinforced)) {
                event.getEntity().getData(ReinforcedDataHandler.reinforced).clear();
                event.getEntity().getData(ReinforcedDataHandler.reinforced)
                        .addAll(event.getOriginal().getData(ReinforcedDataHandler.reinforced))
                ;
            }
        });
        NeoForge.EVENT_BUS.register(new SkillEvent());

        modContainer.registerConfig(ModConfig.Type.CLIENT, ConfigC.fc);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.fc);

    }
    private void registerPayloadHandler(final RegisterPayloadHandlersEvent evt) {
        ChestNetworkHandler.register(evt.registrar("1.0"));
        UseCuriosHandler.register(evt.registrar("1.0"));
        MysteriousMetal.register(evt);
        OppressionHandler.register(evt);
    }
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void RegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event){
            event.register(SkillTooltip.class, Function.identity());
            event.register(BigTooltip.class, Function.identity());
            event.register(ImageTooltip.class, Function.identity());
        }
        @SubscribeEvent // on the mod event bus
        public static void gatherData(GatherDataEvent.Client event) {
            event.createProvider(CISoundDefinitionsProvider::new);
            event.createProvider(ModLanguageProvider::new);
        }
        @SubscribeEvent
        public static void RegisterRenderPipelinesEvent(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(Entitys.AttackEndComing_.get(), AttackEndComingRender::new);
            event.registerEntityRenderer(Entitys.EndComing_.get(), EndComingRender::new);
            event.registerEntityRenderer(Entitys.UnstableSpheres_.get(), UnstableSpheresRender::new);
            event.registerEntityRenderer(Entitys.LaserColumn_.get(), LaserColumnRender::new);
            event.registerEntityRenderer(Entitys.TheHyperplasia_.get(), TheHyperplasiaRender::new);
            event.registerEntityRenderer(Entitys.EvilMotherSpirit_.get(), EvilMotherSpiritRender::new);
            event.registerEntityRenderer(Entitys.ChaosCube_.get(), ChaosCubeRender::new);
        }
        @SubscribeEvent
        public static void setupClient(FMLClientSetupEvent evt) {
            NeoForge.EVENT_BUS.register(new ClientEvent());
        }
        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(Keys.KEY_MAPPING_LAZY_R);
            event.register(Keys.KEY_MAPPING_LAZY_C);
        }
    }
}
