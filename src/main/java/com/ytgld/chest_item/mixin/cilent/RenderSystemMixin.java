package com.ytgld.chest_item.mixin.cilent;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ytgld.chest_item.renderer.VowsGlobals;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    @Inject(at = @At(value = "RETURN"), method = "bindDefaultUniforms")
    private static void render(RenderPass renderPass, CallbackInfo ci) {
        GpuBuffer globalUniform = VowsGlobals.getGpuBuffer();
        if (globalUniform != null) {
            renderPass.setUniform("ChestVowsGlobals", globalUniform);
        }
    }
}
