package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderPipelines.*;

public abstract class MRender extends RenderType {

    public MRender(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
    public static final OutputStateShard OUTLINE_TARGET = new OutputStateShard("set_outline", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MFramebuffer framebuffer){
            if (framebuffer.chest_item$render()!=null) {
                framebuffer.chest_item$render().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                return framebuffer.chest_item$render();
            }
        }
        return Minecraft.getInstance().getMainRenderTarget();
    });
    public static final Function<ResourceLocation, RenderType> OUTLINE = Util.memoize(
            p_414952_ -> {
                CompositeState rendertype$compositestate = CompositeState.builder()
                        .setTextureState(new TextureStateShard(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                ""), false))
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .setOutputState(OUTLINE_TARGET)
                        .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                        .createCompositeState(false);
                return create("entity_shadow", 1536, false,
                        false, RenderPs.ENTITY_SHADOW, rendertype$compositestate);
            }
    );

    public static final RenderType CHEST_END = create(
            "end_chest",
            1536,
            false,
            false,
            RenderPs.BACK,
            RenderType.CompositeState.builder()
                    .setTextureState(
                            RenderStateShard.MultiTextureStateShard.builder()
                                    .add(TheEndPortalRenderer.END_SKY_LOCATION, false)
                                    .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false)
                                    .build()
                    )
                    .createCompositeState(false)
    );

    public static class RenderPs {

        public static final RenderPipeline BACK =(RenderPipeline.builder(
                RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                        .withVertexShader(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                        .withFragmentShader(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                        .withSampler("Sampler0")
                        .withVertexFormat(DefaultVertexFormat.POSITION,
                                VertexFormat.Mode.QUADS).buildSnippet())
                .withLocation("pipeline/end_gateway")
                .withShaderDefine("PORTAL_LAYERS", 16)
                .withCull(false)
                .withBlend(BlendFunction.TRANSLUCENT)
                .build());

        public static final RenderPipeline ENTITY_SHADOW = (RenderPipeline.builder(MATRICES_FOG_SNIPPET).withLocation("pipeline/entity_shadow")
                .withVertexShader("core/rendertype_entity_shadow").withFragmentShader("core/rendertype_entity_shadow")
                .withSampler("Sampler0").withBlend(BlendFunction.TRANSLUCENT).withDepthWrite(false)
                .withCull(false)
                .withVertexFormat(DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS).build());

    }
}
