package com.ytgld.chest_item.tip.an_element.elements;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

/**
 * 有可能对目标造成中毒效果
 * <p>
 * 对中毒生物造成额外伤害
 */
public class PlagueDivinePower  extends SkillBase {
    public PlagueDivinePower(){

    }
    public static void PlagueDivinePowerAttack(LivingDamageEvent.Pre event,ItemStack stack){
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                if (!stack.isEmpty()) {

                    if (SkillBase.isHasElement(stack, SkillList.pPlagueDivinePower)) {
                        LivingEntity living = event.getEntity();

                        int lvl = SkillBase.getHasElementLevel(stack, SkillList.pPlagueDivinePower);
                        int math = Mth.nextInt(RandomSource.create(), 1, 10);
                        if (math <= lvl) {
                            living.addEffect(new MobEffectInstance(MobEffects.POISON, 40, 2), player);
                        }

                        if (living.hasEffect(MobEffects.POISON)) {
                            event.setNewDamage(event.getNewDamage() * 1.2f);
                        }
                        SkillBase.addXP(stack, SkillList.pPlagueDivinePower, 1, 100, SkillList.pPlagueDivinePower.levelMax());
                    }
                }
            }
        }
    }


    @Override
    public String baneName() {
        return "plague_divine_power";
    }

    @Override
    public boolean isPercentage() {
        return true;
    }

    @Override
    public float aneLvlForModify() {
        return 0.1f;
    }

    @Override
    public int levelMax() {
        return 10;
    }
}

