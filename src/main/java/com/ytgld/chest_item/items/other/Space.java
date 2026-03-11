package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class Space extends ItemBase {
    public Space(Properties properties) {
        super(properties);
    }
    @Override
    public void text(ItemStack stack,Consumer<Component> tooltipAdder,TooltipFlag flag){
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,75,50,255);
    }
}


