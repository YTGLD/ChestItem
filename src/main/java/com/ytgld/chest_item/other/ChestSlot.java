package com.ytgld.chest_item.other;

import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.items.black.celestial.TheCelestial;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ChestSlot extends Slot {
    public ChestSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (this.index == 9 ||
                this.index == 10 ||
                this.index == 11) {
            return stack.getItem() instanceof TheImprintOfTheSoul;
        }else {
            if (stack.getItem() instanceof ItemBase){
                if (stack.getItem() instanceof TheImprintOfTheSoul) {
                    return false;
                }
                return true;
            }else {
                return false;
            }
        }
    }
    @Override
    public boolean mayPickup(Player player) {
        ItemStack itemstack = this.getItem();
        if (player instanceof IPlayer iPlayer) {
            iPlayer.cI1_21_11$onRemoveItem(itemstack);
        }
        if (player.isCreative()) {
            return true;
        }
        if (itemstack.getItem() instanceof TheImprintOfTheSoul soul){
            return soul.canRemove(itemstack);
        }
        if (itemstack.getItem() instanceof TheCelestial soul){
            return soul.canRemove(itemstack);
        }
        return true;
    }

}
