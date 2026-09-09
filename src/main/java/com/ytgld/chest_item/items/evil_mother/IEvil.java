package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;

public interface IEvil extends IEvilGift {
    int color = Light.ARGB.color(255,80,120,105);
    default int theColor(){
        return Light.ARGB.color(50,80,120,105);
    }
    default boolean isDecay(){
        return false;
    }
    @Override
    default HashSet<EvilGiftBase> canHasEvilGift(){
        return new HashSet<>();
    }

    @Override
    default int maxGiftNumber(ItemStack stack){
        return 0;
    }
}