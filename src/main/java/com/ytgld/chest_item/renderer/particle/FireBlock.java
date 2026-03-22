package com.ytgld.chest_item.renderer.particle;

import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;
import org.jspecify.annotations.Nullable;

public class FireBlock extends SingleQuadParticle {
    public int max = 200;

    protected FireBlock(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, TextureAtlasSprite textureAtlasSprite) {
        super(level, x, y, z, 0, 0, 0,textureAtlasSprite);
        this.lifetime = max;
        this.scale(4);
        this.setParticleSpeed(0,0,0);
    }
    @Override
    public void tick() {
        super.tick();
        this.setPos(x,y+0.0001f,z);
        alpha -= 0.005f;
        if (alpha <= 0) {
            this.remove();
        }
        if (lifetime <= 0) {
            this.remove();
        }
    }
    @Override
    public void extract(QuadParticleRenderState reusedState, Camera camera, float partialTick) {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.rotateX(-(float)Math.PI / 2);
        this.extractRotatedQuad(reusedState, camera, quaternionf, partialTick);
    }
    @Override
    protected int getLightColor(float p_107249_) {

        return 255;
    }

    @Override
    protected Layer getLayer() {
        return new Layer(true, TextureAtlas.LOCATION_PARTICLES, MRender.RenderPs.part);
    }

    public record Provider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }
        public SpriteSet sprite() {
            return this.sprite;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5, RandomSource randomSource) {
            FireBlock particle = new FireBlock(clientLevel, v,v1,v2, (float) 0, (float) 0, (float) 0,sprite.get(randomSource));
            particle.setSpriteFromAge(this.sprite);
            return particle;
        }
    }
}
