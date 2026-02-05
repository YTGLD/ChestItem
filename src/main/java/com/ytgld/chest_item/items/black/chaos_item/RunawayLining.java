package com.ytgld.chest_item.items.black.chaos_item;

import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.items.black.celestial.TheCelestial;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RunawayLining extends ItemBlackShadow implements IBlackLight, ITheChaos{
    public RunawayLining(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF0000)));
        return co;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            if (!flag.hasShiftDown()) {
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.0").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorText()))));
                tooltipAdder.accept(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.GOLD));
            }else {
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.literal(""));
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));

                tooltipAdder.accept(Component.translatable("item.chest_item.bloody_belt").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.corruption_crystal").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.evil_thoughts_forge_dreams").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.death_omen_stone_monument").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.dry_bones").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("chest_item.the_imprint_of_the_soul").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("chest_item.the_imprint_of_the_soul.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("chest_item.celestial").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));

            }
        }else {
            tooltipAdder.accept((Component.translatable("item.chest_item.runaway_lining.string.0")).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorText()))));
        }
    }

    public static Identifier identifier(ItemStack stack) {
        return Identifier.parse("runaway_lining_string:" + stack.getItem().getDescriptionId());
    }


    public static void addMap(Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap, Player player , ItemStack stack){
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (Handler.has(player,InitItems.RunawayLining_.asItem())) {

            attributeModifierMultimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(identifier(stack),
                    0.03, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            attributeModifierMultimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(identifier(stack),
                    0.02, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            attributeModifierMultimap.put(Attributes.MAX_HEALTH, new AttributeModifier(identifier(stack),
                    0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

            if (stack.is(InitItems.BloodyBelt_)
                    || stack.is(InitItems.CorruptionCrystal_)
                    || stack.is(InitItems.EvilThoughtsForgeDreams_)
                    || stack.is(InitItems.DeathOmenStoneMonument_)
                    || stack.is(InitItems.DryBones_)
                    || stack.getItem() instanceof TheCelestial
                    || stack.getItem() instanceof TheImprintOfTheSoul
            ) {
                if (compoundTag != null) {
                    compoundTag.putBoolean(IBlackLight.blackName,true);
                }else {
                    stack.set(DataReg.tag,new CompoundTag());
                }
            }
        }
        if (compoundTag != null) {
            if (compoundTag.getBooleanOr(IBlackLight.blackName, false)) {
                if (stack.is(InitItems.BloodyBelt_)) {
                    attributeModifierMultimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(identifier(stack),
                            2, AttributeModifier.Operation.ADD_VALUE));
                    attributeModifierMultimap.put(Attributes.MAX_HEALTH, new AttributeModifier(identifier(stack),
                            4, AttributeModifier.Operation.ADD_VALUE));

                }
                if (stack.is(InitItems.CorruptionCrystal_)) {
                    attributeModifierMultimap.put(AttReg.looting, new AttributeModifier(identifier(stack),
                            1, AttributeModifier.Operation.ADD_VALUE));
                    attributeModifierMultimap.put(AttReg.fortune, new AttributeModifier(identifier(stack),
                            1, AttributeModifier.Operation.ADD_VALUE));

                    attributeModifierMultimap.put(AttReg.shadow_shield, new AttributeModifier(identifier(stack),
                            4, AttributeModifier.Operation.ADD_VALUE));
                    attributeModifierMultimap.put(AttReg.shadow_shield_stronger, new AttributeModifier(identifier(stack),
                            0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                }
                if (stack.is(InitItems.EvilThoughtsForgeDreams_)) {
                    attributeModifierMultimap.put(AttReg.shadow_shield_stronger, new AttributeModifier(identifier(stack),
                            0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(AttReg.shadow_shield, new AttributeModifier(identifier(stack),
                            0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(AttReg.shadow_shield_speed, new AttributeModifier(identifier(stack),
                            0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                }
                if (stack.is(InitItems.DeathOmenStoneMonument_)) {
                    attributeModifierMultimap.put(Attributes.MAX_HEALTH, new AttributeModifier(identifier(stack),
                            0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                    attributeModifierMultimap.put(Attributes.ARMOR, new AttributeModifier(identifier(stack),
                            0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

                }
                if (stack.is(InitItems.DryBones_)) {
                    attributeModifierMultimap.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(identifier(stack),
                            0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(identifier(stack),
                            0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(identifier(stack),
                            0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(Attributes.ARMOR, new AttributeModifier(identifier(stack),
                            0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                }
                if (stack.getItem() instanceof TheCelestial) {
                    attributeModifierMultimap.put(AttReg.heal, new AttributeModifier(identifier(stack),
                            0.05F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(AttReg.more_speed, new AttributeModifier(identifier(stack),
                            0.02F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                }
                if (stack.getItem() instanceof TheImprintOfTheSoul) {
                    attributeModifierMultimap.put(AttReg.chaos_armor, new AttributeModifier(identifier(stack),
                            2, AttributeModifier.Operation.ADD_VALUE));
                    attributeModifierMultimap.put(AttReg.chaos_armor_min, new AttributeModifier(identifier(stack),
                            -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

                }
            }
        }
    }
}
