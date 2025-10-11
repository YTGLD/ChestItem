package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.outline.MFramebufferBlack;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderPipelines.*;

public abstract class MRender extends RenderType {

    public MRender(String name, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    public static final OutputStateShard outline2 = new OutputStateShard("set_outline2", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MFramebufferBlack framebuffer){
            if (framebuffer.chest_item$render_black()!=null) {
                framebuffer.chest_item$render_black().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                return framebuffer.chest_item$render_black();
            }
        }
        return Minecraft.getInstance().getMainRenderTarget();
    });
    public static final Function<ResourceLocation, RenderType> ENTITY_SHADOW_Outline= Util.memoize(
            p_414938_ -> {
                RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.builder()
                        .setTextureState(new RenderStateShard.TextureStateShard(p_414938_, false))
                        .setOutputState(ITEM_ENTITY_TARGET)
                        .setOverlayState(OVERLAY)
                        .setOutputState(outline2)
                        .createCompositeState(true);
                return create("item_entity_translucent_cull", 1536,
                        true, true,
                        RenderPipelines.ITEM_ENTITY_TRANSLUCENT_CULL, rendertype$compositestate);
            }
    );
    public static RenderType red(boolean isOutline){
        if (isOutline){
            return create("red1", 1536, false, false, RenderPs.BACK,
                    RenderType.CompositeState.builder().setOutputState(outline2).setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/red.png"), false).add(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/red.png"), false).build()).createCompositeState(false)
            );
        }
        return create("red2", 1536, false, false, RenderPs.TRANSLUCENT,
                RenderType.CompositeState.builder().setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/red.png"), false).add(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/red.png"), false).build()).createCompositeState(false)
        );

    }
    public static RenderType endBlack(boolean isOutline){
        if (isOutline){
            return create("end_gateway", 1536, false, false, RenderPs.TRANSLUCENT,
                    RenderType.CompositeState.builder().setOutputState(outline2).setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/red.png"), false).add(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/red.png"), false).build()).createCompositeState(false)
            );
        }
        return create("end_gateway", 1536, false, false, RenderPs.TRANSLUCENT,
                RenderType.CompositeState.builder().setTextureState(RenderStateShard.MultiTextureStateShard.builder().add(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/red.png"), false).add(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/red.png"), false).build()).createCompositeState(false)
        );
    }
    public static class RenderPs {

        public static final RenderPipeline.Snippet GUI_TEXTURED_SNIPPET_CI =
                RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                        .withVertexShader(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "core/position_tex_color"))
                        .withFragmentShader(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "core/position_tex_color"))
                        .withSampler("Sampler0")
                        .withBlend(new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        )).withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR,
                                VertexFormat.Mode.QUADS)
                        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).buildSnippet();
        public static final RenderPipeline GUI_TEXTURED =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).withBlend(new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        ))
                        .withLocation("pipeline/gui_textured").build());

        public static final RenderPipeline GUI_TEXTURED_CI =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET_CI).withBlend(new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        ))
                        .withLocation("pipeline/gui_textured_ci").build());

        public static final RenderPipeline ENTITY_OUTLINE_BLIT = RenderPipeline.builder().withLocation("pipeline/entity_outline_blit")
                .withVertexShader("core/screenquad").withFragmentShader("core/blit_screen").withSampler("InSampler"
                ).withBlend(new BlendFunction(
                        SourceFactor.SRC_ALPHA,
                        DestFactor.ONE,
                        SourceFactor.ONE,
                        DestFactor.ZERO
                )).withDepthWrite(false)
                .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                .withColorWrite(true,
                false).withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES).build();



        public static final RenderPipeline BACK =(RenderPipeline.builder(
                RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                        .withVertexShader(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                        .withFragmentShader(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                        .withSampler("Sampler0")
                        .withVertexFormat(DefaultVertexFormat.POSITION,
                                VertexFormat.Mode.QUADS).buildSnippet())
                .withLocation("pipeline/end_gateway").withBlend(new BlendFunction(
                        SourceFactor.SRC_ALPHA,
                        DestFactor.ONE,
                        SourceFactor.ONE,
                        DestFactor.ZERO
                ))
                .withShaderDefine("PORTAL_LAYERS", 16)
                .withCull(false)
                .build());
        public static final RenderPipeline TRANSLUCENT =(RenderPipeline.builder(
                        RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                                .withVertexShader(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                                .withFragmentShader(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                                .withSampler("Sampler0")
                                .withVertexFormat(DefaultVertexFormat.POSITION,
                                        VertexFormat.Mode.QUADS).buildSnippet())
                .withLocation("pipeline/end_gateway").withBlend(BlendFunction.TRANSLUCENT)
                .withShaderDefine("PORTAL_LAYERS", 16)
                .withCull(false)
                .build());

    }
}
