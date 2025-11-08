package com.ytgld.chest_item.tip;

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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class SkillEvent {
    @SubscribeEvent
    public void LivingIncomingDamageEvent(LivingDamageEvent.Pre event){
        PlagueDivinePowerAttack(event);
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
                            if (!player.getCooldowns().isOnCooldown(stack)) {
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


                                SkillBase.addXP(stack, SkillList.pPlagueSpores, 1, 10,10);
                                player.getCooldowns().addCooldown(stack,lvl * 10 + 200);
                            }
                        }
                    }
                }
            }
        }
    }


    private static void PlagueDivinePowerAttack(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
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
                                SkillBase.addXP(stack, SkillList.pPlagueDivinePower, 1, 100,10);
                            }
                        }
                    }
                }
            }
        }
    }
}
