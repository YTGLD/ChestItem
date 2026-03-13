package com.ytgld.chest_item.items.memory;

import com.ytgld.chest_item.items.memory.items.Bluster;
import com.ytgld.chest_item.items.memory.items.Contradiction;
import com.ytgld.chest_item.items.memory.items.Extreme;
import com.ytgld.chest_item.items.memory.items.ForeverCurtain;
import com.ytgld.chest_item.items.other.GoldCheese;
import com.ytgld.chest_item.items.other.NuclearReaction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class MemoryEvent {
    @SubscribeEvent
    public  void tick(EntityTickEvent.Post event){
        ForeverCurtain.ForeverCurtainTooltip.tick(event);
    }
    @SubscribeEvent
    public void LivingDamageEvent(LivingDamageEvent.Pre event){
        Bluster.BlusterTooltip.damage(event);
    }
    @SubscribeEvent
    public void LivingDamageEvent(LivingIncomingDamageEvent event){
        Contradiction.ContradictionTooltip.damageRes(event);
    }
    @SubscribeEvent
    public void LivingExperienceDropEvent(LivingExperienceDropEvent event) {
        Extreme.ExtremeTooltip.expDrop(event);
    }
}
