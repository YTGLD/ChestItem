package com.ytgld.chest_item.renderer;

import com.mojang.renderpearl.api.buffers.GpuBuffer;
import org.jspecify.annotations.Nullable;

public final class VowsGlobals {
    private static @Nullable GpuBuffer gpuBuffer;

    public static void setGpuBuffer(GpuBuffer buffer) {
        gpuBuffer = buffer;
    }

    public static @Nullable GpuBuffer getGpuBuffer() {
        return gpuBuffer;
    }

}