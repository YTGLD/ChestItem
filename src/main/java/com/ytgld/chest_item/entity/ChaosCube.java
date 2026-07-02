package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChaosCube extends TamableAnimal {
    private final List<Vec3Color> trailPositions = new ArrayList<>();
    public float xAxis,yAxis,zAxis;
    public int live = 200;
    public boolean canSee = true;
    public boolean isAttack = false;
    public LivingEntity target;
    private int attackTime = 0;
    private final List<Vec3Last> lastAttackPos = new ArrayList<>();

    public static final int maxSize = 10;
    public static final int maxLast = 25;
    public int color = Light.ARGB.color(0,0,0,100);

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        xAxis+= 0.1f;
        yAxis+= 0.1f;
        zAxis+= 0.1f;

        int as = addColor((color >> 24) & 0xFF);
        int rs = addColor((color >> 16) & 0xFF);
        int gs = addColor((color >> 8) & 0xFF);
        int bs = downColor(color & 0xFF);
        color = Light.ARGB.color(as,rs,gs,bs);
        if (!trailPositions.isEmpty()) {
            if (trailPositions.size() > maxSize || !canSee) {
                trailPositions.removeFirst();
            }
        }
        if (!canSee) {
            live--;
        }

        if (live<= 0) {
            this.discard();
        }
        if (target == null) {
            findNewTarget();
        }
        if (target!=null && target.isAlive() && isAttack) {
            if (this.tickCount % 5 == 0) {
                target.invulnerableTime = 0;
                target.hurt(target.damageSources().magic(), 0.5f);
                lastAttackPos .add(new Vec3Last(target.position(),this.position(),this.color));
            }
        }
        if (!lastAttackPos.isEmpty()) {
            if (this.tickCount % 5 == 0) {
                if (lastAttackPos.size() > maxLast || !canSee || !isAttack) {
                    lastAttackPos.removeFirst();
                }
            }
        }


        if (target!=null && target.position().distanceTo(this.position()) > 20){
            isAttack = false;
            target = null;
        }
        if (target!=null && !target.isAlive()) {
            isAttack = false;
            target = null;
        }

        if (isAttack) {
            if (attackTime < 240) {
                attackTime += 15;
            }
        }else {
            if (attackTime > 0) {
                attackTime -= 15;
            }
        }
        attackTime = Math.max(0,attackTime);
    }

    @Override
    public boolean hurtClient(DamageSource source) {
        if (canSee) {
            Vec3 vec3 = new Vec3(this.getX(), this.getY(), this.getZ());
            trailPositions.add(new Vec3Color(vec3, color, new AxisCi(xAxis, yAxis, zAxis)));
        }
        return super.hurtClient(source);
    }

    @Override
    protected void tickDeath() {
        if (live <= 0) {
            super.tickDeath();
        }
        if (!trailPositions.isEmpty()) {
            if (trailPositions.size() > maxSize||!canSee) {
                trailPositions.removeFirst();
            }
        }
    }

    public List<Vec3Last> getLastAttackPos() {
        return lastAttackPos;
    }

    private void findNewTarget() {

        AABB searchBox = this.getBoundingBox().inflate(16);
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, searchBox);
        double closestDistance = Double.MAX_VALUE;
        LivingEntity closestEntity = null;


        for (LivingEntity entity : entities) {
//            if (this.getOwner() != null)
            {
                if (!(entity instanceof Player) && !entity.is(this))
                {
                    double distance = this.distanceToSqr(entity);
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestEntity = entity;
                    }
                }
            }
        }
        this.target = closestEntity;
        this.isAttack = true;
    }
    public List<Vec3Color> getTrailPositions() {
        return trailPositions;
    }

    private int addColor(int in){
        if (in < 255) {
            in = in +1;
        }
        return in;
    }
    private int downColor(int in){
        if (in > 255) {
            in = in - 1;
        }
        return in;
    }
    public ChaosCube(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public void die(DamageSource source) {
        canSee = false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    public record Vec3Color(Vec3 vec3 , int color,AxisCi axisCi){}
    public record Vec3Last(Vec3 target , Vec3 me,int color){}
    public record AxisCi(float x,float y,float z ){}

}
