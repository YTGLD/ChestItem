package com.ytgld.chest_item.renderer.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
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
    public int max = 500;
    public ColorPart(ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ, TextureAtlasSprite textureAtlasSprite) {
        super(level,x,y,z,movementX,movementY,movementZ,textureAtlasSprite);
        this.lifetime = max;
        this.setColor(Mth.nextFloat(RandomSource.create(),0.8f,1),Mth.nextFloat(RandomSource.create(),0.2f,1),0.25f);
        this.scale(Mth.nextFloat(RandomSource.create(),1,5));
        this.gravity = -0.01f;
    }
    @Override
    protected int getLightColor(float p_107249_) {
        return 255;
    }
    private final List<Vec3> trailPositions = new ArrayList<>();
    public int time = 200;
    public void tick() {
        super.tick();

        trailPositions.add(new Vec3(this.x, this.y, this.z));

        if (trailPositions.size() > 33) {
            trailPositions.removeFirst();
        }

        this.quadSize = this.quadSize * 0.9f;
        if (alpha>0.05f) {
            this.alpha -= 0.05f;
        }
        time --;
        if (time<=0){
            this.remove();
        }
    }
    public List<Vec3> getTrailPositions() {
        return trailPositions;
    }
    public void setT(PoseStack matrices,
                              ColorPart entity,
                              VertexConsumer vertexConsumers)
    {
        matrices.pushPose();

        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.getPos().x, prevPos.y - entity.getPos().y, prevPos.z - entity.getPos().z);
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.getPos().x, currPos.y - entity.getPos().y, currPos.z - entity.getPos().z);

            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());

            renderBlood(matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, alpha, RenderTypes.lightning(),0.1f);
        }
        matrices.popPose();
    }
    public void renderBlood(PoseStack poseStack, VertexConsumer vertexConsumer, Vec3 start, Vec3 end, float a, RenderType renderType, float r) {
        int segmentCount = 8; // 圆柱横向细分数

        for (int i = 0; i < segmentCount; i++) {
            double angle1 = (2 * Math.PI * i) / segmentCount;
            double angle2 = (2 * Math.PI * (i + 1)) / segmentCount;

            double x1 = Math.cos(angle1) * r;
            double z1 = Math.sin(angle1) * r;
            double x2 = Math.cos(angle2) * r;
            double z2 = Math.sin(angle2) * r;

            Vec3 up1 = start.add(x1, 0, z1);
            Vec3 up2 = start.add(x2, 0, z2);
            Vec3 down1 = end.add(x1, 0, z1);
            Vec3 down2 = end.add(x2, 0, z2);


            addSquare(vertexConsumer, poseStack, up1, up2, down1, down2, a);
        }
    }
    private static void addSquare(VertexConsumer vertexConsumer, PoseStack poseStack, Vec3 up1, Vec3 up2, Vec3 down1, Vec3 down2, float alpha) {
        // 添加四个顶点来绘制一个矩形
        vertexConsumer.addVertex(poseStack.last().pose(), (float) up1.x, (float) up1.y, (float) up1.z)
                .setColor(255, 255, 0, (int) (alpha * 255))
                .setUv2(240, 240)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(poseStack.last().pose(), (float) down1.x, (float) down1.y, (float) down1.z)
                .setColor(255, 255, 0, (int) (alpha * 255))
                .setUv2(240, 240)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(poseStack.last().pose(), (float) down2.x, (float) down2.y, (float) down2.z)
                .setColor(255, 255, 0, (int) (alpha * 255))
                .setUv2(240, 240)
                .setNormal(0, 0, 1);

        vertexConsumer.addVertex(poseStack.last().pose(), (float) up2.x, (float) up2.y, (float) up2.z)
                .setColor(255, 255, 0, (int) (alpha * 255))
                .setUv2(240, 240)
                .setNormal(0, 0, 1);
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
