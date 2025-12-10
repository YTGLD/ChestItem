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
        this.addChestGrid(container, 8, 0);
        int j = 18 + this.containerRows * 18 + 13;
        this.addStandardInventorySlots(playerInventory, 8, j);
    }

    private void addChestGrid(Container container, int x, int y) {
        for (int j = 0; j < 12; ++j) {
            this.addSlot(new ChestSlot(container, j, x + j * 18, y + 18));
        }
        for (int j = 0; j < 5; ++j) {
            this.addSlot(new ChestSlot(container, j + 12, x + j * 18, y + 36));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 12) {
                if (!this.moveItemStackTo(itemstack1, 12, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 17) {
                if (!this.moveItemStackTo(itemstack1, 0, 12, false)) { // 尝试将物品移动到第一行
                    return ItemStack.EMPTY;
                }
            } else {
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

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
    public int getRowCount() {
        return this.containerRows;
    }
}

