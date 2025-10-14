package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;

public abstract class MRender extends RenderType {
    public MRender(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }

    private static ShaderInstance liveShaderInstance;
    public static void setShaderInstance_liveShaderInstance(ShaderInstance live) {
        liveShaderInstance = live;
    }

    public static ShaderInstance getLiveShaderInstance() {
        return liveShaderInstance;
    }
}

