package com.ytgld.chest_item.other;

import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.EnderChestBlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import javax.annotation.Nullable;
import java.util.Iterator;

public class ChestInventory extends SimpleContainer {
    public ChestInventory(Player player) {
        super(27);
    }
    public void fromSlots(ValueInput.TypedInputList<ItemStackWithSlot> input) {
        for(int i = 0; i < this.getContainerSize(); ++i) {
            this.setItem(i, ItemStack.EMPTY);
        }

        for (ItemStackWithSlot itemstackwithslot : input) {
            if (itemstackwithslot.isValidInContainer(this.getContainerSize())) {
                this.setItem(itemstackwithslot.slot(), itemstackwithslot.stack());
            }
        }
    }

    public ItemStack stack(int get){
        int max = this.getContainerSize();
        if (get > max) {
            get =max;
        }
        return getItem(get);
    }
    public void storeAsSlots(ValueOutput.TypedOutputList<ItemStackWithSlot> output) {
        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                output.add(new ItemStackWithSlot(i, itemstack));
            }
        }

    }
}

