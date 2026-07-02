package com.ytgld.chest_item.renderer.particle.other;


import com.mojang.serialization.MapCodec;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Particles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Chestitem.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> colorPart;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FireBlock_;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> orbAPart;


    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> colorPart_evil;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> orbAPart_evil;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> cube_evil;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> evil_tailing;
    public static final DeferredHolder<ParticleType<?>, ParticleType<SwordEnergyOption>> SwordEnergyOption_ =
            PARTICLE_TYPES.register("sword_energy",
                    () -> new ParticleType<>(false) {
                        @Override
                        public MapCodec<SwordEnergyOption> codec() {
                            return SwordEnergyOption.CODEC;
                        }

                        @Override
                        public StreamCodec<? super RegistryFriendlyByteBuf, SwordEnergyOption> streamCodec() {
                            return SwordEnergyOption.STREAM_CODEC;
                        }
                    });
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> sword_shadow_1;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> sword_shadow_2;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> sword_shadow_3;
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> sword_shadow_4;



    static {
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
        evil_tailing = PARTICLE_TYPES.register("evil_tailing", ()-> new SimpleParticleType(false));

        sword_shadow_1 = PARTICLE_TYPES.register("sword_shadow_1", ()-> new SimpleParticleType(false));
        sword_shadow_2 = PARTICLE_TYPES.register("sword_shadow_2", ()-> new SimpleParticleType(false));
        sword_shadow_3 = PARTICLE_TYPES.register("sword_shadow_3", ()-> new SimpleParticleType(false));
        sword_shadow_4 = PARTICLE_TYPES.register("sword_shadow_4", ()-> new SimpleParticleType(false));

    }
}
