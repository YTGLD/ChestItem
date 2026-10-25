package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.sounds.Sounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class BlackShieldRenderHandler {

    public static int glow;
    public static double lastShield;
    public static float time = 0;
    public static float aGlow = 1f;
    public static float aGlowMin = 0F;
    public static float aGlowDOLDOWN = 0f;
    public static float glowRed = 0f;
    public static boolean isFull = false;

    private static void applySound(Player player,float p ,float v, SoundEvent event,SoundEvent evil){
        //4
        int value = (int) player.getAttributeValue(AttReg.theSanity);
        //10
        int base = (int) player.getAttributeBaseValue(AttReg.theSanity);

        int cha = base - value;

        if (cha >= base) {
            HandlerClient.sound(evil, p,v + 0.5f);
        }else {
            HandlerClient.sound(event, p,v);
        }

    }
    public static void tick(ClientTickEvent event) {
        var player = Minecraft.getInstance().player;
        if (player != null) {
            double max = player.getAttributeValue(AttReg.shadow_shield);
            double now = player.getData(AttReg.shadowShieldTypes);
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
                    isFull = false;
                }else {
                    //满了
                    if (!isFull) {
                        applySound(player,1,1.1f,
                                Sounds.shadow_shield_heal_max.value(),
                                Sounds.evil_shield_full.value());

                        isFull = true;
                    }
                    if (aFloat > 0) {
                        aFloat -= 0.1f;
                    }
                    if (aFloat <= 0) {
                        aFloat = 0;
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
                    if (lastShield < now) {
                        float delta = (float) (player.getData(AttReg.shadowShieldTypes) / max);
                        if (delta > 1) {
                            delta = 1;
                        }
                        float p = 0.2f;
                        if (delta <= 0.25 && delta > 0.1f) {
                            p += 0.25f;
                        } else if (delta > 0.25 && delta <= 0.5) {
                            p += 0.5f;
                        }else if (delta > 0.5 && delta <= 0.75) {
                            p += 0.75f;
                        }else if (delta > 0.75 && delta <= 1) {
                            p += 1f;
                        }
                        applySound(player,p,0.75f,
                                Sounds.shadow_shield_heal.value(),
                                Sounds.evil_shield_heal.value());
                    }
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
                lastShield = now;
                time += 1;
            }
        }
    }
    public static float aFloat = 1;
    public static float aFloatCool = 40;


    public static void renderShield(GuiGraphicsExtractor guiGraphics) {
        var minecraft = Minecraft.getInstance();
        var poseStack = guiGraphics.pose();
        var player = minecraft.player;
        if (player != null && minecraft.level != null) {
            if (!player.isCreative() && !player.isSpectator()) {
                if (player.getData(AttReg.shadowShieldTypes) <= 0) {
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
                poseStack.pushMatrix();
                poseStack.translate(
                        (guiGraphics.guiWidth() / 2f) - (float) (24) / 2,
                        (guiGraphics.guiHeight() - 47f)- (float) (24) / 2);
                if (max > 0) {
                    float s = aFloat;
                    if (s < 0) {
                        s = 0;
                    }
                    float delta = (float) (player.getData(AttReg.shadowShieldTypes) / max);

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
