package com.ytgld.chest_item.items.black.give;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.function.Consumer;

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
                            component.putInt(kill,component.getIntOr(kill,0)+1);
                            return;
                        }else {
                            stack.set(DataReg.tag,new CompoundTag());
                        }

                    }
                }
            }
        }
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        CompoundTag component = stack.get(DataReg.tag);
        int c = 0XFFCD853F;
        if (component != null){
            tooltipComponents.accept(Component.translatable("stat.minecraft.mob_kills")
                    .append(": "+ component.getIntOr(kill, 0)).setStyle(Style.EMPTY.withColor(c)));
        }else {
            tooltipComponents.accept(Component.translatable("stat.minecraft.mob_kills")
                    .append(": "+ 0).setStyle(Style.EMPTY.withColor(c)));
        }

    }

    public static boolean isTrue (ItemStack aBrassCoins, int max){
        CompoundTag component = aBrassCoins.get(DataReg.tag);
        if (component != null) {
            return component.getIntOr(kill,0) >= max;
        }
        return false;
    }
}

