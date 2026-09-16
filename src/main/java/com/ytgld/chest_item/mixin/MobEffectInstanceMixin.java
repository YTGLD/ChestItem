package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.other.IMobEffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobEffectInstance.class)
public class MobEffectInstanceMixin implements IMobEffectInstance {
    @Shadow
    private int duration;

    @Override
    public void ci$setTime(int time) {
        this.duration = time;
    }
}
