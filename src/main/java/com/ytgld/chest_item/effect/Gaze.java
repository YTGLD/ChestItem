package com.ytgld.chest_item.effect;

import com.ytgld.chest_item.items.evil_mother.IEvil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Gaze extends MobEffect {
    public Gaze() {
        super(MobEffectCategory.BENEFICIAL, IEvil.color);
    }
}
