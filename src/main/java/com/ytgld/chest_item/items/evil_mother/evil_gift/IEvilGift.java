package com.ytgld.chest_item.items.evil_mother.evil_gift;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.other.EvilGiftData;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;

public interface IEvilGift {

    HashSet<EvilGiftBase> canHasEvilGift();
    int maxGiftNumber(ItemStack stack);


    default HashSet<EvilGiftBase> theGiftBase(ItemStack stack){
        HashSet<EvilGiftBase> evilGiftBases = new HashSet<>();

        EvilGiftData evilGiftData = evilData(stack);
        Registry<EvilGiftBase> registry =  EvilGifts.GiftRegister;
        if (evilGiftData == null) {
            return evilGiftBases;
        }
        for (String string : evilGiftData.hashSet()){
            EvilGiftBase evilGiftBase =
                    registry.getValue(Identifier.parse(
                            string
                    ));
            evilGiftBases.add(evilGiftBase);
        }
        return evilGiftBases;
    }
    default EvilGiftData evilData(ItemStack stack){
        EvilGiftData data = stack.get(DataReg.evil_gift.get());
        if (data == null) {
            stack.set(DataReg.evil_gift,new EvilGiftData(new HashSet<>()));
        }
        return data;
    }


    default void addGift(ItemStack stack , EvilGiftBase giftBase){
        EvilGiftData evilGiftData = stack.get(DataReg.evil_gift.get());
        if (evilGiftData != null) {
            if (!canHasEvilGift().contains(giftBase)) {
                return;
            }
            if (theGiftBase(stack).contains(giftBase)) {
                return;
            }
            if (evilGiftData.hashSet().size() < maxGiftNumber(stack)) {
                evilGiftData.add(giftBase.id().toString());
                upData(stack);
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
    static void upData(ItemStack stack){
        stack.set(DataReg.evil_gift.get(),stack.get(DataReg.evil_gift.get()));
    }

}
