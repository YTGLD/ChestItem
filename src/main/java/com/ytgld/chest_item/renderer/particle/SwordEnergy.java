package com.ytgld.chest_item.renderer.particle;

import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.other.SwordEnergyOption;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class SwordEnergy  extends SimpleAnimatedParticle {
    public Vec3 axis = Vec3.ZERO;
    public boolean isLight= false;
    public int color = Light.ARGB.color(255,255,255,255);
    public float size = 10;

    private SwordEnergy(ClientLevel level, double x, double y, double z, SpriteSet sprite) {
        super(level,x,y,z,sprite,0);
        this.setParticleSpeed(0,0,0);
        this.scale(size);
    }
    public void extract(QuadParticleRenderState reusedState, Camera camera, float partialTick) {
        Vec3 axis = this.axis;
        Quaternionf rotation = new Quaternionf()
                .rotateX(-(float)Math.PI / 2)
                .rotateXYZ(
                        (float) Math.toRadians(axis.x),
                        (float) Math.toRadians(axis.y),
                        (float) Math.toRadians(axis.z)
                );
        this.extractRotatedQuad(reusedState, camera, rotation, partialTick);
    }

    @Override
    public Layer getLayer() {
        if (!isLight){
            return new Layer(true, TextureAtlas.LOCATION_PARTICLES, MRender.RenderPs.PARTICLE);
        }
        else {
            return new Layer(true, TextureAtlas.LOCATION_PARTICLES, MRender.RenderPs.TRANSLUCENT_PARTICLE);
        }
    }

    public record Provider(SpriteSet sprite) implements ParticleProvider<SwordEnergyOption> {
        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }
        public SpriteSet sprite() {
            return this.sprite;
        }

        @Override
        public @NotNull Particle createParticle(SwordEnergyOption simpleParticleType, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5, RandomSource textureAtlasSprite) {
            SwordEnergy particle = new SwordEnergy(clientLevel, v,v1,v2,sprite);
            particle.setSpriteFromAge(this.sprite);

            particle.axis = simpleParticleType.getVec3();
            particle.isLight = simpleParticleType.isLight();
            int color = simpleParticleType.getColor();
            int as = (color >> 24) & 0xFF;
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;
            particle.setColor(rs / 255f,gs / 255f,bs / 255f);

            particle.size = simpleParticleType.getSize();

            return particle;
        }
    }
}


