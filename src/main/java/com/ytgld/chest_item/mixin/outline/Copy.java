package com.ytgld.chest_item.mixin.outline;

//
//@Mixin(LevelRenderer.class)
//public abstract class Copy implements IWarped
//{
//
//    @Shadow
//    @Final
//    private Minecraft minecraft;
//
//    @Unique
//    private final WarpedFrameSets cI26_1Pre3$warpedFrameSets = new WarpedFrameSets();
//
//    @Unique
//    private RenderTarget cI26_1Pre3$warpedRenderTarget;
//
//    // UBO 配置
//    @Unique
//    private final int cI26_1Pre3$warpedUboSize = new Std140SizeCalculator()
//            .putFloat()    // stronger
//            .putVec2()     // pos
//            .putInt()      // time
//            .get();
//
//    @Override
//    public RenderTarget chest_item$IWarped() {
//        return cI26_1Pre3$warpedRenderTarget;
//    }
//
//    @Unique
//    private final MappableRingBuffer cI26_1Pre3$warpedUbo =
//            new MappableRingBuffer(() -> "WarpedInfo", 130, cI26_1Pre3$warpedUboSize);
//
//    // 初始化 render target
//    @Inject(method = "initOutline", at = @At("RETURN"))
//    private void initWarpedRenderTarget(CallbackInfo ci) {
//        this.cI26_1Pre3$warpedRenderTarget = minecraft.getMainRenderTarget();
//    }
//
//    // resize render target
//    @Inject(method = "resize", at = @At("RETURN"))
//    private void onResized(int width, int height, CallbackInfo ci) {
//        if (this.cI26_1Pre3$warpedRenderTarget != null) {
//            this.cI26_1Pre3$warpedRenderTarget.resize(width, height);
//        }
//    }
//
//    // 关闭时释放资源
//    @Inject(method = "close", at = @At("RETURN"))
//    private void close(CallbackInfo ci) {
//        if (cI26_1Pre3$warpedRenderTarget != null) {
//            cI26_1Pre3$warpedRenderTarget.destroyBuffers();
//        }
//        this.cI26_1Pre3$warpedUbo.close();
//    }
//
//    // 添加主渲染 Pass
//    @Inject(method = "addMainPass", at = @At("RETURN"))
//    private void addWarpedScreenPass(
//            FrameGraphBuilder frameGraphBuilder,
//            Frustum frustum,
//            Matrix4fc modelViewMatrix,
//            GpuBufferSlice terrainFog,
//            boolean renderOutline,
//            LevelRenderState levelRenderState,
//            DeltaTracker deltaTracker,
//            ProfilerFiller profiler,
//            ChunkSectionsToRender chunkSectionsToRender,
//            CallbackInfo ci
//    ) {
//        if (minecraft.player == null || !minecraft.player.isAlive() || cI26_1Pre3$warpedRenderTarget == null) {
//            return;
//        }
//
//        // 导入外部 RenderTarget
//        cI26_1Pre3$warpedFrameSets.WarpedScreenFramebuffer = frameGraphBuilder.importExternal(
//                "main", cI26_1Pre3$warpedRenderTarget
//        );
//
//        // 创建 Pass
//        FramePass warpedPass = frameGraphBuilder.addPass("warped");
//        if (cI26_1Pre3$warpedFrameSets.WarpedScreenFramebuffer != null) {
//            cI26_1Pre3$warpedFrameSets.WarpedScreenFramebuffer =
//                    warpedPass.readsAndWrites(cI26_1Pre3$warpedFrameSets.WarpedScreenFramebuffer);
//        }
//
//        // 清理 RenderTarget
//        RenderTarget rt = cI26_1Pre3$warpedFrameSets.WarpedScreenFramebuffer.get();
//        if (rt.getColorTexture() != null && rt.getDepthTexture() != null) {
//            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(
//                    rt.getColorTexture(), 0,
//                    rt.getDepthTexture(), 1.0f
//            );
//        }
//
//        // 写入 UBO
//        cI26_1Pre3$writeWarpedUbo();
//
//        // 调用 PostChain
//        PostChain postChain = minecraft.getShaderManager().getPostChain(
//                Identifier.fromNamespaceAndPath(Chestitem.MODID, "warped_screen"),
//                Set.of(WarpedFrameSets.MAIN, WarpedFrameSets.WARPED)
//        );
//        if (postChain != null) {
//            postChain.addToFrame(frameGraphBuilder, cI26_1Pre3$warpedRenderTarget.width, cI26_1Pre3$warpedRenderTarget.height, cI26_1Pre3$warpedFrameSets);
//        }
//
//        // Blit 到屏幕
//        cI26_1Pre3$blitAndBlendToTexture(
//                cI26_1Pre3$warpedRenderTarget.getColorTextureView(),
//                cI26_1Pre3$warpedRenderTarget,
//                MRender.RenderPs.sScreenWarped
//        );
//
//        // rotate UBO
//        cI26_1Pre3$warpedUbo.rotate();
//    }
//
//    @Unique
//    private void cI26_1Pre3$blitAndBlendToTexture(GpuTextureView output, RenderTarget renderTarget, RenderPipeline pipeline) {
//        RenderSystem.assertOnRenderThread();
//        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
//
//        try (RenderPass pass = encoder.createRenderPass(() -> "Blit warped screen", output, OptionalInt.empty())) {
//            pass.setPipeline(pipeline);
//            RenderSystem.bindDefaultUniforms(pass);
//
//            // 设置动态 UBO
//            pass.setUniform("WarpedInfo", cI26_1Pre3$warpedUbo.currentBuffer());
//
//            pass.bindTexture("InSampler",
//                    renderTarget.getColorTextureView(),
//                    RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST)
//            );
//
//            pass.draw(0, 3);
//        }
//    }
//
//    @Unique
//    private void cI26_1Pre3$writeWarpedUbo() {
//        RenderSystem.assertOnRenderThread();
//
//        GpuBuffer buffer = cI26_1Pre3$warpedUbo.currentBuffer();
//        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
//
//        try (GpuBuffer.MappedView view = encoder.mapBuffer(buffer, false, true)) {
//            Std140Builder.intoBuffer(view.data())
//                    .putFloat(1.0f)      // stronger
//                    .putVec2(0.5f, 0.5f) // pos
//                    .putInt(555); // time
//        }
//    }
//}
