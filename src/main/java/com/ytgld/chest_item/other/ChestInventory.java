package com.ytgld.chest_item.other;

import com.ytgld.chest_item.items.ItemBase;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ChestInventory extends SimpleContainer {
    public ChestInventory(Player player) {
        super(9);
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

    public void drop(Player player){
        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                if (!(itemstack.getItem() instanceof ItemBase)){
                    int count = itemstack.getCount();
                    player.drop(itemstack.copy(),false);
                    itemstack.shrink(count);
                }
            }
        }
    }

    @Override
    public void stopOpen(Player player) {
        super.stopOpen(player);
        drop(player);
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

