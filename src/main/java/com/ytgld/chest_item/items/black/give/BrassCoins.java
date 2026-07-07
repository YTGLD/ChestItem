package com.ytgld.chest_item.items.black.give;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class BrassCoins extends ItemBase {

    public BrassCoins(Properties properties) {
        super(properties);
    }

    public static final String kill = "killString";


    public static void die(LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.BrassCoins_)) {
                        CompoundTag component = stack.get(DataReg.tag);
                        if (component != null) {
                            component.putInt(kill,component.getInt(kill)+1);
                            return;
                        }else {
                            stack.set(DataReg.tag,new CompoundTag());
                        }

                    }
                }
            }
        }
    }

    public static boolean isTrue (ItemStack aBrassCoins,int max){
        CompoundTag component = aBrassCoins.get(DataReg.tag);
        if (component != null) {
            return component.getInt(kill) >= max;
        }
        return false;
    }
}

