package com.ytgld.chest_item.items;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public interface Terror {
    int color(ItemStack stack);
    @Nullable
    Multimap<Holder<Attribute>, AttributeModifier> muAttribute();
}
