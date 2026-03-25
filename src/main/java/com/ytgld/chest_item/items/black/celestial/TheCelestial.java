package com.ytgld.chest_item.items.black.celestial;

import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class TheCelestial extends ItemBlackShadow {


    public abstract Identifier img(ItemStack stack);
    public abstract int soulColor (ItemStack stack);
    public TheCelestial(Properties properties) {
        super(properties);
    }
    public boolean canRemove(ItemStack stack){
        return true;
    }
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        MutableComponent soul =  Component
                .translatable("chest_item.celestial")
                .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80EE82EE)));
        if (!canRemove(stack)){
            soul = Component
                    .translatable("chest_item.celestial.1")
                    .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80EE82EE)));
        }
        co.setStyle(Style.EMPTY.withColor(Light.ARGB.color(255, 255, 0, 100)));

        return soul.append(Component.literal("["))
                .append(co)
                .append(Component.literal("]"));
    }


    @Override
    public int color(ItemStack stack) {
        return soulColor(stack);
    }
}
