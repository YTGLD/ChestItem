package com.ytgld.chest_item.effect;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class EvilErosion extends MobEffect {
    public EvilErosion() {
        super(MobEffectCategory.BENEFICIAL, Light.ARGB.color(255,155,20,155));
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, Identifier.fromNamespaceAndPath(Chestitem.MODID,"evil_erosion"),
                -0.225, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, Identifier.fromNamespaceAndPath(Chestitem.MODID,"evil_erosion"),
                -0.225, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(Attributes.ATTACK_SPEED, Identifier.fromNamespaceAndPath(Chestitem.MODID,"evil_erosion"),
                -0.225, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(AttReg.heal, Identifier.fromNamespaceAndPath(Chestitem.MODID,"evil_erosion"),
                -0.225, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(Attributes.ARMOR, Identifier.fromNamespaceAndPath(Chestitem.MODID,"evil_erosion"),
                -0.225, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(Attributes.MAX_HEALTH, Identifier.fromNamespaceAndPath(Chestitem.MODID,"evil_erosion"),
                -0.225, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}


