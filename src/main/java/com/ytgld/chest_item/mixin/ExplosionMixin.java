package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.items.memory.items.Martyrdom;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerExplosion;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ServerExplosion.class)
public abstract class ExplosionMixin {

    @Mutable
    @Shadow
    @Final
    private float radius;

    @Shadow
    @Final
    @Nullable
    private Entity source;

    @Shadow
    @Final
    private ServerLevel level;

    @Shadow
    @Final
    private Vec3 center;

    @Inject(
            method = {"<init>"},
            at = {@At("RETURN")}
    )
    private void init(ServerLevel level, Entity source, DamageSource damageSource, ExplosionDamageCalculator damageCalculator, Vec3 center, float radius, boolean fire, Explosion.BlockInteraction blockInteraction, CallbackInfo ci) {
        this.radius = Martyrdom.MartyrdomTooltip.boom(source,this.radius);
    }
    @Inject(
            method = {"explode"},
            at = {@At("HEAD")}
    )
    private void explode(CallbackInfoReturnable<Integer> cir) {
       if (this.source instanceof TraceableEntity traceableEntity&& traceableEntity.getOwner() instanceof Player player){
            float f2 = this.radius * 2.0F;
            int k1 = Mth.floor(this.center.x - (double) f2 - (double) 1.0F);
            int l1 = Mth.floor(this.center.x + (double) f2 + (double) 1.0F);
            int i2 = Mth.floor(this.center.y - (double) f2 - (double) 1.0F);
            int i1 = Mth.floor(this.center.y + (double) f2 + (double) 1.0F);
            int j2 = Mth.floor(this.center.z - (double) f2 - (double) 1.0F);
            int j1 = Mth.floor(this.center.z + (double) f2 + (double) 1.0F);
            List<Entity> list = this.level.getEntities(this.source, new AABB(k1, i2, j2, l1, i1, j1));
            for (Entity entity : list) {
                Martyrdom.MartyrdomTooltip.boom(player,entity);
            }
        }
    }
}
