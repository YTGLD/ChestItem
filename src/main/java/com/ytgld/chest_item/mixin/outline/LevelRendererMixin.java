package com.ytgld.chest_item.mixin.outline;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ytgld.chest_item.ChestitemClient;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.renderer.i.IlevelRender;
import com.ytgld.chest_item.renderer.i.IlevelRenderWarped;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.PostChain;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.io.IOException;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin implements IlevelRender , IlevelRenderWarped {

    @Override
    public RenderTarget cI1_21_1$entityTarget() {
        return cI1_21_1$entityTarget;
    }
    @Override
    public RenderTarget cI1_21_1$entityTargetWarped() {
        return cI1_21_1$renderTargetWarped;
    }
    @Unique
    @Nullable
    private RenderTarget cI1_21_1$renderTargetWarped;
    @Unique
    @Nullable
    private PostChain cI1_21_1$postChainWarped;
    @Unique
    @Nullable
    private RenderTarget cI1_21_1$entityTarget;
    @Unique
    @Nullable
    private PostChain cI1_21_1$entityEffect;
    @Shadow
    @Final
    private Minecraft minecraft;
    @Inject(method = "initOutline()V",
            at = @At("TAIL"))
    private void initOutline(CallbackInfo ci) {
        if (cI1_21_1$postChainWarped != null) {
            cI1_21_1$postChainWarped.close();
        }
        try {
            this.cI1_21_1$postChainWarped = new PostChain(this.minecraft.getTextureManager(), this.minecraft.getResourceManager(), this.minecraft.getMainRenderTarget(), ChestitemClient.Warped);
            this.cI1_21_1$postChainWarped.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
            this.cI1_21_1$renderTargetWarped = this.cI1_21_1$postChainWarped.getTempTarget("final");
        } catch (IOException | JsonSyntaxException var3) {
            this.cI1_21_1$postChainWarped = null;
            this.cI1_21_1$renderTargetWarped = null;
        }

        if (cI1_21_1$entityEffect != null) {
            cI1_21_1$entityEffect.close();
        }
        try {
            this.cI1_21_1$entityEffect = new PostChain(this.minecraft.getTextureManager(), this.minecraft.getResourceManager(), this.minecraft.getMainRenderTarget(), ChestitemClient.POST_Blood);
            this.cI1_21_1$entityEffect.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
            this.cI1_21_1$entityTarget = this.cI1_21_1$entityEffect.getTempTarget("final");
        } catch (IOException | JsonSyntaxException var3) {
            this.cI1_21_1$entityEffect = null;
            this.cI1_21_1$entityTarget = null;
        }

    }
    @Inject(method = "renderLevel",
            at = @At(
                    value = "TAIL"
            ))
    private void doEntityOutline(DeltaTracker deltaTracker, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f frustumMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        if (HandlerClient.showWarped) {
            if (this.cI1_21_1$renderTargetWarped != null) {
                RenderSystem.enableBlend();
                RenderSystem.enableDepthTest();
                RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                if (cI1_21_1$renderTargetWarped != null) {
                    cI1_21_1$renderTargetWarped.blitToScreen(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), false);
                    cI1_21_1$renderTargetWarped.clear(Minecraft.ON_OSX);
                    minecraft.getMainRenderTarget().bindWrite(false);
                    HandlerClient.showWarped = false;
                }
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            }

        }
        if (HandlerClient.showOutline) {
             if (this.cI1_21_1$entityTarget != null) {
                 RenderSystem.enableBlend();
                 RenderSystem.enableDepthTest();
                 RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                 if (cI1_21_1$entityTarget != null) {
                     cI1_21_1$entityTarget.blitToScreen(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), false);
                     cI1_21_1$entityTarget.clear(Minecraft.ON_OSX);
                     minecraft.getMainRenderTarget().bindWrite(false);
                     HandlerClient.showOutline = false;
                 }
                 RenderSystem.disableBlend();
                 RenderSystem.defaultBlendFunc();
             }

        }

    }

    @Inject(method = "resize(II)V",
            at = @At("TAIL"))
    private void resize(int width, int height, CallbackInfo ci) {
        if (this.cI1_21_1$postChainWarped != null) {
            this.cI1_21_1$postChainWarped.resize(width, height);
        }
        if (this.cI1_21_1$entityEffect != null) {
            this.cI1_21_1$entityEffect.resize(width, height);
        }
    }

    @Inject(method = "close",
            at = @At("TAIL"))
    private void close(CallbackInfo ci) {
        if (this.cI1_21_1$postChainWarped != null) {
            this.cI1_21_1$postChainWarped.close();
        }
        if (this.cI1_21_1$entityEffect != null) {
            this.cI1_21_1$entityEffect.close();
        }
    }
    @Unique
    private float cI1_21_1$nas;
    @Inject(method = "renderLevel(Lnet/minecraft/client/DeltaTracker;ZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/OutlineBufferSource;endOutlineBatch()V",
                    shift = At.Shift.BEFORE
            ))
    private void renderLevel2(DeltaTracker deltaTracker, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f frustumMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {

        if (HandlerClient.showWarped) {
            if (this.cI1_21_1$postChainWarped != null) {
                cI1_21_1$nas+=0.02f;
                this.cI1_21_1$postChainWarped.setUniform("GameTime", cI1_21_1$nas);
                this.cI1_21_1$postChainWarped.process(deltaTracker.getGameTimeDeltaTicks());
                this.minecraft.getMainRenderTarget().bindWrite(false);
            }
        }
        if (HandlerClient.showOutline) {
            if (this.cI1_21_1$entityEffect != null) {
                this.cI1_21_1$entityEffect.process(deltaTracker.getGameTimeDeltaTicks());
                this.minecraft.getMainRenderTarget().bindWrite(false);
            }
        }
    }
    @Inject(method = "renderLevel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/RenderBuffers;bufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;",
                    shift = At.Shift.BEFORE
            ))
    private void renderLevel2RETURN(DeltaTracker deltaTracker, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f frustumMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        if (HandlerClient.showWarped) {
            if (this.cI1_21_1$renderTargetWarped != null) {
                this.cI1_21_1$renderTargetWarped.clear(Minecraft.ON_OSX);
                minecraft.getMainRenderTarget().bindWrite(false);
            }
        }
        if (HandlerClient.showOutline) {
            if (this.cI1_21_1$entityTarget != null) {
                this.cI1_21_1$entityTarget.clear(Minecraft.ON_OSX);
                minecraft.getMainRenderTarget().bindWrite(false);
            }
        }
    }
}
