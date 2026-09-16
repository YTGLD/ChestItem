package com.ytgld.chest_item.event.activated.ci;

import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

public class ItemStackTickEvent extends Event {
    public final Player player;
    public final ChestInventory chestInventory;
    public ItemStackTickEvent(Player player, ChestInventory chestInventory){
        this.player = player;
        this.chestInventory = chestInventory;
    }

    public ChestInventory getStack() {
        return chestInventory;
    }

    public Player getPlayer() {
        return player;
    }
}

