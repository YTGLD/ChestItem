package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.SmallSun;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class Test extends ItemBlackShadow {
    public static final String number = "sunNumber";

    public Test(Properties properties) {
        super(properties);
    }

    public static void die(LivingDamageEvent.Pre event) {

    }
    public static void die(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.Test_)) {
                        if (event.getEntity() instanceof LivingEntity entity) {
                            SmallSun smallSun = new SmallSun(Entitys.SmallSun_.get(), player.level());
                            smallSun.setPos(entity.position().add(0, 10, 0));
                            smallSun.setOwner(player);
                            entity.level().addFreshEntity(smallSun);
                        }
                    }
                }
            }
        }
    }
}
