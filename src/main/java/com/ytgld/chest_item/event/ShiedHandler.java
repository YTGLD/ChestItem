package com.ytgld.chest_item.event;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class ShiedHandler {

    public static void ShadowShield (LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (player instanceof Player) {
                if (Handler.has(player, InitItems.ShadowMint_.asItem())) {
                    return;
                }
            }
            if (!player.level().isClientSide()) {
                //幽影稳固度
                AttributeInstance shadow_shield_stronger = player.getAttribute(AttReg.shadow_shield_stronger);
                if (shadow_shield_stronger != null) {
                    float value = (float) shadow_shield_stronger.getValue();
                    if (value <= 0) {
                        return;
                    }
                    float data = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
                    if (data > 0) {
                        float damage = event.getNewDamage();
                        //newData：是减少后的值
                        float newData = data - ((damage / value) / 5f);
                        if (newData > 0) {
                            AttributeInstance shadow_shield_conversion = player.getAttribute(AttReg.shadow_shield_conversion);
                            if (shadow_shield_conversion != null) {
                                float sscValue = (float) shadow_shield_conversion.getValue();
                                float theSanValue = sanValue(player);
                                float doSan = 1 - (theSanValue / 10);
                                sscValue *= doSan;
                                if (sscValue < 0) {
                                    sscValue = 0;
                                }

                                float hyperplasiaAValue = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                                float chaosWindsAValue = player.getData(AttReg.chaosWinds);

                                AttributeInstance max_hyperplasiaAttributeInstance = player.getAttribute(AttReg.hyperplasia);
                                AttributeInstance max_chaos_armorAttributeInstance = player.getAttribute(AttReg.chaos_armor);

                                if (max_hyperplasiaAttributeInstance != null && max_chaos_armorAttributeInstance != null) {
                                    float hyperplasia = (float) max_hyperplasiaAttributeInstance.getValue();
                                    float chaosArmor = (float) max_chaos_armorAttributeInstance.getValue();

                                    float newValueHyp = newData + hyperplasiaAValue;
                                    newValueHyp *= sscValue;
                                    if (newValueHyp > hyperplasia) {
                                        newValueHyp = hyperplasia;
                                    }
                                    Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES, player, newValueHyp);

                                    float newValueChaosArmor = newData + chaosWindsAValue;
                                    newValueChaosArmor *= sscValue;
                                    if (newValueChaosArmor > chaosArmor) {
                                        newValueChaosArmor = chaosArmor;
                                    }

                                    double max = player.getAttributeValue(AttReg.shadow_shield);
                                    float attRes = (float) (player.getAttributeValue(AttReg.shadow_shield_stronger) / 10f);
                                    //这里将 attRes 乘 doSan 是因为理智降低会减少抗性
                                    attRes *= doSan;
                                    float end = 1 - attRes;
                                    if (end < 0.1f) {
                                        end = 0.1f;
                                    }
                                    if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                                        int damageEffect = (int) (5 + Math.min(5, Math.sqrt(max)));
                                        if (theSanValue > 0) {
                                            if (livingEntity.addEffect(new MobEffectInstance(Effects.EvilErosion, (int) (theSanValue * 20 * 4), 0))) {
                                                damageEffect *= 2;
                                            }
                                        } else if (livingEntity.addEffect(new MobEffectInstance(Effects.ShadowErosion_,
                                                (int) (player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) * 60), 0))) {
                                            damageEffect *= 3;
                                        }
                                        if (Mth.nextInt(player.getRandom(), 0, 100) <= damageEffect) {
                                            event.setNewDamage(event.getNewDamage() * 0.1f);
                                            swingHandAndAttack(player, livingEntity);
                                            Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES, player, player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) / 2f);
                                        }
                                    }
                                    float resEvil = 1;
                                    resEvil -= 0.1f + (theSanValue / 100f * 2f);
                                    if (resEvil < 0.5f) {
                                        resEvil = 0.5f;
                                    }
                                    if (theSanValue >= 10) {
                                        event.setNewDamage(event.getNewDamage() * resEvil);
                                    } else {
                                        event.setNewDamage(event.getNewDamage() * end);
                                    }
                                    Handler.setDataValue(AttReg.chaosWinds, player, newValueChaosArmor);
                                    Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES, player, newData);
                                }
                            }
                        } else {
                            Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES, player, 0f);
                        }
                    } else {
                        Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES, player, 0f);
                        Handler.setDataValue(AttReg.shadow_shield_cooldown_dataAttachmentType, player, 10);
                    }
                }
            }
        }
    }

    public static float sanValue (Player player){
        float base = (float) player.getAttributeBaseValue(AttReg.theSanity);
        float san = (float) player.getAttributeValue(AttReg.theSanity) - base;
        if (san < 0) {
            san = -san;
        }
        return san;
    }
    public static void swingHandAndAttack(Player player,LivingEntity livingEntity){
        if (player.getMainHandItem().is(InitItems.EvilAxe_.asItem())) {
            livingEntity.setLastHurtByMob(player);
            livingEntity.setData(AttReg.slashing.get(), 1F);
            hurtEnemy(livingEntity,player);
        }
    }

    public static void hurtEnemy(Entity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayer serverplayer) {
            ServerLevel serverlevel = (ServerLevel) attacker.level();
            if (serverplayer.isIgnoringFallDamageFromCurrentImpulse() && serverplayer.currentImpulseImpactPos != null) {
                if (serverplayer.currentImpulseImpactPos.y > serverplayer.position().y) {
                    serverplayer.currentImpulseImpactPos = serverplayer.position();
                }
            } else {
                serverplayer.currentImpulseImpactPos = serverplayer.position();
            }
            attacker.setIgnoreFallDamageFromCurrentImpulse(true, calculateImpactPosition(attacker));
            serverplayer.connection.send(new ClientboundSetEntityMotionPacket(serverplayer));
            serverplayer.setSpawnExtraParticlesOnFall(true);
            SoundEvent soundevent = SoundEvents.MACE_SMASH_GROUND ;
            serverlevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), soundevent, serverplayer.getSoundSource(), 1.0F, 1.0F);
            knockback(serverlevel, serverplayer, target);
        }
    }
    private static Vec3 calculateImpactPosition(LivingEntity attacker) {
        return attacker.isIgnoringFallDamageFromCurrentImpulse() && attacker.currentImpulseImpactPos.y <= attacker.position().y ? attacker.currentImpulseImpactPos : attacker.position();
    }

    private static void knockback(Level level, Player player, Entity entity) {
        level.levelEvent(2013, entity.getOnPos(), 750);
    }
}
