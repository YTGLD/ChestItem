package com.ytgld.chest_item.renderer.gui_particles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.MGuiGraphics;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector2f;

import java.util.Map;

public class BlackParticlesRenderer {
    public static void onRenderGui(GuiGraphics guiGraphics , PoseStack stack) {
        for (Map.Entry<BlackKey, BlackState> entry : BlackParticlesAdd.all().entrySet()) {
            BlackState state = entry.getValue();
            stack.pushPose();
            addBlackLight(guiGraphics,stack,state.screenX,state.screenY,state);
            stack.popPose();
        }
    }
    private static void  addBlackLight(GuiGraphics guiGraphics,PoseStack pose,int x, int y,BlackState state){
        int alpha = state.alpha;
        int size = state.imageColorAndRenderPipeline.size();
        ResourceLocation identifier = state.imageColorAndRenderPipeline.identifier();

        boolean rot = state.imageColorAndRenderPipeline.rot();
        addCom((state.lifeTime), guiGraphics, pose, x, y,state,alpha,size,identifier,true,rot);
        addCom((state.lifeTime), guiGraphics, pose, x, y,state,alpha / 10,size * 2,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                "textures/item_glowing/all.png"),false,false);
    }

    private static void addCom(
            float deltaTime,
            GuiGraphics guiGraphics,
            PoseStack pose,
            int x,
            int y,
            BlackState state,
            int alpha,
            int size,
            ResourceLocation image,
            boolean downSize,
            boolean canRotate
    ) {
        BlackKey.ColorImage color = state.imageColorAndRenderPipeline.color();

        if (downSize) {
            size = (int) (size * alpha / 255f);
        }

        Vector2f position = state.imageColorAndRenderPipeline.position();
        Vector2f velocity = state.imageColorAndRenderPipeline.velocity();
        Vector2f acceleration = state.imageColorAndRenderPipeline.acceleration();

        velocity.fma(deltaTime, acceleration);
        position.fma(deltaTime, velocity);
        float px = x + position.x;
        float py = y + position.y;

        pose.pushPose();

        pose.translate(px, py,0);

        if (canRotate) {
            pose.mulPose(Axis.ZN.rotationDegrees(deltaTime * 50f * (float)Math.PI));
        }

        pose.translate(-px, -py,0);

        pose.translate(px - size / 2f, py - size / 2f,0);
        new MGuiGraphics.GUI(state.imageColorAndRenderPipeline.shadowImage().shaderInstanceSupplier(),
                state.imageColorAndRenderPipeline.shadowImage().light()).blit(guiGraphics,image,
                0,0,
                0,0,
                size,
                size,
                size,
                size,
                Light.ARGB.color(
                        alpha,
                        color.r(),
                        color.g(),
                        color.b()
                ));
        pose.popPose();
    }
}