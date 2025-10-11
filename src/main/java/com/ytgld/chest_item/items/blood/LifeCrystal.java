package com.ytgld.chest_item.items.blood;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class LifeCrystal extends ItemBase {
    public LifeCrystal(Properties properties) {
        super(properties);
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.Life_Crystal)) {
                    player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap());
                    break;
                } else {
                    player.getAttributes().removeAttributeModifiers(attributeModifierMultimap());
                }
            }
        }
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap() {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

            modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.Life_Crystal.asItem().getDescriptionId()),
                10, AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player) {
        return attributeModifierMultimap();
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,50,50);
    }
}
