package com.ytgld.chest_item.renderer.particle;

import com.ytgld.chest_item.renderer.MRender;
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
import org.jetbrains.annotations.NotNull;

public class ColorPart extends SingleQuadParticle {
    public ColorPart(ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ, TextureAtlasSprite textureAtlasSprite) {
        super(level,x,y,z,movementX,movementY,movementZ,textureAtlasSprite);
        this.setParticleSpeed(movementX,movementY,movementZ);
        this.lifetime = 200;
        this.setColor(Mth.nextFloat(RandomSource.create(),0.9f,1),Mth.nextFloat(RandomSource.create(),0,0.1f),0.1f);
        this.scale(Mth.nextFloat(RandomSource.create(),1,3));
    }

    @Override
    protected int getLightCoords(float a) {
        return 255;
    }

    public int time = 200;
    public void tick() {
        super.tick();
        this.roll+=0.05f + Mth.nextFloat(RandomSource.create(),0.01F,0.2F);
        this.oRoll+= (float) (0.05 + Mth.nextFloat(RandomSource.create(),0.01F,0.2F));
        float ca = this.quadSize * 0.95f;
        if (ca < 0) {
            ca = 0;
        }
        this.quadSize = ca;
        if (alpha>0.05f) {
            this.alpha -= 0.05f;
        }
        time --;
        if (time<=0){
            this.remove();
        }
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
            ColorPart particle = new ColorPart(clientLevel, v,v1,v2, (float) v3, (float) v4, (float) v5,sprite.get(textureAtlasSprite));
            particle.setSpriteFromAge(this.sprite);
            return particle;
        }

    }
}
