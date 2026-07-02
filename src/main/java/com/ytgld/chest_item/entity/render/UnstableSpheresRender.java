package com.ytgld.chest_item.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.UnstableSpheres;
import com.ytgld.chest_item.entity.state.UnstableSpheresState;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class UnstableSpheresRender extends EntityRenderer<UnstableSpheres, UnstableSpheresState> {
    public UnstableSpheresRender(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public boolean shouldRender(UnstableSpheres livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public @NotNull UnstableSpheresState createRenderState() {
        return new UnstableSpheresState();
    }
    @Override
    public void extractRenderState(UnstableSpheres entity, UnstableSpheresState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.entity = entity;
        reusedState.partialTick = partialTick;
    }
    @Override
    public void submit(UnstableSpheresState renderState, PoseStack poseStack, SubmitNodeCollector collector, net.minecraft.client.renderer.state.level.CameraRenderState camera) {
        HandlerClient.showOutline = true;
        HandlerClient.doPass = true;

        UnstableSpheres entity = renderState.entity;
        double x = Mth.lerp(renderState.partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(renderState.partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(renderState.partialTick, entity.zOld, entity.getZ());
        poseStack.pushPose();
        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);

        collector.submitCustomGeometry(poseStack,MRender.colorOutline(true),(pose, bufferSource) -> {
            setT2(pose, entity, bufferSource);
        });
        collector.submitCustomGeometry(poseStack,MRender.colorOutline(false),(pose, bufferSource) -> {
            setT2(pose, entity, bufferSource);
        });

        poseStack.popPose();
    }
    private void setT2(PoseStack.Pose matrices,
                       UnstableSpheres entity,
                       VertexConsumer vertexConsumers)
    {
        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.getX(), prevPos.y - entity.getY(), prevPos.z - entity.getZ());
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.getX(), currPos.y - entity.getY(), currPos.z - entity.getZ());
            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());
            int color = Light.ARGB.color((int) (alpha * 255), 255, 0, 0);
            renderBlood(matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, color,alpha/10f);
            renderBlood(matrices, vertexConsumers, adjustedPrevPos, adjustedCurrPos, color,alpha/10f);
        }
    }
    public void renderBlood(PoseStack.Pose poseStack, VertexConsumer vertexConsumer, Vec3 start, Vec3 end, int color, float r) {
        int segmentCount = 16; // 圆柱横向细分数

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


            addSquare(vertexConsumer, poseStack, up1, up2, down1, down2, color);
        }
    }


    private void addSquare(VertexConsumer vertexConsumer, PoseStack.Pose poseStack, Vec3 up1, Vec3 up2, Vec3 down1, Vec3 down2, int color) {
        // 添加四个顶点来绘制一个矩形
        vertexConsumer.addVertex(poseStack, (float) up1.x, (float) up1.y, (float) up1.z)
                .setColor(color)
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) down1.x, (float) down1.y, (float) down1.z)
                .setColor(color)
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) down2.x, (float) down2.y, (float) down2.z)
                .setColor(color)
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) up2.x, (float) up2.y, (float) up2.z)
                .setColor(color)
                .setUv2(255, 255)
                .setNormal(poseStack,0, 0, 1);
    }

}




