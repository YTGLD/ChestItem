package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class BlackShieldRenderHandler {

    public static int glow;
    public static double lastShield;
    public static float time = 0;
    public static float aSize = 1;
    public static float aGlow = 1f;
    public static float aGlowMin = 0F;
    public static float aGlowDOLDOWN = 0f;
    public static float glowRed = 0f;

    public static void tick(ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player != null) {
            double max = player.getAttributeValue(AttReg.shadow_shield);
            double now = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
            if (max > 0) {
                if (glowRed > 0) {
                    glowRed -= 0.05f;
                }
                if (aFloatCool > 0) {
                    aFloatCool--;
                } else {
                    aFloatCool = 1;
                }
                if (player.hurtDuration == 10) {
                    aFloat = 1;
                    aFloatCool = 40;
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
                    aGlowDOLDOWN = 1;
                    aGlow = 1f;
                    aGlowMin = 0f;
                }
                if (aFloat < 1) {
                    aGlow = 1f;
                    aGlowMin = 0f;
                }
                if (aGlowDOLDOWN > 0) {
                    aGlowDOLDOWN -= 0.1f;
                    if (aGlowMin < 1) {
                        aGlowMin += 0.1f;
                    }
                    if (aGlow > 0) {
                        aGlow -= 0.1f;
                    }
                }
                if (aFloat < 1) {
                    if (aSize < 1.5f) {
                        aSize += 0.003f;
                    }
                }else {
                    aSize = 1;
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

    public static void renderShield(GuiGraphicsExtractor guiGraphics) {
        var minecraft = Minecraft.getInstance();
        var poseStack = guiGraphics.pose();
        var player = minecraft.player;
        if (player != null && minecraft.level != null) {
            if (!player.isCreative() && !player.isSpectator()) {
                if (player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES) <= 0) {
                    return;
                }

                poseStack.pushMatrix();
                poseStack.translate(
                        guiGraphics.guiWidth() / 2f,
                        guiGraphics.guiHeight() - 47f);
                poseStack.rotate(player.tickCount / 12f);
                poseStack.translate(-48 / 2f, -48 / 2f);
                evilGlow(guiGraphics,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/color.png"),48);
                evilGlow(guiGraphics,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/item_glowing/all.png"),48);
                poseStack.popMatrix();


                double max = player.getAttributeValue(AttReg.shadow_shield);
                if (max > 0) {
                    if (aFloat < 1 && sanValue(player) < 10) {
                        poseStack.pushMatrix();
                        poseStack.translate(
                                ((guiGraphics.guiWidth() / 2f) - (24 * aSize) / 2),
                                ((guiGraphics.guiHeight() - 47f) - (24 * aSize) / 2));
                        poseStack.scale(aSize, aSize);

                        guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                                , Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                        "textures/gui/black_shadow_glow_4.png"),0,0,0,0,
                                24,24,
                                24,24,
                                Light.ARGB.color((int) (aFloat * 255),255,255, 255));

                        poseStack.popMatrix();
                    }
                }
                poseStack.pushMatrix();
                poseStack.translate(
                        (guiGraphics.guiWidth() / 2f) - (float) (24) / 2,
                        (guiGraphics.guiHeight() - 47f)- (float) (24) / 2);
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
                        renderSs4(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/black_shadow_1.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/black_shadow_glow_1.png"));
                        renderEvil(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/evil_shadow_1.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/item_glowing/all.png"));
                    }else if (delta > 0.25 && delta <= 0.5) {
                        renderSs4(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/black_shadow_2.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/black_shadow_glow_2.png"));
                        renderEvil(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/evil_shadow_2.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/item_glowing/all.png"));
                    }else if (delta > 0.5 && delta <= 0.75) {
                        renderSs4(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/black_shadow_3.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/black_shadow_glow_3.png"));
                        renderEvil(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/evil_shadow_3.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/item_glowing/all.png"));
                    }else if (delta > 0.75 && delta <= 1) {
                        renderSs4(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/black_shadow_4.png"), Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/black_shadow_glow_4.png"));
                        renderEvil(guiGraphics, s, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/evil_shadow_4.png"),Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/item_glowing/all.png"));
                    }
                }
                poseStack.popMatrix();
            }
        }
    }

    public static void renderSs4(GuiGraphicsExtractor guiGraphics, float s, Identifier resourceLocation, Identifier glowRes){
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player != null && sanValue(player) < 10) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED
                    , resourceLocation,0,0,0,0,
                    24,24,
                    24,24,
                    Light.ARGB.color( (int) (s * 255),255,255,255));

            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                    , glowRes,0,0,0,0,
                    24,24,
                    24,24,
                    Light.ARGB.color( (int) ((glow / 20f) * 255),255,255,255));

            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                    , resourceLocation,0,0,0,0,
                    24,24,
                    24,24,
                    Light.ARGB.color((int) Math.min(aFloat * 255,Math.min(aGlowMin* 255,aGlow* 255)),255,255, 255));
        }
    }

    public static void renderEvil(GuiGraphicsExtractor guiGraphics, float s, Identifier resourceLocation, Identifier glowRes){
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player != null) {
            float value = (float) player.getAttributeValue(AttReg.theSanity);
            float base = (float) player.getAttributeBaseValue(AttReg.theSanity);
            if (value < base) {

                float lvl = value / base;
                float now = (1 - (lvl));

                if (now < 0) {
                    now = 0;
                }
                if (now > 1) {
                    now = 1;
                }
                if (sanValue(player) >= 10) {
                    guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                            , glowRes, 0, 0, 0, 0,
                            24, 24,
                            24, 24,
                            Light.ARGB.color( (int) ((glow / 20f) * 255),45,255,170));
                }

                guiGraphics.blit(RenderPipelines.GUI_TEXTURED
                        , resourceLocation, 0, 0, 0, 0,
                        24, 24,
                        24, 24,
                        Light.ARGB.color((int) Math.min(s * 255f,now * 255f), 255, 255, 255));


                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                        , resourceLocation, 0, 0, 0, 0,
                        24, 24,
                        24, 24,
                        Light.ARGB.color((int) Math.min(aFloat * 255, Math.min(aGlowMin * 255, aGlow * 255)), 255, 255, 255));
            }
        }
    }
    public static void evilGlow(GuiGraphicsExtractor guiGraphics, Identifier glowRes,int size) {
        var minecraft = Minecraft.getInstance();
        var player = minecraft.player;
        if (player != null) {
            float value = (float) player.getAttributeValue(AttReg.theSanity);
            float base = (float) player.getAttributeBaseValue(AttReg.theSanity);
            if (value < base) {
                if (sanValue(player) >= 10) {
                    guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED
                            , glowRes, 0, 0, 0, 0,
                            size, size,
                            size, size,
                            Light.ARGB.color((int) ((glow / 20f) * 255), 45, 255, 170));
                }
            }


        }
    }

    public static float sanValue (Player player){
        float base = (float) player.getAttributeBaseValue(AttReg.theSanity);
        float san = (float) player.getAttributeValue(AttReg.theSanity) - base;
        if (san < 0) {
            san = -san;
        }
        return san;
    }
}
