package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RageRender {
    public static int alpha = 255;
    public static int showTime = 100;
    private static int time = 0;
    public static void tick(ClientTickEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            if (HandlerClient.has(player, InitItems.RottingSubstance_.asItem()) ) {
                time++;
                if (player.attackAnim > 0) {
                    alpha = 255;
                    showTime= 200;
                }else if (showTime > 0){
                    showTime --;
                }


                if (player.attackAnim <= 0 && showTime <= 0) {
                    if (alpha > 0) {
                        alpha -= 5;
                    }
                }
            }
        }
    }

    private static final Map<UUID, Vec2> LAST_ROTATION = new HashMap<>();


    public static void addParticle(GuiGraphics guiGraphics){
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) {
            return;
        }
        PoseStack poseStack = guiGraphics.pose();

        if (HandlerClient.has(player, InitItems.RottingSubstance_.asItem()) ) {
            float yaw = player.getYRot();
            float pitch = player.getXRot();
            Vec2 last = LAST_ROTATION.get(player.getUUID());
            float yawDelta = 0;
            float pitchDelta = 0;


            if (last != null) {
                yawDelta = Mth.wrapDegrees(yaw - last.x);
                pitchDelta = pitch - last.y;
            }

            LAST_ROTATION.put(
                    player.getUUID(),
                    new Vec2(yaw, pitch)
            );

            float viewX = Mth.clamp(-yawDelta * 0.03f,-0.15f,0.15f);
            float viewY = Mth.clamp(pitchDelta * 0.03f,-0.15f,0.15f);


            Vector2f motion =
                    new Vector2f(
                            viewX,
                            -0.055f + viewY
                    );

            poseStack.pushPose();
            poseStack.translate(
                    guiGraphics.guiWidth() / 2f,
                    guiGraphics.guiHeight() - 47f,0);
            poseStack.mulPose(Axis.ZN.rotationDegrees(time));
            poseStack.translate(-48 / 2f, -48 / 2f,0);
            new MGuiGraphics.GUI(GameRenderer::getPositionTexColorShader, true).blit(guiGraphics
                    , ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/shadow/black_2.png"), 0, 0, 0, 0,
                    48, 48,
                    48, 48,
                    Light.ARGB.color(alpha, 250, 225, 80));
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(
                    guiGraphics.guiWidth() / 2f,
                    guiGraphics.guiHeight() - 47f,0);
            poseStack.mulPose(Axis.ZN.rotationDegrees(-time));
            poseStack.translate(-36 / 2f, -36 / 2f,0);
            new MGuiGraphics.GUI(GameRenderer::getPositionTexColorShader, true).blit(guiGraphics
                    , ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/shadow/black_3.png"), 0, 0, 0, 0,
                    36, 36,
                    36, 36,
                    Light.ARGB.color(alpha, 225, 100, 80));
            poseStack.popPose();
            BlackParticlesAdd.markSeen(
                    (int) ((guiGraphics.guiWidth() / 2f)),
                    (int) ((guiGraphics.guiHeight() - 47f)),
                    new BlackKey.ImageColorAndRenderPipeline(
                            16,
                            new BlackKey.ColorImage(
                                    alpha,
                                    (int) (250 - Mth.nextFloat(player.getRandom(),1,8) * 5),
                                    (int) (225 - Mth.nextFloat(player.getRandom(),1,8) * 15),
                                    (int) (100 - Mth.nextFloat(player.getRandom(),1,8) * 6)),
                            ResourceLocation.fromNamespaceAndPath("chest_item", "textures/item_glowing/all.png"),
                            new BlackKey.ShadowImage(CIStateShardsHasBlack::getHasBlock,true),
                            new Vector2f(),
                            motion,
                            new Vector2f(),
                            false));
        }
    }
}
