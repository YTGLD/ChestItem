package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.has_opt.ColorOption;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import com.ytgld.chest_item.sounds.Sounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class EvilMotherSpirit extends ThrowableItemProjectile {


    private final List<Handler.Vec3Color> trailPositions = new ArrayList<>();
    private int attackTIME = 0;
    private int notAttackTime = 10;
    public int doSize= 0;
    public int gColor = 255;
    public boolean canSee = true;
    public int live = 200;


    public EvilMotherSpirit(EntityType<? extends EvilMotherSpirit> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.setNoGravity(true);
    }
    public List<Handler.Vec3Color> getTrailPositions() {
        return trailPositions;
    }
    public int getDoSize() {
        return doSize;
    }

    public void dis(){}

    @Override
    protected boolean updateFluidInteraction() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.isAlive()) {
            canSeeSetFalse();
        }
        if (attackTIME > 0) {
            attackTIME --;
        }
        if (notAttackTime < 10) {
            notAttackTime ++;
        }
        doSize = Math.min(attackTIME,notAttackTime);

        this.setNoGravity(true);
        dis();

        if (this.getOwner() == null) {
            this.discard();
        }
        if (canSee) {
            trailPositions.add(new Handler.Vec3Color(new Vec3(this.getX(), this.getY(), this.getZ()), Light.ARGB.color(255, 80, 255, 180)));
        }
        if (gColor < 245) {
            gColor += 25;
        }
        if (!trailPositions.isEmpty()) {
            if (trailPositions.size() > 85||!canSee) {
                trailPositions.removeFirst();
            }
        }
        if (!canSee) {
            live--;
            this.setDeltaMovement(0,0,0);
        }
        if (live<= 0) {
            this.discard();
        }
        if (tickCount > 100) {
            canSeeSetFalse();
        }
    }
    public static float sanValue (Player player){
        float base = (float) player.getAttributeBaseValue(AttReg.theSanity);
        float san = (float) player.getAttributeValue(AttReg.theSanity) - base;
        if (san < 0) {
            san = -san;
        }
        return san;
    }


    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        Vec3 playerPos = this.position().add(0, 0.75, 0);
        int range = 2;
        if (canSee) {
            List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
            this.level().playSound(null,this.getX(),this.getY(),this.getZ(), Sounds.Heart, SoundSource.BLOCKS,0.1f,1);
            for (LivingEntity entity : entities){
                if ((this.getOwner()!=null && this.getOwner() instanceof Player player) && !entity.is(this.getOwner())){
                    float theSanValue = sanValue(player);
                    entity.hurt(entity.damageSources().playerAttack(player),theSanValue);
                }
            }
        }
        canSeeSetFalse();
        super.onHitBlock(hitResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        if ((this.getOwner()!=null && this.getOwner() instanceof Player player) && !hitResult.getEntity().is(this.getOwner())) {
            float theSanValue = sanValue(player);
            if (hitResult.getEntity() instanceof LivingEntity livingEntity) {
                livingEntity.invulnerableTime = 0;
                float damageEffect = theSanValue;
                if (theSanValue > 0) {
                    if (livingEntity.addEffect(new MobEffectInstance(Effects.EvilErosion, (int) (theSanValue * 20 * 4), 0))){
                        damageEffect *= 1.5f;
                    }
                }
                livingEntity.hurt(livingEntity.damageSources().playerAttack(player),damageEffect);
                canSeeSetFalse();
            }
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        attackTIME = 0;
        notAttackTime = 10;
        return super.hurtServer(level, source, damage);
    }

    @Override
    public boolean hurtClient(DamageSource source) {
        attackTIME = 0;
        notAttackTime = 10;
        return super.hurtClient(source);
    }

    private void canSeeSetFalse(){
        if (canSee) {
            if (this.level() instanceof ServerLevel level) {
                level.sendParticles(Particles.colorPart_evil.get(), getX() + 0, getY() + 0, getZ() + 0, 24, 0, 0, 0, 0.2f);
                level.sendParticles(Particles.cube_evil.get(), getX() + 0, getY() + 0, getZ() + 0, 32, 0, 0, 0, 0.2f);
                level.sendParticles(ColorOption.creatParticle(Particles.colorOption.get(),
                                Vec3.ZERO, true, Light.ARGB.color(100, 40, 125, 90), 10),
                        this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                level.sendParticles(Particles.orbAPart_evil.get(), getX() + 0, getY() + 0.25, getZ() + 0, 1, 0, 0, 0, 0);
            }
            canSee = false;
        }
    }

    public boolean isPushable() {
        return false;
    }


    @Override
    protected Item getDefaultItem() {
        return Items.APPLE;
    }
}


