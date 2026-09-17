package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.reactor.Calciner;
import com.ytgld.chest_item.items.other.Agreement;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.has_opt.ColorOption;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;


public class Reactor extends TamableAnimal {
    public Reactor(EntityType<? extends Reactor> type, Level level) {
        super(type, level);
    }
    private int teleportCooldown;
    public boolean isInReactor =  false;
    @Override
    public void tick() {
        super.tick();
        Vec3 playerPos = this.position().add(0, 1, 0);
        int range = 2;

        List<ItemEntity> entities = this.level().getEntitiesOfClass(
                ItemEntity.class,
                new AABB(
                        playerPos.x - range, playerPos.y - range, playerPos.z - range,
                        playerPos.x + range, playerPos.y + range, playerPos.z + range
                )
        );
        isInReactor = !entities.isEmpty();

        for (ItemEntity entity : entities) {
            if (entity.tickCount > 10) {
                if (entity.tickCount % 5 != 1) {
                    continue;
                }

                ItemStack stack = entity.getItem();

                if (stack.isEmpty()) {
                    continue;
                }

                if (!(this.level() instanceof ServerLevel level)) {
                    continue;
                }
                SingleRecipeInput input = new SingleRecipeInput(stack);

                Optional<RecipeHolder<SmeltingRecipe>> recipe = level.recipeAccess()
                        .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(stack), level);

                if (recipe.isEmpty()) {
                    continue;
                }
                ItemStack result = recipe.get().value().assemble(input);
                if (result.isEmpty() || result.is(stack.getItem())) {
                    continue;
                } else {
                    if (tickCount % 20 == 1) {
                        level.playSound(null, this.blockPosition(), SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 1, 1);
                    }
                }
                stack.shrink(1);
                ItemEntity resultEntity = new ItemEntity(
                        level,
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        result.copy()
                );
                level.sendParticles(ColorOption.creatParticle(Particles.colorOption.get(),
                                new Vec3(0, 0.05, 0), true, Light.ARGB.color(255, 100, 255, 50), 2),
                        resultEntity.getX(), resultEntity.getY() + 0.5f, resultEntity.getZ(), 1, 0, 0, 0, 0);
                ExperienceOrb experienceOrb = new ExperienceOrb(level, resultEntity.getX(), resultEntity.getY(), resultEntity.getZ(),
                        (int) (recipe.get().value().experience() + 1) * 3);
                level.addFreshEntity(experienceOrb);

                resultEntity.setDeltaMovement(entity.getDeltaMovement());
                level.playSound(null, resultEntity.blockPosition(), SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1, 1);
                level.addFreshEntity(resultEntity);

                if (this.getOwner() instanceof Player player && !player.level().isClientSide()){
                    CompoundTag compoundTag = player.getPersistentData();
                    if (compoundTag.getIntOr(Calciner.fireNumber,0) < 100) {
                        compoundTag.putInt(Calciner.fireNumber,
                                compoundTag.getIntOr(Calciner.fireNumber, 0) + 1);
                    }
                }
                if (stack.isEmpty()) {

                    entity.discard();
                }
            }
        }

        if (this.teleportCooldown > 0) {
            --this.teleportCooldown;
        }
        if (this.level() instanceof ServerLevel level) {
            if (this.random.nextInt(100) <= 80) {
                level.sendParticles(ColorOption.creatParticle(Particles.colorOption.get(),
                                new Vec3(0, 0.05, 0), true, Light.ARGB.color(255, 100, 255, 50), 1),
                        this.getX(), this.getY() + 0.5f, this.getZ(), 1, 0, 0, 0, 0);
            }
        }
        if (this.getOwner() instanceof Player player) {
            if (!this.level().isClientSide() && teleportCooldown <= 0) {
                if (player.position().distanceTo(this.position()) > 12.5f) {
                    if (this.teleportSomewhere(player)) {
                        this.teleportCooldown = 20;
                    }
                }
            }
        }
        dis();
        clear();
    }
    public boolean canLive = true;

    private void clear(){
        if (canLive) {
            if (this.getOwner() != null && this.getOwner() instanceof Player player) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    if (!player.level().isClientSide()) {
                        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                            ItemStack stack = chestInventory.getItem(i);
                            if (stack.is(InitItems.Agreement_)) {
                                canLive = true;
                                CompoundTag compoundTag = stack.get(DataReg.tag);
                                if (compoundTag != null) {
                                    if (!compoundTag.getBooleanOr(Agreement.chestHasReactor, false)) {
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

    public void dis(){
        Vec3 playerPos = this.position();
        int range = 10;
        List<Reactor> list = this.level().getEntitiesOfClass(Reactor.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        for (Reactor reactor : list){
            if (reactor.getOwner()!= null &&this.getOwner()!=null) {
                if (!reactor.is(this)){
                    if (reactor.getOwner().is(this.getOwner())){
                        reactor.discard();
                        return;
                    }
                }
            }
        }
    }
    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_REPAIR;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.BEACON_DEACTIVATE;
    }
    protected @Nullable Direction findAttachableSurface(BlockPos target) {
        for(Direction direction : Direction.values()) {
            if (this.canStayAt(target, direction)) {
                return direction;
            }
        }

        return null;
    }

    protected boolean teleportSomewhere(Player player) {
        if (!this.isNoAi() && this.isAlive()) {
            BlockPos current = this.blockPosition();

            for(int attempt = 0; attempt < 5; ++attempt) {
                BlockPos target = player.blockPosition().offset(Mth.randomBetweenInclusive(this.random, -8, 8), Mth.randomBetweenInclusive(this.random, -8, 8), Mth.randomBetweenInclusive(this.random, -8, 8));
                if (target.getY() > this.level().getMinY() && this.level().isEmptyBlock(target) && this.level().getWorldBorder().isWithinBounds(target) && this.level().noCollision(this, (new AABB(target)).deflate(1.0E-6))) {
                    Direction attachmentDirection = this.findAttachableSurface(target);
                    if (attachmentDirection != null) {
                        target = BlockPos.containing(target.getX(), target.getY(), target.getZ());
                    }

                    if (attachmentDirection != null) {
                        this.unRide();
                        this.playSound(SoundEvents.SHULKER_TELEPORT, 1.0F, 1.0F);
                        this.setPos((double)target.getX() + (double)0.5F, (double)target.getY(), (double)target.getZ() + (double)0.5F);
                        this.level().gameEvent(GameEvent.TELEPORT, current, GameEvent.Context.of(this));
                        this.setTarget((LivingEntity)null);
                        return true;
                    }
                }
            }

            return false;
        } else {
            return false;
        }
    }
    private boolean canStayAt(BlockPos target, Direction face) {
        if (this.isPositionBlocked(target)) {
            return false;
        } else {
            Direction oppositeFace = face.getOpposite();
            if (!this.level().loadedAndEntityCanStandOnFace(target.relative(face), this, oppositeFace)) {
                return false;
            } else {
                AABB fullyOpened = getProgressAabb(this.getScale(), oppositeFace, 1.0F, Vec3.atBottomCenterOf(target)).deflate(1.0E-6);
                return this.level().noCollision(this, fullyOpened);
            }
        }
    }
    public static AABB getProgressAabb(float size, Direction direction, float progressTo, Vec3 position) {
        return getProgressDeltaAabb(size, direction, -1.0F, progressTo, position);
    }

    public static AABB getProgressDeltaAabb(float size, Direction direction, float progressFrom, float progressTo, Vec3 position) {
        AABB boundsAtBottomCenter = new AABB((double)(-size) * (double)0.5F, (double)0.0F, (double)(-size) * (double)0.5F, (double)size * (double)0.5F, (double)size, (double)size * (double)0.5F);
        double maxMovement = (double)Math.max(progressFrom, progressTo);
        double minMovement = (double)Math.min(progressFrom, progressTo);
        AABB aabb = boundsAtBottomCenter.expandTowards((double)direction.getStepX() * maxMovement * (double)size, (double)direction.getStepY() * maxMovement * (double)size, (double)direction.getStepZ() * maxMovement * (double)size).contract((double)(-direction.getStepX()) * ((double)1.0F + minMovement) * (double)size, (double)(-direction.getStepY()) * ((double)1.0F + minMovement) * (double)size, (double)(-direction.getStepZ()) * ((double)1.0F + minMovement) * (double)size);
        return aabb.move(position.x, position.y, position.z);
    }
    private boolean isPositionBlocked(BlockPos target) {
        BlockState state = this.level().getBlockState(target);
        if (state.isAir()) {
            return false;
        } else {
            boolean movingPistonInOurCurrentPosition = state.is(Blocks.MOVING_PISTON) && target.equals(this.blockPosition());
            return !movingPistonInOurCurrentPosition;
        }
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }
    @Override
    public boolean canBeCollidedWith(@Nullable Entity other) {
        return true;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return this;
    }
}
