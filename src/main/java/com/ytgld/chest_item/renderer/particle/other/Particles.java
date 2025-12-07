package com.ytgld.chest_item.renderer.particle.other;


import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Particles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES;

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> colorPart;

    static {
        PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Chestitem.MODID);

        colorPart = PARTICLE_TYPES.register("color", ()->{
            return new SimpleParticleType(false);
        });
    }
}
