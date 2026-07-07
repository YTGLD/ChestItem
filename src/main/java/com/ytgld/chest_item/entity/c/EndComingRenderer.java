package com.ytgld.chest_item.entity.c;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.entity.EndComing;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EndComingRenderer extends EntityRenderer<EndComing> {
    public EndComingRenderer(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public boolean shouldRender(EndComing livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public void render(EndComing entity, float p_114486_, float p_114487_, PoseStack poseStack, MultiBufferSource bufferSource, int p_114490_) {
        super.render(entity, p_114486_, p_114487_, poseStack, bufferSource, p_114490_);
        if (ConfigC.config.Render.get()) {
            HandlerClient.showWarped = true;
            HandlerClient.showOutline = true;
        }
        double x = Mth.lerp(p_114487_, entity.xOld, entity.getX());
        double y = Mth.lerp(p_114487_, entity.yOld, entity.getY());
        double z = Mth.lerp(p_114487_, entity.zOld, entity.getZ());


        poseStack.pushPose();
        poseStack.translate(entity.getX()-x, entity.getY()-y,entity.getZ() -z);
        if (!entity.getTags().contains(EndComing.isTrial)) {
            setT(poseStack, entity, bufferSource,MRender.LIGHTNING_Outline);
        }else {
            setTWarped(poseStack, entity, bufferSource,MRender.renderTypeWarped);
        }
        poseStack.popPose();
        if (!entity.getTags().contains(EndComing.isTrial)) {
            renderSphere1(poseStack, bufferSource, 240, 0.5f,MRender.LIGHTNING_Outline, Light.ARGB.color(255,255,0,0));
        }else {
            renderSphere1(poseStack, bufferSource, 240, 0.5f,MRender.renderTypeWarped, Light.ARGB.color(255,255,100,255));
        }



    }

    private void setT(PoseStack matrices,
                      EndComing entity,
                      MultiBufferSource vertexConsumers,RenderType renderType)
    {
        matrices.pushPose();

        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.getX(), prevPos.y - entity.getY(), prevPos.z - entity.getZ());
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.getX(), currPos.y - entity.getY(), currPos.z - entity.getZ());

            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());
            HandlerClient.renderBlood(matrices.last(), vertexConsumers.getBuffer(renderType), adjustedPrevPos, adjustedCurrPos, alpha,alpha/3.3F);
        }
        matrices.popPose();
    }
    private void setTWarped(PoseStack matrices,
                      EndComing entity,
                      MultiBufferSource vertexConsumers,RenderType renderType)
    {
        matrices.pushPose();

        for (int i = 1; i < entity.getTrailPositions().size(); i++){
            Vec3 prevPos = entity.getTrailPositions().get(i - 1);
            Vec3 currPos = entity.getTrailPositions().get(i);
            Vec3 adjustedPrevPos = new Vec3(prevPos.x - entity.getX(), prevPos.y - entity.getY(), prevPos.z - entity.getZ());
            Vec3 adjustedCurrPos = new Vec3(currPos.x - entity.getX(), currPos.y - entity.getY(), currPos.z - entity.getZ());

            float alpha = (float)(i) / (float)(entity.getTrailPositions().size());
            renderBlood(matrices.last(), vertexConsumers.getBuffer(renderType), adjustedPrevPos, adjustedCurrPos, alpha,alpha/3.3F);
        }
        matrices.popPose();
    }
    public static void renderBlood(PoseStack.Pose poseStack, VertexConsumer vertexConsumer, Vec3 start, Vec3 end, float a, float r) {
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


            addSquare(vertexConsumer, poseStack, up1, up2, down1, down2, a);
        }
    }


    private static void addSquare(VertexConsumer vertexConsumer, PoseStack.Pose poseStack, Vec3 up1, Vec3 up2, Vec3 down1, Vec3 down2, float alpha) {
        // 添加四个顶点来绘制一个矩形
        vertexConsumer.addVertex(poseStack, (float) up1.x, (float) up1.y, (float) up1.z)
                .setColor(255,100,255,255)
                .setUv2(240, 240)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) down1.x, (float) down1.y, (float) down1.z)
                .setColor(255,100,255,255)
                .setUv2(240, 240)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) down2.x, (float) down2.y, (float) down2.z)
                .setColor(255,100,255,255)
                .setUv2(240, 240)
                .setNormal(poseStack,0, 0, 1);

        vertexConsumer.addVertex(poseStack, (float) up2.x, (float) up2.y, (float) up2.z)
                .setColor(255,100,255,255)
                .setUv2(240, 240)
                .setNormal(poseStack,0, 0, 1);
    }
    public void renderSphere1(@NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, float s, RenderType renderType,int color) {
        int stacks = 20; // 垂直方向的分割数
        int slices = 20; // 水平方向的分割数
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderType);
        for (int i = 0; i < stacks; ++i) {
            float phi0 = (float) Math.PI * ((i + 0) / (float) stacks);
            float phi1 = (float) Math.PI * ((i + 1) / (float) stacks);

            for (int j = 0; j < slices; ++j) {
                float theta0 = (float) (2 * Math.PI) * ((j + 0) / (float) slices);
                float theta1 = (float) (2 * Math.PI) * ((j + 1) / (float) slices);

                float x0 = s * (float) Math.sin(phi0) * (float) Math.cos(theta0);
                float y0 = s * (float) Math.cos(phi0);
                float z0 = s * (float) Math.sin(phi0) * (float) Math.sin(theta0);

                float x1 = s * (float) Math.sin(phi0) * (float) Math.cos(theta1);
                float y1 = s * (float) Math.cos(phi0);
                float z1 = s * (float) Math.sin(phi0) * (float) Math.sin(theta1);

                float x2 = s * (float) Math.sin(phi1) * (float) Math.cos(theta1);
                float y2 = s * (float) Math.cos(phi1);
                float z2 = s * (float) Math.sin(phi1) * (float) Math.sin(theta1);

                float x3 = s * (float) Math.sin(phi1) * (float) Math.cos(theta0);
                float y3 = s * (float) Math.cos(phi1);
                float z3 = s * (float) Math.sin(phi1) * (float) Math.sin(theta0);

                vertexConsumer.addVertex(matrices.last().pose(), x0, y0, z0).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x1, y1, z1).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x2, y2, z2).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
                vertexConsumer.addVertex(matrices.last().pose(), x3, y3, z3).setColor(color).setOverlay(OverlayTexture.NO_OVERLAY).setUv(0, 0).setUv2(light, light).setNormal(1, 0, 0);
            }
        }
    }
    @Override
    public ResourceLocation getTextureLocation(EndComing p_114482_) {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"null");
    }
}




