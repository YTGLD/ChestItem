package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.outline.ILevelRendererWarped;
import com.ytgld.chest_item.renderer.outline.MFramebufferBlack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.blockentity.AbstractEndPortalRenderer;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

import static com.mojang.blaze3d.pipeline.BlendFunction.OVERLAY;
import static net.minecraft.client.renderer.RenderPipelines.*;
import static net.minecraft.client.renderer.rendertype.OutputTarget.ITEM_ENTITY_TARGET;
import static net.minecraft.client.renderer.rendertype.OutputTarget.WEATHER_TARGET;

public abstract class MRender {

    public static final OutputTarget outline2 = new OutputTarget("set_outline2", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MFramebufferBlack framebuffer){
            if (framebuffer.chest_item$render_black()!=null) {
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
                levelRendererWarped.chest_item$WarpedMixin().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                return levelRendererWarped.chest_item$WarpedMixin();
            }
        }
        return Minecraft.getInstance().getMainRenderTarget();
    });
    public static RenderType warped(){
        return RenderType.create("warpeds",
                RenderSetup.builder(RenderPipelines.LIGHTNING).setOutputTarget(warped).sortOnUpload().createRenderSetup());
    }
    public static RenderType red(boolean isOutline){
        return endBlack(isOutline);
    }
    public static RenderType colorOutline(boolean isOutline){
        if (isOutline){
            return RenderType.create(
                    "lightning", RenderSetup.builder(RenderPipelines.LIGHTNING)
                            .setOutputTarget(outline2).sortOnUpload().createRenderSetup()
            );
        }
        return RenderType.create(
                "lightning",
                RenderSetup.builder(RenderPipelines.LIGHTNING)
                        .sortOnUpload().createRenderSetup());

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
    public static class RenderPs {

        public static final RenderPipeline.Snippet GUI_TEXTURED_SNIPPET_CI =
                RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                        .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/position_tex_color"))
                        .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/position_tex_color"))
                        .withSampler("Sampler0")
                        .withBlend(new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        )).withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR,
                                VertexFormat.Mode.QUADS)
                        .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).buildSnippet();
        public static RenderPipeline.Snippet whirlpoolBase (float speed) {
            return RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                    .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/whirlpool"))
                    .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/whirlpool"))
                    .withSampler("Sampler0")
                    .withBlend(new BlendFunction(
                            SourceFactor.SRC_ALPHA,
                            DestFactor.ONE,
                            SourceFactor.ONE,
                            DestFactor.ZERO
                    )).withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, VertexFormat.Mode.QUADS)
                    .withShaderDefine("speed", speed)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).buildSnippet();
        }
        public static RenderPipeline whirlpool(boolean lig,float speed) {
            if (lig) {
                return (RenderPipeline.builder(whirlpoolBase(speed)).withBlend(new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        ))
                        .withLocation("pipeline/whirlpool").build());
            }
            return (RenderPipeline.builder(whirlpoolBase(speed)).withBlend(BlendFunction.TRANSLUCENT)
                    .withLocation("pipeline/whirlpool").build());
        }
        public static RenderPipeline.Snippet snippet(float a,float offset,float light) {
            return    RenderPipeline.builder(MATRICES_PROJECTION_SNIPPET, FOG_SNIPPET, GLOBALS_SNIPPET)
                    .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/position_tex_color_slowness"))
                    .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/position_tex_color_slowness"))
                    .withSampler("Sampler0").withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR,
                            VertexFormat.Mode.QUADS).withBlend(BlendFunction.TRANSLUCENT)
                    .withShaderDefine("stronger", a)
                    .withShaderDefine("offset", offset)
                    .withShaderDefine("light", light)
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).buildSnippet();
        }

        public static  RenderPipeline LightSlownessHasLight(boolean light,float stronger,float offset,float lightAmout){
            if (light) {
                return  (RenderPipeline.builder(snippet(stronger,offset,lightAmout)).withBlend(new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        ))
                        .withLocation("pipeline/gui_textured_ci").build());
            }
            return  (RenderPipeline.builder(snippet(stronger,offset,lightAmout)).withBlend(BlendFunction.TRANSLUCENT)
                    .withLocation("pipeline/gui_textured_ci").build());
        }
        public static  RenderPipeline LightSlowness(boolean light,float stronger,float offset) {
            if (light) {
                return  (RenderPipeline.builder(snippet(stronger,offset,0)).withBlend(new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        ))
                        .withLocation("pipeline/gui_textured_ci").build());
            }
            return  (RenderPipeline.builder(snippet(stronger,offset,0)).withBlend(BlendFunction.TRANSLUCENT)
                    .withLocation("pipeline/gui_textured_ci").build());
        }
        public static final RenderPipeline GUI_TEXTURED =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).withBlend(new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        ))
                        .withLocation("pipeline/gui_textured").build());

        public static final RenderPipeline  TRANSLUCENT_PARTICLE = (RenderPipeline.builder(PARTICLE_SNIPPET)
                .withLocation("pipeline/translucent_particle").
                withBlend(new BlendFunction(
                        SourceFactor.SRC_ALPHA,
                        DestFactor.ONE,
                        SourceFactor.ONE,
                        DestFactor.ZERO
                )).build());

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
                        .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                        .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
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
                                .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                                .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                                .withSampler("Sampler0")
                                .withVertexFormat(DefaultVertexFormat.POSITION,
                                        VertexFormat.Mode.QUADS).buildSnippet())
                .withLocation("pipeline/end_gateway").withBlend(BlendFunction.TRANSLUCENT)
                .withShaderDefine("PORTAL_LAYERS", 16)
                .withCull(false)
                .build());

    }
}
