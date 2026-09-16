package com.ytgld.chest_item.effect;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ShadowErosion extends MobEffect {
    public ShadowErosion() {
        super(MobEffectCategory.BENEFICIAL, Light.ARGB.color(255,155,20,155));
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, Identifier.fromNamespaceAndPath(Chestitem.MODID,"shadow_erosion"),
                -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, Identifier.fromNamespaceAndPath(Chestitem.MODID,"shadow_erosion"),
                -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        this.addAttributeModifier(Attributes.ATTACK_SPEED, Identifier.fromNamespaceAndPath(Chestitem.MODID,"shadow_erosion"),
                -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }
}

