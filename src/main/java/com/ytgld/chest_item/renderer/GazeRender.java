package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.renderpearl.api.pipeline.CompareOp;
import com.mojang.renderpearl.api.pipeline.DepthStencilState;
import com.mojang.renderpearl.api.pipeline.PrimitiveTopology;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.function.Function;

import static com.ytgld.chest_item.utils.RenderObjects.vColorTargetState;
import static net.minecraft.client.renderer.RenderPipelines.MATRICES_FOG_LIGHT_DIR_SNIPPET;

public class GazeRender {
    public static void emitGizmos(PoseStack poseStack, LevelRenderState levelRenderState, SubmitNodeCollector output) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (minecraft.level != null && player !=null) {
            if (!player.hasEffect(Effects.gaze)) {
                return;
            }
            for (Entity entity : minecraft.level.entitiesForRendering()) {
                if (entity instanceof Player) {
                    continue;
                }
                if (entity instanceof LivingEntity) {
                    Vec3 cameraPos = levelRenderState.cameraRenderState.pos;
                    poseStack.pushPose();
                    poseStack.translate(
                            entity.position().x - cameraPos.x,
                            entity.getEyePosition().y - cameraPos.y,
                            entity.position().z - cameraPos.z
                    );
                    Camera camera = Minecraft.getInstance().gameRenderer.mainCamera();
                    Quaternionf rotation = new Quaternionf();
                    SingleQuadParticle.FacingCameraMode.LOOKAT_XYZ.setRotation(rotation, camera, 0);
                    rotation.rotateZ(Mth.lerp(0, 0, 0));
                    poseStack.rotate(rotation);
                    int alpha = (int) (255 - cameraPos.distanceTo(entity.getEyePosition()) * 4);
                    alpha = Math.max(0, alpha);

                    int color = Light.ARGB.color(alpha, 255, 255, 255);
                    if (entity instanceof Monster) {
                        color = Light.ARGB.color(alpha, 255, 50, 50);
                    } else if (entity instanceof Animal) {
                        color = Light.ARGB.color(alpha, 50, 255, 50);
                    }

                    render(poseStack, output, 1, Identifier.fromNamespaceAndPath(
                            Chestitem.MODID, "textures/entity/gaze.png"
                    ), color);
                    poseStack.popPose();
                }
            }
        }
    }

    private static void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector,float size, Identifier identifier,int color){
        poseStack.pushPose();
        submitNodeCollector.submitCustomGeometry(poseStack, renderTypeFunction.apply(identifier),
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

    private static final RenderPipeline renderPipeline = RenderPipeline.builder(
            RenderPipeline.builder(RenderPipeline.builder(MATRICES_FOG_LIGHT_DIR_SNIPPET).
                            withVertexShader(
                                    "core/item")
                            .withFragmentShader("core/item")
                            .withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER1_SAMPLER2)
                            .withCull(false)
                            .withVertexBinding(0, DefaultVertexFormat.ENTITY).withPrimitiveTopology(PrimitiveTopology.QUADS).
                            withDepthStencilState(new DepthStencilState(CompareOp.ALWAYS_PASS, true)).withColorTargetState(vColorTargetState).buildSnippet())
                    .withLocation("pipeline/item_gaze")
                    .buildSnippet()).withLocation("pipeline/item_gaze")
            .withShaderDefine("ALPHA_CUTOUT", 0.1F).build();

    public static final Function<Identifier, RenderType> renderTypeFunction = Util.memoize((texture) -> {
        RenderSetup state = RenderSetup.builder(renderPipeline)
                .withTexture("Sampler0", texture)
                .useLightmap()
                .useOverlay()
                .affectsCrumbling()
                .createRenderSetup();
        return RenderType.create("vows_item", state);
    });
}
