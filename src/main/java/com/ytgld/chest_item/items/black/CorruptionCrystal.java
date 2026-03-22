package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

public class CorruptionCrystal extends ItemBlackShadow {
    public CorruptionCrystal(Properties properties) {
        super(properties);
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.CorruptionCrystal_.asItem().getDescriptionId()),
                6, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(AttReg.shadow_shield, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.CorruptionCrystal_.asItem().getDescriptionId()),
                6, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(AttReg.fortune, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.CorruptionCrystal_.asItem().getDescriptionId()),
                1, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(AttReg.looting, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.CorruptionCrystal_.asItem().getDescriptionId()),
                1, AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        if (flag.hasShiftDown()) {
            tooltipComponents.accept(Component.translatable("item.chest_item.corruption_crystal.string.2").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipComponents.accept(Component.translatable("item.chest_item.corruption_crystal.string.3").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipComponents.accept(Component.translatable("item.chest_item.corruption_crystal.string.4").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        }else {
            tooltipComponents.accept(Component.translatable("options.key.hold").append(Component.translatable("key.keyboard.left.shift")).withStyle(ChatFormatting.GOLD));
        }
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,0,0);
    }
}

