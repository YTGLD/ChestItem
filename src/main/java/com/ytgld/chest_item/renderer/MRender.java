package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.chest_item.ChestitemClient;
import com.ytgld.chest_item.renderer.i.IlevelRender;
import com.ytgld.chest_item.renderer.i.IlevelRenderWarped;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.ContainerScreenEvent;

import static org.lwjgl.opengl.GL11C.GL_LEQUAL;
import static org.lwjgl.opengl.GL11C.GL_LESS;

public abstract class MRender extends RenderType {
    public MRender(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
    public static final TransparencyStateShard UNIFIED_TRANSPARENCY_STATE = new TransparencyStateShard("unified_transparency", () -> {
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );
        RenderSystem.depthFunc(GL_LESS);
        RenderSystem.depthMask(false);

    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(GL_LEQUAL);
        RenderSystem.disableDepthTest();
    });

    protected static final OutputStateShard setOutputState = new OutputStateShard("set", () -> {
        if (Minecraft.getInstance().levelRenderer instanceof IlevelRender ilevelRender) {
            if (ilevelRender.cI1_21_1$entityTarget() != null) {
                ilevelRender.cI1_21_1$entityTarget() .copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                ilevelRender.cI1_21_1$entityTarget() .bindWrite(false);
            }
        }
    }, () -> {
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    });
    protected static final OutputStateShard wWarped = new OutputStateShard("warped", () -> {
        if (Minecraft.getInstance().levelRenderer instanceof IlevelRenderWarped ilevelRender) {
            if (ilevelRender.cI1_21_1$entityTargetWarped() != null) {
                ilevelRender.cI1_21_1$entityTargetWarped() .copyDepthFrom(Minecraft.getInstance().getMainRenderTarget());
                ilevelRender.cI1_21_1$entityTargetWarped() .bindWrite(false);
            }
        }
    }, () -> {
        Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    });

    public static RenderType LIGHTNING = create("lightning",
            DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS,
            1536, false, true,
            RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setWriteMaskState(COLOR_DEPTH_WRITE).setTransparencyState(UNIFIED_TRANSPARENCY_STATE)
                    .setOutputState(WEATHER_TARGET).createCompositeState(false));
    public static RenderType LIGHTNING_Outline = create("lightning_outline",
            DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS,
            1536, false, true,
            RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setWriteMaskState(COLOR_DEPTH_WRITE).setTransparencyState(UNIFIED_TRANSPARENCY_STATE)
                    .setOutputState(setOutputState).createCompositeState(false));
    public static RenderType renderTypeWarped = create("warped",
            DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS,
            1536, false, true,
            RenderType.CompositeState.builder().setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setWriteMaskState(COLOR_DEPTH_WRITE).setTransparencyState(UNIFIED_TRANSPARENCY_STATE)
                    .setOutputState(wWarped).createCompositeState(false));

    private static ShaderInstance liveShaderInstance;
    private static ShaderInstance liveShaderInstance_slowness;
    private static ShaderInstance whirlpool;
    public static void setShaderInstance_liveShaderInstance(ShaderInstance live) {
        liveShaderInstance = live;
    }

    public static ShaderInstance getLiveShaderInstance() {
        return liveShaderInstance;
    }

    public static ShaderInstance getLiveShaderInstance_slowness() {
        return liveShaderInstance_slowness;
    }
    public static ShaderInstance whirlpool() {
        return whirlpool;
    }

    public static void setLiveShaderInstance_slowness(ShaderInstance liveShaderInstance_slowness) {
        MRender.liveShaderInstance_slowness = liveShaderInstance_slowness;
    }

    public static void setWhirlpool(ShaderInstance whirlpool) {
        MRender.whirlpool = whirlpool;
    }
}

