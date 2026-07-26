package com.ytgld.chest_item.event;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import com.ytgld.chest_item.renderer.particle.other.SwordEnergyOption;
import com.ytgld.chest_item.sounds.Sounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import javax.annotation.Nullable;
import java.util.List;

public class SwordHandler {
    public static void tickAttackHurt(EntityTickEvent.Post event){
        if (event.getEntity() instanceof LivingEntity living) {
            int slashing = (int)(float)living.getData(AttReg.slashing.get());
            if (slashing > 0) {
                if (living.tickCount % 4 == 0) {
                    LivingEntity entity = living.getLastHurtByMob();
                    if (living.level() instanceof ServerLevel level) {
                        RandomSource randomSource = living.getRandom();
                        float size = randomSource.nextInt(15,25);
                        level.sendParticles(SwordEnergyOption.createSwordEnergyOption(Particles.SwordEnergyOption_.get(),
                                        new Vec3(0,0,0), true, Light.ARGB.color(255,255,255,255), size),
                                living.getX(), living.getY() + 1.0f, living.getZ(), 1, 0, 0,0,0);
                    }
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
    /**
     * 斩击造成伤害将治疗自身
     * <p>
     * 若剑气在未消耗完的情况下提前杀死了目标
     * <p>
     * 残余的剑气将扩散至附近生物上
     */

    public static void tickSwordIntent(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity living) {
            int swordIntent = living.getData(AttReg.swordIntent.get());
            if (swordIntent > 0) {
                if (living.tickCount % 2 == 0) {
                    LivingEntity entity = living.getLastHurtByMob();

                    if (living.level() instanceof ServerLevel level) {
                        RandomSource randomSource = living.getRandom();
                        Vec3 axis = new Vec3(randomSource.nextInt(-25, 25), randomSource.nextInt(-360, 360), randomSource.nextInt(-25, 25));
                        int color = Light.ARGB.color(255,165,215,230);
                        float size = randomSource.nextInt(15,25);
                        level.sendParticles(SwordEnergyOption.createSwordEnergyOption(Particles.SwordEnergyOption_.get(),
                                        axis, true, color, size),
                                living.getX(), living.getY() + 1.25f, living.getZ(), 1, 0, 0,0,0);
                        if (swordIntent == 1 && randomSource.nextInt(100) <= 25) {
                            List<ParticleOptions> options = getSwordParticles();
                            int rNext = randomSource.nextInt(options.size());
                            level.sendParticles(options.get(rNext),
                                    living.getX(), living.getEyeY() + 0.1f, living.getZ(), 1, 0, 0,0,0);

                        }
                    }

                    if (entity instanceof Player player) {
                        living.invulnerableTime = 0;
                        living.hurt(living.damageSources().playerAttack(player),
                                (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE) / 2
                        );
                        player.heal(2);
                        swordEnemy(living,swordIntent,player);
                        if (living.tickCount % 6 == 0) {
                            living.level().playSound(null, living.getX(), living.getY(), living.getZ(), Sounds.Sword.value(), SoundSource.PLAYERS, 0.15F, 1);
                        }
                        if (living.tickCount % 10 == 0 && !living.isAlive()) {
                            living.level().playSound(null, living.getX(), living.getY(), living.getZ(),
                                    Sounds.Kill.value(), SoundSource.PLAYERS, 0.75f, 1);
                        }
                        living.setData(AttReg.swordIntent.get(), swordIntent - 1);
                    }else {
                        living.invulnerableTime = 0;
                        living.hurt(living.damageSources().magic(),
                                4
                        );
                        swordEnemy(living,swordIntent,null);
                        living.setData(AttReg.swordIntent.get(), swordIntent - 1);
                    }
                }
            }
            if (swordIntent < 0){
                living.setData(AttReg.swordIntent.get(),0);
            }
        }
    }
    public static List<ParticleOptions> getSwordParticles(){
        return List.of(Particles.sword_shadow_1.get(),
                Particles.sword_shadow_2.get(),
                Particles.sword_shadow_3.get(),
                Particles.sword_shadow_4.get());
    }
    public static void swordEnemy(LivingEntity living,int residual,@Nullable Player player){
        if (!living.isAlive()) {
            Vec3 playerPos = living.position().add(0, 1.5f, 0);
            int range = 4;
            List<LivingEntity> entities = living.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
            for (LivingEntity other : entities){
                if (other.isAlive()) {
                    if (entities.size() == 1 && other.is(living) && !other.isAlive()) {
                        living.level().playSound(null, living.getX(), living.getY(), living.getZ(),
                                Sounds.FlySword.value(), SoundSource.PLAYERS, 0.33f, 1);
                    }
                    if (!other.is(living) && other.getData(AttReg.swordIntent.get()) < 1) {
                        if (player != null) {
                            player.getCooldowns().addCooldown(InitItems.Adjudication_.asItem(), 100);
                            if (other.is(player)) {
                                return;
                            }
                        }
                        other.setData(AttReg.swordIntent.get(), other.getData(AttReg.swordIntent.get()) + (residual + enemy(player)));
                        other.setLastHurtByMob(player);
                        return;
                    }
                }
            }
        }
    }
    public static int enemy(@Nullable Player player){
        if (player!=null) {
            if (Handler.has(player, InitItems.Adjudication_.asItem())) {
                return 1;
            }
        }else {
            return 0;
        }
        return 0;
    }
}
