package com.ytgld.chest_item.renderer.particle.has_opt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public class ColorOption implements ParticleOptions{

    public static final MapCodec<ColorOption> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Vec3.CODEC.fieldOf("vec3").forGetter(ColorOption::getVec3),
                    Codec.BOOL.fieldOf("is_light").forGetter(opt -> opt.isLight),
                    Codec.INT.fieldOf("color").forGetter(opt -> opt.color),
                    Codec.FLOAT.fieldOf("size").forGetter(opt -> opt.size)
            ).apply(instance, ColorOption::new
            ));
    public static final StreamCodec<ByteBuf, ColorOption> STREAM_CODEC =
            StreamCodec.composite(
                    Vec3.STREAM_CODEC, ColorOption::getVec3,
                    ByteBufCodecs.BOOL, opt -> opt.isLight,
                    ByteBufCodecs.INT, opt -> opt.color,
                    ByteBufCodecs.FLOAT, opt -> opt.size,
                    ColorOption::new
            );

    private final Vec3 vec3;
    private final boolean isLight;
    private final int color;
    private final float size;

    private ColorOption(Vec3 vec3, boolean isLight, int color, float size){
        this.vec3 = vec3;
        this.isLight = isLight;
        this.color = color;
        this.size = size;
    }

    public static ColorOption creatParticle(ParticleType<ColorOption> type, Vec3 vec3, boolean isLight, int color, float size) {
        return new ColorOption(vec3,isLight,color,size );
    }

    @Override
    public ParticleType<?> getType() {
        return Particles.colorOption.get();
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