package com.ytgld.chest_item.tip.an_element.elements;

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
                            event.setNewDamage(event.getNewDamage() * (1+(lvl*0.4f)));

                            SkillBase.addXP(stack, SkillList.pDecisively, 1, 30, 3);



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
}
