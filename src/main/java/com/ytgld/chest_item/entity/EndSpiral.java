package com.ytgld.chest_item.entity;

import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public class EndSpiral extends ThrowableItemProjectile {
    public EndSpiral(EntityType<? extends EndSpiral> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.setNoGravity(true);
        this.noPhysics = true;
    }
    @Override
    protected void checkSupportingBlock(boolean onGround, @Nullable Vec3 movement) {
    }
    @Override
    public void tick() {
        this.setNoGravity(true);
        if (this.tickCount > 200) {
            this.discard();
        }
    }
    private int time = 0;
    @Override
    public void playerTouch(Player player) {
        if (this.getOwner() instanceof Player player1) {
            if (player.is(player1)) {
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.AMBIENT, time / 100F, time / 100F);
                time ++ ;
                if (time > 100) {
                    Optional<GlobalPos> optionalGlobalPos = player.getLastDeathLocation();
                    if (optionalGlobalPos.isPresent()) {
                        GlobalPos globalPos = optionalGlobalPos.get();
                        if (player.getServer() != null) {
                            ServerLevel targetWorld = player.getServer().getLevel(globalPos.dimension());
                            if (targetWorld != null) {
                                player.teleportTo(targetWorld, globalPos.pos().getX(), globalPos.pos().getY(), globalPos.pos().getZ(),
                                        Set.of(), player.getYRot(), player.getXRot(), false);
                                player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 50, 3));
                                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 50, 3));
                                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 50, 3));
                                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 50, 3));
                                player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.AMBIENT, 1, 1);
                                time = 0;
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.IRON_SWORD;
    }
}


