package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.black.chaos_item.ChaosFortress;
import com.ytgld.chest_item.items.black.soul.TheOrderOfTheUndead;
import com.ytgld.chest_item.items.evil_mother.EvilBelt;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "isInvulnerableTo", at = @At(value = "RETURN"), cancellable = true)
    private void isInvulnerableToBase(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this ;
        if (entity instanceof Player player) {
            if (player.hasEffect(Effects.invulnerable)) {
                cir.setReturnValue(true);
            }
            TheOrderOfTheUndead.immMagic(player,damageSource,cir);
            ChaosFortress.isInvulnerableToBase(player,damageSource,cir);
        }
    }
    @Inject(method = "canBeCollidedWith", at = @At(value = "RETURN"), cancellable = true)
    private void canBeCollidedWith(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this ;
        EvilBelt.canBeCollidedWith(entity,cir);
    }
    @Inject(method = "isPushable", at = @At(value = "RETURN"), cancellable = true)
    private void isPushable(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this ;
        EvilBelt.canBeCollidedWith(entity,cir);
    }
    @Inject(method = "push(DDD)V", at = @At(value = "HEAD"), cancellable = true)
    private void push(double x, double y, double z, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this ;
        EvilBelt.push(entity,ci);
    }
}
