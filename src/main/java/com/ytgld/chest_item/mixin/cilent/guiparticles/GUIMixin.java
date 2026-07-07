package com.ytgld.chest_item.mixin.cilent.guiparticles;


import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesRenderer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GUIMixin {

    @Inject(at = @At(value = "RETURN"),method = "render")
    private void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        guiGraphics.pose().pushPose();
        BlackParticlesRenderer.onRenderGui(guiGraphics,guiGraphics.pose());
        guiGraphics.pose().popPose();
    }
}
