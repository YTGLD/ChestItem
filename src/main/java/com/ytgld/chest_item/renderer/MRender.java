package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
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
    public static final OutputStateShard outline = new OutputStateShard("set_outline", () -> {
        LevelRenderer rendertarget = Minecraft.getInstance().levelRenderer;
        if (rendertarget instanceof MFramebuffer framebuffer){
            if (framebuffer.chest_item$render()!=null) {
                framebuffer.chest_item$render().copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                return framebuffer.chest_item$render();
            }
        }
        return Minecraft.getInstance().getMainRenderTarget();
    });

    public static final RenderType LIGHTNING_OUTLINE = create(
            "lightning",
            1536,
            false,
            true,
            RenderPipelines.LIGHTNING,
            RenderType.CompositeState.builder().setOutputState(outline).createCompositeState(false)
    );
    public static class RenderPs {
        public static final RenderPipeline  GUI_TEXTURED =
                (RenderPipeline.builder(GUI_TEXTURED_SNIPPET).withBlend(new BlendFunction(
                                SourceFactor.SRC_ALPHA,
                                DestFactor.ONE,
                                SourceFactor.ONE,
                                DestFactor.ZERO
                        ))
                        .withLocation("pipeline/gui_textured").build());

        public static final RenderPipeline  ENTITY_OUTLINE_BLIT = (RenderPipeline.builder().withLocation("pipeline/entity_outline_blit")
                .withVertexShader("core/blit_screen")
                .withFragmentShader("core/blit_screen")
                .withSampler("InSampler")
                .withBlend(new BlendFunction(
                        SourceFactor.SRC_ALPHA,
                        DestFactor.ONE,
                        SourceFactor.ONE,
                        DestFactor.ZERO
                ))
                .withDepthWrite(false)
                .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                .withColorWrite(true, false)
                .withVertexFormat(DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS).build());

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
                .build());

    }
}
