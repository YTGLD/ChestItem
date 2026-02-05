package com.ytgld.chest_item.items.meet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class LifeCoin  extends ItemBase implements Meat {
    public LifeCoin(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);
        modifiers.put(AttReg.hyperplasia, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.LifeCoin_.asItem().getDescriptionId()),
                1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(AttReg.hyperplasia_stronger, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.LifeCoin_.asItem().getDescriptionId()),
                -0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }
}

