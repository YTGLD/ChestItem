package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class BlackShieldRenderHandler {

    public static int glow;

    public static double lastShield;
    public static float displayedShield;

    public static float theAlpha = 0;

    public static float time = 0;
    public static void tick(ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player != null) {
            double max = player.getAttributeValue(AttReg.shadow_shield);
            double now = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
            if (max > 0) {

                if (aFloatCool > 0) {
                    aFloatCool--;
                } else {
                    aFloatCool = 1;
                }

                if (now < max) {
                    aFloat = 1;
                    aFloatCool = 40;
                }else {
                    if (aFloatCool <= 0) {
                        if (aFloat > 0) {
                            aFloat -= 0.025f;
                        }

                        if (aFloat <= 0) {
                            aFloat = 0;
                        }
                    }
                }
                if (glow > 0) {
                    glow--;
                }
                if (lastShield != now) {
                    glow = 20;
                }

                lastShield = now;
                time += 1;
                if (soundCool > 0) {
                    soundCool--;
                }
            }
        }
    }
    public static float aFloat = 1;
    public static float aFloatCool = 40;
    public static int soundCool = 1;

    public static void renderShield(GuiGraphics guiGraphics) {
        var minecraft = Minecraft.getInstance();
        var poseStack = guiGraphics.pose();
        if (!minecraft.options.hideGui) {
            var player = minecraft.player;
            if (player != null && minecraft.level != null) {
                if (!player.isCreative() && !player.isSpectator()) {
                    if (player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) <= 0) {
                        return;
                    }
                    double max = player.getAttributeValue(AttReg.shadow_shield);
                    poseStack.pushPose();
                    int left = guiGraphics.guiWidth() / 2;
                    int top = guiGraphics.guiHeight() - 47;
                    if (max > 0) {
                        float s = aFloat;
                        if (s < 0) {
                            s = 0;
                        }
                        float delta = (float) (player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) / max);

                        if (delta > 1) {
                            delta = 1;
                        }
                        if (delta <= 0.25 && delta > 0.1f) {
                            renderSs4(guiGraphics,left,top,s, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_1.png"),ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_glow_1.png"));
                        }else if (delta > 0.25 && delta <= 0.5) {
                            renderSs4(guiGraphics,left,top,s, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_2.png"),ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_glow_2.png"));
                        }else if (delta > 0.5 && delta <= 0.75) {
                            renderSs4(guiGraphics,left,top,s, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_3.png"),ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_glow_3.png"));
                        }else if (delta > 0.75 && delta <= 1) {
                            renderSs4(guiGraphics,left,top,s, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_4.png"),ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/black_shadow_glow_4.png"));
                        }

                    }
                    poseStack.popPose();
                }
            }
        }
    }

    public static void renderSs4(GuiGraphics guiGraphics, int left, int top , float s, ResourceLocation resourceLocation,ResourceLocation glowRes){
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player == null) {
            return;
        }else {
            new MGuiGraphics.GUI(GameRenderer::getPositionTexColorShader, false)
                    .blit(guiGraphics, resourceLocation,
                            (left - (float) (24) / 2), (top - (float) (24) / 2),
                            0, 0,
                            24,
                            24,
                            24,
                            24,
                            1, 1, 1, s);

            new MGuiGraphics.GUI(GameRenderer::getPositionTexColorShader, true)
                    .blit(guiGraphics, glowRes,
                            (left - (float) (24) / 2), (top - (float) (24) / 2),
                            0, 0,
                            24,
                            24,
                            24,
                            24,
                            1, 1, 1, glow / 20f);
        }
    }
    public static void hurtBlackShield (LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            AttributeInstance shadowShield = player.getAttribute(AttReg.shadow_shield);
            if (shadowShield != null) {
                float theNumber = (float) shadowShield.getValue();
                float shadowShieldAttachment = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
                if (shadowShieldAttachment > 0 && theNumber > 0) {
                    aFloat = 1;
                    aFloatCool = 40;
                }
            }
        }
    }
}
