package com.ytgld.chest_item.items.gold;

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

public class SeparateRune extends ItemBase implements IGold {
    public SeparateRune(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);

        modifiers.put(Attributes.ARMOR, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Separate_Rune.asItem().getDescriptionId()),
                -0.25f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Separate_Rune.asItem().getDescriptionId()),
                0.25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }
    @Override
    public int guiColor(ItemStack stack) {
        return Light.ARGB.color(200,255,50,50);
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,255,20);
    }
}




