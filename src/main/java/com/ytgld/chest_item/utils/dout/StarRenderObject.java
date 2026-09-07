package com.ytgld.chest_item.utils.dout;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.utils.RenderObjects;
import com.ytgld.chest_item.utils.WorldRenderObject;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

public class StarRenderObject extends WorldRenderObject {
    public Vec3 pitch;
    public float size = 1;
    public int color = 0xffffffff;

    public boolean hasDown;

    public StarRenderObject(Vec3 vec3,Vec3 pitch,float size,boolean hasDown) {
        super(vec3);
        this.maxTime = 100;
        this.hasDown = hasDown;
        this.pitch = pitch;
        this.size = size;
    }

    public int newAlpha = 255;

    @Override
    public void clientTick() {
        super.clientTick();
        if (newAlpha > 0) {
            newAlpha -= 5;
        }
    }

    @Override
    public void render(PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera, float partialTick) {
        HandlerClient.showOutline = true;
        HandlerClient.doPass = true;

        Vec3 cameraPos = camera.pos;
        poseStack.pushPose();
        poseStack.translate(
                position.x - cameraPos.x,
                position.y - cameraPos.y,
                position.z - cameraPos.z
        );
        if (!hasDown) {
            poseStack.translate(0,age / 10F,0);
        }else {
            poseStack.translate(0,-age / 10F,0);
        }

        int as = (color >> 24) & 0xFF;
        int rs = (color >> 16) & 0xFF;
        int gs = (color >> 8) & 0xFF;
        int bs = color & 0xFF;

        Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/render_object/star.png");
        render(poseStack,collector,size,
                RenderObjects.renderTypeFunctionLive.apply(
                        identifier
                ),
                Light.ARGB.color(newAlpha,rs,gs,bs));
        render(poseStack,collector,size,
                RenderObjects.renderTypeFunctionLiveOutline.apply(
                        identifier
                ),
                Light.ARGB.color(newAlpha,rs,gs,bs));

        poseStack.popPose();
    }

    private void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, float size, RenderType renderType, int color){
        poseStack.pushPose();
        poseStack.mulPose(Axis.XN.rotation((float) pitch.x));
        poseStack.mulPose(Axis.YN.rotation((float) pitch.y));
        poseStack.mulPose(Axis.ZN.rotation((float) pitch.z));
        submitNodeCollector.submitCustomGeometry(poseStack, renderType,
                (pose, vc) -> {
                    Matrix4f mat = pose.pose();
                    vc.addVertex(mat, -size, -size, 0)
                            .setColor(color)
                            .setUv(0, 1)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);

                    vc.addVertex(mat, size, -size, 0)
                            .setColor(color)
                            .setUv(1, 1)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);

                    vc.addVertex(mat, size, size, 0)
                            .setColor(color)
                            .setUv(1, 0)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);

                    vc.addVertex(mat, -size, size, 0)
                            .setColor(color)
                            .setUv(0, 0)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);
                });
        poseStack.popPose();
    }
}
