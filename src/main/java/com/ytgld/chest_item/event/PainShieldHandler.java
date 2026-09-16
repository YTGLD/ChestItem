package com.ytgld.chest_item.event;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

import java.util.function.Supplier;

import static com.ytgld.chest_item.Handler.isInHeartShieldCooldown;

public class PainShieldHandler {

    public static void tickShield(LivingEntity living){
        tickCooldown(living);
        if (!isInHeartShieldCooldown(living)) {
            if (living instanceof Player player && !player.level().isClientSide()) {
                AttributeInstance maxShield = player.getAttribute(AttReg.painShield_number);
                AttributeInstance speed = player.getAttribute(AttReg.painShield_speed);
                if (maxShield != null && speed != null) {
                    if (maxShield.getValue() <= 0) {
                        return;
                    }
                    Supplier<AttachmentType<Float>> supplier = AttReg.painShield;
                    if (player.getData(supplier) <= maxShield.getValue()) {
                        if (player.tickCount % 10 == 1 && !player.level().isClientSide()) {
                            {
                                float other = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                                if (other > 0) {
                                    addPain(player, speed.getValue(), other / 3);
                                    Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES, player, player.getData(AttReg.hyperplasiaATTACHMENT_TYPES) - 1);
                                }
                            }
                            {
                                float other = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
                                if (other > 0) {
                                    addPain(player, speed.getValue(), other);
                                    Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES, player, player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) - 1);
                                }
                            }
                            {
                                float other = player.getData(AttReg.chaosWinds);
                                if (other > 0) {
                                    addPain(player, speed.getValue(), other / 4);
                                    Handler.setDataValue(AttReg.chaosWinds, player, player.getData(AttReg.chaosWinds) - 1);
                                }
                            }
                        }
                    } else {
                        Handler.setDataValue(AttReg.chaosWinds,player, 0f);
                        Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player, 0f);
                        Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES,player, 0f);
                    }
                }
            }
        }
    }
    public static void tickCooldown(LivingEntity living){
        if (living instanceof Player player) {
            if (!player.level().isClientSide()) {
                if (isInHeartShieldCooldown(living)) {
                    Handler.setDataValue(AttReg.theHeartCooldown,player, player.getData(AttReg.theHeartCooldown) - 1);
                }
                if (player.getData(AttReg.theHeartCooldown) < 0) {
                    Handler.setDataValue(AttReg.theHeartCooldown,player, 0);
                }
            }
        }
    }
    public static void addPain(Player player, double speed, float add) {
        Handler.addHeartShield(player,add * (float) speed);
    }
    public static boolean canHeal(LivingEntity living){
        if (isInHeartShieldCooldown(living)) {
            return true;
        }
        if (living instanceof Player player) {
            AttributeInstance maxShield = player.getAttribute(AttReg.painShield_number);
            if (maxShield != null) {
                Supplier<AttachmentType<Float>> supplier = AttReg.painShield;
                return !(player.getData(supplier) > maxShield.getValue());
            }
        }
        return true;
    }
}
