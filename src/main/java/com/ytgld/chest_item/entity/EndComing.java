package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.has_opt.ColorOption;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import oshi.driver.mac.net.NetStat;

import java.util.*;

import static com.ytgld.chest_item.items.other.end.TheEndIsComing.chestHasEndComing;

    public class EndComing  extends TamableAnimal implements TrailEntity{


        private final List<Vec3> trailPositions = new ArrayList<>();
        public int color1Add = 0;
        private int attackTIME = 0;
        private int notAttackTime = 10;
        public int doSize= 0;
        public boolean canLive = true;
        public int blueColor = 255;
        public static final String  isTrial = "isTrial";


        public EndComing(EntityType<? extends EndComing> p_21803_, Level p_21804_) {
            super(p_21803_, p_21804_);
            this.setNoGravity(true);
        }
        @Override
        public void die(@NotNull DamageSource p_21809_) {
        }

        public void dis(){
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
        public boolean isHasEffectEnd(){
            if (this.getOwner()!= null &&this.getOwner() instanceof Player player){
                return Handler.has(player, InitItems.EndEffect_.asItem());
            }
            return false;
        }

        public void hurtaTTACK(){
            int time = 8;
            if (isHasEffectEnd()) {
                time = 5;
            }

            if (this.getOwner() instanceof Player player && this.getTarget() instanceof LivingEntity living) {
                if (player.position().distanceTo(living.position()) < 45) {
                    if (this.tickCount % time ==1) {
                        AttackEndComing attackEndComing = new AttackEndComing(Entitys.AttackEndComing_.get(), living.level());
                        attackEndComing.setPos(this.position().add(0, 0, 0));
                        attackEndComing.setOwner(player);
                        attackEndComing.setTarget(living);
                        attackEndComing.damages += ((float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.1F));
                        this.level().addFreshEntity(attackEndComing);
                        if (this.level() instanceof ServerLevel level) {
                            level.sendParticles(ColorOption.creatParticle(Particles.colorOption.get(),
                                            Vec3.ZERO, true, Light.ARGB.color(255, 255, 12, 20), 3),
                                    this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                        }
                    }
                }
            }
        }

        @Override
        public List<Vec3> trailPositions() {
            return trailPositions;
        }

        @Override
        public void move(MoverType type, Vec3 movement) {
        }
        @Override
        public void tick() {
            super.tick();

            this.setPos(
                    this.getX() + this.getDeltaMovement().x,
                    this.getY() + this.getDeltaMovement().y,
                    this.getZ() + this.getDeltaMovement().z
            );

            if (this.level() instanceof ServerLevel level) {
                level.sendParticles(ColorOption.creatParticle(Particles.colorOption.get(),
                                new Vec3(
                                        this.getDeltaMovement().x * 1.5f ,
                                        this.getDeltaMovement().y * 1.5f ,
                                        this.getDeltaMovement().z * 1.5f
                                ),
                                true, Light.ARGB.color(255, 255, 10, 50), 2),
                        this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
            }
            if (this.tickCount == 10) {
                if (this.level() instanceof ServerLevel level) {
                    level.sendParticles(ColorOption.creatParticle(Particles.colorOption.get(),
                                    Vec3.ZERO, true, Light.ARGB.color(255, 255, 12, 20), 10),
                            this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                }
            }
            if (tickCount == 10) {
                if (this.level() instanceof ServerLevel level) {
                    level.sendParticles(Particles.COLOR_PART.get(), getX() + 0, getY() + 0, getZ() + 0, 24, 0, 0, 0, 0.2f);
                    level.sendParticles(Particles.FireBlock_.get(), getX() + 0, getY() + 0, getZ() + 0, 16, 0, 0, 0, 0.2f);
                }
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
            LivingEntity owner = getOwner();
            if (owner != null) {
                if (owner.isDeadOrDying()) {
                    this.discard();
                }
                Vec3 currentPos = this.position();
                float speed = (float) (0.25f - (8 - owner.position().distanceTo(currentPos)) / 10f);
                if (speed < 0) {
                    speed = 0;
                }
                double desiredDistance = 2;
                Vec3 targetPos = owner.position().add(0, 3, 1);

                Vec3 forward = owner.getLookAngle();
                Vec3 direction = forward.scale(-1).normalize();

                Vec3 newTargetPos = targetPos.add(direction.scale(desiredDistance));

                this.setDeltaMovement(newTargetPos.subtract(currentPos).normalize().scale(speed));
            }

            if (this.level().isClientSide()) {
                trailPositions.add(this.position());
                if (blueColor > 0) {
                    blueColor -= 5;
                }
                if (trailPositions.size() > 16) {
                    trailPositions.removeFirst();
                }
            }
            if (owner instanceof Player player) {
                if (Handler.has(player,InitItems.EndEffect_.asItem())) {
                    if (this.getTarget() == null) {
                        Vec3 playerPos = this.position().add(0, 0.75, 0);
                        int range = 20;
                        List<Mob> entities = this.level().getEntitiesOfClass(Mob.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
                        for (Mob mob : entities) {
                            if (!mob.is(this)) {
                                this.setTarget(mob);
                            }
                        }
                    }
                } else {
                    this.setTarget(player.getLastHurtMob());
                }
            }
            clear();
            hurtaTTACK();
        }

        private void clear(){
            if (canLive) {
                if (this.getOwner() != null && this.getOwner() instanceof Player player) {
                    ChestInventory chestInventory = Handler.getItem(player);
                    if (chestInventory != null) {
                        if (!player.level().isClientSide()) {
                            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                                ItemStack stack = chestInventory.getItem(i);
                                if (stack.is(InitItems.TheEndIsComing_)) {
                                    canLive = true;
                                    CompoundTag compoundTag = stack.get(DataReg.tag);
                                    if (compoundTag != null) {
                                        if (!compoundTag.getBooleanOr(chestHasEndComing, false)) {
                                            canLive = false;
                                        }
                                    }
                                    return;
                                }else {
                                    canLive = false;
                                }

                            }
                        }
                    }
                }
            }
            if (!canLive){
                this.discard();
            }
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
        public boolean hurtServer(ServerLevel p_376221_, DamageSource p_376460_, float p_376610_) {
            return false;
        }

        protected void doPush(Entity p_27415_) {
        }
        protected void pushEntities() {
        }

        @Nullable
        @Override
        public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
           return null;
        }
    }

