package com.ytgld.chest_item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OwnerLead extends ItemBase {
    public OwnerLead(Properties properties) {
        super(properties);
    }

    public static final String name = "compoundTagMyLoveCharm";

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        return attributeModifierMultimap();
    }

    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap() {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                0.05F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                2, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(NeoForgeMod.SWIM_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                0.15F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                0.08F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                4, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.fortune, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                1, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.looting, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                1, AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player, ItemStack stack) {
        return attributeModifierMultimap();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            tooltipAdder.add(Component.literal(compoundTag.getString(name)).append(Component.literal("的礼物！").withStyle(ChatFormatting.GOLD)));
        }
    }
}
