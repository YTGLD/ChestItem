package com.ytgld.chest_item.utils.dout;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.utils.RenderObjects;
import com.ytgld.chest_item.utils.WorldRenderObject;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.Map;

public class SwordRenderObject extends WorldRenderObject {
    public Vec3 pitch;
    public float size;
    public int color = 0xffffffff;
    public SwordRenderObject(Vec3 vec3,Vec3 pitch,float size) {
        super(vec3);
        this.maxTime = 50;
        this.pitch = pitch;
        this.size = size;
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
        int imageNumber = age / 10;
        if (imageNumber < 1) {
            imageNumber = 1;
        }

        render(poseStack,collector,size,
                RenderObjects.renderTypeFunctionLive.apply(
                        integerIdentifierMap().get(imageNumber)
                ),
                color);
        render(poseStack,collector,size,
                RenderObjects.renderTypeFunctionLiveOutline.apply(
                        integerIdentifierMap().get(imageNumber)
                ),
                color);
        poseStack.popPose();
    }

    private Map<Integer ,Identifier> integerIdentifierMap (){
        Map<Integer ,Identifier> map = new HashMap<>();
        map.put(1,Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/render_object/sword_energy_1.png"));
        map.put(2,Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/render_object/sword_energy_2.png"));
        map.put(3,Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/render_object/sword_energy_3.png"));
        map.put(4,Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/render_object/sword_energy_4.png"));
        map.put(5,Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/render_object/sword_energy_5.png"));
        return map ;
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