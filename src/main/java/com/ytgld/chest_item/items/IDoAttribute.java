package com.ytgld.chest_item.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IDoAttribute {
    default Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player){
        return HashMultimap.create();
    }
}
