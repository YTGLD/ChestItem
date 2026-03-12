package com.ytgld.chest_item.items.memory;

import com.ytgld.chest_item.items.memory.items.Bluster;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class MemoryEvent {
    @SubscribeEvent
    public void LivingDamageEvent(LivingDamageEvent.Pre event){
        Bluster.BlusterTooltip.damage(event);
    }
}
