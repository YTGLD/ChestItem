package com.ytgld.chest_item.items;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface Terror {
    int color(ItemStack stack);

    @Nullable
    default Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap() {
        return null;
    }
}
