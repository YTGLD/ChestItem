package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;

public abstract class MRender extends RenderType {
    public MRender(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
    private static ShaderInstance liveShaderInstance;
    private static ShaderInstance liveShaderInstance_slowness;
    public static void setShaderInstance_liveShaderInstance(ShaderInstance live) {
        liveShaderInstance = live;
    }

    public static ShaderInstance getLiveShaderInstance() {
        return liveShaderInstance;
    }

    public static ShaderInstance getLiveShaderInstance_slowness() {
        return liveShaderInstance_slowness;
    }

    public static void setLiveShaderInstance_slowness(ShaderInstance liveShaderInstance_slowness) {
        MRender.liveShaderInstance_slowness = liveShaderInstance_slowness;
    }
}

