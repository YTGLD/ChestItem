package com.ytgld.chest_item;

import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.IPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class Handler {
    public static void openItemChest(Player player){
        if (player instanceof IPlayer iPlayer) {
            player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.CHEST_OPEN, SoundSource.AMBIENT,1,1);
            player.openMenu(new SimpleMenuProvider(
                    (i, inventory, p_53126_) -> ChestMenu.threeRows(i, inventory,
                            iPlayer.chest_item$chestInventory().get()), Component.translatable("container.chest_item.chest")
            ));
        }
    }
    public static @Nullable ChestInventory getItem(Player player){
        if (player instanceof IPlayer iPlayer) {
            return iPlayer.chest_item$chestInventory().get();
        }
        return null;
    }
}
