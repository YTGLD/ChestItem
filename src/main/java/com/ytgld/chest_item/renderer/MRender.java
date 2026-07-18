package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.outline.ILevelRendererWarped;
import com.ytgld.chest_item.renderer.outline.IWarped;
import com.ytgld.chest_item.renderer.outline.MFramebufferBlack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.rendertype.LayeringTransform;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;

import static com.mojang.blaze3d.platform.BlendFactor.*;
import static net.minecraft.client.renderer.RenderPipelines.*;

public abstract class MRender {

    public static final OutputTarget outline2 = new OutputTarget("set_outline2", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MFramebufferBlack framebuffer){
            if (framebuffer.chest_item$render_black()!=null) {
                GlStateManager._enableDepthTest();
                framebuffer.chest_item$render_black().copyDepthFrom(Minecraft.getInstance().gameRenderer.mainRenderTarget());
                return framebuffer.chest_item$render_black();
            }
        }
        return Minecraft.getInstance().gameRenderer.mainRenderTarget();
    });
    public static final OutputTarget warped = new OutputTarget("warped", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof ILevelRendererWarped levelRendererWarped){
            if (levelRendererWarped.chest_item$WarpedMixin()!=null) {
                GlStateManager._enableDepthTest();
                levelRendererWarped.chest_item$WarpedMixin().copyDepthFrom(Minecraft.getInstance().gameRenderer.mainRenderTarget());
                return levelRendererWarped.chest_item$WarpedMixin();
            }
        }
        return Minecraft.getInstance().gameRenderer.mainRenderTarget();
    });
    private static final  RenderPipeline.Snippet buildSnippetItem = RenderPipeline.builder(MATRICES_FOG_LIGHT_DIR_SNIPPET)
            .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/item"))
            .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/item")).
            withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER1_SAMPLER2).
            withVertexBinding(0, DefaultVertexFormat.ENTITY).
            withPrimitiveTopology(PrimitiveTopology.QUADS).withDepthStencilState(DepthStencilState.DEFAULT).buildSnippet();

    private static final  RenderPipeline renderPipelineItem  =
            RenderPipeline.builder(buildSnippetItem
                    ).withLocation("pipeline/item_cutout")
                    .withColorTargetState(new ColorTargetState(new BlendFunction(SRC_ALPHA,
                            ONE,
                            ONE,
                            ZERO))).withCull(false)
                    .withShaderDefine("ALPHA_CUTOUT", 0.1F).build();

    private static RenderType ITEM_CUTOUT (Identifier identifier,boolean o) {
        if (o) {
            return RenderType.create("item_cutout", RenderSetup.builder(renderPipelineItem)
                    .withTexture("Sampler0", identifier)
                    .useLightmap().useOverlay().affectsCrumbling()
                    .setOutputTarget(warped)
                    .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                    .createRenderSetup());
        }

        return RenderType.create("item_cutout", RenderSetup.builder(renderPipelineItem)
                .withTexture("Sampler0", identifier)
                .useLightmap().useOverlay().affectsCrumbling()
                .setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
                .createRenderSetup());
    }
    public static RenderType cutoutItemSheet() {
        return itemCutout(TextureAtlas.LOCATION_ITEMS,true);
    }
    public static RenderType itemCutout(Identifier texture,boolean outline) {
        return ITEM_CUTOUT(texture,outline);
    }


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
                                    .withPrimitiveTopology(PrimitiveTopology.QUADS)
                                    .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR).withCull(false).build())
                            .setOutputTarget(outline2).sortOnUpload().createRenderSetup()
            );
        }
        return RenderType.create(
                "lightning",
                RenderSetup.builder(RenderPipeline.builder(MATRICES_FOG_SNIPPET).withLocation("pipeline/lightning").
                                withVertexShader("core/rendertype_lightning").withFragmentShader("core/rendertype_lightning")
                                .withColorTargetState(new ColorTargetState(BlendFunction.LIGHTNING))
                                .withPrimitiveTopology(PrimitiveTopology.QUADS)
                                .withVertexBinding(0,DefaultVertexFormat.POSITION_COLOR)
                                .withDepthStencilState(DepthStencilState.DEFAULT)
                                .withDepthStencilState(DepthStencilState.DEFAULT).withCull(false).build()
       )
                        .sortOnUpload().createRenderSetup());

    }

    public static RenderType colorOutlineLines(boolean isOutline){
        RenderPipeline.Snippet renderPipeline =   RenderPipeline.builder(MATRICES_FOG_SNIPPET)
                .withVertexShader("core/rendertype_lines")
                .withFragmentShader("core/rendertype_lines")
                .withColorTargetState(new ColorTargetState(new BlendFunction(SRC_ALPHA,
                        ONE,
                        ONE,
                        ZERO)))
                .withCull(false)
                .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH)
                .withPrimitiveTopology(PrimitiveTopology.LINES)
                .withDepthStencilState(DepthStencilState.DEFAULT)
                .buildSnippet();
        if (isOutline){
           return  RenderType.create(
                   "lines",
                           RenderSetup.builder(RenderPipeline.builder(renderPipeline)
                                           .withLocation("pipeline/lines_translucent")
                                           .withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))
                                           .build())
                                   .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                                   .setOutputTarget(outline2)
                                   .createRenderSetup());
        }
        return  RenderType.create(
                "lines",
                RenderSetup.builder(RenderPipeline.builder(renderPipeline)
                                .withLocation("pipeline/lines_translucent")
                                .withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))
                                .build())
                        .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                        .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
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
                                    .withColorTargetState(new ColorTargetState(new BlendFunction(SRC_ALPHA,
                                            ONE,
                                            ONE,
                                            ZERO))).withCull(false)
                                    .withPrimitiveTopology(PrimitiveTopology.QUADS)
                                    .withCull(false).withVertexBinding(0,DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH).withDepthStencilState(DepthStencilState.DEFAULT).buildSnippet()).withLocation("pipeline/lines").build()))
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
                                .withColorTargetState(new ColorTargetState(new BlendFunction(SRC_ALPHA,
                                        ONE,
                                        ONE,
                                        ZERO))).withCull(false)
                                .withPrimitiveTopology(PrimitiveTopology.QUADS)
                                .withCull(false).withVertexBinding(0,DefaultVertexFormat.POSITION_COLOR_NORMAL_LINE_WIDTH).withDepthStencilState(DepthStencilState.DEFAULT).buildSnippet()).withLocation("pipeline/lines").build()))
                        .setLayeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                        .setOutputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                        .createRenderSetup());
    }
    public static class RenderPs {
        public static final RenderPipeline.Snippet  GUI_TEXTURED_SNIPPET = RenderPipeline.builder(GLOBALS_SNIPPET).
                withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION).
                withVertexShader("core/position_tex_color").withFragmentShader("core/position_tex_color")
                .withBindGroupLayout(BindGroupLayouts.SAMPLER0).withColorTargetState(new ColorTargetState(new BlendFunction(
                        SRC_ALPHA,
                        ONE,
                        ONE,
                        ZERO)))
                .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR).withPrimitiveTopology(PrimitiveTopology.QUADS).buildSnippet();

        public static final RenderPipeline.Snippet  LiveTImage = RenderPipeline.builder(GLOBALS_SNIPPET).
                withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION).
                withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/live"))
                .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/live"))
                .withBindGroupLayout(BindGroupLayouts.SAMPLER0).withColorTargetState(new ColorTargetState(new BlendFunction(
                        SRC_ALPHA,
                        ONE,
                        ONE,
                        ZERO)))
                .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR).withPrimitiveTopology(PrimitiveTopology.QUADS).buildSnippet();


        public static final RenderPipeline GUI_TEXTURED =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).
                        withLocation("pipeline/gui_textured").build());

        public static final RenderPipeline LiveTImageRenderPipe =
                (RenderPipeline.builder(LiveTImage).
                        withLocation("pipeline/gui_textured_live").build());


        public static final RenderPipeline GUI_TEXTURED_BLACK_BlendFunction =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                        .withDepthStencilState(DepthStencilState.DEFAULT)
                        .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/gui_textured")).build());

        public static final RenderPipeline TRANSLUCENT_PARTICLE =
                RenderPipeline.builder(
                                RenderPipeline.builder(MATRICES_FOG_SNIPPET)
                                        .withVertexShader("core/particle")
                                        .withFragmentShader("core/particle")
                                        .withCull(false)
                                        .withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER2)
                                        .withVertexBinding(0, DefaultVertexFormat.PARTICLE)
                                        .withPrimitiveTopology(PrimitiveTopology.QUADS)
                                        .withDepthStencilState(
                                                new DepthStencilState(
                                                        CompareOp.GREATER_THAN_OR_EQUAL,
                                                        false
                                                )
                                        )
                                        .buildSnippet()
                        )
                        .withLocation("pipeline/translucent_particle")
                        .withColorTargetState(
                                new ColorTargetState(
                                        new BlendFunction(
                                                SRC_ALPHA,
                                                ONE,
                                                ONE,
                                                ZERO
                                        )
                                )
                        )
                        .build();
        public static final RenderPipeline PARTICLE =
                RenderPipeline.builder(
                                RenderPipeline.builder(MATRICES_FOG_SNIPPET)
                                        .withVertexShader("core/particle")
                                        .withCull(false)
                                        .withFragmentShader("core/particle")
                                        .withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER2)
                                        .withVertexBinding(0, DefaultVertexFormat.PARTICLE)
                                        .withPrimitiveTopology(PrimitiveTopology.QUADS)
                                        .withDepthStencilState(
                                                new DepthStencilState(
                                                        CompareOp.GREATER_THAN_OR_EQUAL,
                                                        false
                                                )
                                        )
                                        .buildSnippet()
                        )
                        .withLocation("pipeline/translucent_particle")
                        .build();

        public static final RenderPipeline ENTITY_OUTLINE_BLIT = (
                RenderPipeline.builder()
                        .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/entity_outline_blit"))
                        .withVertexShader("core/screenquad")
                        .withFragmentShader("core/blit_screen")
                        .withBindGroupLayout(BindGroupLayouts.IN_SAMPLER)
                        .withColorTargetState(new ColorTargetState(new BlendFunction(
                                SRC_ALPHA,
                                ONE,
                                ONE,
                                ZERO)))
                        .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
                        .withCull(false)
                        .build()
        );

        public static final RenderPipeline BACK =(RenderPipeline.builder(
                RenderPipeline.builder(GLOBALS_SNIPPET)
                        .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                        .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                                .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
                        .withVertexBinding(0,DefaultVertexFormat.POSITION).buildSnippet())
                .withPrimitiveTopology(PrimitiveTopology.QUADS)
                .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/end_gateway")).withColorTargetState(new ColorTargetState(
                        new BlendFunction(
                                SRC_ALPHA,
                                ONE,
                                ONE,
                                ZERO
                        )
                ))
                .withDepthStencilState(DepthStencilState.DEFAULT)
                .withShaderDefine("PORTAL_LAYERS", 16)
                .withCull(false)
                .build());
        public static final RenderPipeline TRANSLUCENT =(RenderPipeline.builder(
                        RenderPipeline.builder(GLOBALS_SNIPPET)
                                .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                                .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID, "core/rendertype_end_portal"))
                                        .withBindGroupLayout(BindGroupLayouts.SAMPLER0)
                                .withVertexBinding(0,DefaultVertexFormat.POSITION).buildSnippet())
                .withPrimitiveTopology(PrimitiveTopology.QUADS)
                .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/end_gateway")).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                .withShaderDefine("PORTAL_LAYERS", 16)
                .withDepthStencilState(DepthStencilState.DEFAULT)
                .withCull(false)
                .build());

    }
}
