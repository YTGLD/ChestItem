package com.ytgld.chest_item.effect;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Eye extends MobEffect {
    public Eye() {
        super(MobEffectCategory.BENEFICIAL, 0xff0000);
    }
}
