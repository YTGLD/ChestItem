package com.ytgld.chest_item.items.other;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Stone extends ItemBase {

    public Stone(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);
        modifiers.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Stone_.asItem().getDescriptionId()),
                5, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ARMOR, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Stone_.asItem().getDescriptionId()),
                5, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Stone_.asItem().getDescriptionId()),
                2, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,255,0);
    }
}
