package com.ytgld.chest_item.mixin.outline;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.outline.BlackFramebufferSets;
import com.ytgld.chest_item.renderer.outline.MFramebufferBlack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.LevelRenderState;
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
public abstract class BlackMixin implements MFramebufferBlack {
    @Shadow @Final private Minecraft minecraft;
    @Unique
    private  RenderTarget chest_item$renderTarget_black;
    @Unique
    private final BlackFramebufferSets chest_item$defaultFramebufferSets_black = new BlackFramebufferSets();

    @Override
    public RenderTarget chest_item$render_black() {
        return chest_item$renderTarget_black;
    }

    @Inject(method = "close", at = @At(value = "RETURN"))
    private void close(CallbackInfo ci) {
        if (chest_item$renderTarget_black != null) {
            chest_item$renderTarget_black.destroyBuffers();
        }
    }
    @Inject(method = "initOutline", at = @At(value = "RETURN"))
    private void loadEntityOutlinePostProcessor(CallbackInfo ci) {

        this.chest_item$renderTarget_black = new TextureTarget(
                "Entity Outline For Black", this.minecraft.getWindow().getWidth(),
                this.minecraft.getWindow().getHeight(), true);
    }
    @Inject(method = "doEntityOutline", at = @At(value = "RETURN"))
    private void drawEntityOutlinesFramebuffer(CallbackInfo ci) {
        if (this.minecraft.getMainRenderTarget().getColorTextureView() != null) {
            if (HandlerClient.showOutline) {
                chest_item$blitAndBlendToTexture(this.minecraft.getMainRenderTarget().getColorTextureView());
                HandlerClient.showOutline = false;
            }
        }
    }

    @Inject(method = "resize", at = @At(value = "RETURN"))
    private void onResized(int width, int height, CallbackInfo ci) {
        if (this.chest_item$renderTarget_black != null) {
            this.chest_item$renderTarget_black.resize(width, height);
        }
    }
    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMainHEAD(FrameGraphBuilder frameGraphBuilder, Frustum p_366590_, Matrix4f p_362420_, GpuBufferSlice p_418185_, boolean p_363964_, LevelRenderState p_451509_, DeltaTracker p_360931_, ProfilerFiller p_362234_, CallbackInfo ci) {
        FramePass framepass = frameGraphBuilder.addPass(Chestitem.MODID);
        if (this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer != null) {
            this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer =
                    framepass.readsAndWrites(this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer);
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMain(FrameGraphBuilder frameGraphBuilder, Frustum p_366590_, Matrix4f p_362420_, GpuBufferSlice p_418185_, boolean p_363964_, LevelRenderState p_451509_, DeltaTracker p_360931_, ProfilerFiller p_362234_, CallbackInfo ci) {
        if (this.chest_item$renderTarget_black != null) {
            this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer =
                    frameGraphBuilder.importExternal("main", this.chest_item$renderTarget_black);
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMain2INVOKE_ASSIGN(FrameGraphBuilder p_361593_, Frustum p_366590_, Matrix4f p_362420_, GpuBufferSlice p_418185_, boolean p_363964_, LevelRenderState p_451509_, DeltaTracker p_360931_, ProfilerFiller p_362234_, CallbackInfo ci) {
        ResourceHandle<RenderTarget> handle4 = this.chest_item$defaultFramebufferSets_black.entityOutlineFramebuffer;
        if (handle4 != null) {
            RenderTarget rendertarget = handle4.get();
            if (rendertarget.getColorTexture()!=null&&rendertarget.getDepthTexture()!=null) {

                RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(rendertarget.getColorTexture(),
                        0, rendertarget.getDepthTexture(), 1.0);
            }
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMains(FrameGraphBuilder frameGraphBuilder, Frustum p_366590_, Matrix4f p_362420_, GpuBufferSlice p_418185_, boolean p_363964_, LevelRenderState p_451509_, DeltaTracker p_360931_, ProfilerFiller p_362234_, CallbackInfo ci) {
        int i = this.minecraft.getMainRenderTarget().width;
        int j = this.minecraft.getMainRenderTarget().height;

        PostChain postchain1 = this.minecraft.getShaderManager().getPostChain(Chestitem.POST_BLACK, Set.of(BlackFramebufferSets.MAIN,BlackFramebufferSets.ENTITY_OUTLINE));
        if (postchain1 != null) {
            postchain1.addToFrame(frameGraphBuilder, i, j, this.chest_item$defaultFramebufferSets_black);
        }
    }


    @Unique
    public void chest_item$blitAndBlendToTexture(GpuTextureView textureView) {
        RenderSystem.assertOnRenderThread();
        RenderPass renderpass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> {
            return "Blit render target";
        }, textureView, OptionalInt.empty());

        try {
            renderpass.setPipeline(MRender.RenderPs.ENTITY_OUTLINE_BLIT);
            RenderSystem.bindDefaultUniforms(renderpass);
            renderpass.bindSampler("InSampler", chest_item$renderTarget_black.getColorTextureView());
            renderpass.draw(0, 3);
        } catch (Throwable var6) {
            try {
                renderpass.close();
            } catch (Throwable var5) {
                var6.addSuppressed(var5);
            }

            throw var6;
        }

        renderpass.close();

    }
}
