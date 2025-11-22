package com.ytgld.chest_item;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.c.AttackEndComingRenderer;
import com.ytgld.chest_item.entity.c.EndComingRenderer;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestMenuScreen;
import com.ytgld.chest_item.other.ChestMenuTypes;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.DetectedVersion;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.internal.NeoForgeAdvancementProvider;
import net.neoforged.neoforge.common.data.internal.NeoForgeBlockTagsProvider;
import net.neoforged.neoforge.common.data.internal.NeoForgeItemTagsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mod(value = Chestitem.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Chestitem.MODID, value = Dist.CLIENT)
public class ChestitemClient{

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
