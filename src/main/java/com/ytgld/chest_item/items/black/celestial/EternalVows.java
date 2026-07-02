package com.ytgld.chest_item.items.black.celestial;

import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.items.black.soul.NotLight;
import com.ytgld.chest_item.renderer.RenderBlackSoul;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class EternalVows extends ItemBlackShadow implements NotLight {

    public abstract List<RenderBlackSoul.ColorAndImage> colorAndImage();

    public EternalVows(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        MutableComponent soul =  Component
                .translatable("chest_item.eternal_vows")
                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80EE82EE)));
        co.setStyle(Style.EMPTY.withColor(Light.ARGB.color(255, 255, 0, 100)));

        return soul.append(Component.literal("<").withStyle(ChatFormatting.GRAY))
                .append(co)
                .append(Component.literal(">").withStyle(ChatFormatting.GRAY));
    }


    @Override
    public int color(ItemStack stack) {
        return 0;
    }
}
