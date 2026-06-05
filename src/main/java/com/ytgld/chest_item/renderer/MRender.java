package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.renderer.outline.ILevelRendererWarped;
import com.ytgld.chest_item.renderer.outline.IWarped;
import com.ytgld.chest_item.renderer.outline.MFramebufferBlack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderPipelines.*;

public abstract class MRender {

    public static final OutputTarget outline2 = new OutputTarget("set_outline2", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MFramebufferBlack framebuffer){
            if (framebuffer.chest_item$render_black()!=null) {
                GlStateManager._enableDepthTest();
                framebuffer.chest_item$render_black().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                return framebuffer.chest_item$render_black();
            }
        }
        return Minecraft.getInstance().getMainRenderTarget();
    });
    public static final OutputTarget warped = new OutputTarget("warped", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof ILevelRendererWarped levelRendererWarped){
            if (levelRendererWarped.chest_item$WarpedMixin()!=null) {
                GlStateManager._enableDepthTest();
                levelRendererWarped.chest_item$WarpedMixin().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                return levelRendererWarped.chest_item$WarpedMixin();
            }
        }
        return Minecraft.getInstance().getMainRenderTarget();
    });
//    public static final OutputTarget sScreenWarped = new OutputTarget("screen_warped", () -> {
//        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
//        if (rendertarget instanceof IWarped iWarped){
//            if (iWarped.chest_item$IWarped()!=null) {
//                iWarped.chest_item$IWarped().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
//                return iWarped.chest_item$IWarped();
//            }
//        }
//        return Minecraft.getInstance().getMainRenderTarget();
//    });

    private static Function<Identifier, RenderType> ITEM_TRANSLUCENT(OutputTarget outline2) {
        return Util.memoize((texture) -> {
            RenderSetup state = RenderSetup.builder(RenderPipelines.ITEM_TRANSLUCENT)
                    .withTexture("Sampler0", texture)
                    .setOutputTarget(outline2).
                    useLightmap().affectsCrumbling().sortOnUpload().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
            return RenderType.create("item_translucent", state);
        });
    }
    public static RenderType itemTranslucent(Identifier texture,OutputTarget outline2) {
        return ITEM_TRANSLUCENT(outline2).apply(texture);
    }

//    public static RenderType warpedScreen(){
//        return RenderType.create("warped",
//                RenderSetup.builder(RenderPipelines.LIGHTNING).setOutputTarget(sScreenWarped).sortOnUpload().createRenderSetup());
//    }
    public static RenderType red(boolean isOutline){
        return endBlack(isOutline);
    }
    public static RenderType colorOutline(boolean isOutline){
        if (isOutline){
            return RenderType.create(
                    "lightning", RenderSetup.builder(RenderPipeline.builder(MATRICES_FOG_SNIPPET).withLocation("pipeline/lightning")
                                    .withVertexShader("core/rendertype_lightning").withFragmentShader("core/rendertype_lightning")
                                    .withColorTargetState(new ColorTargetState(BlendFunction.LIGHTNING))
                                    .withDepthStencilState(DepthStencilState.DEFAULT)
                                    .withVertexFormat(DefaultVertexFormat.POSITION_COLOR,
                                            VertexFormat.Mode.QUADS).withCull(false).build())
                            .setOutputTarget(outline2).sortOnUpload().createRenderSetup()
            );
        }
        return RenderType.create(
                "lightning",
                RenderSetup.builder(RenderPipeline.builder(MATRICES_FOG_SNIPPET).withLocation("pipeline/lightning").
                                withVertexShader("core/rendertype_lightning").withFragmentShader("core/rendertype_lightning")
                                .withColorTargetState(new ColorTargetState(BlendFunction.LIGHTNING)
                                ).withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS)
                                .withDepthStencilState(DepthStencilState.DEFAULT)
                                .withDepthStencilState(DepthStencilState.DEFAULT).withCull(false).build()
       )
                        .sortOnUpload().createRenderSetup());

    }

    public static RenderType colorOutlineLines(boolean isOutline){
        if (isOutline){
           return  RenderType.create(
                   "lines",
                   RenderSetup.builder(RenderPipeline.builder(RenderPipeline.builder(MATRICES_FOG_SNIPPET, GLOBALS_SNIPPET)
                                   .withVertexShader("core/rendertype_lines")
                                   .withFragmentShader("core/rendertype_lines")
                                   .withColorTargetState(new ColorTargetState(new BlendFunction(SourceFactor.SRC_ALPHA,
                                           DestFactor.ONE,
                                           SourceFactor.ONE,
                                           DestFactor.ZERO)))
                                   .withCull(false)
                                   .withVertexFormat(DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH,
                                           VertexFormat.Mode.LINES).withDepthStencilState(DepthStencilState.DEFAULT)
                                   .buildSnippet()).withLocation("pipeline/lines").build())
                           .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                           .setOutputTarget(outline2)
                           .createRenderSetup());
        }
        return  RenderType.create(
                "lines",
                RenderSetup.builder(RenderPipeline.builder(RenderPipeline.builder(MATRICES_FOG_SNIPPET, GLOBALS_SNIPPET)
                                .withVertexShader("core/rendertype_lines")
                                .withFragmentShader("core/rendertype_lines")
                                .withColorTargetState(new ColorTargetState(new BlendFunction(SourceFactor.SRC_ALPHA,
                                        DestFactor.ONE,
                                        SourceFactor.ONE,
                                        DestFactor.ZERO)))
                                .withCull(false)
                                .withVertexFormat(DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH,
                                        VertexFormat.Mode.LINES).withDepthStencilState(DepthStencilState.DEFAULT)
                                .buildSnippet()).withLocation("pipeline/lines").build())
                        .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                        .createRenderSetup());
    }
    public static RenderType endBlack(boolean isOutline){
        if (isOutline){
            return RenderType.create(
                    "end_gateway",
                    RenderSetup.builder(RenderPs.BACK).setOutputTarget(outline2)
                            .withTexture("Sampler0", Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/red_all.png"))
                            .withTexture("Sampler1", Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/red_all.png"))
                            .createRenderSetup());
        }
        return RenderType.create(
                "end_gateway",
                RenderSetup.builder(RenderPs.TRANSLUCENT)
                        .withTexture("Sampler0", Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/red.png"))
                        .withTexture("Sampler1", Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/red.png"))
                        .createRenderSetup());
    }
    public static RenderType line(boolean isOutline) {
        if (isOutline) {
            return RenderType.create(
                    "lines",
                    RenderSetup.builder((RenderPipeline.builder(RenderPipeline.builder(MATRICES_FOG_SNIPPET, GLOBALS_SNIPPET)
                                    .withVertexShader("core/rendertype_lines")
                                    .withFragmentShader("core/rendertype_lines")
                                    .withColorTargetState(new ColorTargetState(new BlendFunction(SourceFactor.SRC_ALPHA,
                                            DestFactor.ONE,
                                            SourceFactor.ONE,
                                            DestFactor.ZERO))).withCull(false)
                                    .withCull(false).withVertexFormat(DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH,
                                            VertexFormat.Mode.LINES).withDepthStencilState(DepthStencilState.DEFAULT).buildSnippet()).withLocation("pipeline/lines").build()))
                            .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                            .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET).setOutputTarget(outline2)
                            .createRenderSetup()
            );
        }
        return RenderType.create(
                "lines",
                RenderSetup.builder((RenderPipeline.builder(RenderPipeline.builder(MATRICES_FOG_SNIPPET, GLOBALS_SNIPPET)
                                .withVertexShader("core/rendertype_lines")
                                .withFragmentShader("core/rendertype_lines")
                                .withColorTargetState(new ColorTargetState(new BlendFunction(SourceFactor.SRC_ALPHA,
                                        DestFactor.ONE,
                                        SourceFactor.ONE,
                                        DestFactor.ZERO))).withCull(false)
                                .withCull(false).withVertexFormat(DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH,
                                        VertexFormat.Mode.LINES).withDepthStencilState(DepthStencilState.DEFAULT).buildSnippet()).withLocation("pipeline/lines").build()))
                        .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                        .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                        .createRenderSetup());
    }
    public static class RenderPs {

        public static RenderPipeline part =   RenderPipeline.builder(PARTICLE_SNIPPET).withCull(false).withLocation("pipeline/translucent_particle")
                .withDepthStencilState(DepthStencilState.DEFAULT)
                        .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT)).build();

        public static final RenderPipeline.Snippet GUI_TEXTURED_SNIPPET = RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET)
                .withVertexShader("core/position_tex_color")
                .withFragmentShader("core/position_tex_color")
                .withSampler("Sampler0")
                .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                .withDepthStencilState(DepthStencilState.DEFAULT)
                .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
                .buildSnippet();
        public static final RenderPipeline GUI_TEXTURED =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).withColorTargetState(new ColorTargetState(new BlendFunction(SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO)))
                        .withDepthStencilState(DepthStencilState.DEFAULT)
                        .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/gui_textured")).build());

        public static final RenderPipeline GUI_TEXTURED_BLACK_BlendFunction =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                        .withDepthStencilState(DepthStencilState.DEFAULT)
                        .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/gui_textured")).build());

        public static final RenderPipeline  TRANSLUCENT_PARTICLE = (RenderPipeline.builder( RenderPipeline.builder(MATRICES_FOG_SNIPPET)
                        .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/particle"))
                        .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/particle"))
                        .withSampler("Sampler0").withSampler("Sampler2")
                        .withCull(false)
                        .withVertexFormat(DefaultVertexFormat.PARTICLE, VertexFormat.Mode.QUADS)
                        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false)).buildSnippet())
                .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/translucent_particle")).
                withColorTargetState(new ColorTargetState(
                        new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO)
                )).build());

        public static final RenderPipeline ENTITY_OUTLINE_BLIT = (
                RenderPipeline.builder()
                        .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/entity_outline_blit"))
                        .withVertexShader("core/screenquad")
                        .withFragmentShader("core/blit_screen")
                        .withSampler("InSampler")
                        .withColorTargetState(new ColorTargetState(new BlendFunction(SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO)))
                        .withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
                        .withCull(false)
                        .build()
        );
//        public static final RenderPipeline sScreenWarped = (
//                RenderPipeline.builder()
//                        .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/screen_warped"))
//                        .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/screenquad"))
//                        .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"post/box_blur"))
//                        .withSampler("InSampler")
//                        .withUniform("WarpConfig",UniformType.UNIFORM_BUFFER)
//                        .withVertexFormat(DefaultVertexFormat.EMPTY, VertexFormat.Mode.TRIANGLES)
//                        .build()
//        );


        public static final RenderPipeline BACK =(RenderPipeline.builder(
                RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                        .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                        .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                        .withSampler("Sampler0")
                        .withVertexFormat(DefaultVertexFormat.POSITION,
                                VertexFormat.Mode.QUADS).buildSnippet())
                .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/end_gateway")).withColorTargetState(new ColorTargetState(
                        new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        )
                ))
                .withDepthStencilState(DepthStencilState.DEFAULT)
                .withShaderDefine("PORTAL_LAYERS", 16)
                .withCull(false)
                .build());
        public static final RenderPipeline TRANSLUCENT =(RenderPipeline.builder(
                        RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                                .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                                .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                                .withSampler("Sampler0")
                                .withVertexFormat(DefaultVertexFormat.POSITION,
                                        VertexFormat.Mode.QUADS).buildSnippet())
                .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/end_gateway")).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                .withShaderDefine("PORTAL_LAYERS", 16)
                .withDepthStencilState(DepthStencilState.DEFAULT)
                .withCull(false)
                .build());

    }
}
