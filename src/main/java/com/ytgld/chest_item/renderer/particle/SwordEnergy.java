package com.ytgld.chest_item.renderer.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.other.SwordEnergyOption;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class SwordEnergy extends SimpleAnimatedParticle {
    public Vec3 axis = Vec3.ZERO;
    public boolean isLight= false;
    public int color = Light.ARGB.color(255,255,255,255);
    public float size = 10;

    private SwordEnergy(ClientLevel level, double x, double y, double z, SpriteSet sprite) {
        super(level,x,y,z,sprite,0);
        this.setParticleSpeed(0,0,0);
        this.scale(size);
    }

    @Override
    public void render(VertexConsumer consumer, Camera renderInfo, float partialTicks) {
        Vec3 axis = this.axis;
        Quaternionf rotation = new Quaternionf()
                .rotateX(-(float)Math.PI / 2)
                .rotateXYZ(
                        (float) Math.toRadians(axis.x),
                        (float) Math.toRadians(axis.y),
                        (float) Math.toRadians(axis.z)
                );

        this.renderRotatedQuad(consumer, renderInfo, rotation,partialTicks);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return (tesselator, manager) -> {
            RenderSystem.depthMask(true);
            RenderSystem.setShader(CIStateShardsHasBlack::getHasBlock);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(
                    GlStateManager.SourceFactor.SRC_ALPHA,
                    GlStateManager.DestFactor.ONE,
                    GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO
            );
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        };
    }

    public record Provider(SpriteSet sprite) implements ParticleProvider<SwordEnergyOption> {
        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }
        public SpriteSet sprite() {
            return this.sprite;
        }

        @Override
        public Particle createParticle(SwordEnergyOption simpleParticleType, ClientLevel clientLevel, double v, double v1, double v2, double v3, double v4, double v5) {
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


