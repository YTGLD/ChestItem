package com.ytgld.chest_item.utils.dout;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
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

public class Scratch extends WorldRenderObject {
    public Vec3 pitch;
    public float size = 1;
    public int color = 0xffffffff;
    public float xx = 1f;

    public static final int maxAge = 60;

    public Scratch(Vec3 vec3, Vec3 pitch, float size) {
        super(vec3);
        this.maxTime = maxAge;
        this.pitch = pitch;
        this.size = size;
    }

    public int imageNumber = 1;
    public int newAlpha;

    @Override
    public void clientTick() {
        super.clientTick();
        imageNumber = age / 5;
        if (imageNumber < 1) {
            imageNumber = 1;
        }
        newAlpha = maxTime - age;
        newAlpha = Math.max(0,newAlpha);
        xx += 0.05f;
    }

    @Override
    public void render(PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera, float partialTick) {
        Vec3 cameraPos = camera.pos;
        poseStack.pushPose();
        poseStack.translate(
                position.x - cameraPos.x,
                position.y - cameraPos.y,
                position.z - cameraPos.z
        );
        render(poseStack,collector,color,size);
        poseStack.popPose();
    }

    private void render(PoseStack poseStack,
                        SubmitNodeCollector submitNodeCollector,
                        int color,float maxSize){
        int as = (color >> 24) & 0xFF;
        int rs = (color >> 16) & 0xFF;
        int gs = (color >> 8) & 0xFF;
        int bs = color & 0xFF;

        float offset = Math.min(maxSize,age);
        int alpha = (int) (( maxSize - offset) * (255 / maxSize));
        alpha = Math.max(0,alpha);
        Identifier identifier =  Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/render_object/fatal.png");
        render(poseStack,submitNodeCollector,2, RenderObjects.renderTypeFunctionLiveOutline
                .apply(identifier), Light.ARGB.color(alpha,rs,gs,bs),maxSize - offset,xx);


    }
    private void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                        float size, RenderType renderType, int color,
                        float offset,float x1x2){
        poseStack.pushPose();
        poseStack.rotate(Axis.XN.rotation((float) pitch.x));
        poseStack.rotate(Axis.YN.rotation((float) pitch.y));
        poseStack.rotate(Axis.ZN.rotation((float) pitch.z));
        {
            poseStack.pushPose();
            {
                poseStack.pushPose();
                poseStack.translate(0, -1, 0);
                submitNodeCollector.submitCustomGeometry(poseStack, renderType,
                        (pose, vc) -> {
                            Matrix4f mat = pose.pose();
                            vc.addVertex(mat, -size * offset * x1x2, -size * offset * x1x2, 0).setColor(color).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                            vc.addVertex(mat, size / offset, -size / offset, 0).setColor(color).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                            vc.addVertex(mat, size * offset, size * offset, 0).setColor(color).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                            vc.addVertex(mat, -size / offset * x1x2, size / offset * x1x2, 0).setColor(color).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                        });
                poseStack.popPose();
            }
            {
                poseStack.pushPose();
                poseStack.translate(0, 0, 0);
                submitNodeCollector.submitCustomGeometry(poseStack, renderType,
                        (pose, vc) -> {
                            Matrix4f mat = pose.pose();
                            vc.addVertex(mat, -size * offset * x1x2, -size * offset * x1x2, 0).setColor(color).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                            vc.addVertex(mat, size / offset, -size / offset, 0).setColor(color).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                            vc.addVertex(mat, size * offset, size * offset, 0).setColor(color).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                            vc.addVertex(mat, -size / offset * x1x2, size / offset * x1x2, 0).setColor(color).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                        });
                poseStack.popPose();
            }
            {
                poseStack.pushPose();
                poseStack.translate(0, 1, 0);
                submitNodeCollector.submitCustomGeometry(poseStack, renderType,
                        (pose, vc) -> {
                            Matrix4f mat = pose.pose();
                            vc.addVertex(mat, -size * offset * x1x2, -size * offset * x1x2, 0).setColor(color).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                            vc.addVertex(mat, size / offset, -size / offset, 0).setColor(color).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                            vc.addVertex(mat, size * offset, size * offset, 0).setColor(color).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                            vc.addVertex(mat, -size / offset * x1x2, size / offset * x1x2, 0).setColor(color).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setUv2(255, 255).setNormal(0, 0, 1);
                        });
                poseStack.popPose();
            }
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}

