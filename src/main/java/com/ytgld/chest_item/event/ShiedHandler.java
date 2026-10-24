package com.ytgld.chest_item.event;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class ShiedHandler {

    /**
     * 固定减伤的幽影护盾
     * <p>
     * 只要存在幽影护盾，就给予5%抗性（1点幽影容量增加0.5%抗性加成）
     * <p>
     * 幽影护盾稳固度是直接增加护盾强度，1.5为额外50%强度，2就是100%额外强度
     * <p>
     * 被击碎后仍然存在抗性，不过降低75%加成属性
     * <p>
     * 邪母化：
     * <p>
     * 邪母化的幽影护盾将额外吸收50%的魔法伤害
     * <p>
     * 并且每点负的理智增加5%魔法抗性，最高90%
     *
     *
     *
     */

    public static void shadowShield(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                AttributeInstance shadowShieldStronger = player.getAttribute(AttReg.shadow_shield_stronger);
                if (shadowShieldStronger != null) {
                    float value = (float) shadowShieldStronger.getValue();
                    if (value <= 0) {
                        return;
                    }
                    float data = player.getData(AttReg.shadowShieldTypes);
                    float damage = event.getNewDamage();
                    float newData = data - ((damage / value) / 5f);
                    float theSanValue = sanValue(player);
                    float max = (float) player.getAttributeValue(AttReg.shadow_shield);
                    float resDamage = (5 +  (max * 0.5f)) * value;
                    if (data <= 0) {
                        resDamage *= 0.25f;
                        Handler.setDataValue(AttReg.shadowShieldCooldownType, player, 10);
                    }
                    float endDamageCommon = damage * (100f - resDamage) / 100f;
                    float magicDamageRes = 0.05f;
                    if (theSanValue >= 10) {
                        float magicEndDamage = magicDamageRes * theSanValue;
                        float endDamageMagic = Math.max(0,1 - magicEndDamage);
                        if (event.getSource().is(Tags.DamageTypes.IS_MAGIC)) {
                            float doMagic = event.getNewDamage() * endDamageMagic;
                            event.setNewDamage(Math.max(0.1f,doMagic));
                        }
                    } else {
                        event.setNewDamage(Math.max(0.1f,endDamageCommon));
                    }
                    Handler.setDataValue(AttReg.shadowShieldTypes, player, Math.max(0,newData));
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
}
