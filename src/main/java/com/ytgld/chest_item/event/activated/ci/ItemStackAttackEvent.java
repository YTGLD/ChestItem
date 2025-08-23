package com.ytgld.chest_item.event.activated.ci;

import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public class ItemStackAttackEvent extends Event {
    public final LivingIncomingDamageEvent event;
    public final Player player;
    public final ChestInventory chestInventory;
    public ItemStackAttackEvent(LivingIncomingDamageEvent event, Player player, ChestInventory chestInventory){
        this.event = event;
        this.player = player;
        this.chestInventory = chestInventory;
    }

    public ChestInventory getStack() {
        return chestInventory;
    }

    public Player getPlayer() {
        return player;
    }

    public LivingIncomingDamageEvent getEvent() {
        return event;
    }
}
