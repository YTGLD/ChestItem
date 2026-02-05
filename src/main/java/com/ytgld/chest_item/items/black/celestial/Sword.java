package com.ytgld.chest_item.items.black.celestial;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
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

public class Sword  extends TheCelestial{
    public Sword(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);

        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Sword_.asItem().getDescriptionId()),
                0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Sword_.asItem().getDescriptionId()),
                0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }

    @Override
    public Identifier img(ItemStack stack) {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/celestial/sword.png");
    }

    @Override
    public int soulColor(ItemStack stack) {
        return Light.ARGB.color(255,60,100,255);
    }
}

