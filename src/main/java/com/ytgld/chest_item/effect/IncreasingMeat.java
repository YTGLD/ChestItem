package com.ytgld.chest_item.effect;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class IncreasingMeat extends MobEffect {
    public IncreasingMeat() {
        super(MobEffectCategory.BENEFICIAL, 0xff0000);
        this.addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"increasing_meat"),1.5, AttributeModifier.Operation.ADD_VALUE);
    }
}
