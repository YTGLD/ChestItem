package com.ytgld.chest_item.renderer.visual;

import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.RenderTargetDescriptor;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.GpuFormat;
import com.mojang.renderpearl.api.buffers.GpuBuffer;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.pipeline.*;
import com.mojang.renderpearl.api.textures.FilterMode;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RegisterRenderPipelinesEvent;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public final class Visual {
    public static final BindGroupLayout BUILD =
            BindGroupLayout.builder()
                    .withUniform("VisualColor", UniformType.UNIFORM_BUFFER)
                    .withUniform("MainSampler", UniformType.COMBINED_IMAGE_SAMPLER)
                    .withUniform("DepthSampler", UniformType.COMBINED_IMAGE_SAMPLER)
                    .withUniform("Projection", UniformType.UNIFORM_BUFFER)
                    .withUniform("Globals", UniformType.UNIFORM_BUFFER)
                    .build();
    public static final RenderPipeline PIPELINE =
            RenderPipeline.builder()
                    .withLocation(Identifier.fromNamespaceAndPath(Chestitem.MODID ,
                            "pipeline/visual_post"))
                    .withVertexShader(Identifier.fromNamespaceAndPath(Chestitem.MODID ,
                            "visual/screenquad"))
                    .withFragmentShader(Identifier.fromNamespaceAndPath(Chestitem.MODID ,
                            "visual/visual_post"))
                    .withBindGroupLayout(BUILD)
                    .withColorTargetState(ColorTargetState.DEFAULT)
                    .withPrimitiveTopology(PrimitiveTopology.TRIANGLES)
                    .build();



    private static final int MAX_SIZE = 32;
    private static final int BUFFER_SIZE = MAX_SIZE * 16 * 2;
    private static final List<VisualVec3> visualVec3s = new ArrayList<>();
    private static GpuBuffer theGpuBuffer;


    private Visual() {
    }

    public static void addPostPass(
            FrameGraphBuilder frame,
            LevelTargetBundle targets
    ) {
        ResourceHandle<RenderTarget> main =
                targets.main;

        FramePass framePass = frame.addPass("Chest Visual Post");
        framePass.reads(main);
        ResourceHandle<RenderTarget> renderTargetResourceHandle =
                framePass.createsInternal("Chest Visual Target",
                        new RenderTargetDescriptor(main.get().width, main.get().height,
                                new RenderTargetDescriptor.TextureProperties(null, GpuFormat.RGBA8_UNORM),
                                null
                        )
                );

        framePass.executes(() -> {
            RenderTarget source = main.get();
            RenderTarget destination = renderTargetResourceHandle.get();

            try (RenderPass renderPass = RenderSystem.getDevice()
                    .createCommandEncoder().createRenderPass(() -> "Chest Visual Post",
                            destination.getColorTextureView(),
                            Optional.empty(), null, OptionalDouble.empty())
            ) {
                renderPass.setPipeline(RenderSystem.getCompiledPipeline(PIPELINE));
                RenderSystem.bindDefaultUniforms(
                        renderPass
                );
                renderPass.setUniform("Projection",
                        RenderSystem.getProjectionMatrixBuffer());
                renderPass.setUniform("Globals",
                        RenderSystem.getGlobalSettingsUniform());
                renderPass.setUniform("MainSampler", source.getColorTextureView(),
                        RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));
                renderPass.setUniform("DepthSampler",
                        source.getDepthTextureView(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));

                renderPass.setUniform("VisualColor", getTheGpuBuffer());
                renderPass.draw(3, 1, 0, 0
                );
            }
        });
        FramePass copyPass = frame.addPass("Chest Visual Copy");
        copyPass.reads(renderTargetResourceHandle);
        ResourceHandle<RenderTarget> newMain = copyPass.readsAndWrites(main);
        copyPass.executes(() -> {
            RenderTarget source = renderTargetResourceHandle.get();
            RenderTarget destination = newMain.get();
            destination.copyColorFrom(source);
        });
        targets.replace(LevelTargetBundle.MAIN_TARGET_ID, newMain);
    }
    private static void ensureBuffer() {

        if (theGpuBuffer != null) {
            return;
        }

        theGpuBuffer = RenderSystem
                .getDevice().createBuffer(() -> "Chest Item Visual Color",
                        GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_COPY_DST,
                        BUFFER_SIZE
                );
    }
    public static void upload(
            double cameraX,
            double cameraY,
            double cameraZ,
            Matrix4fc viewRotationMatrix
    ) {
        ensureBuffer();
        try (MemoryStack stack = MemoryStack.stackPush()) {
            ByteBuffer buffer = stack.malloc(BUFFER_SIZE);
            Std140Builder builder =
                    Std140Builder.intoBuffer(buffer);

            for (int i = 0; i < MAX_SIZE; i++) {
                if (i < visualVec3s.size()) {
                    VisualVec3 visualVec3 = visualVec3s.get(i);
                    float x = (float) (visualVec3.vec3.x - cameraX);
                    float y = (float) (visualVec3.vec3.y - cameraY);
                    float z = (float) (visualVec3.vec3.z - cameraZ);
                    Vector3f viewPosition = new Vector3f(x, y, z);
                    viewRotationMatrix.transformPosition(viewPosition);
                    builder.putVec4(viewPosition.x, viewPosition.y, viewPosition.z,visualVec3.valueStronger);
                } else {
                    builder.putVec4(0.0F, 0.0F, 0.0F,0.0f);
                }
            }
            for (int i = 0; i < MAX_SIZE; i++) {
                if (i < visualVec3s.size()) {
                    VisualVec3 visualVec3 = visualVec3s.get(i);
                    builder.putVec4(visualVec3.r, visualVec3.g, visualVec3.b, 1);
                } else {
                    builder.putVec4(0.0F, 0.0F, 0.0F, 0.0F);
                }
            }

            ByteBuffer data =
                    builder.get();

            RenderSystem.getDevice().createCommandEncoder()
                    .writeToBuffer(theGpuBuffer.slice(0, BUFFER_SIZE), data);
        }
    }
    public static GpuBuffer getTheGpuBuffer() {
        ensureBuffer();
        return theGpuBuffer;
    }
    public static void putVisualVec3(VisualVec3 visualVec3){
        visualVec3s.add(visualVec3);
    }

    public static void registerPipelines(
            RegisterRenderPipelinesEvent event
    ) {
        event.registerPipeline(
                PIPELINE
        );
    }

    public static void clear() {
        visualVec3s.clear();
    }

    public record VisualVec3(Vec3 vec3 , float valueStronger,float r,float g, float b){}
}