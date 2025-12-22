package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.sound.SoundEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SmallSun extends ThrowableItemProjectile {
    private final List<Vec3> trailPositions = new ArrayList<>();
    public SmallSun(EntityType<? extends SmallSun> entityType, Level level) {
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
    public int live = 100;

    public boolean canSee = true;
    public boolean isCanAttack = true;
    public int attackSize = 0;
    public int maxAttackSize = 5;


    public boolean isG = false;
    public int onGTime = 0;
    public float onGTimeAlpha = 1;

    public void attack(){
        Vec3 playerPos = this.position();
        int range = 4;
        if (canSee) {
            if (attackSize > maxAttackSize) {
                return;
            }
            if (isCanAttack&&this.tickCount % 20 == 1) {
                List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
                for (LivingEntity entity : entities) {
                    if (this.getOwner() != null) {
                        if (!entity.is(this.getOwner()) && this.getOwner() instanceof Player player) {
                            Identifier entitys = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
                            if (!entitys.getNamespace().equals(Chestitem.MODID)) {
                                if (entity.isAlive()) {
                                    if (entity instanceof OwnableEntity ownableEntity) {
                                        if (ownableEntity.getOwner() != null) {
                                            if (ownableEntity.getOwner().is(this.getOwner())) {
                                                return;
                                            }
                                        }
                                    }
                                    entity.invulnerableTime = 0;
                                    entity.hurt(this.getOwner().damageSources().playerAttack(player), (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.1f);
                                    entity.setRemainingFireTicks(100);

                                    attackSize++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(new Vec3(0,-1,0));

        if (canSee) {
            if (this.tickCount > 200) {
                canSee = false;
            } else if (this.tickCount > 100 || attackSize >= maxAttackSize) {
//
//                if (this.getOwner() instanceof Player player) {
//                    Vec3 targetPos = player.position().add(0, 0.5, 0);
//                    Vec3 currentPos = this.position();
//                    Vec3 direction = targetPos.subtract(currentPos).normalize();
//                    this.setDeltaMovement(direction.x * 5, direction.y * 5, direction.z * 5);
//                    Vec3 playerPos = this.position();
//                    int range = 1;
//                    List<Player> entities = this.level().getEntitiesOfClass(Player.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
//                    for (Player player1 : entities){
//                        if (this.getOwner() instanceof Player player2) {
//                            if (player1.is(player2)){
//                                canSee = false;
//                            }
//                        }
//                    }
//                }
//
            }
            if (!this.level().getBlockState(this.blockPosition()).isEmpty()) {
                if (!isG){
                    this.level().playSound(null,this.blockPosition(), SoundEvents.MACE_SMASH_GROUND_HEAVY, SoundSource.AMBIENT,0.5f,0.5f);
                    BlockPos blockpos = new BlockPos(this.blockPosition().getX(),this.blockPosition().getY()-2,this.blockPosition().getX());
                    BlockState blockstate = this.level().getBlockState(blockpos);

                    int i = 150;
                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), (int) this.position().x, (int) this.position().y, (int) this.position().z, i, 1.25, 1.25, 1.25, 0.15000000596046448);
                    }
                    Vec3 playerPos = this.position();
                    int range = 5;
                    List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
                    for (LivingEntity entity : entities){
                        if (this.getOwner() instanceof Player player) {
                            if (!entity.is(player)){
                                entity.hurt(entity.damageSources().magic(), (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
                                entity.setRemainingFireTicks(200);
                            }
                        }
                    }

                    isG = true;
                }
                if (onGTime < 20) {
                    onGTime+=2;
                } else {
                    if (onGTimeAlpha>0.05) {
                        onGTimeAlpha -= 0.05f;
                    }
                }
                if (this.tickCount < 100) {
                    this.setDeltaMovement(0, 0, 0);
                }
            }
        }
        if (!canSee) {
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
        if (this.owner == null) {
            this.discard();
        }
        if (live<= 0) {
            this.discard();
        }
        this.setNoGravity(true);


        attack();
    }
}

