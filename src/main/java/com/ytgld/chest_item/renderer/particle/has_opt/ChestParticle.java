package com.ytgld.chest_item.renderer.particle.has_opt;

import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ChestParticle extends SingleQuadParticle {
    public boolean isLight= false;
    public int color = Light.ARGB.color(255,255,255,255);
    public float size = 2;

    private ChestParticle(ClientLevel level, double x, double y, double z, SpriteSet sprite) {
        super(level, x, y, z, sprite.first());
        this.lifetime = 50;
        this.scale(size);
    }

    @Override
    protected int getLightCoords(float a) {
        return 255;
    }
    public void tick() {
        super.tick();

        this.roll+=0.05f + Mth.nextFloat(RandomSource.create(),0.01F,0.2F);
        this.oRoll+= (float) (0.05 + Mth.nextFloat(RandomSource.create(),0.01F,0.2F));
        if (alpha>0.05f) {
            this.alpha -= 0.05f;
        }
        if (alpha < 0.1) {
            this.remove();
        }
    }

    @Override
    protected @NotNull Layer getLayer() {
        if (!isLight){
            return new Layer(true, TextureAtlas.LOCATION_PARTICLES, MRender.RenderPs.PARTICLE);
        }
        else {
            return new Layer(true, TextureAtlas.LOCATION_PARTICLES, MRender.RenderPs.TRANSLUCENT_PARTICLE);
        }
    }
    public record Provider(SpriteSet sprite) implements ParticleProvider<ColorOption> {
        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }
        public SpriteSet sprite() {
            return this.sprite;
        }

        @Override
        public @NotNull Particle createParticle(ColorOption simpleParticleType, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5, RandomSource textureAtlasSprite) {
            ChestParticle particle = new ChestParticle(clientLevel, v,v1,v2,sprite);
            particle.setSpriteFromAge(this.sprite);

            int color = simpleParticleType.getColor();
            int as = (color >> 24) & 0xFF;
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;

            particle.setColor(rs / 255f,gs / 255f,bs / 255f);
            particle.isLight = simpleParticleType.isLight();
            particle.scale(simpleParticleType.getSize());

            particle.setParticleSpeed(simpleParticleType.getVec3().x,simpleParticleType.getVec3().y,simpleParticleType.getVec3().z);
            return particle;
        }
    }
}