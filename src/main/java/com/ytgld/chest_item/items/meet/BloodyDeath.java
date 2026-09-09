package com.ytgld.chest_item.items.meet;

import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 血殇
 * <p>
 * <p>
 *
 +4增生组织
 * <p>
 +35%组织恢复
 * <p>
 -20%治疗
 */

 public class BloodyDeath extends ItemBase implements Meat {
    public BloodyDeath(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);
        modifiers.put(AttReg.heal, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.hyperplasia, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                4, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.hyperplasia_speed, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                -0.35, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }
}
