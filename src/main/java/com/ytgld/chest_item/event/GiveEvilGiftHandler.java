package com.ytgld.chest_item.event;

import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.Dawn;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.RottenUtensils;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.SnapString;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.Synthesizer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;

import java.util.HashSet;

public class GiveEvilGiftHandler {
    public static void event(LivingDeathEvent event){
        Synthesizer.event(event);
        Dawn.event(event);
        SnapString.event(event);
    }
    public static void event(LivingUseTotemEvent event){
        RottenUtensils.event(event);
    }

    public static void tick(ItemStack stack, Player player){
        if (stack.getItem() instanceof IEvilGift iEvilGift) {
            HashSet<EvilGiftBase> hashSet = iEvilGift.theGiftBase(stack);
            if (hashSet !=null &&!hashSet.isEmpty()) {
                for (EvilGiftBase evilGiftBase : hashSet.stream().toList()) {
                    evilGiftBase.tickGift(player,stack);
                }
            }
        }
    }
}
