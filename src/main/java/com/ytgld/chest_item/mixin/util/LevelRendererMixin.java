package com.ytgld.chest_item.mixin.util;


import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.ytgld.chest_item.renderer.GazeRender;
import com.ytgld.chest_item.utils.RenderObjectManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin{
    @Inject(method = "submitEntities", at = @At(value = "RETURN"))
    private void close(PoseStack poseStack, LevelRenderState levelRenderState, SubmitNodeCollector output, CallbackInfo ci) {
        float deltaPartialTick = Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
        RenderObjectManager.render(poseStack,output,levelRenderState.cameraRenderState,deltaPartialTick);
    }
    @Inject(method = "submitEntities", at = @At(value = "RETURN"))
    private void submitEntities(PoseStack poseStack, LevelRenderState levelRenderState, SubmitNodeCollector output, CallbackInfo ci) {
        GazeRender.emitGizmos(poseStack, levelRenderState, output);
    }
}
