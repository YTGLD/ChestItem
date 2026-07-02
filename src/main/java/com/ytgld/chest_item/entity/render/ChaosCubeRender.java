package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.ChaosCube;
import com.ytgld.chest_item.entity.EndComing;
import com.ytgld.chest_item.entity.state.ChaosCubeRenderState;
import com.ytgld.chest_item.entity.state.EndComingRenderState;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.List;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class ChaosCubeRender extends EntityRenderer<@NotNull ChaosCube, ChaosCubeRenderState> {
    public ChaosCubeRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRender(@NotNull ChaosCube entity, Frustum culler, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void submit(ChaosCubeRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        HandlerClient.showOutline = true;
        HandlerClient.doPass = true;
        render(state, poseStack, submitNodeCollector, camera,false);
        render(state, poseStack, submitNodeCollector, camera,true);

    }
    public void render(ChaosCubeRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera,boolean outline){
        ChaosCube entity = state.entity;
        ChaosCube.AxisCi axisCi =new ChaosCube.AxisCi(entity.xAxis,entity.yAxis,entity.zAxis);
        double x = Mth.lerp(state.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(state.partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(state.partialTick, entity.zOld, entity.getZ());

        float size = 0.8f;

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotation(axisCi.x()));
        poseStack.mulPose(Axis.YP.rotation(axisCi.y()));
        poseStack.mulPose(Axis.ZP.rotation(axisCi.z()));
        poseStack.translate(-size, -size, -size);
        submitNodeCollector.submitCustomGeometry(poseStack, MRender.colorOutline(outline),
                (pose, bufferSource) -> {
                    Matrix4f mat = new Matrix4f(pose.pose());
                    renderCubeFace(mat,bufferSource,6,entity.color,size);
                });
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);
        submitNodeCollector.submitCustomGeometry(poseStack, MRender.colorOutline(outline),
                (pose, bufferSource) -> {
                    moreCube(pose,entity,bufferSource,size,outline);
                });
        poseStack.popPose();


        poseStack.pushPose();
        submitNodeCollector.submitCustomGeometry(poseStack, MRender.colorOutlineLines(false),
                (pose, bufferSource) -> {
                    renderAttackLine(pose,entity,bufferSource);
                });
        poseStack.popPose();
    }
    private void renderAttackLine(PoseStack.Pose pose,
                                  ChaosCube entity,
                                  VertexConsumer vertexConsumers) {
        List<ChaosCube.Vec3Last> lastAttackPos = entity.getLastAttackPos();
        int trailSize = lastAttackPos.size();
        for (int i = 1; i < trailSize; i++) {
            Vec3 target = lastAttackPos.get(i).target();
            Vec3 me = lastAttackPos.get(i).me();
            if (entity.target != null) {
                Vec3 end = target.subtract(me);
                int baseColor = lastAttackPos.get(i).color();
                float alpha = (float) i / (float) trailSize;
                int r = (baseColor >> 16) & 0xFF;
                int g = (baseColor >> 8) & 0xFF;
                int b = baseColor & 0xFF;

                int colorWithAlpha =
                        (Math.round(alpha * 255) << 24)
                                | (r << 16)
                                | (g << 8)
                                | b;
                addLine(vertexConsumers, pose, new Vec3(0, 0.5, 0), end.add(0,1,0), colorWithAlpha);
            }
        }
    }
    private static void addLine(VertexConsumer vertexConsumer, PoseStack.Pose poseStack, Vec3 s, Vec3 e, int colorS) {
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
    private void moreCube(PoseStack.Pose pose,
                          ChaosCube entity,
                          VertexConsumer vertexConsumers,float size,boolean outline) {

        List<ChaosCube.Vec3Color> trail = entity.getTrailPositions();
        int trailSize = trail.size();

        for (int i = 1; i < trailSize; i++) {

            Vec3 currPos = trail.get(i).vec3();

            double dx = currPos.x - entity.getX();
            double dy = currPos.y - entity.getY();
            double dz = currPos.z - entity.getZ();

            Matrix4f mat = new Matrix4f(pose.pose());

            mat.translate((float) dx, (float) dy, (float) dz);

            mat.rotate(Axis.XP.rotation(trail.get(i).axisCi().x()));
            mat.rotate(Axis.YP.rotation(trail.get(i).axisCi().y()));
            mat.rotate(Axis.ZP.rotation(trail.get(i).axisCi().z()));

            mat.translate(-size, -size, -size);
            int baseColor = trail.get(i).color();
            float alpha = (float) i / (float) trailSize;
            if (outline) {
                alpha /= 10f;
            }else {
                return;
            }
            int r = (baseColor >> 16) & 0xFF;
            int g = (baseColor >> 8) & 0xFF;
            int b = baseColor & 0xFF;

            int colorWithAlpha =
                    (Math.round(alpha * 255) << 24)
                            | (r << 16)
                            | (g << 8)
                            | b;
            renderCubeFace(mat, vertexConsumers, 6, colorWithAlpha,0.5f + alpha / 5f);
        }
    }
    @Override
    public ChaosCubeRenderState createRenderState() {
        return new ChaosCubeRenderState();
    }
    @Override
    public void extractRenderState(ChaosCube entity, ChaosCubeRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.entity = entity;
        reusedState.partialTick = partialTick;
    }
    public void renderCubeFace(@NotNull Matrix4f matrix4f, @NotNull VertexConsumer vertexConsumer, int face, int color,float size) {
        float x = size;
        float y = size;
        float z = size;
        if (face > 0) { // 前面
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 1) { // 后面
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 2) { // 左面
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 3) { // 右面
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 4) { // 上面
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y + size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
        if (face > 5) { // 下面
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z + size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x + size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
            vertexConsumer.addVertex(matrix4f, x - size, y - size, z - size).setUv(0, 0).setUv2(255,255).setOverlay(NO_OVERLAY).setNormal(0, 0, 0).setColor(color);
        }
    }
}
