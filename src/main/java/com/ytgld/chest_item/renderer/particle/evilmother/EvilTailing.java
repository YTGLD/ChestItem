package com.ytgld.chest_item.renderer.particle.evilmother;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.entity.EvilMotherSpirit;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.IParticleEngine;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class EvilTailing extends SingleQuadParticle {
    public EvilTailing(ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ, TextureAtlasSprite textureAtlasSprite) {
        super(level,x,y,z,movementX,movementY,movementZ,textureAtlasSprite);
        this.lifetime = 500;
        this.setAlpha(0);
    }

    @Override
    protected int getLightCoords(float a) {
        return 255;
    }

    public int time = 100;
    private final List<Vec3> trailPositions = new ArrayList<>();
    public List<Vec3> getTrailPositions() {
        return trailPositions;
    }
    public boolean canSee = true;
    public int lastTime = 50;

    public void tick() {
        super.tick();
        this.gravity = 0;
        Vec3 playerPos = this.getPos().add(0,1,0);
        float range = 2;

        List<EvilMotherSpirit> entities =
                this.level.getEntitiesOfClass(EvilMotherSpirit.class,
                        new AABB(playerPos.x - range,
                                playerPos.y - range,
                                playerPos.z - range,
                                playerPos.x + range,
                                playerPos.y + range,
                                playerPos.z + range));
        List<Player> list =
                this.level.getEntitiesOfClass(Player.class,
                        new AABB(playerPos.x - range,
                                playerPos.y - range,
                                playerPos.z - range,
                                playerPos.x + range,
                                playerPos.y + range,
                                playerPos.z + range));
        for (Player player : list){
            if (player.isAlive()) {
                canSee = false;
            }
        }

        for (EvilMotherSpirit evilMotherSpirit : entities){
            if (evilMotherSpirit.getOwner() instanceof Player player) {
                setSpeed(player);
            }
        }
        time --;
        if (time <= 0) {
            canSee = false;
        }
        if (!canSee){
            this.setParticleSpeed(0,0,0);
            lastTime --;
        }
        if (canSee) {
            trailPositions.add(new Vec3(this.x, this.y, this.z));
        }
        if (trailPositions.size() > 30 || !canSee) {
            if (!trailPositions.isEmpty()) {
                trailPositions.removeFirst();
            }
        }
        if (lastTime <= 0) {
            remove();
        }
    }
    public void setSpeed(Entity entity){
        Vec3 targetPos = entity.position().add(0, 0.5, 0);
        Vec3 currentPos = this.getPos();
        Vec3 direction = targetPos.subtract(currentPos).normalize();

        float s  = 0.25f;
        // 获取当前运动方向

        Vec3 currentDirection = new Vec3(this.xd, this.yd, this.zd).normalize();

        // 计算目标方向与当前方向之间的夹角
        double angle = Math.acos(currentDirection.dot(direction)) * (180.0 / Math.PI);

        // 如果夹角超过10度，则限制方向
        if (angle > 45) {
            // 计算旋转后的新方向
            double angleLimit = Math.toRadians(45); // 将10度转为弧度

            // 根据正弦法则计算限制后的方向
            Vec3 limitedDirection = currentDirection.scale(Math.cos(angleLimit)) // 计算缩放因子
                    .add(direction.normalize().scale(Math.sin(angleLimit))); // 根据目标方向进行调整

            this.setParticleSpeed(limitedDirection.x * (0.125f + s), limitedDirection.y * (0.125f + s), limitedDirection.z * (0.125f + s));
        } else {
            this.setParticleSpeed(direction.x * (0.125f + s), direction.y * (0.125f + s), direction.z * (0.125f + s));
        }
    }
    @Override
    public void remove() {
        super.remove();
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
            EvilTailing particle = new EvilTailing(clientLevel, v,v1,v2, (float) v3, (float) v4, (float) v5,sprite.get(textureAtlasSprite));
            particle.setSpriteFromAge(this.sprite);
            return particle;
        }
    }
    public static void RenderLevelStageEvent(RenderLevelStageEvent.AfterTranslucentParticles event) {

        PoseStack poseStack = event.getPoseStack();

        VertexConsumer consumer = Minecraft.getInstance()
                .renderBuffers()
                .bufferSource()
                .getBuffer(RenderTypes.lines());

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 camPos = camera.position();

        if (Minecraft.getInstance().particleEngine instanceof IParticleEngine particleEngine) {

            iterateParticles(particle -> {

                if (particle instanceof EvilTailing tailing) {

                    setT2(
                            poseStack.last(),
                            tailing,
                            consumer,
                            camPos
                    );
                }

            }, particleEngine);
        }

        Minecraft.getInstance()
                .renderBuffers()
                .bufferSource()
                .endBatch(RenderTypes.lines());
    }
    private static void setT2(
            PoseStack.Pose matrices,
            EvilTailing entity,
            VertexConsumer vertexConsumers,
            Vec3 camPos
    ) {

        for (int i = 1; i < entity.getTrailPositions().size(); i++) {

            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);

            Vec3 adjustedPrevPos = prevPos.subtract(camPos);
            Vec3 adjustedCurrPos = currPos.subtract(camPos);

            float alpha = (float) i / (float) entity.getTrailPositions().size();

            int color = Light.ARGB.color(255,160,255,180);

            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;

            addSquare(
                    vertexConsumers,
                    matrices,
                    adjustedPrevPos,
                    adjustedCurrPos,
                    Light.ARGB.color((int)(alpha * 255), rs, gs, bs)
            );
        }
    }

    private static void addSquare(VertexConsumer vertexConsumer, PoseStack.Pose poseStack, Vec3 s, Vec3 e, int colorS) {
        vertexConsumer.addVertex(poseStack, (float) s.x, (float) s.y, (float) s.z)
                .setColor(colorS)
                .setUv2(255, 255)
                .setNormal(1,0,1)
                .setLight(255)
                .setOverlay(NO_OVERLAY)
                .setLineWidth(4);

        vertexConsumer.addVertex(poseStack, (float) e.x, (float) e.y, (float) e.z)
                .setColor(colorS)
                .setUv2(255, 255)
                .setNormal(1,0,1)
                .setLight(255)
                .setOverlay(NO_OVERLAY)
                .setLineWidth(4);
    }
    private static void iterateParticles(Consumer<Particle> consumer,IParticleEngine particleEngine) {
        for(ParticleRenderType particlerendertype : particleEngine.cI1_21_11$particles().keySet()) {
            if (particlerendertype != ParticleRenderType.NO_RENDER) {
                ParticleGroup<?> iterable = particleEngine.cI1_21_11$particles().get(particlerendertype);
                if (iterable != null) {
                    for (int i = 0; i < iterable.getAll().stream().toList().size(); i++) {
                        consumer.accept(iterable.getAll().stream().toList().get(i));
                    }
                }
            }
        }

    }
}
