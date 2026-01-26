package com.ytgld.chest_item.items.black.give;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.function.Consumer;

public class LeadOfEnlightenment extends ItemBlackShadow implements IBlackLight {

    public static final String killWarmaker = "killWarmaker";
    public static final String hurtGiveChaosFortress = "hurtGiveChaosFortress";

    public LeadOfEnlightenment(Properties properties) {
        super(properties);
    }
    public static void die(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (Handler.has(player, InitItems.ChaosFortress_.asItem())) {
                return;
            }
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.LeadOfEnlightenment_)) {
                        CompoundTag component = stack.get(DataReg.tag);
                        if (isTrue(stack, 15000,hurtGiveChaosFortress)) {
                            player.level().playSound(null,player.blockPosition(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.AMBIENT);
                            chestInventory.setItem(i,new ItemStack(InitItems.ChaosFortress_.asItem()));
                        }
                        if (component != null) {
                            component.putInt(hurtGiveChaosFortress,
                                    (int) (component.getIntOr(hurtGiveChaosFortress,0)+ event.getNewDamage()));
                            return;
                        }else {
                            stack.set(DataReg.tag,new CompoundTag());
                        }
                    }
                }
            }
        }
    }
    public static void die(LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (Handler.has(player, InitItems.Warmaker_.asItem())) {
                return;
            }
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.LeadOfEnlightenment_)) {
                        CompoundTag component = stack.get(DataReg.tag);
                        if (isTrue(stack, 550,killWarmaker)) {
                            player.level().playSound(null,player.blockPosition(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.AMBIENT);
                            chestInventory.setItem(i,new ItemStack(InitItems.Warmaker_.asItem()));
                        }
                        if (component != null) {
                            component.putInt(killWarmaker,component.getIntOr(killWarmaker,0)+1);
                            return;
                        }else {
                            stack.set(DataReg.tag,new CompoundTag());
                        }
                    }
                }
            }
        }
    }

    private static boolean isTrue (ItemStack target, int max, String tag){
        CompoundTag component = target.get(DataReg.tag);
        if (component != null) {
            return component.getIntOr(tag, 0) >= max;
        }
        return false;
    }
}
