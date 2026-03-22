package com.ytgld.chest_item.other;

import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicReference;

public interface IPlayer {
    AtomicReference<ChestInventory> chest_item$chestInventory();

    void cI1_21_11$onRemoveItem(ItemStack itemStack);
}
