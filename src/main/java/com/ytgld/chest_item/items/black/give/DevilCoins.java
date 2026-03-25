package com.ytgld.chest_item.items.black.give;

import com.ytgld.chest_item.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class DevilCoins extends ItemBase {

    public DevilCoins(Properties properties) {
        super(properties);
    }

    public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.devil_coins.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
    }
}
