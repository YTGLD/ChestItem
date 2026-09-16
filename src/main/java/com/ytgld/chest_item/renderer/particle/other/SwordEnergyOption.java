package com.ytgld.chest_item.renderer.particle.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
public class SwordEnergyOption implements ParticleOptions{

    public static final MapCodec<SwordEnergyOption> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Vec3.CODEC.fieldOf("vec3").forGetter(SwordEnergyOption::getVec3),
                    Codec.BOOL.fieldOf("is_light").forGetter(opt -> opt.isLight),
                    Codec.INT.fieldOf("color").forGetter(opt -> opt.color),
                    Codec.FLOAT.fieldOf("size").forGetter(opt -> opt.size)
            ).apply(instance, SwordEnergyOption::new
            ));
    public static final StreamCodec<ByteBuf, SwordEnergyOption> STREAM_CODEC =
            StreamCodec.composite(
                    Vec3.STREAM_CODEC, SwordEnergyOption::getVec3,
                    ByteBufCodecs.BOOL, opt -> opt.isLight,
                    ByteBufCodecs.INT, opt -> opt.color,
                    ByteBufCodecs.FLOAT, opt -> opt.size,
                    SwordEnergyOption::new
            );

    private final Vec3 vec3;
    private final boolean isLight;
    private final int color;
    private final float size;

    private SwordEnergyOption(Vec3 vec3, boolean isLight, int color, float size){
        this.vec3 = vec3;
        this.isLight = isLight;
        this.color = color;
        this.size = size;
    }

    public static SwordEnergyOption createSwordEnergyOption(ParticleType<SwordEnergyOption> type, Vec3 vec3, boolean isLight, int color, float size) {
        return new SwordEnergyOption(vec3,isLight,color,size );
    }

    @Override
    public ParticleType<?> getType() {
        return Particles.SwordEnergyOption_.get();
    }

    public Vec3 getVec3() {
        return vec3;
    }

    public boolean isLight() {
        return isLight;
    }

    public int getColor() {
        return color;
    }

    public float getSize() {
        return size;
    }
}