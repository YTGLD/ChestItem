package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.renderer.VowsSettingsUniform;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Unique
    private final VowsSettingsUniform chest26_2$vowsSettingsUniform = new VowsSettingsUniform();
    @Inject(at = @At(value = "RETURN"), method = "render")
    public void render(CallbackInfo ci) {
        chest26_2$vowsSettingsUniform.update();;
    }
    @Inject(at = @At(value = "RETURN"), method = "close")
    public void close(CallbackInfo ci) {
        chest26_2$vowsSettingsUniform.close();;
    }
}

