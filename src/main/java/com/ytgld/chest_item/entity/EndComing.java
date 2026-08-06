package com.ytgld.chest_item.entity;

import com.mojang.math.Axis;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.has_opt.ColorOption;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import com.ytgld.chest_item.renderer.particle.other.SwordEnergyOption;
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
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.*;

import static com.ytgld.chest_item.items.other.end.TheEndIsComing.chestHasEndComing;

    public class EndComing  extends TamableAnimal {


        private final List<Handler.Vec3Color> trailPositions = new ArrayList<>();
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
        public List<Handler.Vec3Color> getTrailPositions() {
            return trailPositions;
        }
        public int getDoSize() {
            return doSize;
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

            if (this.getOwner() instanceof Player player && player.getLastHurtByMob() instanceof LivingEntity living) {
                if (player.position().distanceTo(living.position()) < 45) {
                    if (this.tickCount % time ==1) {
                        TheHyperplasia theHyperplasia = new TheHyperplasia(Entitys.TheHyperplasia_.get(), living.level());
                        theHyperplasia.setTarget(living);
                        theHyperplasia.setPos(this.position().add(0, 0, 0));
                        theHyperplasia.setOwner(player);
                        theHyperplasia.setDamage( 5 + (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.1F));
                        this.level().addFreshEntity(theHyperplasia);
                        if (this.level() instanceof ServerLevel level) {
                            level.sendParticles(Particles.orbAPart.get(), getX() + 0, getY() + 0, getZ() + 0, 2, 0, 0, 0, 0);
                        }
                    }
                }
            }
        }

        public static final class ColorBlood {
            public int c1;
            public int c2;
            public int c3;
            public int c4;
            public int c5;
            public int c6;

            public ColorBlood() {
                this.c1 = 0;
                this.c2 = 0;
                this.c3 = 0;
                this.c4 = 0;
                this.c5 = 0;
                this.c6 = 0;
            }
        }

        public ColorBlood colorBlood = null;


        @Override
        public void tick() {
            super.tick();
            if (this.tickCount <= 240){
                if (this.tickCount % 40 == 0) {
                    if (this.level() instanceof ServerLevel level) {
                        level.sendParticles(ColorOption.creatParticle(Particles.colorOption.get(),
                                        Vec3.ZERO, true, Light.ARGB.color(255, 255, 12, 20), 7),
                                this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0);
                    }
                }
            }
            if (colorBlood == null) {
                colorBlood = new ColorBlood();
            }else {
                int l = 15;
                if (tickCount % 20 == 1) {
                    if (new Random().nextInt(100)< l) {
                        colorBlood.c1 = 20;
                    }
                    if (new Random().nextInt(100)< l) {
                        colorBlood.c2 = 20;
                    }
                    if (new Random().nextInt(100)< l) {
                        colorBlood.c3 = 20;
                    }
                    if (new Random().nextInt(100)< l) {
                        colorBlood.c4 = 20;
                    }
                    if (new Random().nextInt(100)< l) {
                        colorBlood.c5 = 20;
                    }
                    if (new Random().nextInt(100)< l) {
                        colorBlood.c6 = 20;
                    }
                }



                if (colorBlood.c1 > 0) {colorBlood.c1--;}
                if (colorBlood.c2 > 0) {colorBlood.c2--;}
                if (colorBlood.c3 > 0) {colorBlood.c3--;}
                if (colorBlood.c4 > 0) {colorBlood.c4--;}
                if (colorBlood.c5 > 0) {colorBlood.c5--;}
                if (colorBlood.c6 > 0) {colorBlood.c6--;}
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

            if (this.level().isClientSide()) {
                trailPositions.add(new Handler.Vec3Color(new Vec3(this.getX(), this.getY(), this.getZ()), Light.ARGB.color(255, 255, 0, blueColor)));
                if (blueColor > 0) {
                    blueColor -= 5;
                }
                if (trailPositions.size() > 85) {
                    trailPositions.removeFirst();
                }
            }
            clear();
            hurtaTTACK();
        }
        public static void getOwnerHurt(LivingDamageEvent.@UnknownNullability Pre event){
            if (event.getSource().getDirectEntity() instanceof Player player) {
                if (player.position().distanceTo(event.getEntity().position()) > 5) {
                    return;
                }
                if (Handler.has(player, InitItems.TheEndIsComing_.asItem())) {
                    if (!player.getCooldowns().isOnCooldown(InitItems.TheEndIsComing_.asItem().getDefaultInstance())) {
                        Vec3 playerPos = player.position();
                        int range = 4;
                        List<EndComing> imperialHematomas = player.level().getEntitiesOfClass(EndComing.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
                        for (EndComing endComing : imperialHematomas) {
                            if (endComing.getOwner() instanceof Player player1 && player.is(player1)) {
                                TheHyperplasia theHyperplasia = new TheHyperplasia(Entitys.TheHyperplasia_.get(), endComing.level());
                                theHyperplasia.setTarget(event.getEntity());
                                theHyperplasia.setPos(endComing.position().add(0, 0, 0));
                                theHyperplasia.setOwner(player);
                                theHyperplasia.setDamage(5 + (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.1F));
                                endComing.level().addFreshEntity(theHyperplasia);
                                if (endComing.level() instanceof ServerLevel level) {
                                    level.sendParticles(Particles.orbAPart.get(), endComing.getX() + 0, endComing.getY() + 0, endComing.getZ() + 0, 2, 0, 0, 0, 0);
                                }
                                player.getCooldowns().addCooldown(InitItems.TheEndIsComing_.asItem().getDefaultInstance(),20);
                            }
                        }
                    }
                }
            }
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

