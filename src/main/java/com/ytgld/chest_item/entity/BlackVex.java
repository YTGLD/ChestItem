package com.ytgld.chest_item.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class BlackVex extends TamableAnimal {
    public static final int TICKS_PER_FLAP = Mth.ceil(3.9269907F);
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID;
    @Nullable
    private BlockPos boundOrigin;
    private boolean hasLimitedLife;
    private int limitedLifeTicks;

    public BlackVex(EntityType<? extends BlackVex> p_33984_, Level p_33985_) {
        super(p_33984_, p_33985_);
        this.moveControl = new BlackVex.BlackVexMoveControl(this);
        this.xpReward = 0;
    }

    public boolean isFlapping() {
        return this.tickCount % TICKS_PER_FLAP == 0;
    }

    protected boolean isAffectedByBlocks() {
        return !this.isRemoved();
    }

    @Override
    public void die(DamageSource cause) {

    }

    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
        if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
            this.limitedLifeTicks = 15;
            this.hurt(this.damageSources().starve(), 2);
        }
        if (this.getOwner() instanceof Player player) {
            if (this.getTarget()==null) {
                Vec3 playerPos = player.position().add(0, 0.75, 0);
                float range = 10;
                List<LivingEntity> entities =
                        player.level().getEntitiesOfClass(LivingEntity.class,
                                new AABB(playerPos.x - range,
                                        playerPos.y - range,
                                        playerPos.z - range,
                                        playerPos.x + range,
                                        playerPos.y + range,
                                        playerPos.z + range));

                for (LivingEntity living : entities) {
                    if (!living.is(player)) {
                        this.setTarget(living);
                        break;
                    }
                }
            }else {
                Vec3 currentPos = this.position();
                this.setIsCharging(true);

                double desiredDistance = 2; // 设置想要保持的距离
                Vec3 targetPos = this.getTarget().position().add(0, 3, 0); // 获取玩家位置并抬高

                Vec3 forward = this.getTarget().getLookAngle(); // 获取玩家的朝向向量
                Vec3 direction = forward.scale(Math.sin(tickCount/40f)*2).normalize(); // 计算背后的方向（逆向）

                Vec3 newTargetPos = targetPos.add(direction.scale(desiredDistance)); // 计算新的目标位置

                if (currentPos.distanceTo(targetPos)<=desiredDistance*1.5f){
                    if (tickCount%60==1) {
                        this.getTarget().invulnerableTime = 0;
                        this.getTarget().hurt(this.getTarget().damageSources().magic(), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                    }
                }

                this.setDeltaMovement(newTargetPos.subtract(currentPos).normalize().scale(0.5)); // 设置对象的运动速度

            }
        }
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(8, new BlackVex.BlackVexRandomMoveGoal());
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, LivingEntity.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{Raider.class})).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 10).add(Attributes.ATTACK_DAMAGE, 5);
    }

    protected void defineSynchedData(SynchedEntityData.Builder p_326059_) {
        super.defineSynchedData(p_326059_);
        p_326059_.define(DATA_FLAGS_ID, (byte)0);
    }

    protected void readAdditionalSaveData(ValueInput p_422040_) {
        super.readAdditionalSaveData(p_422040_);
        this.boundOrigin = p_422040_.read("bound_pos", BlockPos.CODEC).orElse(null);
        p_422040_.getInt("life_ticks").ifPresentOrElse(this::setLimitedLife, () -> {
            this.hasLimitedLife = false;
        });
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    protected void addAdditionalSaveData(ValueOutput p_422240_) {
        super.addAdditionalSaveData(p_422240_);
        p_422240_.storeNullable("bound_pos", BlockPos.CODEC, this.boundOrigin);
        if (this.hasLimitedLife) {
            p_422240_.putInt("life_ticks", this.limitedLifeTicks);
        }
    }

    @Nullable
    public BlockPos getBoundOrigin() {
        return this.boundOrigin;
    }

    private boolean getBlackVexFlag(int mask) {
        int i = (Byte)this.entityData.get(DATA_FLAGS_ID);
        return (i & mask) != 0;
    }

    private void setBlackVexFlag(int mask, boolean value) {
        int i = (Byte)this.entityData.get(DATA_FLAGS_ID);
        if (value) {
            i |= mask;
        } else {
            i &= ~mask;
        }

        this.entityData.set(DATA_FLAGS_ID, (byte)(i & 255));
    }

    public boolean isCharging() {
        return this.getBlackVexFlag(1);
    }

    public void setIsCharging(boolean charging) {
        this.setBlackVexFlag(1, charging);
    }

    public void setLimitedLife(int limitedLifeTicks) {
        this.hasLimitedLife = true;
        this.limitedLifeTicks = limitedLifeTicks;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.VEX_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.VEX_HURT;
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34002_, DifficultyInstance p_34003_, EntitySpawnReason p_364407_, @Nullable SpawnGroupData p_34005_) {
        RandomSource randomsource = p_34002_.getRandom();
        this.populateDefaultEquipmentSlots(randomsource, p_34003_);
        this.populateDefaultEquipmentEnchantments(p_34002_, randomsource, p_34003_);
        return super.finalizeSpawn(p_34002_, p_34003_, p_364407_, p_34005_);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }
    protected void populateDefaultEquipmentSlots(RandomSource p_219135_, DifficultyInstance p_219136_) {
        if (this.getOwner() != null&&getOwner() instanceof Player player) {
            this.setItemSlot(EquipmentSlot.MAINHAND, player.getMainHandItem());
        }
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Override
    public ItemStack getItemInHand(InteractionHand hand) {
        if (this.getOwner() != null&&getOwner() instanceof Player player) {
            return player.getMainHandItem();
        }
        return super.getItemInHand(hand);
    }

    static {
        DATA_FLAGS_ID = SynchedEntityData.defineId(BlackVex.class, EntityDataSerializers.BYTE);
    }

    class BlackVexMoveControl extends MoveControl {
        public BlackVexMoveControl(BlackVex vex) {
            super(vex);
        }

        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - BlackVex.this.getX(), this.wantedY - BlackVex.this.getY(), this.wantedZ - BlackVex.this.getZ());
                double d0 = vec3.length();
                if (d0 < BlackVex.this.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    BlackVex.this.setDeltaMovement(BlackVex.this.getDeltaMovement().scale(0.5));
                } else {
                    BlackVex.this.setDeltaMovement(BlackVex.this.getDeltaMovement().add(vec3.scale(this.speedModifier * 0.05 / d0)));
                    if (BlackVex.this.getTarget() == null) {
                        Vec3 vec31 = BlackVex.this.getDeltaMovement();
                        BlackVex.this.setYRot(-((float)Mth.atan2(vec31.x, vec31.z)) * 57.295776F);
                    } else {
                        double d2 = BlackVex.this.getTarget().getX() - BlackVex.this.getX();
                        double d1 = BlackVex.this.getTarget().getZ() - BlackVex.this.getZ();
                        BlackVex.this.setYRot(-((float)Mth.atan2(d2, d1)) * 57.295776F);
                    }
                    BlackVex.this.yBodyRot = BlackVex.this.getYRot();
                }
            }

        }
    }

    class BlackVexRandomMoveGoal extends Goal {
        public BlackVexRandomMoveGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return !BlackVex.this.getMoveControl().hasWanted() && BlackVex.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {
            BlockPos blockpos = BlackVex.this.getBoundOrigin();
            if (blockpos == null) {
                blockpos = BlackVex.this.blockPosition();
            }

            for(int i = 0; i < 3; ++i) {
                BlockPos blockpos1 = blockpos.offset(BlackVex.this.random.nextInt(15) - 7, BlackVex.this.random.nextInt(11) - 5, BlackVex.this.random.nextInt(15) - 7);
                if (BlackVex.this.level().isEmptyBlock(blockpos1)) {
                    BlackVex.this.moveControl.setWantedPosition((double)blockpos1.getX() + 0.5, (double)blockpos1.getY() + 0.5, (double)blockpos1.getZ() + 0.5, 0.1);
                    if (BlackVex.this.getTarget() == null) {
                        BlackVex.this.getLookControl().setLookAt((double)blockpos1.getX() + 0.5, (double)blockpos1.getY() + 0.5, (double)blockpos1.getZ() + 0.5, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }

}
