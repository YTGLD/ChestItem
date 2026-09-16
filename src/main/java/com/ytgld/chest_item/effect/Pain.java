package com.ytgld.chest_item.effect;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Pain extends MobEffect {
    public Pain() {
        super(MobEffectCategory.BENEFICIAL, 0xff0000);
        this.addAttributeModifier(Attributes.MAX_HEALTH, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "pain"),2, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ARMOR, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "pain"),1, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "pain"),0.5F, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "pain"),0.05F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "pain"),0.03F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}

