package com.ytgld.chest_item.tip.an_element.elements;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.BlackSkill;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class Rotten extends SkillBase implements BlackSkill {
    public Rotten(){

    }
    public static void pRotten(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (SkillBase.isHasElement(stack, SkillList.pRotten)) {
                            float lv =(1 + (SkillBase.getHasElementLevel(stack, SkillList.pRotten))) * SkillList.pRotten.aneLvlForModify();
                            lv*= 100;

                            if (Mth.nextInt(RandomSource.create(),0,100) < lv) {
                                event.setNewDamage(event.getNewDamage()*1.5f);
                                SkillBase.addXP(stack, SkillList.pRotten, 1, 20, SkillList.pRotten.levelMax());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public String baneName() {
        return "rotten";
    }

    @Override
    public boolean isPercentage() {
        return true;
    }

    @Override
    public float aneLvlForModify() {
        return 0.06f;
    }

    @Override
    public int levelMax() {
        return 6;
    }
}
