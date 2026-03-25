package com.ytgld.chest_item.items.meet;

import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class Heart  extends ItemBase implements Meat {
    public Heart(Properties properties) {
        super(properties);
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.heart.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.heart.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,135,105);
    }
}
