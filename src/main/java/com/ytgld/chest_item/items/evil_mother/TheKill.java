package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.SweepAttackEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * 苍戮
 * <p>
 * 攻击有%d%%的概率造成%d次的连续斩击
 *
 */
public class TheKill extends EvilMother{
    public TheKill(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue;
        public static ModConfigSpec.IntValue intValue2;

        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("TheKill");
            intValue = builder.translation("chest_item.config.TheKill")
                    .defineInRange("number", 50, 0, 100);
            intValue2 = builder.translation("chest_item.config.TheKill2")
                    .defineInRange("number2", 4, 0, Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("TheKill",
                            "苍戮", "连续斩击的触发概率"),
                    new CIString("TheKill2",
                            "苍戮2", "连斩次数")
            );
        }
    }
    public static void attack(SweepAttackEvent event){
        if (!event.isSweeping()) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.TheKill_.asItem())) {
                if (!player.getCooldowns().isOnCooldown(InitItems.TheKill_.asItem().getDefaultInstance())){
                    if (Mth.nextInt(RandomSource.create(),0,100) <= ConfigItem.intValue.getAsInt()){
                        Entity entity = event.getTarget();
                        if (entity instanceof LivingEntity living) {
                            int s = (int)(float)living.getData(AttReg.slashing.get());
                            living.setData(AttReg.slashing.get(),(float)ConfigItem.intValue2.getAsInt());
                            player.getCooldowns().addCooldown(InitItems.TheKill_.asItem().getDefaultInstance(),ConfigItem.intValue2.getAsInt() * 20);
                        }
                    }
                }

            }
        }
    }
    public static void tickAttackHurt(EntityTickEvent.Post event){
        if (event.getEntity() instanceof LivingEntity living) {
            int slashing = (int)(float)living.getData(AttReg.slashing.get());
            if (slashing > 0) {
                if (living.tickCount % 4 == 0) {
                    LivingEntity entity = living.getLastHurtByMob();
                    if (entity != null) {
                        if (entity instanceof Player player) {
                            HolderLookup.RegistryLookup<Enchantment> registrylookup = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                            float sweep = EnchantmentHelper.getEnchantmentLevel(registrylookup.getOrThrow(Enchantments.SWEEPING_EDGE),player);
                            float sharpness = EnchantmentHelper.getEnchantmentLevel(registrylookup.getOrThrow(Enchantments.SHARPNESS),player);
                            if (slashing == 1){
                                living.invulnerableTime = 0;
                                CriticalHitEvent criticalHitEvent = new CriticalHitEvent(player,living,2,true);
                                criticalHitEvent.setCriticalHit(true);
                                NeoForge.EVENT_BUS.post(criticalHitEvent);

                                if (sharpness > 0) {
                                    player.crit(living);
                                }

                                living.hurt(living.damageSources().playerAttack(player),
                                        criticalHitEvent.getDamageMultiplier()
                                                + sweep * 1.5f
                                                + sharpness * 2f

                                );
                                living.knockback(0.1f, player.getX() - living.getX(), player.getZ() - living.getZ());

                                player.crit(living);

                                living.level().playSound(null, living.blockPosition(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.MASTER, 1, 1);
                                living.setData(AttReg.slashing.get(), slashing - 1f);
                            }
                            if (slashing > 1) {
                                living.invulnerableTime = 0;
                                LivingIncomingDamageEvent livingIncomingDamageEvent = new LivingIncomingDamageEvent(living,
                                        new DamageContainer(
                                                living.damageSources().playerAttack(player),
                                                (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                                        ));
                                NeoForge.EVENT_BUS.post(livingIncomingDamageEvent);

                                if (sharpness > 0) {
                                    player.crit(living);
                                }

                                living.hurt(living.damageSources().playerAttack(player),
                                        livingIncomingDamageEvent.getAmount()
                                                + sweep
                                                + sharpness
                                );



                                living.knockback(0.1f, player.getX() - living.getX(), player.getZ() - living.getZ());
                                if (living.level() instanceof ServerLevel serverLevel) {
                                    serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK,
                                            living.getX(), living.getY(0.5), living.getZ(),
                                            0, 1, 1, 1, 0.0);
                                }
                                living.level().playSound(null, living.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.MASTER, 1, 1);

                                living.setData(AttReg.slashing.get(), slashing - 1f);
                            }
                        }else {
                            living.hurt(living.damageSources().mobAttack(entity), 10);
                            living.setData(AttReg.slashing.get(),slashing - 1f);
                        }
                    }
                }
            }
            if (slashing < 0){
                living.setData(AttReg.slashing.get(),0f);
            }
        }
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.text(stack, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.the_kill.string.0",ConfigItem.intValue.getAsInt(),ConfigItem.intValue2.getAsInt()).withStyle(Style.EMPTY.withColor(colorBlack().color())));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_kill.string.1").withStyle(Style.EMPTY.withColor(colorBlack().color())));
    }

    @Override
    public int getSanity() {
        return -3;
    }
}
