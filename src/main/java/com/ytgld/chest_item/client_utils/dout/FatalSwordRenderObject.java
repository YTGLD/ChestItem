package com.ytgld.chest_item.client_utils.dout;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.client_utils.RenderObjects;
import com.ytgld.chest_item.client_utils.WorldRenderObject;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class FatalSwordRenderObject extends WorldRenderObject {
    public Vec3 pitch;
    public float size = 1;
    public int color = 0xffffffff;

    public static final int maxAge = 60;

    public FatalSwordRenderObject(Vec3 vec3, Vec3 pitch, float size) {
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

        int rs = (color >> 16) & 0xFF;
        int gs = (color >> 8) & 0xFF;
        int bs = color & 0xFF;
        float offset = Math.min(5,age);
        int alpha = (int) (( 5 -offset) * 50f);
        if (alpha < 10) {
            alpha = 0;
        }
        Identifier identifier =  Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/render_object/fatal.png");
        render(poseStack,collector,3.35f, RenderObjects.renderTypeFunctionLiveOutline
                .apply(identifier),Light.ARGB.color(alpha,rs,gs,bs),5 - offset);

        poseStack.popPose();
    }

    private void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, float size, RenderType renderType, int color,float offset){
        poseStack.pushPose();
        poseStack.rotate(Axis.XN.rotation((float) pitch.x));
        poseStack.rotate(Axis.YN.rotation((float) pitch.y));
        poseStack.rotate(Axis.ZN.rotation((float) pitch.z));
        submitNodeCollector.submitCustomGeometry(poseStack, renderType,
                (pose, vc) -> {
                    Matrix4f mat = pose.pose();
                    vc.addVertex(mat, -size * offset, -size * offset, 0)
                            .setColor(color)
                            .setUv(0, 1)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);

                    vc.addVertex(mat, size/offset, -size/offset, 0)
                            .setColor(color)
                            .setUv(1, 1)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);

                    vc.addVertex(mat, size * offset, size * offset, 0)
                            .setColor(color)
                            .setUv(1, 0)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);

                    vc.addVertex(mat, -size/offset, size/offset, 0)
                            .setColor(color)
                            .setUv(0, 0)
                            .setOverlay(OverlayTexture.NO_OVERLAY)
                            .setUv2(255,255)
                            .setNormal(0, 0, 1);
                });
        poseStack.popPose();
    }
}
