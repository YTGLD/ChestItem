package com.ytgld.chest_item.items;

import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemBlackShadow extends ItemBase{

    public ItemBlackShadow(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD)));
        return co;
    }
    public int colorText(){
        int s = (int) (100 * Math.sin(EventMain.time / 20f / 4f));
        int f = (int) (100 * Math.sin(EventMain.time / 40f / 4f));
        int c = (int) (20 * Math.sin(EventMain.time / 60f / 4f));
        if (c<0) {
            c = 0;
        }
        return Light.ARGB.color(230 + c,255, 55 + f/2, 105 + s);
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255, 100, 40, 255);
    }
}
