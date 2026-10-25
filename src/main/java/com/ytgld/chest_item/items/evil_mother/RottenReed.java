package com.ytgld.chest_item.items.evil_mother;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RottenReed extends EvilMother{
    public RottenReed(Properties properties) {
        super(properties);
    }

    @Override
    public int getSanity() {
        return -5;
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.LUCK, new AttributeModifier(identifier(),
                3, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(AttReg.shadow_shield, new AttributeModifier(identifier(),
                0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        modifiers.put(AttReg.shadow_shield_stronger, new AttributeModifier(identifier(),
                0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        modifiers.put(AttReg.heal, new AttributeModifier(identifier(),
                -0.1f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }


}
