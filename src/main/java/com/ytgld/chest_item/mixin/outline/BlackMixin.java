package com.ytgld.chest_item.mixin.outline;

import com.mojang.blaze3d.GpuFormat;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.outline.BlackFramebufferSets;
import com.ytgld.chest_item.renderer.outline.ILevelRendererWarped;
import com.ytgld.chest_item.renderer.outline.MFramebufferBlack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.util.profiling.ProfilerFiller;
import org.joml.Matrix4fc;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;

@Mixin(LevelRenderer.class)
public abstract class BlackMixin implements MFramebufferBlack , ILevelRendererWarped {
    @Unique
    private  RenderTarget chest_item$renderTarget_black;
    @Unique
    private  RenderTarget chest_item$Warped;
    @Unique
    private  RenderTarget chest_item$Decay;

    @Unique
    private final BlackFramebufferSets chest_item$defaultFramebufferSets_black = new BlackFramebufferSets();

    @Override
    public RenderTarget chest_item$render_black() {
        return chest_item$renderTarget_black;
    }
    @Override
    public RenderTarget chest_item$WarpedMixin() {
        return chest_item$Warped;
    }

    @Override
    public RenderTarget chest_item$render_Decay() {
        return chest_item$Decay;
    }

    @Inject(method = "close", at = @At(value = "RETURN"))
    private void close(CallbackInfo ci) {
        if (chest_item$renderTarget_black != null) {
            chest_item$renderTarget_black.destroyBuffers();
        }
        if (chest_item$Warped != null) {
            chest_item$Warped.destroyBuffers();
        }
        if (chest_item$Decay != null) {
            chest_item$Decay.destroyBuffers();
        }
    }
    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void loadEntityOutlinePostProcessor(CallbackInfo ci) {
        this.chest_item$renderTarget_black = new TextureTarget(
                "Entity Outline For Black", Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight(), true,GpuFormat.RGBA8_UNORM);

        this.chest_item$Warped = new TextureTarget(
                "Warped", Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight(), true, GpuFormat.RGBA8_UNORM);

        this.chest_item$Decay = new TextureTarget(
                "Decay", Minecraft.getInstance().getWindow().getWidth(),
                Minecraft.getInstance().getWindow().getHeight(), true, GpuFormat.RGBA8_UNORM);
    }
    @Inject(method = "doEntityOutline", at = @At(value = "RETURN"))
    private void drawEntityOutlinesFramebuffer(CallbackInfo ci) {
        if (!ConfigC.config.Render.get()){
            return;
        }
        if (Minecraft.getInstance().gameRenderer.mainRenderTarget().getColorTextureView() != null) {
            if (HandlerClient.showOutline) {
                chest_item$blitAndBlendToTexture(Minecraft.getInstance().gameRenderer.mainRenderTarget().getColorTextureView(),
                        Minecraft.getInstance().gameRenderer.mainRenderTarget().getDepthTextureView(),
                        chest_item$renderTarget_black,MRender.RenderPs.ENTITY_OUTLINE_BLIT);
                HandlerClient.showOutline = false;
            }
            if (HandlerClient.showRenderWarped) {
                chest_item$blitAndBlendToTexture(Minecraft.getInstance().gameRenderer.mainRenderTarget().getColorTextureView(),
                        Minecraft.getInstance().gameRenderer.mainRenderTarget().getDepthTextureView(),
                        chest_item$Warped,MRender.RenderPs.ENTITY_OUTLINE_BLIT);
                HandlerClient.showRenderWarped = false;
            }
            if (HandlerClient.showRenderDecay) {
                chest_item$blitAndBlendToTexture(Minecraft.getInstance().gameRenderer.mainRenderTarget().getColorTextureView(),
                        Minecraft.getInstance().gameRenderer.mainRenderTarget().getDepthTextureView(),
                        chest_item$Decay,MRender.RenderPs.ENTITY_OUTLINE_BLIT);
                HandlerClient.showRenderDecay = false;
            }
        }
    }

    @Inject(method = "resize", at = @At(value = "RETURN"))
    private void onResized(int width, int height, CallbackInfo ci) {
        if (this.chest_item$renderTarget_black != null) {
            this.chest_item$renderTarget_black.resize(width, height);
        }
        if (this.chest_item$Warped != null) {
            this.chest_item$Warped.resize(width, height);
        }
        if (this.chest_item$Decay != null) {
            this.chest_item$Decay.resize(width, height);
        }
    }
    @Inject(method = "addMainPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/feature/FeatureRenderDispatcher$PreparedFrame;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/client/renderer/state/level/LevelRenderState;Lnet/minecraft/util/profiling/ProfilerFiller;Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;Lorg/joml/Matrix4fc;)V", at = @At(value = "RETURN"))
    private void renderMainHEAD(FrameGraphBuilder frameGraphBuilder, FeatureRenderDispatcher.PreparedFrame featureFrame, GpuBufferSlice terrainFog, LevelRenderState levelRenderState, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, Matrix4fc modelViewMatrix, CallbackInfo ci) {
        FramePass framepass = frameGraphBuilder.addPass(Chestitem.MODID);
        if (this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer != null) {
            this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer =
                    framepass.readsAndWrites(this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer);
        }
        if (this.chest_item$defaultFramebufferSets_black.WarpedFramebuffer != null) {
            this.chest_item$defaultFramebufferSets_black.WarpedFramebuffer =
                    framepass.readsAndWrites(this.chest_item$defaultFramebufferSets_black.WarpedFramebuffer);
        }
        if (this.chest_item$defaultFramebufferSets_black.resourceHandleDecay != null) {
            this.chest_item$defaultFramebufferSets_black.resourceHandleDecay =
                    framepass.readsAndWrites(this.chest_item$defaultFramebufferSets_black.resourceHandleDecay);
        }
    }

    @Inject(method = "addMainPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/feature/FeatureRenderDispatcher$PreparedFrame;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/client/renderer/state/level/LevelRenderState;Lnet/minecraft/util/profiling/ProfilerFiller;Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;Lorg/joml/Matrix4fc;)V", at = @At(value = "RETURN"))
    private void renderMain(FrameGraphBuilder frameGraphBuilder, FeatureRenderDispatcher.PreparedFrame featureFrame, GpuBufferSlice terrainFog, LevelRenderState levelRenderState, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, Matrix4fc modelViewMatrix, CallbackInfo ci) {
        if (this.chest_item$renderTarget_black != null) {
            this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer =
                    frameGraphBuilder.importExternal("main", this.chest_item$renderTarget_black);
        }
        if (this.chest_item$Warped != null) {
            this.chest_item$defaultFramebufferSets_black.WarpedFramebuffer =
                    frameGraphBuilder.importExternal("main", this.chest_item$Warped);
        }
        if (this.chest_item$Decay != null) {
            this.chest_item$defaultFramebufferSets_black.resourceHandleDecay =
                    frameGraphBuilder.importExternal("main", this.chest_item$Decay);
        }
    }

    @Inject(method = "addMainPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/feature/FeatureRenderDispatcher$PreparedFrame;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/client/renderer/state/level/LevelRenderState;Lnet/minecraft/util/profiling/ProfilerFiller;Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;Lorg/joml/Matrix4fc;)V", at = @At(value = "RETURN"))
    private void renderMain2INVOKE_ASSIGN(FrameGraphBuilder frameGraphBuilder, FeatureRenderDispatcher.PreparedFrame featureFrame, GpuBufferSlice terrainFog, LevelRenderState levelRenderState, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, Matrix4fc modelViewMatrix, CallbackInfo ci) {
        {
            ResourceHandle<RenderTarget> handle4 = this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer;
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
        {
            ResourceHandle<RenderTarget> w = this.chest_item$defaultFramebufferSets_black.WarpedFramebuffer;
            if (w != null) {
                RenderTarget outlineTarget = w.get();
                if (outlineTarget.getColorTexture() != null && outlineTarget.getDepthTexture() != null) {
                    RenderSystem.getDevice()
                            .createCommandEncoder()
                            .clearColorAndDepthTextures(outlineTarget.getColorTexture(),
                                    new Vector4f(0.0F), outlineTarget.getDepthTexture(), 0.0);

                }
            }
        }
        {
            ResourceHandle<RenderTarget> decay = this.chest_item$defaultFramebufferSets_black.resourceHandleDecay;
            if (decay != null) {
                RenderTarget outlineTarget = decay.get();
                if (outlineTarget.getColorTexture() != null && outlineTarget.getDepthTexture() != null) {
                    RenderSystem.getDevice()
                            .createCommandEncoder()
                            .clearColorAndDepthTextures(outlineTarget.getColorTexture(),
                                    new Vector4f(0.0F), outlineTarget.getDepthTexture(), 0.0);

                }
            }
        }
    }

    @Inject(method = "addMainPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/feature/FeatureRenderDispatcher$PreparedFrame;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lnet/minecraft/client/renderer/state/level/LevelRenderState;Lnet/minecraft/util/profiling/ProfilerFiller;Lnet/minecraft/client/renderer/chunk/ChunkSectionsToRender;Lorg/joml/Matrix4fc;)V", at = @At(value = "RETURN"))
    private void renderMains(FrameGraphBuilder frameGraphBuilder, FeatureRenderDispatcher.PreparedFrame featureFrame, GpuBufferSlice terrainFog, LevelRenderState levelRenderState, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, Matrix4fc modelViewMatrix, CallbackInfo ci) {
        if (!ConfigC.config.Render.get()){
            return;
        }
        int i = Minecraft.getInstance().gameRenderer.mainRenderTarget().width;
        int j = Minecraft.getInstance().gameRenderer.mainRenderTarget().height;

        {
            PostChain postchain1 = Minecraft.getInstance().getShaderManager().getPostChain(Chestitem.warpedPOST,
                    Set.of(BlackFramebufferSets.MAIN,BlackFramebufferSets.WARPED));
            if (postchain1 != null) {
                if (HandlerClient.doPassWarped) {
                    postchain1.addToFrame(frameGraphBuilder, i, j, this.chest_item$defaultFramebufferSets_black);
                    HandlerClient.doPassWarped = false;
                }
            }
        }
        {
            PostChain postchain1 = Minecraft.getInstance().getShaderManager().getPostChain(Chestitem.POST_BLACK,
                    Set.of(BlackFramebufferSets.MAIN, BlackFramebufferSets.ENTITY_OUTLINE));
            if (postchain1 != null) {
                if (HandlerClient.doPass) {
                    postchain1.addToFrame(frameGraphBuilder, i, j, this.chest_item$defaultFramebufferSets_black);
                    HandlerClient.doPass = false;
                }
            }
        }
        {
            PostChain postchain1 = Minecraft.getInstance().getShaderManager().getPostChain(Chestitem.decay,
                    Set.of(BlackFramebufferSets.MAIN, BlackFramebufferSets.decay));
            if (postchain1 != null) {
                if (HandlerClient.doPassDecay) {
                    postchain1.addToFrame(frameGraphBuilder, i, j, this.chest_item$defaultFramebufferSets_black);
                    HandlerClient.doPassDecay = false;
                }
            }
        }
    }
    @Unique
    public void chest_item$blitAndBlendToTexture(GpuTextureView output,GpuTextureView outputDepth,RenderTarget renderTarget, RenderPipeline renderPipeline) {
        RenderSystem.assertOnRenderThread();
        try (RenderPass renderPass = RenderSystem.getDevice()
                .createCommandEncoder()
                .createRenderPass(() -> "Blit render target", output, Optional.empty(), outputDepth, OptionalDouble.empty())) {
            renderPass.setPipeline(renderPipeline);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.bindTexture("InSampler", renderTarget.getColorTextureView(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
            renderPass.draw(3, 1, 0, 0);
        }

    }
}
