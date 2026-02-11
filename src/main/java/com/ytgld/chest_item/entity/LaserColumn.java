package com.ytgld.chest_item.entity;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LaserColumn extends TamableAnimal {
    private static final Logger log = LoggerFactory.getLogger(LaserColumn.class);
    protected void registerGoals() {
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, false));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, (double)1.0F, true));
        this.goalSelector.addGoal(7, new BreedGoal(this, (double)1.0F));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
    }

    @Override
    protected boolean isImmobile() {
        return true;
    }

    public LaserColumn(EntityType<? extends LaserColumn> entityType, Level level) {
        super(entityType, level);

    }

    public static Multimap<Holder<Attribute>, AttributeModifier> modifySpeedAndDamageTerriblePotion() {Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.GRAVITY, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                "g"),
                100, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.STEP_HEIGHT, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                "g"),
                100, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                "g"),
                0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return modifiers;
    }

    @Override
    public boolean hurtServer(ServerLevel p_376221_, DamageSource p_376460_, float p_376610_) {
        return false;
    }
    public final int maxLiveTime = 600;
    private void placeFire(Vec3 vec3,int x,int z){
        if (this.level().getBlockState(BlockPos.containing(vec3.add(x,0,z))).isAir()) {
            if (this.onGround()&&this.level().getBlockState(BlockPos.containing(vec3.add(x,-1,z))).isSolid()) {
                this.level().setBlock(BlockPos.containing(vec3.add(x,0,z)), Blocks.FIRE.defaultBlockState(), 3);
            }
        }
    }
    @Override
    public void tick() {
        super.tick();
        if (tickCount > maxLiveTime) {
            this.discard();
        }
        if (this.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.LAVA, getX(), getY(), getZ(), 9, 2, 2, 2, 0.2f);
        }
        placeFire(position(),0,0);

        this.setDeltaMovement(this.getDeltaMovement().x, -100, this.getDeltaMovement().z);
        this.getAttributes().addTransientAttributeModifiers(modifySpeedAndDamageTerriblePotion());
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
                    this.setTarget(living);
                    float offset = (float) Math.max(0, 20 - living.position().distanceTo(this.position()));
                    offset/=20f;
                    living.setYHeadRot(living.getYHeadRot() + Mth.nextFloat(RandomSource.create(), -offset, offset));
                    living.setXRot(living.getXRot() + Mth.nextFloat(RandomSource.create(), -offset, offset));
                    living.setYRot(living.getYRot() + Mth.nextFloat(RandomSource.create(), -offset, offset));
                }
            }
        }
        if ( this.tickCount % 4 == 0){
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
                    living.invulnerableTime = 0;
                    living.hurt(living.damageSources().inFire(), 10);
                    living.setRemainingFireTicks(100);

                }
            }
        }
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
