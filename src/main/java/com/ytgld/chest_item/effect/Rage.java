package com.ytgld.chest_item.effect;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Rage extends MobEffect {
    public Rage() {
        super(MobEffectCategory.BENEFICIAL, 0xff0000);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"rage"),0.2f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"rage"),0.05f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"rage"),0.05f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(AttReg.heal, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"rage"),-0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}

