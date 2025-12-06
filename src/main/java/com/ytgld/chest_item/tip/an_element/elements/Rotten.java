package com.ytgld.chest_item.tip.an_element.elements;

import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.BlackSkill;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Collection;
import java.util.List;

public class Rotten extends SkillBase implements BlackSkill {
    public Rotten(){

    }
    public static void pRotten(Player player , ItemStack stack){
        if (!player.level().isClientSide()) {
            if (player.tickCount % 20 == 1) {
                if (SkillBase.isHasElement(stack, SkillList.pRotten)) {
                    Collection<MobEffectInstance> collection = player.getActiveEffects();
                    for (MobEffectInstance effectInstance : collection) {
                        if (!effectInstance.getEffect().value().isBeneficial()) {
                            Vec3 playerPos = player.position();
                            int range = (int)(1+ (SkillBase.getHasElementLevel(stack, SkillList.pRotten)) * SkillList.pRotten.aneLvlForModify());
                            List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class,
                                    new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range,
                                            playerPos.x + range, playerPos.y + range, playerPos.z + range));
                            for (LivingEntity living : entities) {
                                if (!living.is(player)) {
                                    if (!living.hasEffect(effectInstance.getEffect())) {
                                        living.addEffect(effectInstance);
                                        SkillBase.addXP(stack, SkillList.pRotten, 1, 10, 3);
                                    }
                                }
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
        return false;
    }

    @Override
    public float aneLvlForModify() {
        return 3;
    }
}
