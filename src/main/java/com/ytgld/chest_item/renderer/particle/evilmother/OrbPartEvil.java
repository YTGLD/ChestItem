package com.ytgld.chest_item.renderer.particle.evilmother;

import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3d;

public class OrbPartEvil extends SingleQuadParticle {
    public OrbPartEvil(ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ, TextureAtlasSprite textureAtlasSprite) {
        super(level,x,y,z,movementX,movementY,movementZ,textureAtlasSprite);
        this.setParticleSpeed(0,0,0);
        this.lifetime = 150;
        this.setColor(80 / 255f,255 / 255f,180 /255f);
        this.scale(Mth.nextFloat(RandomSource.create(),5,10));
        this.gravity = 0;
    }

    @Override
    protected int getLightCoords(float a) {
        return 255;
    }
    public int time = 20;
    public void tick() {
        super.tick();
        if (alpha>0) {
            this.alpha *= 0.8f;
        }
        this.quadSize *= 1.075f;
        time --;
        if (time<=0){
            this.remove();
        }
    }
    public void extract(QuadParticleRenderState reusedState, Camera camera, float partialTick) {
        Quaternionf quaternionf = new Quaternionf();
        quaternionf.transform(new Vector3d(0,1,0));
        quaternionf.rotateX(-(float)Math.PI / 2);
        this.extractRotatedQuad(reusedState, camera, quaternionf, partialTick);
    }
    @Override
    protected @NotNull Layer getLayer() {
        return new Layer(true, TextureAtlas.LOCATION_PARTICLES, MRender.RenderPs.TRANSLUCENT_PARTICLE);
    }
    public record Provider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }
        public SpriteSet sprite() {
            return this.sprite;
        }

        @Override
        public @NotNull Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5, RandomSource textureAtlasSprite) {
            OrbPartEvil particle = new OrbPartEvil(clientLevel, v,v1,v2, (float) v3, (float) v4, (float) v5,sprite.get(textureAtlasSprite));
            particle.setSpriteFromAge(this.sprite);
            return particle;
        }

    }
}

