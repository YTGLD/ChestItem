package com.ytgld.chest_item.items;

import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;

public interface OtherGift extends IEvilGift {
    @Override
    default HashSet<EvilGiftBase> canHasEvilGift(){
        return new HashSet<>();
    }

    @Override
    default int maxGiftNumber(ItemStack stack){
        return 0;
    }
}
