package com.ytgld.chest_item.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class UtilEvent {

    @SubscribeEvent
    public void event(LivingChangeTargetEvent event){}
}
