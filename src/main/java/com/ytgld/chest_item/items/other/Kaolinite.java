package com.ytgld.chest_item.items.other;

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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class Kaolinite extends ItemBase {

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
                        if (player.tickCount%100==1) {
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
                    stack.setDamageValue(stack.getDamageValue() - 1);
                    player.giveExperiencePoints(-1);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.kaolinite.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.kaolinite.string.1").withStyle(ChatFormatting.GOLD));
    }


    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,0,255);
    }


}
