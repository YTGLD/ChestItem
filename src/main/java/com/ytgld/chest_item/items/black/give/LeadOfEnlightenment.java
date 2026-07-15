package com.ytgld.chest_item.items.black.give;

import com.ytgld.chest_item.Handler;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.items.black.ITheChaos;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

public class LeadOfEnlightenment extends ItemBlackShadow implements ITheChaos {

    public static final String killWarmaker = "killWarmaker";
    public static final String hurtGiveChaosFortress = "hurtGiveChaosFortress";

    public LeadOfEnlightenment(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "BlackShadow";
        }
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("LeadOfEnlightenment");
            intValue =  builder.translation("chest_item.config.LeadOfEnlightenment")
                    .defineInRange("number",3000,1,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.LeadOfEnlightenment2")
                    .defineInRange("number2",500,1,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("LeadOfEnlightenment",
                            "启明之铅","需要受到多少伤害来获取“”混沌要塞之护"),
                    new CIString("LeadOfEnlightenment2",
                            "启明之铅2","需要杀死多少生物来获取“战争缔造者”")

            );
        }
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
                            if (isTrue(stack, ConfigItem.intValue.get(),hurtGiveChaosFortress)) {
                            player.level().playSound(null,player.blockPosition(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.AMBIENT);
                            chestInventory.setItem(i,new ItemStack(InitItems.ChaosFortress_.asItem()));
                        }
                        if (component != null) {
                            component.putInt(hurtGiveChaosFortress,
                                    (int) (component.getInt(hurtGiveChaosFortress)+ event.getNewDamage()));
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
                        if (isTrue(stack, ConfigItem.intValue2.get(),killWarmaker)) {
                            player.level().playSound(null,player.blockPosition(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.AMBIENT);
                            chestInventory.setItem(i,new ItemStack(InitItems.Warmaker_.asItem()));
                        }
                        if (component != null) {
                            component.putInt(killWarmaker,component.getInt(killWarmaker)+1);
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
            return component.getInt(tag) >= max;
        }
        return false;
    }
}
