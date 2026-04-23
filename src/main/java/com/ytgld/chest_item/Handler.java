package com.ytgld.chest_item;

import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.reinforced.items.Excite;
import com.ytgld.chest_item.items.reinforced.meat.RegenerationPlugin;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.ChestItemMenu;
import com.ytgld.chest_item.other.IPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class Handler {
    public static<T> void setDataValue(Supplier<AttachmentType<T>> attachmentTypeSupplier,LivingEntity living ,T number){
        if (living instanceof Player player) {
            player.setData(attachmentTypeSupplier,number);
        }
    }
    public static void upDATA(Player player){
        if (player instanceof IPlayer iPlayer){
            iPlayer.cI1_21_11$upDATA();
        }
    }
    public static void addHyperplasiaData(LivingEntity living ,float number){
        if (living instanceof Player player) {
            AttributeInstance attribute = player.getAttribute(AttReg.hyperplasia);
            AttributeInstance attributeCooldown = player.getAttribute(AttReg.hyperplasiaCooldown);
            if (attribute != null && attributeCooldown != null) {
                int cooldown = player.getData(AttReg.hyperplasiaCooldownAttachmentType);
                if (cooldown > 0) {
                    number = 0;
                }
                number = RegenerationPlugin.doubleAdd(living,number);
                float hyperplasiaValue = (float) attribute.getValue();
                float data = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                if (data >= hyperplasiaValue) {
                    return;
                }
                float newValue = data + number;
                if (newValue > hyperplasiaValue) {
                    newValue = hyperplasiaValue;
                }
                if (number > 0) {
                    RegenerationPlugin.heal(living);
                }
                player.setData(AttReg.hyperplasiaATTACHMENT_TYPES,newValue);
            }
        }
    }
    public static void reduceHyperplasiaData(LivingEntity living ,float number){
        if (living instanceof Player player) {
            float data = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
            float newValue = data - number;
            if (newValue < 0) {
                newValue = 0;
            }
            player.setData(AttReg.hyperplasiaATTACHMENT_TYPES, newValue);
        }
    }
    public static void addHeartShield(Player player ,float number){
        if (isInHeartShieldCooldown(player)){
            return;
        }
        Supplier<AttachmentType<Float>> supplier = AttReg.painShield;
        AttributeInstance maxShield = player.getAttribute(AttReg.painShield_number);
        if (maxShield != null) {
            if (player.getData(supplier) <= maxShield.getValue()) {

                number = Excite.doINtHeal(number, player);

                player.setData(supplier, player.getData(supplier) + number);
            }
        }
    }
    public static boolean isInHeartShieldCooldown(LivingEntity living){
        if (living instanceof Player player){
            int cooldown = player.getData(AttReg.theHeartCooldown);
            return cooldown > 0;
        }
        return false;
    }
    public static boolean has(Player player, Item item){
        ChestInventory chestInventory= getItem(player);
        if (chestInventory != null) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack= chestInventory.getItem(i);
                if (stack.is(item)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static void openItemChest(Player player){
        if (player instanceof IPlayer iPlayer) {
            player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.CHEST_OPEN, SoundSource.AMBIENT,1,1);
            player.openMenu(new SimpleMenuProvider(
                    (i, inventory, p_53126_) -> new ChestItemMenu(i,inventory,
                            iPlayer.chest_item$chestInventory().get(),2), Component.translatable("container.chest_item.chest")
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
