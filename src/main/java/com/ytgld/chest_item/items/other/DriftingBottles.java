package com.ytgld.chest_item.items.other;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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

public class DriftingBottles extends ItemBase {
    public DriftingBottles(Properties properties) {
        super(properties);
    }

    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.DriftingBottles_)) {
                    player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap(player));
                    break;
                }else {
                    player.getAttributes().removeAttributeModifiers(attributeModifierMultimap(player));
                }
            }
        }
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player, ItemStack stack) {
        return attributeModifierMultimap(player);
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float g = 0;
        if (player.isInWater()) {
            g = -0.95f;
        }

        modifiers.put(Attributes.GRAVITY, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DriftingBottles_.asItem().getDescriptionId()),
                g, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));


        modifiers.put(Attributes.WATER_MOVEMENT_EFFICIENCY, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DriftingBottles_.asItem().getDescriptionId()),
                0.5, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(NeoForgeMod.SWIM_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DriftingBottles_.asItem().getDescriptionId()),
                1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));


        modifiers.put(Attributes.OXYGEN_BONUS, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DriftingBottles_.asItem().getDescriptionId()),
                3, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        if (tooltipFlag.hasShiftDown()) {

            tooltipComponents.add(Component.translatable("item.chest_item.drifting_bottles.string.2").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80DAA520))));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_item.drifting_bottles.string.3").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80DAA520))));
            tooltipComponents.add(Component.translatable("item.chest_item.drifting_bottles.string.4").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80DAA520))));
            tooltipComponents.add(Component.translatable("item.chest_item.drifting_bottles.string.5").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80DAA520))));
        }else {
            tooltipComponents.add(Component.translatable("options.key.hold").append(Component.translatable("key.keyboard.left.shift")).withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.literal(""));
            tooltipComponents.add(Component.translatable("item.chest_item.drifting_bottles.string.1").withStyle(ChatFormatting.GOLD));
        }
    }


    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,50,100,50);
    }
}

