package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.items.other.NuclearReaction;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {
    @Inject(method = "playerTouch", at = @At(value = "RETURN"))
    private void playerTouch(Player entity, CallbackInfo ci) {
        NuclearReaction.applyExp(entity,(ExperienceOrb) (Object) this );
    }
}
