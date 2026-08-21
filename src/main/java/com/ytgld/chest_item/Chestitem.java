package com.ytgld.chest_item;

import com.ytgld.chest_item.config.ModLanguageProvider;
import com.ytgld.chest_item.crafting.ModRecipes;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.OppressionHandler;
import com.ytgld.chest_item.event.activated.EventHandler;
import com.ytgld.chest_item.event.key.ChestNetworkHandler;
import com.ytgld.chest_item.event.key.ClientEvent;
import com.ytgld.chest_item.event.key.UseCuriosHandler;
import com.ytgld.chest_item.event.loot.Loots;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ClientAttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ToolTipSpiritItem;
import com.ytgld.chest_item.items.memory.MemoryAttreg;
import com.ytgld.chest_item.items.memory.MemoryEvent;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.items.memory.TheMemoryDataHandler;
import com.ytgld.chest_item.items.memory.tooltip.BigTooltip;
import com.ytgld.chest_item.items.reinforced.ReinforcedAttreg;
import com.ytgld.chest_item.items.reinforced.ReinforcedDataHandler;
import com.ytgld.chest_item.items.reinforced.ReinforcedEvent;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import com.ytgld.chest_item.other.ChestMenuTypes;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import com.ytgld.chest_item.sounds.CISoundDefinitionsProvider;
import com.ytgld.chest_item.sounds.Sounds;
import com.ytgld.chest_item.tip.SkillEvent;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Mod(Chestitem.MODID)
public class Chestitem {
    public static final String MODID = "chest_item";
    public static final ResourceLocation POST_BLACK = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
            "black");
    public Chestitem(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::registerPayloadHandler);
        AttReg.REGISTRY.register(modEventBus);
        Effects.EFFECT_DEFERRED_REGISTER.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);
        InitItems.TabChestItem.CREATIVE_MODE_TABS.register(modEventBus);
        AttReg.ATTACHMENT_TYPES.register(modEventBus);
        DataReg.REGISTRY.register(modEventBus);
        Loots.LOOT.register(modEventBus);
        NeoForge.EVENT_BUS.register(new EventHandler());
        NeoForge.EVENT_BUS.register(new EventMain());
        modEventBus.addListener(this::onGatherData);
        ChestMenuTypes.register.register(modEventBus);
        NeoForge.EVENT_BUS.register(new SkillEvent());
        Particles.PARTICLE_TYPES.register(modEventBus);
        Entitys.REGISTRY.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ConfigC.fc);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.fc);
        Sounds.REGISTRY.register(modEventBus);
        TheMemoryDataHandler.ATTACHMENT_TYPES.register(modEventBus);
        MemoryItems.ITEMS.register(modEventBus);
        NeoForge.EVENT_BUS.register(new MemoryEvent());
        MemoryAttreg.REGISTRY.register(modEventBus);
        ClientAttReg.ATTACHMENT_TYPES.register(modEventBus);

        ModRecipes.SERIALIZERS.register(modEventBus);
        ModRecipes.TYPES.register(modEventBus);

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
            if (event.isWasDeath() && event.getOriginal().hasData(AttReg.itemRecord)) {
                event.getEntity().getData(AttReg.itemRecord).clear();
                event.getEntity().getData(AttReg.itemRecord)
                        .addAll(event.getOriginal().getData(AttReg.itemRecord))
                ;
            }
            if (event.isWasDeath() && event.getOriginal().hasData(ReinforcedDataHandler.reinforced)) {
                event.getEntity().getData(ReinforcedDataHandler.reinforced).clear();
                event.getEntity().getData(ReinforcedDataHandler.reinforced)
                        .addAll(event.getOriginal().getData(ReinforcedDataHandler.reinforced))
                ;
            }
        });
    }

    public void onGatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        Chestitem.BlockTagsProviderCO blockTags = new Chestitem.BlockTagsProviderCO(packOutput, lookupProvider, existingFileHelper);
        gen.addProvider(event.includeClient(),new ModLanguageProvider(packOutput));
        gen.addProvider(event.includeServer(), blockTags);
        gen.addProvider(event.includeServer(), new InitItems.TagsProvider(packOutput, lookupProvider, blockTags.contentsGetter(), existingFileHelper));
        gen.addProvider(
                event.includeClient(),
                new CISoundDefinitionsProvider(packOutput, existingFileHelper)
        );
    }
    private void registerPayloadHandler(final RegisterPayloadHandlersEvent evt) {
        ChestNetworkHandler.register(evt.registrar("1.0"));
        UseCuriosHandler.register(evt.registrar("1.0"));
        MysteriousMetal.register(evt);
        OppressionHandler.register(evt);
    }


    public static class  BlockTagsProviderCO extends BlockTagsProvider {

        public BlockTagsProviderCO(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, Chestitem.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {

        }
    }
    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void RegisterClientTooltipComponentFactoriesEvent(RegisterClientTooltipComponentFactoriesEvent event){
            event.register(SkillTooltip.class, Function.identity());
            event.register(BigTooltip.class, Function.identity());
            event.register(ToolTipSpiritItem.class, Function.identity());
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
