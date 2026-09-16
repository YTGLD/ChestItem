package com.ytgld.chest_item.tip.an_element.elements;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 对满生命值的生物造成大量额外伤害
 */
public class Decisively extends SkillBase {
    public Decisively(){

    }
    public static void useSkill(LivingDamageEvent.Pre event, ItemStack stack){
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                if (!stack.isEmpty()) {
                    if (SkillBase.isHasElement(stack, SkillList.pDecisively)) {
                        LivingEntity living = event.getEntity();
                        if (living.getHealth() >=living.getMaxHealth()){

                            int lvl = SkillBase.getHasElementLevel(stack, SkillList.pDecisively);
                            lvl ++;
                            event.setNewDamage(event.getNewDamage() * (1+(lvl*SkillList.pDecisively.aneLvlForModify(stack))));

                            SkillBase.addXP(stack, SkillList.pDecisively, 1, 30, SkillList.pDecisively.levelMax(stack));



                        }
                    }
                }
            }
        }
    }
    @Override
    public String baneName() {
        return "decisively";
    }

    @Override
    public boolean isPercentage() {
        return true;
    }

    @Override
    public float aneLvlForModify(ItemStack stack) {
        return Handler.isBlackAddPower(stack,1.25f) *  0.2f;
    }

    @Override
    public int levelMax(ItemStack stack) {
        return Handler.blackLevel(stack,2 , 3);
    }
}
