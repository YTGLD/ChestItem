package com.ytgld.chest_item.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AbyssOrb  extends ThrowableItemProjectile {
    public AbyssOrb(EntityType<? extends AbyssOrb> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.setNoGravity(true);
        this.noPhysics = true;
    }


    public boolean space = false;

    public boolean canSee = true;
    public int live = 50;
    @Override
    protected void checkSupportingBlock(boolean onGround, @Nullable Vec3 movement) {

    }

    private final List<Vec3> trailPositions = new ArrayList<>();

    public LivingEntity target ;
    @Override
    public void tick() {
        this.setNoGravity(true);
        noPhysics = true;
        if (canSee) {
            trailPositions.add(new Vec3(this.getX(), this.getY(), this.getZ()));
        }
        if (!trailPositions.isEmpty()) {
            if (trailPositions.size() > 20 || !canSee) {
                trailPositions.removeFirst();
            }
        }
        float speed = 1;
        if (space) {
            speed = 3;
        }
        if (canSee) {
            if (this.target != null) {
                if (this.tickCount > 6||space) {
                    if (!this.level().isClientSide) {
                        Vec3 targetPos = this.target.position().add(0, 1, 0);
                        Vec3 currentPos = this.position();
                        Vec3 direction = targetPos.subtract(currentPos).normalize();
                        this.setDeltaMovement(direction.x * speed, direction.y * speed, direction.z * speed);
                    }
                }
            }
        } else {
            this.setDeltaMovement(0, 0, 0);
        }
        if (this.tickCount > 120) {
            canSee = false;
            live--;
        }
        if (!canSee) {
            live--;
        }
        if (live <= 0) {
            this.discard();
        }
        if (canSee) {
            Vec3 playerPos = this.position();
            float range = 0.5f;
            List<LivingEntity> entities =
                    this.level().getEntitiesOfClass(LivingEntity.class,
                            new AABB(playerPos.x - range,
                                    playerPos.y - range,
                                    playerPos.z - range,
                                    playerPos.x + range,
                                    playerPos.y + range,
                                    playerPos.z + range));
            for (LivingEntity living : entities) {
                if (this.getOwner() != null && !living.is(this.getOwner()) && this.getOwner() instanceof Player player) {
                    if (this.tickCount > 15) {
                        living.hurt(living.damageSources().magic(), 4);
                        living.invulnerableTime = 0;
                        living.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100, 1));
                        this.level().addParticle(ParticleTypes.SONIC_BOOM, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
                        canSee = false;
                    }
                }
            }
            super.tick();
        }
    }

    public List<Vec3> getTrailPositions() {
        return trailPositions;
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.IRON_SWORD;
    }
}

