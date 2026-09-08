package com.ytgld.chest_item.items.evil_mother.evil_gift;

import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.other.EvilGiftData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public interface IEvilGift {
    HashSet<EvilGiftBase> theGiftBase(ItemStack stack);
    HashSet<EvilGiftBase> canHasEvilGift(ItemStack stack);
    int maxGiftNumber(ItemStack stack);



    default EvilGiftData evilData(ItemStack stack){
        EvilGiftData data = stack.get(DataReg.evil_gift.get());
        if (data == null) {
            stack.set(DataReg.evil_gift,new EvilGiftData(new HashSet<>()));
        }
        return data;
    }


    static void addGift(ItemStack stack , EvilGiftBase giftBase){
        EvilGiftData evilGiftData = stack.get(DataReg.evil_gift.get());
        if (stack.getItem() instanceof IEvilGift gift &&  evilGiftData != null) {
            if (!gift.canHasEvilGift(stack).contains(giftBase)) {
                return;
            }
            if (evilGiftData.hashSet().size() < gift.maxGiftNumber(stack)) {
                evilGiftData.add(giftBase.id());
            }
        }
    }
    static boolean isHasEvilGift(ItemStack stack , EvilGiftBase giftBase){
        if (stack.getItem() instanceof IEvilGift iEvilGift) {
            EvilGiftData evilGiftData = iEvilGift.evilData(stack);
            return evilGiftData.hashSet().contains(giftBase.id());
        }
        return false;
    }

}
