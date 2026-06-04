package com.ytgld.chest_item.mixin.outline;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ChestitemClient;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.outline.IGameRenderer;
import com.ytgld.chest_item.renderer.outline.IWarped;
import com.ytgld.chest_item.renderer.outline.WarpedFrameSets;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

import static net.minecraft.client.renderer.PostChain.MAIN_TARGET_ID;

@Mixin(LevelRenderer.class)
public abstract class WarpedMixin implements IWarped {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private final WarpedFrameSets cI26_1Pre3$warpedFrameSets = new WarpedFrameSets();
    @Unique
    private RenderTarget cI26_1Pre3$renderTarget;

    @Override
    public RenderTarget chest_item$IWarped() {
        return cI26_1Pre3$renderTarget;
    }

    @Unique
    private final int cI26_1PreLIGHTMAP_UBO_SIZE =new Std140SizeCalculator()
           .putFloat()
           .putIVec2()
           .putInt()
           .get();
    @Unique
    private final MappableRingBuffer cI26_1Pre3$ubo = new MappableRingBuffer(() -> "Lightmap UBO", 130, cI26_1PreLIGHTMAP_UBO_SIZE);

    @Inject(method = "close", at = @At(value = "RETURN"))
    private void close(CallbackInfo ci) {
        if (cI26_1Pre3$renderTarget != null) {
            cI26_1Pre3$renderTarget.destroyBuffers();
        }
        this.cI26_1Pre3$ubo.close();
    }
    @Inject(method = "initOutline", at = @At(value = "RETURN"))
    private void loadEntityOutlinePostProcessor(CallbackInfo ci) {
        this.cI26_1Pre3$renderTarget = minecraft.getMainRenderTarget();
    }
    @Inject(method = "doEntityOutline", at = @At(value = "RETURN"))
    private void drawEntityOutlinesFramebuffer(CallbackInfo ci) {
//        chest_item$blitAndBlendToTexture(this.minecraft.getMainRenderTarget().getColorTextureView(),cI26_1Pre3$renderTarget, MRender.RenderPs.sScreenWarped);
    }

    @Inject(method = "resize", at = @At(value = "RETURN"))
    private void onResized(int width, int height, CallbackInfo ci) {
        if (this.cI26_1Pre3$renderTarget != null) {
            this.cI26_1Pre3$renderTarget.resize(width, height);
        }
    }
    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMain(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, boolean renderOutline, LevelRenderState levelRenderState, DeltaTracker deltaTracker, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, CallbackInfo ci) {
        if (this.cI26_1Pre3$renderTarget != null) {
            this.cI26_1Pre3$warpedFrameSets.WarpedScreenFramebuffer =
                    frameGraphBuilder.importExternal("main", this.cI26_1Pre3$renderTarget);
        }
    }
    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void renderMain2INVOKE_ASSIGN(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, boolean renderOutline, LevelRenderState levelRenderState, DeltaTracker deltaTracker, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, CallbackInfo ci) {
        ResourceHandle<RenderTarget> handle4 = this.cI26_1Pre3$warpedFrameSets.WarpedScreenFramebuffer;
        if (handle4 != null) {
            RenderTarget rendertarget = handle4.get();
            if (rendertarget.getColorTexture()!=null&&rendertarget.getDepthTexture()!=null) {

                RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(rendertarget.getColorTexture(),
                        0, rendertarget.getDepthTexture(), 1.0);
            }
        }
    }

    @Inject(method = "addMainPass", at = @At(value = "RETURN"))
    private void render(FrameGraphBuilder frame, Frustum frustum, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, boolean renderOutline, LevelRenderState levelRenderState, DeltaTracker deltaTracker, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, CallbackInfo ci) {
        if (minecraft.player != null) {
            if (minecraft.player.isAlive()) {
                {

                    PostChain postChain = this.minecraft.getShaderManager().getPostChain(Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "warped_screen"),
                            Set.of(WarpedFrameSets.MAIN, WarpedFrameSets.WARPED));

                    int i = this.cI26_1Pre3$renderTarget.width;
                    int j = this.cI26_1Pre3$renderTarget.height;
                    if (postChain != null) {
                        postChain.addToFrame(frame, i, j, this.cI26_1Pre3$warpedFrameSets);
                    }
                }
            }
        }
    }
    @Unique
    public void chest_item$blitAndBlendToTexture(GpuTextureView output, RenderTarget renderTarget, RenderPipeline renderPipeline) {
        RenderSystem.assertOnRenderThread();
        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
        try (GpuBuffer.MappedView view = commandEncoder.mapBuffer(this.cI26_1Pre3$ubo.currentBuffer(), false, true)) {
            Std140Builder.intoBuffer(view.data())
                    .putFloat(1f)
                    .putVec2(0.2f,0.5f)
                    .putInt(EventMain.time)
            ;
        }

        try (RenderPass renderPass = commandEncoder.createRenderPass(() -> "Blit render target", output, OptionalInt.empty())) {
            renderPass.setPipeline(renderPipeline);
            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("WarpedInfo", this.cI26_1Pre3$ubo.currentBuffer());
            renderPass.bindTexture("InSampler", renderTarget.getColorTextureView(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
            renderPass.draw(0, 3);
        }
        cI26_1Pre3$ubo.rotate();
    }
}
