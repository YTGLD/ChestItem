package com.ytgld.chest_item.renderer.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class FireBlock extends TextureSheetParticle {
    public int max = 200;

    protected FireBlock(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, 0, 0, 0);
        this.lifetime = max;
        this.gravity = 10;
        this.setParticleSpeed(0,0,0);
    }
    @Override
    public void tick() {
        super.tick();
        this.setPos((int)x,(int)y,(int)z);
        if (this.lifetime < 100) {
            this.alpha -= 0.02f;
            if (alpha <= 0) {
                this.remove();
            }
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
        super.render(buffer, renderInfo, partialTicks);

        // 获取粒子位置
        double px = this.x;
        double py = this.y; // 固定在方块顶面
        double pz = this.z;

        float size = 0.2f; // 粒子大小

        // 构建四边形顶点，不受相机旋转影响
        buffer.addVertex((float) (px - size), (float) py, (float) (pz - size)).setColor(1f,1f,1f,1f);
        buffer.addVertex((float) (px + size), (float) py, (float) (pz - size)).setColor(1f,1f,1f,1f);
        buffer.addVertex((float) (px + size), (float) py, (float) (pz + size)).setColor(1f,1f,1f,1f);
        buffer.addVertex((float) (px - size), (float) py, (float) (pz + size)).setColor(1f,1f,1f,1f);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    @Override
    protected int getLightColor(float p_107249_) {
        float s = (float) Math.sin(lifetime / 20f);
        if (s < 0) {
            s = -s;
        }
        s *= 100f;
        return (int) (150 + s);
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
            FireBlock particle = new FireBlock(clientLevel, v,v1,v2, (float) 0, (float) 0, (float) 0);
            particle.setSpriteFromAge(this.sprite);
            return particle;
        }
    }
}
