package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import oshi.driver.mac.net.NetStat;

import java.util.ArrayList;
import java.util.List;

public class AttackEndComing extends ThrowableItemProjectile {
    private LivingEntity target;
    private final List<Vec3> trailPositions = new ArrayList<>();
    public float damages = 4;
    public float addDamgae = 0;
    public float speeds = 2;
    public boolean follow;

    public AttackEndComing(EntityType<? extends AttackEndComing> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);

    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
    }

    public LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {

        this.target = target;
    }

    public List<Vec3> getTrailPositions() {
        return trailPositions;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.ENDER_PEARL;
    }

    @Override
    public @NotNull ItemStack getItem() {
        return Items.ENDER_PEARL.getDefaultInstance();
    }
    @Override
    public float getXRot() {
        return 0;
    }

    @Override
    public void move(MoverType type, Vec3 movement) {

    }

    @Override
    public float getYRot() {
        return 0;
    }
    public int live = 50;

    public boolean canSee = true;
    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        this.noPhysics = true;
        Vec3 playerPos = this.position().add(0, 0.75, 0);
        int range = 1;
        if (canSee) {
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
            for (LivingEntity entity : entities) {
                if (this.getOwner() != null) {
                    if (!entity.is(this.getOwner()) && this.getOwner() instanceof Player player) {
                        ResourceLocation entitys = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
                        if (!entitys.getNamespace().equals(Chestitem.MODID)) {
                            if (entity.isAlive()) {
                                entity.invulnerableTime = 0;
                                entity.hurt(this.getOwner().damageSources().playerAttack(player), (float) (damages + addDamgae + player.getMaxHealth() / 10 + player.getAttributeValue(Attributes.ATTACK_DAMAGE) / 10));
                                canSee = false;
                            }
                        }
                    }
                }
            }
        }

        if (canSee) {
            if (this.tickCount > 100) {
                canSee = false;
            }
            if (target != null) {
                if (!target.isAlive()) {
                    findNewTarget();
                }
            }
        }

        float s = 0.175F;
        if (canSee) {
            if (target != null) {
                if (follow) {
                    if (tickCount>10) {
                        Vec3 targetPos = target.position().add(0, 1, 0);
                        Vec3 currentPos = this.position();
                        Vec3 direction = targetPos.subtract(currentPos).normalize();

                        // 获取当前运动方向
                        Vec3 currentDirection = this.getDeltaMovement().normalize();

                        // 计算目标方向与当前方向之间的夹角
                        double angle = Math.acos(currentDirection.dot(direction)) * (180.0 / Math.PI);

                        // 如果夹角超过10度，则限制方向
                        if (angle > 10) {
                            // 计算旋转后的新方向
                            double angleLimit = Math.toRadians(10); // 将10度转为弧度

                            // 根据正弦法则计算限制后的方向
                            Vec3 limitedDirection = currentDirection.scale(Math.cos(angleLimit)) // 计算缩放因子
                                    .add(direction.normalize().scale(Math.sin(angleLimit))); // 根据目标方向进行调整

                            this.setDeltaMovement(limitedDirection.x * (0.125f + s), limitedDirection.y * (0.125f + s), limitedDirection.z * (0.125f + s));
                        } else {
                            this.setDeltaMovement(direction.x * (0.125f + s), direction.y * (0.125f + s), direction.z * (0.125f + s));
                        }
                    }
                }else if (this.tickCount <= 1) {
                    Vec3 targetPos = target.position().add(0, 0.5, 0);
                    Vec3 currentPos = this.position();
                    Vec3 direction = targetPos.subtract(currentPos).normalize();
                    this.lookAt(EntityAnchorArgument.Anchor.EYES,new Vec3(targetPos.x, targetPos.y, targetPos.z));
                    this.lookAt(EntityAnchorArgument.Anchor.FEET,new Vec3(targetPos.x, targetPos.y, targetPos.z));
                    this.setDeltaMovement(direction.x * (speeds + s), direction.y * (speeds + s), direction.z * (speeds + s));
                }
            }
        }else {
            this.setDeltaMovement(0,0,0);
        }
        if (canSee) {
            trailPositions.add(new Vec3(this.getX(), this.getY(), this.getZ()));
        }
        if (!trailPositions.isEmpty()) {
            if (trailPositions.size() > 16||!canSee) {
                trailPositions.removeFirst();
            }
        }
        if (!canSee) {
            live--;
        }
        if (live<= 0) {
            this.discard();
        }
        this.setNoGravity(true);
    }
    private void findNewTarget() {

        AABB searchBox = this.getBoundingBox().inflate(16);
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, searchBox);
        double closestDistance = Double.MAX_VALUE;
        LivingEntity closestEntity = null;


        for (LivingEntity entity : entities) {
            ResourceLocation name = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            if (this.getOwner() != null) {
                if (!name.getNamespace().equals(Chestitem.MODID) && !(entity.is(this.getOwner()))) {
                    double distance = this.distanceToSqr(entity);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestEntity = entity;
                    }
                }
            }
        }

        this.target = closestEntity;
    }
}
