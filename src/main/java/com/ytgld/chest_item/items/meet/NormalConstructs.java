package com.ytgld.chest_item.items.meet;

import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 常态构造体
 * <p>
 * <p>
 +10%组织恢复
 * <p>
 +10%暗影恢复
 * <p>
 +10%chaos恢复
 */
public class NormalConstructs extends ItemBase implements Meat {
    public NormalConstructs(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);
        modifiers.put(AttReg.hyperplasia_speed, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.shadow_shield_speed, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.chaos_armor_speed, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }
}
