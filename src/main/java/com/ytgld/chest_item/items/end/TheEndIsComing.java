package com.ytgld.chest_item.items.end;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.EndComing;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TheEndIsComing  extends ItemBase {
    public TheEndIsComing(Properties properties) {
        super(properties);
    }

    public static final String chestHasEndComing= "ChestHasEndComing";
    public static void event( Player player) {
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.TheEndIsComing_)) {
                        CompoundTag compoundTag = stack.get(DataReg.tag);
                        if (compoundTag!=null) {
                            if (!compoundTag.getBooleanOr(chestHasEndComing,false)) {
                                EndComing EndComing = new EndComing(Entitys.EndComing_.get(), player.level());
                                EndComing.setPos(player.position());
                                EndComing.setOwner(player);
                                player.level().addFreshEntity(EndComing);
                                compoundTag.putBoolean(chestHasEndComing,true);
                                break;
                            }else {
                                compoundTag.putBoolean(chestHasEndComing,false);
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
