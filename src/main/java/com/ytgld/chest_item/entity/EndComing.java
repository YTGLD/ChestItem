package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ytgld.chest_item.items.end.TheEndIsComing.chestHasEndComing;

public class EndComing  extends TamableAnimal {
    public EndComing(EntityType<? extends EndComing> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.setNoGravity(true);
    }
    @Override
    public void die(@NotNull DamageSource p_21809_) {
    }

    private final List<Vec3> trailPositions = new ArrayList<>();

    public List<Vec3> getTrailPositions() {
        return trailPositions;
    }


    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        {
            Vec3 playerPos = this.position();
            int range = 10;
            List<EndComing> imperialHematomas = this.level().getEntitiesOfClass(EndComing.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
            for (EndComing imperialHematoma : imperialHematomas){
                if (imperialHematoma.getOwner()!= null &&this.getOwner()!=null) {
                    if (!imperialHematoma.is(this)){
                        if (imperialHematoma.getOwner().is(this.getOwner())){
                            imperialHematoma.discard();
                            return;
                        }
                    }
                }
            }
        }

        LivingEntity owner = getOwner(); // 获取主人
        LivingEntity target = getTarget(); // 获取目标
        Vec3 currentPos = this.position();

        if (target != null) {
            Vec3 targetPos = target.position().add(0, 0.5, 0);
            Vec3 direction = targetPos.subtract(currentPos).normalize();
            this.setDeltaMovement(direction.x * (0.075f + 0.5), direction.y * (0.075f + 0.5), direction.z * (0.075f + 0.5));
        }
        if (owner != null){
            double desiredDistance = 2; // 设置想要保持的距离
            Vec3 targetPos = owner.position().add(0, 3, -1.5); // 获取玩家位置并抬高

            Vec3 forward = owner.getLookAngle(); // 获取玩家的朝向向量
            Vec3 direction = forward.scale(-1).normalize(); // 计算背后的方向（逆向）

            Vec3 newTargetPos = targetPos.add(direction.scale(desiredDistance)); // 计算新的目标位置

            if (owner.level() instanceof ServerLevel serverLevel) {
                this.teleportTo(serverLevel, newTargetPos.x, newTargetPos.y, newTargetPos.z,
                        Set.of(), this.getYRot(), this.getXRot(), false);
            }
        }
        trailPositions.add(new Vec3(this.getX(), this.getY(), this.getZ()));

        if (trailPositions.size() > 30) {
            trailPositions.removeFirst();
        }

        if (this.getTarget() != null&&this.getOwner()!=null){
            if (this.getTarget().is(this.getOwner())){
                this.setTarget(null);
            }
        }
        if (this.getTarget() != null) {
            if (!this.getTarget().isAlive()) {
                this.setTarget(null);
            }
        }
        if (this.getOwner()!= null &&this.getOwner() instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.TheEndIsComing_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag!=null) {
                                if (!compoundTag.getBooleanOr(chestHasEndComing,false)){
                                    this.discard();
                                }
                            }
                        }
                    }
                }
            }

        }
        float s = 5;
        if (this.getOwner()!= null &&this.getOwner() instanceof Player player&&this.getTarget()!=null){
            if (this.tickCount % (int) s == 0) {
                AttackEndComing attackBlood = new AttackEndComing(Entitys.AttackEndComing_.get(), this.level());
                attackBlood.setTarget(this.getTarget());
                attackBlood.setPos(this.position());
                attackBlood.setOwner(this.getOwner());
                attackBlood.follow = false;
                this.level().addFreshEntity(attackBlood);
                playRemoveOneSound(this);

            }
            if (this.tickCount % 10 == 0) {
                AttackEndComing attackBlood = new AttackEndComing(Entitys.AttackEndComing_.get(), this.level());
                attackBlood.setTarget(this.getTarget());
                attackBlood.setPos(this.position());
                attackBlood.setOwner(this.getOwner());
                attackBlood.follow = true;
                attackBlood.setDeltaMovement(new Vec3(Mth.nextFloat(RandomSource.create(),-0.5f,0.5f),Mth.nextFloat(RandomSource.create(),-0.5f,0.5f),Mth.nextFloat(RandomSource.create(),-0.5f,0.5f)));
                this.level().addFreshEntity(attackBlood);
                playRemoveOneSound(this);

            }
        }
        if (this.getOwner()!= null&&this.getOwner() instanceof Player player) {
            if (look(player.level(),player) instanceof LivingEntity living) {
                this.setTarget(living);
            } else {
                this.setTarget(null);
            }
        }
    }
    public Entity look(Level level, LivingEntity living) {
        Entity pointedEntity = null;
        double range = 20.0D;
        Vec3 srcVec = living.getEyePosition();
        Vec3 lookVec = living.getViewVector(1.0F);
        Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
        float var9 = 1.0F;
        List<Entity> possibleList = level.getEntities(living, living.getBoundingBox().expandTowards(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range).inflate(var9, var9, var9));
        double hitDist = 0;

        for (Entity possibleEntity : possibleList) {

            if (possibleEntity.isPickable()) {
                float borderSize = possibleEntity.getPickRadius();
                AABB collisionBB = possibleEntity.getBoundingBox().inflate(borderSize, borderSize, borderSize);
                Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);

                if (collisionBB.contains(srcVec)) {
                    if (0.0D < hitDist || hitDist == 0.0D) {
                        pointedEntity = possibleEntity;
                        hitDist = 0.0D;
                    }
                } else if (interceptPos.isPresent()) {
                    double possibleDist = srcVec.distanceTo(interceptPos.get());

                    if (possibleDist < hitDist || hitDist == 0.0D) {
                        pointedEntity = possibleEntity;
                        hitDist = possibleDist;
                    }
                }
            }
        }
        return pointedEntity;
    }
    private boolean isMoon(LivingEntity living){
        if (living != null){
            if (living instanceof OwnableEntity entity) {
                if (entity.getOwner() != null && this.getOwner() != null) {
                    if (entity.getOwner().is(this.getOwner())){
                        return false;
                    }
                }
            }
            ResourceLocation entity = BuiltInRegistries.ENTITY_TYPE.getKey(living.getType());
            return !entity.getNamespace().equals(Chestitem.MODID);
        }
        return  true;
    }
    private void playRemoveOneSound(Entity p_186343_) {
        p_186343_.playSound(SoundEvents.WARDEN_HEARTBEAT, 0.8F, 0.8F + p_186343_.level().getRandom().nextFloat() * 0.4F);
    }
    @Override
    public boolean isFood(ItemStack pStack) {
        return false;
    }
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean attackable() {
        return false;
    }


    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity owner) {
        return false;
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return false;
    }


    @Override
    public boolean hurtServer(ServerLevel p_376221_, DamageSource p_376460_, float p_376610_) {
        return false;
    }

    protected void doPush(Entity p_27415_) {
    }
    protected void pushEntities() {
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());

        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, Monster.class, false));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
       return null;
    }
}

