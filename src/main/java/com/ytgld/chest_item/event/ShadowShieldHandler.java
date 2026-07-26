package com.ytgld.chest_item.event;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class ShadowShieldHandler {

    /**
     * 幽影护盾现在不仅可以吸收伤害，还可以反弹伤害
     * <p>
     * 反弹的伤害为150%幽影护盾当量
     * <p>
     * 反弹伤害时会使目标施加“幽影侵蚀”效果，并且时间由幽影护盾当量决定(幽影侵蚀）：减少10%速度  伤害  攻速
     * <p>
     * 如果完全转变为邪母之盾则返回“邪母之拒”，并且时间由邪母的理智值当量决定 (邪母之拒）：减少22.5%速度  伤害  攻速  护甲  治疗  生命值
     * <p>
     * 当然，如果目标无法添加相关效果，则受到额外伤害
     * <p>
     * 但是由低理智转化的“邪母之盾”的双相盾转化效率下降，其下降效果为：每减少1理智值则减少10%转化效果
     * <p>
     * 当理智开始消失时，每减少1理智值则减少10%抵御伤害的效果
     * <p>
     * 但也不会太过于薄弱，作为舍弃双相之盾的补偿，邪母之盾会提供10%的抗性，并且每减少1点理智，获得的抗性增加2%，最多50%
     * @since 26.1.2-1.0.4.5
     */

    public static float sanValue (Player player){
        float base = (float) player.getAttributeBaseValue(AttReg.theSanity);
        float san = (float) player.getAttributeValue(AttReg.theSanity) - base;
        if (san < 0) {
            san = -san;
        }
        return san;
    }

    public static void ShadowShield (LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (player instanceof Player) {
                if (Handler.has(player, InitItems.ShadowMint_.asItem())){
                    return;
                }
            }
            //幽影稳固度
            AttributeInstance shadow_shield_stronger = player.getAttribute(AttReg.shadow_shield_stronger);
            if (shadow_shield_stronger != null) {
                float value = (float) shadow_shield_stronger.getValue();
                if (value <= 0) {
                    return;
                }
                float data = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
                if (data > 0) {
                    float damage = event.getNewDamage() ;
                    float newData = data - (damage / value);
                    if (newData > 0) {
                        AttributeInstance shadow_shield_conversion = player.getAttribute(AttReg.shadow_shield_conversion);
                        if (shadow_shield_conversion != null) {
                            float sscValue = (float) shadow_shield_conversion.getValue();
                            float theSanValue = sanValue(player);
                            float doSan = 1 - (theSanValue / 10);
                            sscValue *= doSan;
                            if (sscValue < 0) {
                                sscValue =0;
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
                                Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES,player,newValueHyp);

                                float newValueChaosArmor = newData + chaosWindsAValue;
                                newValueChaosArmor *= sscValue;
                                if (newValueChaosArmor > chaosArmor) {
                                    newValueChaosArmor = chaosArmor;
                                }

                                float attRes = (float) (player.getAttributeValue(AttReg.shadow_shield_stronger) / 10f);
                                attRes *= doSan;
                                float end = 1 - attRes;
                                if (end < 0.1f) {
                                    end = 0.1f;
                                }
                                if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                                    float damageEffect = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) * 1.5f;
                                    if (theSanValue > 0) {
                                        if (livingEntity.addEffect(new MobEffectInstance(Effects.EvilErosion, (int) (theSanValue * 20 * 4), 0))){
                                            damageEffect *= 1.5f;
                                        }
                                    }else if (livingEntity.addEffect(new MobEffectInstance(Effects.ShadowErosion_,
                                            (int) (player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) * 60),0))){
                                        damageEffect *= 1.5f;
                                    }
                                    livingEntity.hurt(livingEntity.damageSources().magic(),damageEffect);
                                }
                                float resEvil = 1;
                                resEvil -= 0.1f + (theSanValue / 100f * 2f);
                                if (resEvil < 0.5f) {
                                    resEvil = 0.5f;
                                }
                                if (theSanValue >= 10) {
                                    event.setNewDamage(event.getNewDamage() * resEvil);
                                }else {
                                    event.setNewDamage(event.getNewDamage() * end);
                                }
                                Handler.setDataValue(AttReg.chaosWinds,player,newValueChaosArmor);
                                Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player,newData);
                            }
                        }
                    }else {
                        Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player,0f);
                    }
                }else {
                    Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player, 0f);
                    Handler.setDataValue(AttReg.shadow_shield_cooldown_dataAttachmentType,player,10);
                }
            }
        }
    }
}
