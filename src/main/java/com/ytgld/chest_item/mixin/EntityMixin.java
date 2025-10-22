package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.black.soul.TheOrderOfTheUndead;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "isInvulnerableToBase", at = @At(value = "RETURN"), cancellable = true)
    private void isInvulnerableToBase(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this ;
        if (entity instanceof Player player) {
            if (player.hasEffect(Effects.invulnerable)) {
                cir.setReturnValue(true);
            }
            TheOrderOfTheUndead.immMagic(player,damageSource,cir);
        }
    }
}
