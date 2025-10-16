package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.items.black.soul.TheOrderOfTheUndead;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "canBeAffected", at = @At(value = "RETURN"),cancellable = true)
    private void canBeAffected(MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this ;
        if (entity instanceof Player player) {
            TheOrderOfTheUndead.canHasEffect(player,effectInstance,cir);
        }
    }
    @Inject(method = "isInvertedHealAndHarm", at = @At(value = "RETURN"), cancellable = true)
    private void isInvertedHealAndHarm(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this ;
        if (entity instanceof Player player) {
            TheOrderOfTheUndead.healAndMagicDamage(player,cir);
        }
    }
}
