package com.ytgld.chest_item.items.evil_mother.decay;

import com.ytgld.chest_item.items.evil_mother.EvilMother;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class DecayItem extends EvilMother implements IDecay{
    public DecayItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(theColor())));
        return co;
    }

    @Override
    public boolean isDecay() {
        return true;
    }
}
