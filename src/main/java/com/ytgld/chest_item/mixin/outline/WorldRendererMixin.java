package com.ytgld.chest_item.mixin.outline;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.outline.DefaultFramebufferSets;
import com.ytgld.chest_item.renderer.outline.MFramebuffer;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.profiling.ProfilerFiller;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalInt;
import java.util.Set;

@Mixin(LevelRenderer.class)
public abstract class WorldRendererMixin implements MFramebuffer {
    @Shadow @Final private Minecraft minecraft;

    @Shadow public abstract void needsUpdate();
    @Unique
    private  RenderTarget chest_item$renderT;
    @Unique
    private final DefaultFramebufferSets chest_item$defaultFramebufferSets = new DefaultFramebufferSets();


    @Inject(method = "close", at = @At(value = "RETURN"))
    private void close(CallbackInfo ci) {
        if (chest_item$renderT != null) {
            chest_item$renderT.destroyBuffers();
        }
    }
    @Inject(method = "initOutline", at = @At(value = "RETURN"))
    private void loadEntityOutlinePostProcessor(CallbackInfo ci) {
        this.chest_item$renderT = new TextureTarget(
                "Entity Outline For Beacon", this.minecraft.getWindow().getWidth(),
                this.minecraft.getWindow().getHeight(), true
        );
    }
    @Inject(method = "doEntityOutline", at = @At(value = "RETURN"))
    private void drawEntityOutlinesFramebuffer(CallbackInfo ci) {
        if (this.minecraft.getMainRenderTarget().getColorTextureView()!=null) {
            chest_item$blitAndBlendToTexture(this.minecraft.getMainRenderTarget().getColorTextureView());
        }
    }

    @Inject(method = "resize", at = @At(value = "RETURN"))
    private void onResized(int width, int height, CallbackInfo ci) {
        this.needsUpdate();
        if (this.chest_item$renderT != null) {
            this.chest_item$renderT.resize(width, height);
        }
    }
    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMain(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Camera camera, Matrix4f frustumMatrix, GpuBufferSlice shaderFog, boolean renderBlockOutline, boolean renderEntityOutline, DeltaTracker deltaTracker, ProfilerFiller profiler, CallbackInfo ci) {
        if (this.chest_item$renderT != null) {
            this.chest_item$defaultFramebufferSets.entityOutlineFramebuffer =
                    frameGraphBuilder.importExternal("entity_outline", this.chest_item$renderT);
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMain2INVOKE_ASSIGN(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Camera camera, Matrix4f frustumMatrix, GpuBufferSlice shaderFog, boolean renderBlockOutline, boolean renderEntityOutline, DeltaTracker deltaTracker, ProfilerFiller profiler, CallbackInfo ci) {
        ResourceHandle<RenderTarget> handle4 = this.chest_item$defaultFramebufferSets.entityOutlineFramebuffer;
        if (handle4 != null) {
            RenderTarget rendertarget = handle4.get();
            if (rendertarget.getColorTexture()!=null&&rendertarget.getDepthTexture()!=null) {

                RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(rendertarget.getColorTexture(),
                        0, rendertarget.getDepthTexture(), 1.0);
            }
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMains(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Camera camera, Matrix4f frustumMatrix, GpuBufferSlice shaderFog, boolean renderBlockOutline, boolean renderEntityOutline, DeltaTracker deltaTracker, ProfilerFiller profiler, CallbackInfo ci) {
        int i = this.minecraft.getMainRenderTarget().width;
        int j = this.minecraft.getMainRenderTarget().height;

        PostChain postchain1 = this.minecraft.getShaderManager().getPostChain(Chestitem.POST, Set.of(DefaultFramebufferSets.MAIN,DefaultFramebufferSets.ENTITY_OUTLINE));
        if (postchain1 != null) {
            postchain1.addToFrame(frameGraphBuilder, i, j, this.chest_item$defaultFramebufferSets);
        }

    }
    @Override
    public RenderTarget chest_item$render() {
        return chest_item$renderT;
    }
    @Unique
    public void chest_item$blitAndBlendToTexture(GpuTextureView textureView) {
        RenderSystem.assertOnRenderThread();
        RenderSystem.AutoStorageIndexBuffer rendersystem$autostorageindexbuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
        GpuBuffer gpubuffer = rendersystem$autostorageindexbuffer.getBuffer(6);
        GpuBuffer gpubuffer1 = RenderSystem.getQuadVertexBuffer();
        RenderPass renderpass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> {
            return "Blit render target";
        }, textureView, OptionalInt.empty());

        try {
            renderpass.setPipeline(MRender.RenderPs.ENTITY_OUTLINE_BLIT);
            RenderSystem.bindDefaultUniforms(renderpass);
            renderpass.setVertexBuffer(0, gpubuffer1);
            renderpass.setIndexBuffer(gpubuffer, rendersystem$autostorageindexbuffer.type());
            renderpass.bindSampler("InSampler",chest_item$renderT.getColorTextureView());
            renderpass.drawIndexed(0, 0, 6, 1);

        } catch (Throwable var9) {
            try {
                renderpass.close();
            } catch (Throwable var8) {
                var9.addSuppressed(var8);
            }

            throw var9;
        }
        renderpass.close();
    }

}
