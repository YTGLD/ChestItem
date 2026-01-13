package com.ytgld.chest_item.items.black.celestial;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
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

public class NineDome extends TheCelestial{
    public NineDome(Properties properties) {
        super(properties);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.NineDome_.asItem().getDescriptionId()),
                0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }

    @Override
    public ResourceLocation img(ItemStack stack) {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/celestial/nine_dome.png");
    }

    @Override
    public int soulColor(ItemStack stack) {
        return Light.ARGB.color(255,255,100,20);
    }
}
