package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.function.Consumer;

public class VexRing extends ItemBase {

    public VexRing(Properties properties) {
        super(properties);
    }

    public static void tick(LivingDeathEvent event) {

    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.vex_ring.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.vex_ring.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.vex_ring.string.1").withStyle(ChatFormatting.GOLD));
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,75,125,255);
    }
}

