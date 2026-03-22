package com.ytgld.chest_item.items.black;

import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import net.minecraft.world.item.ItemStack;

public abstract class TheSoul extends TheImprintOfTheSoul {
    public TheSoul(Properties properties) {
        super(properties);
    }
    public boolean canRemove(ItemStack stack){
        return true;
    }

}
