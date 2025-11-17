package com.ytgld.chest_item.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface Attrib {
   @Nullable
   Multimap<Holder<Attribute>, AttributeModifier> chestModifierMultimap(Player player, ItemStack stack);
}
