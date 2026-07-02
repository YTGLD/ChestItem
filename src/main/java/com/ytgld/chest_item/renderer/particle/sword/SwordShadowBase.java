package com.ytgld.chest_item.renderer.particle.sword;

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

public class SwordShadowBase extends SingleQuadParticle {
    public SwordShadowBase(ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ, TextureAtlasSprite textureAtlasSprite) {
        super(level,x,y,z,0,0,0,textureAtlasSprite);
        this.setParticleSpeed(0,0,0);
        this.lifetime = 100;
        alpha = 0;
        this.setColor(165/255f,215/255f,230/255f);
        this.scale(Mth.nextFloat(this.random,15,17));
    }

    @Override
    protected int getLightCoords(float a) {
        return 255;
    }

    public float aMax = 1;
    public float aMin = 0;
    public void tick() {
        super.tick();
        if (aMin < 0.9f) {
            aMin += 0.1f;
        }
        if (aMax > 0.066f) {
            aMax -= 0.033f;
        }
        this.alpha = Math.max(0,Math.min(aMin,aMax));
    }

    @Override
    protected @NotNull Layer getLayer() {
        return new Layer(true, TextureAtlas.LOCATION_PARTICLES, MRender.RenderPs.TRANSLUCENT_PARTICLE);
    }
}

