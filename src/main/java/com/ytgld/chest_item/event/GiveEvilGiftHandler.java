package com.ytgld.chest_item.event;

import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.*;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.meat.EvilStomach;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.meat.Factory;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.meat.FortressCone;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.meat.UncleanCoins;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;

import java.util.HashSet;

public class GiveEvilGiftHandler {
    public static void event(LivingDeathEvent event){
        Synthesizer.event(event);
        Dawn.event(event);
        SnapString.event(event);
        EvilStomach.event(event);
        Factory.event(event);
    }
    public static void event(LivingUseTotemEvent event){
        RottenUtensils.event(event);
    }
    public static void event(LivingEntityUseItemEvent.Finish event){
        UncleanCoins.event(event);
        FortressCone.event(event);
        FortressCone.event_2(event);
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
