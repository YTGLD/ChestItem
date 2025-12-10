package com.ytgld.chest_item.items.black;

import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class TheSoul extends TheImprintOfTheSoul {
    public TheSoul(Properties properties) {
        super(properties);
    }
    public boolean canRemove(ItemStack stack){
        return true;
    }

}
