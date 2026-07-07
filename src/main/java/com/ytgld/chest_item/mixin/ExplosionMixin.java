package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.items.memory.items.Martyrdom;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
    @Shadow
    public abstract float radius();

    @Mutable
    @Shadow
    @Final
    private float radius;

    @Shadow
    @Final
    private double x;

    @Shadow
    @Final
    private double y;

    @Shadow
    @Final
    private double z;

    @Shadow
    @Final
    @Nullable
    private Entity source;

    @Shadow
    @Final
    private Level level;

    @Inject(
            method = {"<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Explosion$BlockInteraction;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/particles/ParticleOptions;Lnet/minecraft/core/Holder;)V"},
            at = {@At("RETURN")}
    )
    private void init(Level level, Entity source, DamageSource damageSource, ExplosionDamageCalculator damageCalculator, double x, double y, double z, float radius, boolean fire, Explosion.BlockInteraction blockInteraction, ParticleOptions smallExplosionParticles, ParticleOptions largeExplosionParticles, Holder explosionSound, CallbackInfo ci) {
        this.radius = Martyrdom.MartyrdomTooltip.boom(source,this.radius);
    }
    @Inject(
            method = {"explode"},
            at = {@At("HEAD")}
    )
    private void explode(CallbackInfo ci) {
       if (this.source instanceof TraceableEntity traceableEntity&& traceableEntity.getOwner() instanceof Player player){
            float f2 = this.radius * 2.0F;
            int k1 = Mth.floor(this.x - (double) f2 - (double) 1.0F);
            int l1 = Mth.floor(this.x + (double) f2 + (double) 1.0F);
            int i2 = Mth.floor(this.y - (double) f2 - (double) 1.0F);
            int i1 = Mth.floor(this.y + (double) f2 + (double) 1.0F);
            int j2 = Mth.floor(this.z - (double) f2 - (double) 1.0F);
            int j1 = Mth.floor(this.z + (double) f2 + (double) 1.0F);
            List<Entity> list = this.level.getEntities(this.source, new AABB(k1, i2, j2, l1, i1, j1));
            EventHooks.onExplosionDetonate(this.level, (Explosion) (Object) this, list, f2);
            for (Entity entity : list) {
                Martyrdom.MartyrdomTooltip.boom(player,entity);
            }
        }
    }
}
