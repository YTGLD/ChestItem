package com.ytgld.chest_item.mixin.outline;

import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.textures.FilterMode;
import com.mojang.renderpearl.api.textures.GpuTextureView;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.outline.BlackFramebufferSets;
import com.ytgld.chest_item.renderer.outline.IPreparedFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow
    @Final
    private static Vector4fc ZERO_CLEAR_COLOR;
    @Unique
    private RenderTarget chest26_3$entityOutlineTarget;
    @Unique
    private final BlackFramebufferSets chest26_3$blackFramebufferSets = new BlackFramebufferSets();

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void init(CallbackInfo ci) {
        this.chest26_3$entityOutlineTarget = new TextureTarget(
                "Entity Outline For Black", Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight(), GpuFormat.RGBA8_UNORM, GpuFormat.D32_FLOAT);
    }
    @Inject(method = "close", at = @At(value = "RETURN"))
    private void close(CallbackInfo ci) {
        this.chest26_3$entityOutlineTarget.destroyBuffers();
    }
    @Inject(method = "resize", at = @At(value = "RETURN"))
    private void resize(int width, int height, CallbackInfo ci) {
        this.chest26_3$entityOutlineTarget.resize(width, height);
    }
    @Inject(method = "blitEntityOutline", at = @At(value = "RETURN"))
    private void blitEntityOutline(CallbackInfo ci) {
        if (Minecraft.getInstance().gameRenderer.mainRenderTarget().getDepthTextureView() != null) {
            if (Minecraft.getInstance().gameRenderer.mainRenderTarget().getColorTextureView() != null) {
                if (HandlerClient.showOutline) {
                    chest26_3$blitAndBlendToTexture(Minecraft.getInstance().gameRenderer.mainRenderTarget().getColorTextureView(),
                            Minecraft.getInstance().gameRenderer.mainRenderTarget().getDepthTextureView());
                    HandlerClient.showOutline = false;
                }

            }
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "HEAD"))
    private void addMainPass(FrameGraphBuilder frame, FeatureRenderDispatcher.PreparedFrame featureFrame, GpuBufferSlice terrainFog, ChunkSectionsToRender chunkSectionsToRender, boolean consistentDepthRequired, CallbackInfo ci) {
        this.chest26_3$blackFramebufferSets.entityOutlineFramebuffer =
                frame.importExternal("black", this.chest26_3$entityOutlineTarget);
    }
    @Inject(
            method = "addMainPass",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/framegraph/FramePass;executes(Ljava/lang/Runnable;)V")
    )
    private void chest26_3$executeOutline(
            FrameGraphBuilder frame, FeatureRenderDispatcher.PreparedFrame featureFrame, GpuBufferSlice terrainFog, ChunkSectionsToRender chunkSectionsToRender, boolean consistentDepthRequired, CallbackInfo ci) {
        this.chest26_3$executeOutline(featureFrame);
    }
    @Inject(method = "addMainPass", at = @At(value = "HEAD"))
    private void renderMainHEAD(FrameGraphBuilder frame, FeatureRenderDispatcher.PreparedFrame featureFrame, GpuBufferSlice terrainFog, ChunkSectionsToRender chunkSectionsToRender, boolean consistentDepthRequired, CallbackInfo ci) {
        FramePass framepass = frame.addPass(Chestitem.MODID);
        if (this.chest26_3$blackFramebufferSets.entityOutlineFramebuffer != null) {
            this.chest26_3$blackFramebufferSets.entityOutlineFramebuffer =
                    framepass.readsAndWrites(this.chest26_3$blackFramebufferSets.entityOutlineFramebuffer);
        }
    }
    @Inject(method = "addMainPass", at = @At(value = "HEAD"))
    private void renderMain2INVOKE_ASSIGN(FrameGraphBuilder frame, FeatureRenderDispatcher.PreparedFrame featureFrame, GpuBufferSlice terrainFog, ChunkSectionsToRender chunkSectionsToRender, boolean consistentDepthRequired, CallbackInfo ci) {
        ResourceHandle<RenderTarget> handle4 = this.chest26_3$blackFramebufferSets.entityOutlineFramebuffer;
        if (handle4 != null) {
            RenderTarget rendertarget = handle4.get();
            if (rendertarget.getColorTexture() != null && rendertarget.getDepthTexture() != null) {
                RenderSystem.getDevice()
                        .createCommandEncoder()
                        .clearColorAndDepthTextures(rendertarget.getColorTexture(),
                                new Vector4f(0.0F), rendertarget.getDepthTexture(), 0.0);
            }
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "HEAD"))
    private void renderMains(FrameGraphBuilder frame, FeatureRenderDispatcher.PreparedFrame featureFrame, GpuBufferSlice terrainFog, ChunkSectionsToRender chunkSectionsToRender, boolean consistentDepthRequired, CallbackInfo ci) {
        int i = Minecraft.getInstance().gameRenderer.mainRenderTarget().width;
        int j = Minecraft.getInstance().gameRenderer.mainRenderTarget().height;

        PostChain postchain1 = Minecraft.getInstance().getShaderManager().getPostChain(Chestitem.POST_BLACK,
                Set.of(BlackFramebufferSets.MAIN, BlackFramebufferSets.BLACK));
        if (postchain1 != null) {
            if (HandlerClient.doPass) {
                postchain1.addToFrame(frame, i, j, this.chest26_3$blackFramebufferSets);
                HandlerClient.doPass = false;
            }
        }

    }
    @Unique
    private void chest26_3$executeOutline(FeatureRenderDispatcher.PreparedFrame featureFrame) {
        GpuTextureView mainDepthTextureView =
                Minecraft.getInstance().gameRenderer.mainRenderTarget().getDepthTextureView();

        if (this.chest26_3$entityOutlineTarget.getColorTextureView() != null) {
            try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                    () -> "Outline",
                    this.chest26_3$entityOutlineTarget.getColorTextureView(),
                    Optional.of(ZERO_CLEAR_COLOR),
                    mainDepthTextureView,
                    OptionalDouble.empty()
            )) {
                RenderSystem.bindDefaultUniforms(renderPass);

                if (featureFrame instanceof IPreparedFrame iPreparedFrame) {
                    iPreparedFrame.chest_item$executeReactorGlow(renderPass);
                }
            }
        }
    }
    @Unique
    public void chest26_3$blitAndBlendToTexture(GpuTextureView output, GpuTextureView outputDepth) {
        RenderSystem.assertOnRenderThread();
        try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Blit render target", output, Optional.empty(), outputDepth, OptionalDouble.empty())) {
            renderPass.setPipeline(RenderSystem.getCompiledPipeline(MRender.RenderPs.ENTITY_OUTLINE_BLIT));
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("InSampler", this.chest26_3$entityOutlineTarget.getColorTextureView(),
                    RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
            renderPass.draw(3, 1, 0, 0);
        }
    }
}

