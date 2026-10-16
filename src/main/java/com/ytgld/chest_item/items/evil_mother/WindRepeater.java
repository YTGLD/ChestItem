package com.ytgld.chest_item.items.evil_mother;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.Consumer;

public class WindRepeater extends EvilMother{
    public WindRepeater(Properties properties) {
        super(properties);
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.wind_repeater.string.1").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.wind_repeater.string.2").withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public int getSanity() {
        return -2;
    }
}
