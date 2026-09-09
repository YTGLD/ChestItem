package com.ytgld.chest_item.event;

import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.Dawn;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.RottenUtensils;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.SnapString;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.Synthesizer;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;

public class GiveEvilGiftHandler {
    public static void event(LivingDeathEvent event){
        Synthesizer.event(event);
        Dawn.event(event);
        SnapString.event(event);
    }
    public static void event(LivingUseTotemEvent event){
        RottenUtensils.event(event);
    }
}
