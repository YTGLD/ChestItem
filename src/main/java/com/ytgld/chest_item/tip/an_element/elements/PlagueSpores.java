package com.ytgld.chest_item.tip.an_element.elements;


import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * 按下技能按键对附近生物造成中毒
 *
 */
public class PlagueSpores extends SkillBase {
    public PlagueSpores(){

    }
    public static void useSkill(Player player){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (!stack.isEmpty()) {
                        if (stack.getItem() instanceof SkillList) {
                            if (stack.get(DataReg.tag) == null) {
                                stack.set(DataReg.tag,new CompoundTag());
                            }
                        }

                        if (SkillBase.isHasElement(stack, SkillList.pPlagueSpores)) {
                            if (!player.getCooldowns().isOnCooldown(stack.getItem())) {
                                int lvl = SkillBase.getHasElementLevel(stack, SkillList.pPlagueSpores);
                                Vec3 playerPos = player.position().add(0, 0.75, 0);
                                int range = lvl + 3;
                                List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class,
                                        new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range,
                                                playerPos.x + range, playerPos.y + range, playerPos.z + range));


                                for (LivingEntity entity : entities) {
                                    if (!entity.is(player)) {
                                        entity.addEffect(new MobEffectInstance(MobEffects.POISON,200 + lvl*20,1),player);
                                    }
                                }


                                SkillBase.addXP(stack, SkillList.pPlagueSpores, 1, 10,SkillList.pPlagueSpores.levelMax());
                                player.getCooldowns().addCooldown(stack.getItem(),lvl * 10 + 200);
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
        return "plague_spores";
    }

    @Override
    public boolean isPercentage() {
        return false;
    }

    @Override
    public float aneLvlForModify() {
        return 1;
    }

    @Override
    public int levelMax() {
        return 10;
    }
}
