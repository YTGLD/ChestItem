package com.ytgld.chest_item.mixin.cilent.guiparticles;


import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Inject(at = @At(value = "RETURN"),method = "extractRenderState")
    private void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        BlackParticlesRenderer.onRenderGui(graphics,graphics.pose());
    }
}
