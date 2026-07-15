package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class Kaolinite extends ItemBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Other";
        }
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Kaolinite");
            intValue =  builder.translation("chest_item.config.Kaolinite")
                    .defineInRange("number",100,1,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Kaolinite2")
                    .defineInRange("number2",1,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Kaolinite",
                            "高岭石","时间"),
                    new CIString("Kaolinite2",
                            "高岭石2","每次恢复的耐久")
            );
        }
    }
    public Kaolinite(Properties properties) {
        super(properties);
    }
    public static void event(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.Kaolinite_)) {
                    if (player.experienceLevel > 0 ){
                        if (player.tickCount%ConfigItem.intValue.get().intValue()==1) {
                            addDamage(player,EquipmentSlot.HEAD);
                            addDamage(player,EquipmentSlot.CHEST);
                            addDamage(player,EquipmentSlot.LEGS);
                            addDamage(player,EquipmentSlot.FEET);
                            addDamage(player,EquipmentSlot.MAINHAND);
                        }
                    }
                }
            }
        }
    }
    public static void addDamage(Player player,EquipmentSlot slot){
        ItemStack stack = player.getItemBySlot(slot);
        if (!stack.isEmpty()) {
            if (stack.getMaxDamage() != 0) {
                if (stack.getDamageValue() > 0) {
                    stack.setDamageValue(stack.getDamageValue() - ConfigItem.intValue2.get().intValue());
                    player.giveExperiencePoints(-1);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.kaolinite.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_item.kaolinite.string.1").withStyle(ChatFormatting.GOLD));
    }


    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,0,255);
    }


}
