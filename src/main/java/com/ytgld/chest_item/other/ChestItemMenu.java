package com.ytgld.chest_item.other;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ChestItemMenu extends AbstractContainerMenu {
    private final Container container;
    private final int containerRows;

    public ChestItemMenu( int containerId, Inventory playerInventory, Container container, int rows) {
        super(ChestMenuTypes.GENERIC_12.get(), containerId);
        checkContainerSize(container, rows * 12);
        this.container = container;
        this.containerRows = rows;
        container.startOpen(playerInventory.player);
        this.addChestGrid(container, 8, 18);
        int j = 18 + this.containerRows * 18 + 13;
        this.addStandardInventorySlots(playerInventory, 8, j);
    }

    private void addChestGrid(Container container, int x, int y) {
        for(int i = 0; i < this.containerRows; ++i) {
            for(int j = 0; j < 12; ++j) {
                this.addSlot(new ChestSlot(container, j + i * 9, x + j * 18, y + i * 18));
            }
        }

    }

    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.containerRows * 12) {
                if (!this.moveItemStackTo(itemstack1, this.containerRows * 12, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * 12, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
    public int getRowCount() {
        return this.containerRows;
    }
}

