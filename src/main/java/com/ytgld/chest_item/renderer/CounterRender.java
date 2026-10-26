package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.CounterHandler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CounterRender {
    public static int glow;
    public static double lastCounter;
    public static float time = 0.0F;
    public static int alpha = 255;

    public static int scoreLastSize = 0;

    public static void tick(ClientTickEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            float max = (float) player.getAttributeValue(AttReg.counter);
            float now =  (float) player.getData(AttReg.counter_data);
            if (max > (double)0.0F) {
                ++time;

                int down = glow - 25;
                glow = Math.max(0,down);
                if (lastCounter != now) {
                    glow = 250;
                }
                lastCounter = now;

                if (now >= max) {
                    int aDown = alpha - 25;
                    if (aDown < 0) {
                        aDown = 0;
                    }
                    alpha = aDown;
                }else {
                    alpha = 255;
                }
            }
        }
    }
    private static final Map<UUID, Vec2> LAST_ROTATION = new HashMap<>();
    private static void addParticle(GuiGraphicsExtractor guiGraphics){
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) {
            return;
        }
        if (minecraft.gui.hud.isHidden()) {
            return;
        }
        int theDifference =
                player.getData(AttReg.score_consecutive_victories.get())
                        - CounterHandler.ScoreConsecutiveVictoriesHandler.HowValueToAddDamage(player);

        if (scoreLastSize != theDifference) {
            if (scoreLastSize - theDifference > 0) {
                for (int i = 0; i < 6; i++) {
                    BlackParticlesAdd.markSeen(
                            (int) ((float)guiGraphics.guiWidth() / 2.0F + 4f + 12),
                            (int) ((float)guiGraphics.guiHeight() / 2  + 12),
                            new BlackKey.ImageColorAndRenderPipeline(
                                    12,
                                    new BlackKey.ColorImage(
                                            (int) (255f),
                                            (int) (250 - Mth.nextFloat(player.getRandom(),1,8) * 5),
                                            (int) (225 - Mth.nextFloat(player.getRandom(),1,8) * 15),
                                            (int) (100 - Mth.nextFloat(player.getRandom(),1,8) * 6)),
                                    Identifier.fromNamespaceAndPath("chest_item", "textures/item_glowing/all.png"),
                                    MRender.RenderPs.GUI_TEXTURED,
                                    new Vector2f(),
                                    new Vector2f(Mth.nextFloat(player.getRandom(), -0.02f, 0.02f), -Mth.nextFloat(player.getRandom(), 0.05f, 0.075f)),
                                    new Vector2f(),
                                    false));
                }

            }
        }

        if (theDifference > 0) {
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
                            -0.025f + viewY
                    );
            float light = (float) theDifference / (float) CounterHandler.ScoreConsecutiveVictoriesHandler.maxValue(player);
            BlackParticlesAdd.markSeen(
                    (int) ((float)guiGraphics.guiWidth() / 2.0F + 4f + 12),
                    (int) ((float)guiGraphics.guiHeight() / 2  + 12),
                    new BlackKey.ImageColorAndRenderPipeline(
                            12,
                            new BlackKey.ColorImage(
                                    Math.min(alpha,(int) (255 * light)),
                                    (int) (250 - Mth.nextFloat(player.getRandom(),1,8) * 5),
                                    (int) (225 - Mth.nextFloat(player.getRandom(),1,8) * 15),
                                    (int) (100 - Mth.nextFloat(player.getRandom(),1,8) * 6)),
                            Identifier.fromNamespaceAndPath("chest_item", "textures/item_glowing/all.png"),
                            MRender.RenderPs.GUI_TEXTURED,
                            new Vector2f(),
                            motion,
                            new Vector2f(),
                            false));
        }
        scoreLastSize = theDifference;
    }

    public static void renderShield(GuiGraphicsExtractor guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        Matrix3x2fStack poseStack = guiGraphics.pose();
        LocalPlayer player = minecraft.player;
        if (player != null && minecraft.level != null && !player.isCreative() && !player.isSpectator()) {
            float max = (float) player.getAttributeValue(AttReg.counter);
            float now =  (float) player.getData(AttReg.counter_data);
            int cooldown = player.getData(AttReg.counter_data_cooldown);
            if (max <= 0) {
                return;
            }
            float delta = now / max;
            if (delta > 1.0F) {
                delta = 1.0F;
            }
            addParticle(guiGraphics);
            poseStack.pushMatrix();
            poseStack.translate((float)guiGraphics.guiWidth() / 2.0F + 4f, (float)guiGraphics.guiHeight() / 2 );
            if ((double)delta <= (double)0.25F && delta > 0f) {
                renderDecaySword(guiGraphics,
                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/layer/counter_4.png"),
                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/layer/counter_4_glow.png"));
            } else if ((double)delta > (double)0.25F && (double)delta <= (double)0.5F) {
                renderDecaySword(guiGraphics,
                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/layer/counter_3.png"),
                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/layer/counter_3_glow.png"));
            } else if ((double)delta > (double)0.5F && (double)delta <= (double)0.75F) {
                renderDecaySword(guiGraphics,
                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/layer/counter_2.png"),
                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/layer/counter_2_glow.png"));
            } else if ((double)delta > (double)0.75F && delta <= 1.0F) {
                renderDecaySword(guiGraphics,
                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/layer/counter_1.png"),
                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/layer/counter_1_glow.png"));
            }

            if (cooldown > 0) {
                int alpha = (int) (Math.abs(Math.sin(time / 20)) * 255);
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                        Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/layer/counter_cooldown.png"), 0, 0, 0.0F, 0.0F,
                        24, 24, 24, 24,
                        Light.ARGB.color(alpha, 255, 255, 255));
            }
            poseStack.popMatrix();
        }
    }
    private static void renderDecaySword(GuiGraphicsExtractor guiGraphics, Identifier resourceLocation, Identifier glowRes) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer player = minecraft.player;
        if (player != null) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                    resourceLocation, 0, 0, 0.0F, 0.0F, 24, 24, 24, 24,
                    Light.ARGB.color(alpha, 255, 255, 255));
            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED,
                    glowRes, 0, 0, 0.0F, 0.0F, 24, 24, 24, 24,
                    Light.ARGB.color(Math.min(alpha,glow), 255, 255, 255));
            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED,
                    resourceLocation, 0, 0, 0.0F, 0.0F, 24, 24, 24, 24,
                    Light.ARGB.color(Math.min(alpha,glow), 255, 255, 255));
        }

    }
}
