package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.other.DataReg;
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

import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;
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
        @Override
        public String theCategory() {
            return "EvilMother";
        }
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
                if (!player.getCooldowns().isOnCooldown(InitItems.AnnualPlate_.asItem().getDefaultInstance())) {
                    if (Mth.nextInt(RandomSource.create(), 1, 100) <= ConfigItem.intValue.getAsInt()) {
                        hurtEnemy(event.getTarget(), player);
                        event.setDamageMultiplier((float) (event.getDamageMultiplier() * ConfigItem.intValue2.getAsDouble()));
                        player.getCooldowns().addCooldown(InitItems.AnnualPlate_.asItem().getDefaultInstance(),20);
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
            attacker.setIgnoreFallDamageFromCurrentImpulse(true, calculateImpactPosition(attacker));
            serverplayer.connection.send(new ClientboundSetEntityMotionPacket(serverplayer));
            serverplayer.setSpawnExtraParticlesOnFall(true);
            SoundEvent soundevent = SoundEvents.MACE_SMASH_GROUND_HEAVY ;
            serverlevel.playSound(null, serverplayer.getX(), serverplayer.getY(), serverplayer.getZ(), soundevent, serverplayer.getSoundSource(), 1.0F, 1.0F);
            knockback(serverlevel, serverplayer, target);
        }
    }
    private static Vec3 calculateImpactPosition(LivingEntity attacker) {
        return attacker.isIgnoringFallDamageFromCurrentImpulse() && attacker.currentImpulseImpactPos.y <= attacker.position().y ? attacker.currentImpulseImpactPos : attacker.position();
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
    private static void knockback(Level level, Entity attacker, Entity entity) {
        level.levelEvent(2013, entity.getOnPos(), 750);
        level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate((double)3.5F), knockbackPredicate(attacker, entity)).forEach((nearby) -> {
            Vec3 direction = nearby.position().subtract(entity.position());
            double knockbackPower = getKnockbackPower(attacker, nearby, direction);
            Vec3 knockbackVector = direction.normalize().scale(knockbackPower);
            if (knockbackPower > (double)0.0F) {
                nearby.push(knockbackVector.x, (double)0.7F, knockbackVector.z);
                if (nearby instanceof ServerPlayer) {
                    ServerPlayer otherPlayer = (ServerPlayer)nearby;
                    otherPlayer.connection.send(new ClientboundSetEntityMotionPacket(otherPlayer));
                }
            }

        });
    }
    private static Predicate<LivingEntity> knockbackPredicate(Entity attacker, Entity entity) {
        return (nearby) -> {
            boolean notSpectator;
            boolean notPlayer;
            boolean notAlliedToPlayer;
            boolean var10000;
            label87: {
                notSpectator = !nearby.isSpectator();
                notPlayer = nearby != attacker && nearby != entity;
                notAlliedToPlayer = !attacker.isAlliedTo(nearby);
                if (nearby instanceof TamableAnimal animal) {
                    if (entity instanceof LivingEntity livingAttacker) {
                        if (animal.isTame() && animal.isOwnedBy(livingAttacker)) {
                            var10000 = true;
                            break label87;
                        }
                    }
                }

                var10000 = false;
            }

            boolean notTamedByPlayer;
            label79: {
                notTamedByPlayer = !var10000;
                if (nearby instanceof ArmorStand armorStand) {
                    if (armorStand.isMarker()) {
                        var10000 = true;
                        break label79;
                    }
                }

                var10000 = false;
            }

            boolean notArmorStand;
            boolean withinRange;
            label73: {
                notArmorStand = !var10000;
                withinRange = entity.distanceToSqr(nearby) <= Math.pow((double)3.5F, (double)2.0F);
                if (nearby instanceof Player player) {
                    if (player.isCreative() && player.getAbilities().flying) {
                        var10000 = true;
                        break label73;
                    }
                }

                var10000 = false;
            }

            boolean notFlyingInCreative = !var10000;
            return notSpectator && notPlayer && notAlliedToPlayer && notTamedByPlayer && notArmorStand && withinRange && notFlyingInCreative;
        };
    }

    private static double getKnockbackPower(Entity attacker, LivingEntity nearby, Vec3 direction) {
        return ((double)3.5F - direction.length()) * (double)0.7F * (double)(attacker.fallDistance > (double)5.0F ? 2 : 1) * ((double)1.0F - nearby.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }

    @Override
    public void tick(Player player, ItemStack stack) {
        super.tick(player, stack);
        IEvilGift.addGift(stack,EvilGifts.rotten_utensils.get());
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.annual_plate.string.0", ConfigItem.intValue.getAsInt(), TheKill.ConfigItem.intValue2.getAsInt()).withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.annual_plate.string.1",ConfigItem.intValue2.getAsDouble() * 100F).withStyle(Style.EMPTY.withColor(color)));

    }
    @Override
    public int getSanity() {
        return -4;
    }

    @Override
    public int maxGiftNumber(ItemStack stack) {
        return 1;
    }

    @Override
    public HashSet<EvilGiftBase> canHasEvilGift() {
        HashSet<EvilGiftBase> evilGiftBases = new HashSet<>();
        evilGiftBases.add(EvilGifts.rotten_utensils.get());
        return evilGiftBases;
    }
}
