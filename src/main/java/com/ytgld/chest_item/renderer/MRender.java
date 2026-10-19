package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.pipeline.*;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

import java.util.Optional;
import java.util.function.Function;

import static com.mojang.renderpearl.api.pipeline.BlendFactor.*;
import static com.ytgld.chest_item.client_utils.RenderObjects.vColorTargetState;
import static net.minecraft.client.renderer.RenderPipelines.*;

public abstract class MRender {

    private static final  RenderPipeline.Snippet buildSnippetItem = RenderPipeline.builder(MATRICES_FOG_LIGHT_DIR_SNIPPET)
            .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/item"))
            .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/item")).
            withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER1_SAMPLER2).withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS).
            withVertexBinding(0, DefaultVertexFormat.ENTITY).
            withPrimitiveTopology(PrimitiveTopology.QUADS).withDepthStencilState(DepthStencilState.DEFAULT).buildSnippet();

    private static final RenderPipeline renderPipelineItem  =
            RenderPipeline.builder(buildSnippetItem
                    ).withLocation("pipeline/item_cutout")
                    .withColorTargetState(new ColorTargetState(new BlendFunction(SRC_ALPHA,
                            ONE,
                            ONE,
                            ZERO))).withCull(false).withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
                    .withShaderDefine("ALPHA_CUTOUT", 0.1F).build();

    public static Function<Identifier, RenderType> ITEM_CUTOUT =
            Util.memoize((texture) -> {
        RenderSetup state = RenderSetup.builder(renderPipelineItem)
                .withTexture("Sampler0", texture).useLightmap().useOverlay().affectsCrumbling().setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE).createRenderSetup();
        return RenderType.create("item_cutout", state);
    });
    public static RenderType colorOutline = RenderType.create(
            "lightning", RenderSetup.builder(RenderPipeline.builder(MATRICES_FOG_SNIPPET).withLocation("pipeline/lightning")
                            .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/rendertype_lightning"))
                            .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/rendertype_lightning"))
                            .withColorTargetState(new ColorTargetState(BlendFunction.LIGHTNING))
                            .withPrimitiveTopology(PrimitiveTopology.QUADS)
                            .withDepthStencilState(DepthStencilState.DEFAULT)
                            .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR).withCull(false).build())
                    .sortOnUpload().createRenderSetup()
    );
    public static final RenderPipeline.Snippet GUI_TEXTURED_SNIPPET =
            RenderPipeline.builder(GLOBALS_SNIPPET).
            withBindGroupLayout(BindGroupLayouts.PROJECTION)
                    .withVertexShader(Identifier.fromNamespaceAndPath(
                    Chestitem.MODID, "core/vows"))
            .withFragmentShader(Identifier.fromNamespaceAndPath(
                    Chestitem.MODID, "core/vows"))
            .withBindGroupLayout(BindGroupLayouts.SAMPLER0).withColorTargetState(new ColorTargetState(new BlendFunction(
                    SRC_ALPHA,
                    ONE,
                    ONE,
                    ZERO))).withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS) .withBindGroupLayout(BindGroupLayout.builder().withUniform(
                    "ChestVowsGlobals", UniformType.UNIFORM_BUFFER).build())
            .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR).withPrimitiveTopology(PrimitiveTopology.QUADS).buildSnippet();

    public static final RenderPipeline VowGlow =
            (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).withColorTargetState(new ColorTargetState(new BlendFunction(
                            SRC_ALPHA,
                            ONE,
                            ONE,
                            ZERO))).
                    withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/vows_glow")).build());


    private static final RenderPipeline renderPipeline = RenderPipeline.builder(
                    RenderPipeline.builder(RenderPipeline.builder(MATRICES_FOG_LIGHT_DIR_SNIPPET).
                                    withVertexShader(
                                            "core/item")
                                    .withFragmentShader("core/item")
                                    .withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER1_SAMPLER2)
                                    .withCull(false)
                                    .withVertexBinding(0, DefaultVertexFormat.ENTITY).withPrimitiveTopology(PrimitiveTopology.QUADS).
                                    withDepthStencilState(new DepthStencilState(CompareOp.ALWAYS_PASS, true)).withColorTargetState(vColorTargetState).buildSnippet())
                            .withLocation("pipeline/item_gaze")
                            .buildSnippet()).withLocation("pipeline/item_gaze")
            .withShaderDefine("ALPHA_CUTOUT", 0.1F).build();

    public static final Function<Identifier, RenderType> renderTypeFunction = Util.memoize((texture) -> {
        RenderSetup state = RenderSetup.builder(renderPipeline)
                .withTexture("Sampler0", texture)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .createRenderSetup();
        return RenderType.create("chest_vows_item", state);
    });

    public static RenderType endBlack =RenderType.create(
                "end_gateway",
                RenderSetup.builder(RenderPipeline.builder(END_PORTAL_SNIPPET).withLocation("pipeline/end_portal").withShaderDefine("PORTAL_LAYERS", 15).build())
                        .withTexture("Sampler0", Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/red.png"))
                        .withTexture("Sampler1", Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/red.png"))
                        .createRenderSetup());


    public static class RenderPs {
        public static final RenderPipeline.Snippet  GUI_TEXTURED_SNIPPET = RenderPipeline.builder(GLOBALS_SNIPPET).
                withBindGroupLayout(BindGroupLayouts.PROJECTION).
                withVertexShader("core/position_tex_color").withFragmentShader("core/position_tex_color")
                .withBindGroupLayout(BindGroupLayouts.SAMPLER0).withColorTargetState(new ColorTargetState(new BlendFunction(
                        SRC_ALPHA,
                        ONE,
                        ONE,
                        ZERO))).withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
                .withVertexBinding(0, DefaultVertexFormat.POSITION_TEX_COLOR).withPrimitiveTopology(PrimitiveTopology.QUADS).buildSnippet();

        public static final RenderPipeline GUI_TEXTURED =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).
                        withLocation("pipeline/gui_textured").withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS).build());


        public static final RenderPipeline GUI_TEXTURED_BLACK_BlendFunction =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
                        .withDepthStencilState(DepthStencilState.DEFAULT).withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
                        .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID,"pipeline/gui_textured")).build());

        public static final RenderPipeline TRANSLUCENT_PARTICLE =
                RenderPipeline.builder(
                                RenderPipeline.builder(MATRICES_FOG_SNIPPET).withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
                                        .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/particle"))
                                        .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/particle"))
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
                                        .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/particle"))
                                        .withCull(false)
                                        .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID,"core/particle"))
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
                        ).withBindGroupLayout(BindGroupLayouts.DYNAMIC_TRANSFORMS)
                        .withLocation("pipeline/translucent_particle")
                        .build();

        public static final RenderPipeline ENTITY_OUTLINE_BLIT =
                (RenderPipeline.builder(GLOBALS_SNIPPET).withLocation("pipeline/entity_outline_blit").
                        withVertexShader("core/screenquad").withFragmentShader("core/blit_screen").
                        withBindGroupLayout(BindGroupLayouts.IN_SAMPLER).withColorTargetState(new
                                ColorTargetState(Optional.of(new BlendFunction(
                                SRC_ALPHA,
                                ONE,
                                ONE,
                                ZERO)),
                                GpuFormat.RGBA8_UNORM, 7)).withPrimitiveTopology(PrimitiveTopology.TRIANGLES).build());
    }
}
