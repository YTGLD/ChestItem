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
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FireBlock_;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> orbAPart;


    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> colorPart_evil;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> orbAPart_evil;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> cube_evil;

    static {
        PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Chestitem.MODID);
        FireBlock_ = PARTICLE_TYPES.register("fire_block", ()->{
            return new SimpleParticleType(false);
        });
        colorPart = PARTICLE_TYPES.register("color", ()->{
            return new SimpleParticleType(false);
        });
        orbAPart = PARTICLE_TYPES.register("orb", ()->{
            return new SimpleParticleType(false);
        });


        colorPart_evil = PARTICLE_TYPES.register("color_evil", ()-> new SimpleParticleType(false));
        orbAPart_evil = PARTICLE_TYPES.register("orb_evil", ()-> new SimpleParticleType(false));
        cube_evil = PARTICLE_TYPES.register("cube_evil", ()-> new SimpleParticleType(false));
    }
}
