package com.ytgld.chest_item.other;

import com.google.common.collect.Sets;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ClientAttReg;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.items.black.celestial.TheCelestial;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class ChestSlot extends Slot {
    public ChestSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    public Player player ;
    @Override
    public void onTake(Player player, ItemStack stack) {
        super.onTake(player, stack);
        onPlayerTake(player,this,stack);
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
    public void set(ItemStack stack) {

        ItemStack old = this.getItem();

        super.set(stack);

        if (old.isEmpty() && !stack.isEmpty()) {
            onPlayerPut(player, this, stack);
        }
    }

    private void onPlayerPut(Player player, Slot slot, ItemStack stack) {
        if (!(slot instanceof ChestSlot)) {
            return;
        }
        if (player == null) {
            return;
        }
        String s = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        Set<String> stringSet = player.getData(ClientAttReg.record.get());
        stringSet.add(s);
        player.setData(ClientAttReg.record,stringSet);
    }

    private void onPlayerTake(Player player, Slot slot, ItemStack stack) {
        if (!(slot instanceof ChestSlot)) {
            return;
        }
        if (player == null) {
            return;
        }
        Set<String> stringSet = player.getData(ClientAttReg.record.get());
        String s = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        stringSet.remove(s);
        player.setData(ClientAttReg.record, stringSet);
    }
    @Override
    public boolean mayPickup(Player player) {
        ItemStack itemstack = this.getItem();
        var va = player.getData(AttReg.itemRecord);
        va.add(
                BuiltInRegistries.ITEM.getKey(itemstack.getItem()).getNamespace()
                        + ":"
                        + BuiltInRegistries.ITEM.getKey(itemstack.getItem()).getPath()
        );
        player.setData(AttReg.itemRecord,va);

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
