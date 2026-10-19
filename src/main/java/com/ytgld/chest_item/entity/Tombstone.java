package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.utils.AddUtil;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class Tombstone extends TamableAnimal {
    public int face = 0;
    public int lastFace = 0;
    private final int time = 400;
    public boolean attack = false;
    public int lastTick = 0;
    public Tombstone(EntityType<? extends Tombstone> type, Level level) {
        super(type, level);
    }

    @Override
    public void tick() {
        lastTick = tickCount;
        super.tick();
        if (face <= 1) {
            face = this.getId();
        }
        lastFace = face;
        face++;
        if (!(this.getOwner() instanceof Player player)) {
            return;
        }
        this.setDeltaMovement(new Vec3(0, -5, 0));
        if (!level().isClientSide()) {
            if (this.tickCount > time) {
                this.hurt(this.damageSources().genericKill(),1000);
            }
        }
        Vec3 playerPos = this.position();
        int range = 10;
        List<LivingEntity> livingEntities = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        for (LivingEntity living : livingEntities){
            if (living.is(this)) {
                continue;
            }
            if (!living.is(player) && living.tickCount % 3 == 1) {
                living.hurt(living.damageSources().playerAttack(player), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                living.setInvulnerableTime(0);
                living.damageCooldownTime = 0;
                float offSet = 3f;
                AddUtil.addScratchEffect(
                        living,living.getEyePosition().add(new Vec3(
                                Mth.nextFloat(random,-offSet,offSet),
                                Mth.nextFloat(random,0,offSet/ 2f),
                                Mth.nextFloat(random,-offSet,offSet)
                        )),
                        Light.ARGB.color(255,240,50,255),
                        Light.ARGB.color(255,200,0,0),
                        16
                );
                break;
            }
        }
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, (double)0)
                .add(Attributes.ATTACK_DAMAGE, (double)3.0F)
                .add(Attributes.ARMOR, (double)10)
                .add(Attributes.KNOCKBACK_RESISTANCE,50);
    }

    @Override
    public void die(DamageSource source) {
        tearDown();
    }
    public void tearDown() {
        Level var2 = this.level();
        if (var2 instanceof ServerLevel serverLevel) {
            AABB box = this.getBoundingBox();
            Vec3 center = box.getCenter();
            double xSpread = box.getXsize() * 0.3;
            double ySpread = box.getYsize() * 0.3;
            double zSpread = box.getZsize() * 0.3;
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK_CRUMBLE,
                    Blocks.OBSIDIAN.defaultBlockState()), center.x, center.y, center.z,
                    50, xSpread, ySpread, zSpread, 0.0F);
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK_CRUMBLE,
                    Blocks.CRYING_OBSIDIAN.defaultBlockState()), center.x, center.y, center.z,
                    25, xSpread, ySpread, zSpread, 0.0F);
        }

        this.makeSound(this.getDeathSound());
        this.remove(RemovalReason.DISCARDED);
    }
    @Override
    public boolean canBeCollidedWith(@Nullable Entity other) {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(ServerLevel level, DamageSource source) {
        if (source.is(DamageTypes.FALL)) {
            return true;
        }
        return super.isInvulnerableTo(level, source);
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR;
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ZOMBIE_BREAK_WOODEN_DOOR;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }
}
