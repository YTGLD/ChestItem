package com.ytgld.chest_item.items.end;

import com.ytgld.chest_item.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

/**
 *仿生血灵将不计后果的攻击附近生物
 * <p>
 *  仿生血灵的伤害和射速都将有所增加
 */
public class EndEffect   extends ItemBase {
    public EndEffect(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.end_effect.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.end_effect.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.end_effect.string.2").withStyle(ChatFormatting.GOLD));
    }
}
