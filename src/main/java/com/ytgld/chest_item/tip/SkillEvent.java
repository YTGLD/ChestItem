package com.ytgld.chest_item.tip;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.elements.*;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.Map;

public class SkillEvent {

    @SubscribeEvent
    public void AddAttributeTooltipsEvent(ItemTooltipEvent event){
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        TooltipFlag flag = event.getFlags();

        if (player!=null&&flag.hasShiftDown()) {
            if (stack.getItem() instanceof SkillList skillList
                    && stack.getItem() instanceof ItemBase itemBase) {
                Map<SkillBase, Integer> map = skillList.element(stack);
                if (map != null) {
                    for (SkillBase base : map.keySet()){
                        String text = base.baneName();
                        int lvl = SkillBase.getHasElementLevel(stack,base);
                        lvl++;
                        float s = lvl * base.aneLvlForModify(stack);
                        String  p = "";
                        if (base.isPercentage()) {
                            p = "%";
                            s*=100f;
                        }



                        int color = itemBase.color(stack);
                        event.getToolTip().add(1, Component.translatable("item.chest_item.skill."+base.baneName())
                                .append(": ")
                                .append(Component.translatable( "item.chest_item.skill."+text+".text"))
                                .append(Component.literal(String.valueOf(s)).append(p))

                                .withStyle(Style.EMPTY.withColor(color)));
                    }
                }
            }
        }else if (stack.getItem() instanceof SkillList){
            event.getToolTip().add(1,Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.GOLD));
        }
    }
    @SubscribeEvent
    public void LivingIncomingDamageEvent(LivingDamageEvent.Pre event){
        Rotten.pRotten(event);
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);

                        if (stack.getItem() instanceof SkillList) {
                            if (stack.get(DataReg.tag) == null) {
                                stack.set(DataReg.tag, new CompoundTag());
                            }
                        }

                        PlagueDivinePower.PlagueDivinePowerAttack(event,stack);
                        Decisively.useSkill(event,stack);
                    }
                }
            }
        }

    }
    @SubscribeEvent
    public void tick(ItemStackTickEvent event){
        Therapeutic.pPlagueSpores(event);
        TerriblePotion.pTerriblePotion(event);
        Hyperplasia.pHyperplasia(event);


        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.getItem() instanceof SkillList) {
                    if (stack.get(DataReg.tag) == null) {
                        stack.set(DataReg.tag,new CompoundTag());
                    }
                }
            }
        }
    }
}
