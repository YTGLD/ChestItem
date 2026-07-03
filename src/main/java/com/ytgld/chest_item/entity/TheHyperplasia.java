package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TheHyperplasia extends ThrowableItemProjectile implements Targeting {
    public TheHyperplasia(EntityType<? extends TheHyperplasia> type, Level level) {
        super(type, level);
    }

    public LivingEntity target;
    @Override
    protected Item getDefaultItem() {
        return Items.APPLE;
    }
    public int live = 50;
    public boolean canSee = true;
    private final List<Vec3> trailPositions = new ArrayList<>();

    @Override
    protected boolean updateFluidInteraction() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        attack();
        float speed = 1.5f;
        if (isHasEffectEnd()) {
            speed *= 2;
        }
        if (canSee) {
            if (!this.level().isClientSide()) {
                if (target instanceof LivingEntity livingEntity) {
                    if (this.tickCount == 1) {
                        Vec3 targetPos = livingEntity.position().add(0, 1, 0);
                        Vec3 currentPos = this.position();
                        Vec3 direction = targetPos.subtract(currentPos).normalize();
                        this.setDeltaMovement(direction.x * (speed), direction.y * (speed), direction.z * (speed));
                    }
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
        if (this.tickCount > 200) {
            canSee = false;
        }
        if (live<= 0) {
            this.discard();
        }
    }

    public List<Vec3> getTrailPositions() {
        return trailPositions;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    public @Nullable LivingEntity getTarget() {
        return target;
    }

    float damage = 5;
    public void setDamage(float v) {
        damage = v;
    }
    public void attack(){
        Vec3 playerPos = this.position().add(0, 0.75, 0);
        int range = 1;
        if (canSee) {
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
            for (LivingEntity entity : entities) {
                if (this.getOwner() instanceof Player player && target instanceof LivingEntity livingEntity) {
                    if (entity.is(livingEntity) &&!entity .is(player)) {
                        livingEntity.invulnerableTime = 0;
                        livingEntity.hurt(livingEntity.damageSources().playerAttack(player),damage);
                        if (!livingEntity.isAlive()) {
                            if (this.level() instanceof ServerLevel level) {
                                level.sendParticles(Particles.colorPart.get(), getX() + 0, getY() + 0, getZ() + 0, 24, 0, 0, 0, 0.2f);
                                level.sendParticles(Particles.FireBlock_.get(), getX() + 0, getY() + 0, getZ() + 0, 16, 0, 0, 0, 0.2f);
                            }
                        }
                        canSee = false;
                    }
                }
            }
        }
    }
    public boolean isHasEffectEnd(){
        if (this.getOwner()!= null &&this.getOwner() instanceof Player player){
            return Handler.has(player, InitItems.EndEffect_.asItem());
        }
        return false;
    }

}
