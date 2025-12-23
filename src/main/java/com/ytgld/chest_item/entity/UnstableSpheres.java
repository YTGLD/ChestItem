package com.ytgld.chest_item.entity;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UnstableSpheres extends ThrowableItemProjectile {
    private LivingEntity target;
    private final List<Vec3> trailPositions = new ArrayList<>();
    public boolean  isAttack = true;
    public UnstableSpheres(EntityType<? extends UnstableSpheres> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);

    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public boolean onGround() {
        return false;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
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
        if (canSee) {
            trailPositions.add(new Vec3(this.getX(), this.getY(), this.getZ()));
        }
        if (!trailPositions.isEmpty()) {
            if (trailPositions.size() > 5||!canSee) {
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

        if (canSee) {
            if (this.tickCount > 30) {
                canSee = false;
            }
        }
        if (canSee) {
            if (target != null) {
               if (this.tickCount == 1) {
                   this.setPos(target.position().add(0, 1, 0));

                   if (this.getOwner() != null && this.getOwner() instanceof Player player) {
                       if (target instanceof OwnableEntity ownableEntity) {
                           if (ownableEntity.getOwner() != null) {
                               if (ownableEntity.getOwner().is(player)) {
                                   target.heal(4);
                                   canSee = false;
                               }
                           }
                       }

                       if (target instanceof Targeting targeting) {
                           if (targeting.getTarget() != null) {
                               if (targeting.getTarget().is(player)) {
                                   target.hurt(target.damageSources().playerAttack(player),5);
                                   player.heal(2);
                                   target.invulnerableTime = 0;
                                   canSee = false;
                               }
                           }
                       }

                   }
               }
            }
        }else {
            this.setDeltaMovement(0,0,0);
        }
    }
}
