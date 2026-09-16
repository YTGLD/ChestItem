package com.ytgld.chest_item.items.meet;

import com.ytgld.chest_item.items.OtherGift;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;

public interface MeetGift extends OtherGift {
    @Override
    default HashSet<EvilGiftBase> canHasEvilGift(){
        HashSet<EvilGiftBase> set = new HashSet<>();
        set.add(EvilGifts.evil_stomach.get());
        set.add(EvilGifts.factory.get());
        set.add(EvilGifts.unclean_coins.get());
        set.add(EvilGifts.fortress_cone.get());
        return set;
    }

    @Override
    default int maxGiftNumber(ItemStack stack){
        return 1;
    }
}
