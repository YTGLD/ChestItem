package com.ytgld.chest_item.renderer.particle.sword;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwordShadow1 extends SwordShadowBase{
    public SwordShadow1(ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ) {
        super(level,x,y,z,movementX,movementY,movementZ);
    }
    public record Provider(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }
        public SpriteSet sprite() {
            return this.sprite;
        }
        @Override
        public @NotNull Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5) {
            SwordShadow1 particle = new SwordShadow1(clientLevel, v,v1,v2, (float) v3, (float) v4, (float) v5);
            particle.setSpriteFromAge(this.sprite);
            return particle;
        }
    }
}
