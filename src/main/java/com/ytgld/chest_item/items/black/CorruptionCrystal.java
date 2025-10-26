package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class CorruptionCrystal  extends ItemBlackShadow {
    public CorruptionCrystal(Properties properties) {
        super(properties);
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.CorruptionCrystal_)) {
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

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.CorruptionCrystal_.asItem().getDescriptionId()),
                6, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.shadow_shield, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.CorruptionCrystal_.asItem().getDescriptionId()),
                6, AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return attributeModifierMultimap();
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        if (flag.hasShiftDown()) {
            tooltipAdder.accept(Component.translatable("item.chest_item.corruption_crystal.string.2").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.corruption_crystal.string.3").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.corruption_crystal.string.4").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        }else {
            tooltipAdder.accept(Component.translatable("options.key.hold").append(Component.translatable("key.keyboard.left.shift")).withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,0,0);
    }
}

