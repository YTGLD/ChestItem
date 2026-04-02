package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.List;
import java.util.function.Predicate;

/**
 * 邪母年盘
 * <p>
 * 造成暴击时有%d%%的概率触发致命一击
 * <p>
 * 致命一击的伤害倍率为正常暴击的%d%%
 */
public class AnnualPlate extends EvilMother{
    public AnnualPlate(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue;
        public static ModConfigSpec.DoubleValue intValue2;

        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("AnnualPlate");
            intValue = builder.translation("chest_item.config.AnnualPlate")
                    .defineInRange("number", 10, 0, 100);
            intValue2 = builder.translation("chest_item.config.AnnualPlate2")
                    .defineInRange("number2", 3F, 1, Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("AnnualPlate",
                            "邪母年盘", "致命一击的概率"),
                    new CIString("AnnualPlate2",
                            "邪母年盘2", "致命一击的伤害倍率")
            );
        }
    }
    public static void  dieAnnualPlate(CriticalHitEvent event){
        if (!event.isCriticalHit()) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.AnnualPlate_.asItem())) {
                if (!player.getCooldowns().isOnCooldown(InitItems.AnnualPlate_.asItem())) {
                    if (Mth.nextInt(RandomSource.create(), 1, 100) <= ConfigItem.intValue.getAsInt()) {
                        hurtEnemy(event.getTarget(), player);
                        event.setDamageMultiplier((float) (event.getDamageMultiplier() * ConfigItem.intValue2.getAsDouble()));
                        player.getCooldowns().addCooldown(InitItems.AnnualPlate_.asItem(),20);
                    }
                }
            }
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
            serverplayer.setIgnoreFallDamageFromCurrentImpulse(true);
            serverplayer.connection.send(new ClientboundSetEntityMotionPacket(serverplayer));
            serverplayer.setSpawnExtraParticlesOnFall(true);
            SoundEvent soundevent = SoundEvents.MACE_SMASH_GROUND_HEAVY ;
            serverlevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), soundevent, serverplayer.getSoundSource(), 1.0F, 1.0F);
            knockback(serverlevel, serverplayer, target);
        }
    }
    private static void knockback(Level level, Player player, Entity entity) {
        level.levelEvent(2013, entity.getOnPos(), 750);
        level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate((double)3.5F), knockbackPredicate(player, entity)).forEach((p_347296_) -> {
            Vec3 vec3 = p_347296_.position().subtract(entity.position());
            double d0 = getKnockbackPower(player, p_347296_, vec3);
            Vec3 vec31 = vec3.normalize().scale(d0);
            if (d0 > (double)0.0F) {
                p_347296_.push(vec31.x, (double)0.7F, vec31.z);
            }
        });
    }
    private static Predicate<LivingEntity> knockbackPredicate(Player player, Entity entity) {
        return (p_344407_) -> {
            boolean flag;
            boolean flag1;
            boolean flag2;
            boolean flag6;
            label62: {
                flag = !p_344407_.isSpectator();
                flag1 = p_344407_ != player && p_344407_ != entity;
                flag2 = !player.isAlliedTo(p_344407_);
                if (p_344407_ instanceof TamableAnimal tamableanimal) {
                    if (tamableanimal.isTame() && player.getUUID().equals(tamableanimal.getOwnerUUID())) {
                        flag6 = true;
                        break label62;
                    }
                }

                flag6 = false;
            }

            boolean flag3;
            label55: {
                flag3 = !flag6;
                if (p_344407_ instanceof ArmorStand armorstand) {
                    if (armorstand.isMarker()) {
                        flag6 = false;
                        break label55;
                    }
                }

                flag6 = true;
            }

            boolean flag5 = entity.distanceToSqr(p_344407_) <= Math.pow((double)3.5F, (double)2.0F);
            return flag && flag1 && flag2 && flag3 && flag6 && flag5;
        };
    }
    private static double getKnockbackPower(Player player, LivingEntity entity, Vec3 entityPos) {
        return ((double)3.5F - entityPos.length()) * (double)0.7F * (double)(player.fallDistance > 5.0F ? 2 : 1) * ((double)1.0F - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        text(stack, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.annual_plate.string.0", ConfigItem.intValue.getAsInt(), TheKill.ConfigItem.intValue2.getAsInt()).withStyle(Style.EMPTY.withColor(colorBlack().color())));
        tooltipAdder.add(Component.translatable("item.chest_item.annual_plate.string.1",ConfigItem.intValue2.getAsDouble() * 100F).withStyle(Style.EMPTY.withColor(colorBlack().color())));

    }

    @Override
    public int getSanity() {
        return -2;
    }
}
