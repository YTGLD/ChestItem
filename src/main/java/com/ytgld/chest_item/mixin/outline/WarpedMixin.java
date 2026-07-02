package com.ytgld.chest_item.mixin.outline;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.outline.IWarped;
import com.ytgld.chest_item.renderer.outline.WarpedFrameSets;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.profiling.ProfilerFiller;
import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
//
//@Mixin(LevelRenderer.class)
//public abstract class WarpedMixin implements IWarped {
//    @Shadow
//    @Final
//    private Minecraft minecraft;
//    @Unique
//    private final WarpedFrameSets cI26_1Pre3$warpedFrameSets = new WarpedFrameSets();
//    @Unique
//    private RenderTarget cI26_1Pre3$renderTarget;
//
//    @Override
//    public RenderTarget chest_item$IWarped() {
//        return cI26_1Pre3$renderTarget;
//    }
//    @Inject(method = "close", at = @At(value = "RETURN"))
//    private void close(CallbackInfo ci) {
//        if (cI26_1Pre3$renderTarget != null) {
//            cI26_1Pre3$renderTarget.destroyBuffers();
//        }
//    }
//    @Inject(method = "initOutline", at = @At(value = "RETURN"))
//    private void loadEntityOutlinePostProcessor(CallbackInfo ci) {
//        this.cI26_1Pre3$renderTarget = minecraft.getMainRenderTarget();
//    }
//    @Inject(method = "doEntityOutline", at = @At(value = "RETURN"))
//    private void drawEntityOutlinesFramebuffer(CallbackInfo ci) {
////        chest_item$blitAndBlendToTexture(this.minecraft.getMainRenderTarget().getColorTextureView(),cI26_1Pre3$renderTarget, MRender.RenderPs.sScreenWarped);
//    }
//
//    @Inject(method = "resize", at = @At(value = "RETURN"))
//    private void onResized(int width, int height, CallbackInfo ci) {
//        if (this.cI26_1Pre3$renderTarget != null) {
//            this.cI26_1Pre3$renderTarget.resize(width, height);
//        }
//    }
//    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
//    private void renderMain(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, boolean renderOutline, LevelRenderState levelRenderState, DeltaTracker deltaTracker, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, CallbackInfo ci) {
//        if (this.cI26_1Pre3$renderTarget != null) {
//            this.cI26_1Pre3$warpedFrameSets.WarpedScreenFramebuffer =
//                    frameGraphBuilder.importExternal("main", this.cI26_1Pre3$renderTarget);
//        }
//    }
//    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
//    private void renderMain2INVOKE_ASSIGN(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, boolean renderOutline, LevelRenderState levelRenderState, DeltaTracker deltaTracker, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, CallbackInfo ci) {
//        ResourceHandle<RenderTarget> handle4 = this.cI26_1Pre3$warpedFrameSets.WarpedScreenFramebuffer;
//        if (handle4 != null) {
//            RenderTarget rendertarget = handle4.get();
//            if (rendertarget.getColorTexture()!=null&&rendertarget.getDepthTexture()!=null) {
//
//                RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(rendertarget.getColorTexture(),
//                        0, rendertarget.getDepthTexture(), 1.0);
//            }
//        }
//    }
//    @Unique
//    private MappableRingBuffer cI26_1Pre3$warpedInfoBuffer;
//
//    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
//    private void render(FrameGraphBuilder frame, Frustum frustum, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, boolean renderOutline, LevelRenderState levelRenderState, DeltaTracker deltaTracker, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, CallbackInfo ci) {
//
//        if (minecraft.player == null || !minecraft.player.isAlive()) {
//            return;
//        }
//
//        ShaderManager shaderManager = minecraft.getShaderManager();
//
//        PostChain postChain = shaderManager.getPostChain(
//                Identifier.fromNamespaceAndPath(Chestitem.MODID, "warped_screen"),
//                Set.of(WarpedFrameSets.MAIN, WarpedFrameSets.WARPED)
//        );
//
//        int width = this.cI26_1Pre3$renderTarget.width;
//        int height = this.cI26_1Pre3$renderTarget.height;
//
//        if (postChain == null) return;
//
//        // 初始化 buffer，一次即可
//        if (cI26_1Pre3$warpedInfoBuffer == null) {
//            cI26_1Pre3$warpedInfoBuffer = new MappableRingBuffer(() -> "WarpedInfo",
//                    130,
//                    new Std140SizeCalculator()
//                            .putFloat()   // strength
//                            .putVec2()    // center
//                            .putInt()     // mode
//                            .get()
//            );
//        }
//
//        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
//
//        try (GpuBuffer.MappedView view = commandEncoder.mapBuffer(cI26_1Pre3$warpedInfoBuffer.currentBuffer(), false, true)) {
//            Std140Builder builder = Std140Builder.intoBuffer(view.data());
//            builder.putFloat(5f);         // strength
//            builder.putVec2(0.25f, 0.25f);  // center
//            builder.putInt(555);          // mode
//        }
//
//        // 确保 RenderTarget 有 Color Texture
//        RenderTarget renderTarget = this.cI26_1Pre3$renderTarget;
//        if (renderTarget.getColorTextureView() != null) {
//
//            try (RenderPass renderPass = commandEncoder.createRenderPass(
//                    () -> "WarpedInfo",
//                    renderTarget.getColorTextureView(),
//                    OptionalInt.empty(),
//                    renderTarget.useDepth ? renderTarget.getDepthTextureView() : null,
//                    OptionalDouble.empty()
//            )) {
//                // **Pipeline 绑定**
//                renderPass.setPipeline(MRender.RenderPs.sScreenWarped);
//                RenderSystem.bindDefaultUniforms(renderPass);
//                renderPass.bindTexture("InSampler", renderTarget.getColorTextureView(),
//                        RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
//                renderPass.setUniform(
//                        "WarpConfig",
//                        cI26_1Pre3$warpedInfoBuffer.currentBuffer()
//                );
//                renderPass.draw(0, 3);
//            }
//        }
//
//        // **可选：将后处理添加到 PostChain**
//        // 注意：PostChain 不支持自定义 UBO，如果你 shader 里有 WarpedInfo，日志会出现 WARN
//        postChain.addToFrame(frame, width, height, this.cI26_1Pre3$warpedFrameSets);
//    }
//}
