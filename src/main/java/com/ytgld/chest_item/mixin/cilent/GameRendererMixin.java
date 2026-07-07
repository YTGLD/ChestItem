package com.ytgld.chest_item.mixin.cilent;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(at = @At("RETURN"), method = "getNightVisionScale", cancellable = true)
    private static void getNightVisionScale(LivingEntity livingEntity, float nanoTime, CallbackInfoReturnable<Float> cir) {


    }


}
