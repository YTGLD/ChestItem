package com.ytgld.chest_item.tip.an_element.elements;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.EndComing;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.BlackSkill;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class DoomsdayJudgment extends SkillBase implements BlackSkill {
    public DoomsdayJudgment(){

    }
    public static void useSkill(Entity entity){
        if (entity instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (!stack.isEmpty()) {
                            if (SkillBase.isHasElement(stack, SkillList.pDoomsdayJudgment)) {
                                SkillBase.addXP(stack, SkillList.pDoomsdayJudgment, 1, 100, SkillList.pDoomsdayJudgment.levelMax(stack));
                            }
                        }
                    }
                }
            }
        }
    }

    public static int attackTime(LivingEntity living){
        int def = 5;
        if (living instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (SkillBase.isHasElement(stack,SkillList.pDoomsdayJudgment)){
                            float l = 5f;
                            int lvl = SkillBase.getHasElementLevel(stack,SkillList.pDoomsdayJudgment);
                            float apply = SkillList.pDoomsdayJudgment.aneLvlForModify(stack);
                            float doIt = lvl * apply;
                            return (int) (l * (1 - doIt));
                        }
                    }
                }
            }
        }
        return def;
    }
    public static float damageModify(LivingEntity living){
        float def = 1f;
        if (living instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (SkillBase.isHasElement(stack,SkillList.pDoomsdayJudgment)){
                            float l = 1f;
                            int lvl = SkillBase.getHasElementLevel(stack,SkillList.pDoomsdayJudgment);
                            float apply = SkillList.pDoomsdayJudgment.aneLvlForModify(stack);

                            float doIt = lvl * apply;
                            return l * (1 + doIt);
                        }
                    }
                }
            }
        }
        return def;
    }
    public static boolean isHas(Entity entity){
        if (entity instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (SkillBase.isHasElement(stack,SkillList.pDoomsdayJudgment)){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
    @Override
    public String baneName() {
        return "doomsday_judgment";
    }

    @Override
    public boolean isPercentage() {
        return true;
    }

    @Override
    public float aneLvlForModify(ItemStack stack) {
        return Handler.isBlackAddPower(stack,1.25f) *   0.08f;
    }

    @Override
    public int levelMax(ItemStack stack) {
        return Handler.blackLevel(stack,2 , 5);
    }
}

