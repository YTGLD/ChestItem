package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.other.EvilGiftData;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public interface IEvil extends IEvilGift {
    int color = Light.ARGB.color(255,80,120,105);
    default int theColor(){
        return Light.ARGB.color(50,80,120,105);
    }
    default boolean isDecay(){
        return false;
    }

    @Override
    default EvilGiftData evilData(ItemStack stack){
        return new EvilGiftData(new HashSet<>());
    }

    @Override
    default HashSet<EvilGiftBase> canHasEvilGift(ItemStack stack){
        return new HashSet<>();
    }

    @Override
    default int maxGiftNumber(ItemStack stack){
        return 0;
    }

    @Override
    default HashSet<EvilGiftBase> theGiftBase(ItemStack stack){
        return new HashSet<>();
    }
}