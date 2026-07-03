package com.ytgld.chest_item.entity;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.sounds.Sounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LaserColumn extends TamableAnimal {
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, (double)1.0F, true));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
    }
    public LaserColumn(EntityType<? extends LaserColumn> entityType, Level level) {
        super(entityType, level);
        this.getAttributes().addTransientAttributeModifiers(modifySpeedAndDamageTerriblePotion());
    }

    public Multimap<Holder<Attribute>, AttributeModifier> modifySpeedAndDamageTerriblePotion() {Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.GRAVITY, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                "g"),
                100, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.STEP_HEIGHT, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                "g"),
                100, AttributeModifier.Operation.ADD_VALUE));
        if (this.getOwner() != null && this.getOwner() instanceof Player player) {
            modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    "g"),
                    player.getAttributeValue(Attributes.MOVEMENT_SPEED), AttributeModifier.Operation.ADD_VALUE));
        }
        return modifiers;
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean hurtServer(ServerLevel p_376221_, DamageSource p_376460_, float p_376610_) {
        return false;
    }
    public final int maxLiveTime = 600;
    private void placeFire(Vec3 vec3,int x,int z,LivingEntity living){
        if (living.level().getBlockState(BlockPos.containing(vec3.add(x,0,z))).isAir()) {
            if (living.onGround()&&living.level().getBlockState(BlockPos.containing(vec3.add(x,-1,z))).isSolid()) {
                living.level().setBlock(BlockPos.containing(vec3.add(x,0,z)), Blocks.FIRE.defaultBlockState(), 3);
            }
        }
    }

    @Override
    public void die(DamageSource cause) {

    }
    private final List<Vec3> trailPositions = new ArrayList<>();
    public List<Vec3> getTrailPositions() {
        return trailPositions;
    }

    public int live = 100;

    public boolean canSee = true;
    @Override
    public void tick() {
        super.tick();
        if (tickCount > maxLiveTime) {
            canSee = false;
        }
        placeFire(position(),0,0,this);
        if (!canSee) {
            live--;
            if (live<= 0) {
                this.discard();
            }
        }
        if (this.tickCount % 5 == 1) {
            if (canSee) {
                trailPositions.add(new Vec3(this.getX() + Mth.nextFloat(RandomSource.create(),0.1f,0.125f),
                        this.getY()+ Mth.nextFloat(RandomSource.create(),0.1f,0.125f),
                        this.getZ()+ Mth.nextFloat(RandomSource.create(),0.1f,0.125f)));
            }
        }
        if (!trailPositions.isEmpty()) {
            if (trailPositions.size() > 30 || !canSee) {
                trailPositions.removeFirst();
            }
        }
        if (canSee) {
            float sound = maxLiveTime - tickCount;
            float doS = Math.min(100, sound);
            doS /= 100f;
            if (this.tickCount % 5 == 1) {
                this.level().playSound(null, this.blockPosition(), Sounds.LASER.value(), SoundSource.NEUTRAL, 0.25F * doS, 1);
            }
            if (tickCount == 1) {
                this.level().playSound(null, this.blockPosition(), Sounds.LASER.value(), SoundSource.NEUTRAL, 1f, 1f);
                this.level().playSound(null, this.blockPosition(), SoundEvents.TRIAL_SPAWNER_OMINOUS_ACTIVATE, SoundSource.NEUTRAL, 5.0F, 1f);
            }
            if (this.level() instanceof ServerLevel level) {
                level.sendParticles(ParticleTypes.LAVA, getX(), getY(), getZ(), 9, 2, 2, 2, 0.2f);
            }

            this.setDeltaMovement(this.getDeltaMovement().x, -100, this.getDeltaMovement().z);
            Vec3 playerPos = this.position().add(0, 0, 0);
            {
                int range = 20;
                List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class,
                        new AABB(playerPos.x - range,
                                playerPos.y - range,
                                playerPos.z - range,

                                playerPos.x + range,
                                playerPos.y + 30,
                                playerPos.z + range));
                for (LivingEntity living : entities) {
                    if (!living.is(this)) {
                        if (living instanceof LaserColumn) {
                            this.discard();
                        }
                        if (getTarget() == null && getTarget() != this) {
                            this.setTarget(living);
                        }
                        float offset = (float) Math.max(0, 20 - living.position().distanceTo(this.position()));
                        offset /= 10f;
                        living.setYHeadRot(living.getYHeadRot() + Mth.nextFloat(RandomSource.create(), -offset, offset));
                        living.setXRot(living.getXRot() + Mth.nextFloat(RandomSource.create(), -offset, offset));
                        living.setYRot(living.getYRot() + Mth.nextFloat(RandomSource.create(), -offset, offset));
                    }
                }
            }
            if (this.tickCount % 4 == 0) {
                int range = 6;
                List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class,
                        new AABB(playerPos.x - range,
                                playerPos.y - range,
                                playerPos.z - range,

                                playerPos.x + range,
                                playerPos.y + 30,
                                playerPos.z + range));
                for (LivingEntity living : entities) {
                    if (!living.is(this)) {
                        float damage = 10;
                        if (this.getOwner() != null && this.getOwner() instanceof Player player) {
                            damage += (float) (player.getAttributeValue(Attributes.MAX_HEALTH) + player.getAttributeValue(Attributes.ATTACK_DAMAGE));
                            if (!living.is(player)) {
                                living.invulnerableTime = 0;
                                living.hurt(living.damageSources().inFire(), damage);
                                living.setRemainingFireTicks(100);
                                placeFire(living.position(), 0, 0, living);
                                placeFire(living.position(), 1, 0, living);
                                placeFire(living.position(), 0, 1, living);
                                placeFire(living.position(), -1, 0, living);
                                placeFire(living.position(), 0, -1, living);
                            }
                        }
                    }
                }
            }
            if (this.getTarget() != null && !this.getTarget().isAlive()) {
                this.setTarget(null);
            }
            if (this.getOwner() != null && this.getOwner() instanceof Player player) {
                if (this.getTarget() != null && this.getTarget().is(player)) {
                    this.setTarget(null);
                }
            }
        }else {
            this.setDeltaMovement(0,0,0);
        }
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return false;
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
