package com.ytgld.chest_item.items.black.give;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class DevilCoins extends ItemBase {

    public DevilCoins(Properties properties) {
        super(properties);
    }
    @Override
    public void text(ItemStack stack,Consumer<Component> tooltipAdder,TooltipFlag flag){
        tooltipAdder.accept(Component.translatable("item.chest_item.devil_coins.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
    }
}
