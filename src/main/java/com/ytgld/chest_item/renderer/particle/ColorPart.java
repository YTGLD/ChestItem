package com.ytgld.chest_item.renderer.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.QuadParticleRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ColorPart extends SingleQuadParticle {
    public ColorPart(ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ, TextureAtlasSprite textureAtlasSprite) {
        super(level,x,y,z,movementX,movementY,movementZ,textureAtlasSprite);
        this.lifetime = 200;
        this.setColor(Mth.nextFloat(RandomSource.create(),0.8f,1),Mth.nextFloat(RandomSource.create(),0.2f,1),0.25f);
        this.scale(Mth.nextFloat(RandomSource.create(),1,5));
        this.gravity = -0.01f;
    }
    @Override
    protected int getLightColor(float p_107249_) {
        return 255;
    }
    public int time = 200;
    public void tick() {
        super.tick();
        this.quadSize = this.quadSize * 0.9f;
        if (alpha>0.05f) {
            this.alpha -= 0.05f;
        }
        time --;
        if (time<=0){
            this.remove();
        }
    }

    @Override
    public void extract(QuadParticleRenderState reusedState, Camera camera, float partialTick) {
        super.extract(reusedState, camera, partialTick);
    }

    @Override
    protected @NotNull Layer getLayer() {
        return new Layer(true, TextureAtlas.LOCATION_PARTICLES, MRender.RenderPs.TRANSLUCENT_PARTICLE);
    }

    @Override
    public ParticleRenderType getGroup() {
        return super.getGroup();
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
