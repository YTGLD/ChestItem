package com.ytgld.chest_item.other;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.ClientAttReg;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class ChestItemMenu extends AbstractContainerMenu {
    private final Container container;
    private final int containerRows;

    public ChestItemMenu(int containerId, Inventory playerInventory, Container container, int rows) {
        super(ChestMenuTypes.GENERIC_12.get(), containerId);
        checkContainerSize(container, rows * 12);
        this.container = container;
        this.containerRows = rows;
        container.startOpen(playerInventory.player);
        int i = (this.containerRows - 4) * 18;
        this.addChestGrid(container, 8, 0);
        int i1;
        int j1;
        for(i1 = 0; i1 < 3; ++i1) {
            for(j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + i1 * 9 + 9, 8 + j1 * 18, 103 + i1 * 18 + i));
            }
        }

        for(i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
        }

    }
    private void addChestGrid(Container container, int x, int y) {
        for (int j = 0; j < 12; ++j) {
            this.addSlot(new ChestSlot(container, j, x + j * 18, y + 18));
        }
        for (int j = 0; j < 5; ++j) {
            this.addSlot(new ChestSlot(container, j + 12, x + j * 18, y + 36));
        }
    }
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void clicked(
            int slotId,
            int button,
            ClickType clickType,
            Player player
    ) {
        if(slotId >= 0 && this.getSlot(slotId) instanceof ChestSlot slot){
            slot.player = player;
        }
        super.clicked(slotId, button, clickType, player);
    }

    public ItemStack quickMoveStack(Player player, int index) {
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

    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public Container getContainer() {
        return this.container;
    }

    public int getRowCount() {
        return this.containerRows;
    }
}

