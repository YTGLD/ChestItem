package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.EvilMotherSpirit;
import com.ytgld.chest_item.entity.state.EvilMotherSpiritState;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class EvilMotherSpiritRender extends EntityRenderer<EvilMotherSpirit, EvilMotherSpiritState> {
    public EvilMotherSpiritRender(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public boolean shouldRender(EvilMotherSpirit livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public @NotNull EvilMotherSpiritState createRenderState() {
        return new EvilMotherSpiritState();
    }
    @Override
    public void extractRenderState(EvilMotherSpirit entity, EvilMotherSpiritState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.entity = entity;
        reusedState.partialTick = partialTick;
    }


    @Override
    public void submit(EvilMotherSpiritState renderState, PoseStack poseStack, SubmitNodeCollector collector, net.minecraft.client.renderer.state.level.CameraRenderState camera) {
        EvilMotherSpirit entity = renderState.entity;
        double x = Mth.lerp(renderState.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(renderState.partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(renderState.partialTick, entity.zOld, entity.getZ());
        poseStack.pushPose();
        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);
        collector.submitCustomGeometry(poseStack, MRender.colorOutlineLines(false), (pose, bufferSource) -> {
            setT2(pose, entity, bufferSource);
        });
        poseStack.popPose();
    }
    private void setT2(PoseStack.Pose matrices,
                       EvilMotherSpirit entity,
                       VertexConsumer vertexConsumers)
    {
        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1).vec3();
            Vec3 currPos = entity.getTrailPositions().get(i).vec3();
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.getX(), prevPos.y - entity.getY(), prevPos.z - entity.getZ());
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.getX(), currPos.y - entity.getY(), currPos.z - entity.getZ());
            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());
            alpha *= 255;

            int color = entity.getTrailPositions().get(i - 1).color();
            int as = (color >> 24) & 0xFF;
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;

            addSquare(vertexConsumers,matrices,adjustedCurrPos, adjustedPrevPos, Light.ARGB.color((int) alpha,rs,gs,bs));
        }
    }

    private static void addSquare(VertexConsumer vertexConsumer, PoseStack.Pose poseStack, Vec3 s, Vec3 e, int colorS) {
        vertexConsumer.addVertex(poseStack, (float) s.x, (float) s.y, (float) s.z)
                .setColor(colorS)
                .setUv2(255, 255)
                .setNormal(1,0,1)
                .setLight(255)
                .setOverlay(NO_OVERLAY)
                .setLineWidth(3);

        vertexConsumer.addVertex(poseStack, (float) e.x, (float) e.y, (float) e.z)
                .setColor(colorS)
                .setUv2(255, 255)
                .setNormal(1,0,1)
                .setLight(255)
                .setOverlay(NO_OVERLAY)
                .setLineWidth(3);
    }


    public void renderCubeFace(@NotNull PoseStack.Pose matrix4f, @NotNull VertexConsumer vertexConsumer, int light, int face,int color,float size) {
        float x = size; // 正方形中心的x坐标
        float y = size; // 正方形中心的y坐标
        float z = size; // 正方形中心的z坐标
        if (face > 0) { // 前面
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 1) { // 后面
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 2) { // 左面
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 3) { // 右面
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 4) { // 上面
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 5) { // 下面
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z + size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z - size).setUv(0, 0).setUv2(light, light).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
    }

}




