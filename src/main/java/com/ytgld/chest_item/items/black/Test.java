package com.ytgld.chest_item.items.black;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.EndComing;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.entity.LaserColumn;
import com.ytgld.chest_item.entity.Reactor;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.items.SkillItem;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Test extends ItemBlackShadow implements SkillItem {
    public Test(Properties properties) {
        super(properties);
    }
    public static final String chestHasReactor= "ChestHasReactor";
    public static void onKeyIsDown(Player player){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.Test_)) {
                        CompoundTag compoundTag = stack.get(DataReg.tag);
                        if (compoundTag!=null) {
                            if (!compoundTag.getBooleanOr(chestHasReactor,false)) {
                                Reactor reactor = new Reactor(Entitys.Reactor_.get(), player.level());
                                reactor.setPos(player.position());
                                reactor.setOwner(player);
                                reactor.tame(player);
                                player.level().addFreshEntity(reactor);
                                compoundTag.putBoolean(chestHasReactor,true);
                                break;
                            }else {
                                compoundTag.putBoolean(chestHasReactor,false);
                            }
                        }else {
                            stack.set(DataReg.tag, new CompoundTag());
                        }
                    }
                }
            }
        }
    }
}
