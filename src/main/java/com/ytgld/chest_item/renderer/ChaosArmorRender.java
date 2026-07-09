package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

public class ChaosArmorRender {

    public static void renderArmorLevel(GuiGraphicsExtractor graphics, Player player,int rightHeight) {
        if (player != null) {
            int l = graphics.guiWidth() / 2 + 10;
            Profiler.get().push("chaos_wind");
            renderChaos(graphics, player, graphics.guiHeight() - rightHeight + 10, l);
            Profiler.get().pop();
        }
    }
    public static int setRightHeight(int value,Player player){
        float i = player.getData(AttReg.chaosWinds);
        if (i > 0 && showAlpha > 0) {
            return value + 10;
        }
        return value;
    }



    private static float showAlpha = 255;
    public static double lastShield;

    public static int glow;
    public static float aGlow = 1f;
    public static float aGlowMin = 0F;
    public static float aGlowDOLDOWN = 0f;

    private static void renderChaos(GuiGraphicsExtractor guiGraphics, Player player, int y, int x) {
        float i = player.getData(AttReg.chaosWinds);
        if (i > 0) {
            if (lastShield != i) {
                glow = 20;
                aGlowDOLDOWN = 1;
                aGlow = 1f;
                aGlowMin = 0f;
            }
            if (glow > 0) {
                glow--;
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
            int hurtTime = player.hurtTime;
            if (hurtTime > 0) {
                showAlpha = 255;
            }
            if (i>=player.getAttributeValue(AttReg.chaos_armor) ){
                if (hurtTime <= 0) {
                    if (showAlpha > 0) {
                        showAlpha -= 2.5f;
                    }
                }
            }
            int alpha = (int) showAlpha;
            int light = (int) Math.min(aGlowMin* 255,aGlow* 255);
            if (showAlpha < 0) {
                showAlpha = 0;
            }



            Identifier a1 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/chaos_wind_1.png");
            Identifier a2 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/chaos_wind_2.png");
            Identifier a3 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/chaos_wind_3.png");
            Identifier a4 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/chaos_wind_4.png");

            int rowSize = 40;
            int slotsPerRow = 10;

            int rowIndex = (int) i / rowSize;
            int baseI = (int) i % rowSize;

            int yyBase = y - 10;

            for (int row = 0; row <= rowIndex; row++) {
                int offsetI = row == rowIndex ? baseI : rowSize;

                int yyOffset = yyBase - row * 5;

                for (int j = 0; j < slotsPerRow; j++) {
                    drawArmor(
                            offsetI,
                            x,
                            guiGraphics,
                            yyOffset,
                            a1, a2, a3, a4,
                            1 + j * 4, 2 + j * 4,
                            3 + j * 4, 4 + j * 4,
                            j,
                            alpha,
                            RenderPipelines.GUI_TEXTURED
                    );

                    drawArmor(
                            offsetI,
                            x,
                            guiGraphics,
                            yyOffset,
                            a1, a2, a3, a4,
                            1 + j * 4, 2 + j * 4,
                            3 + j * 4, 4 + j * 4,
                            j,
                            light,
                            MRender.RenderPs.GUI_TEXTURED
                    );
                }
            }
            lastShield = i;
        }
    }


    private static void drawArmor(int i, int x, GuiGraphicsExtractor guiGraphics, int yy,
                                  Identifier a1,
                                  Identifier a2,
                                  Identifier a3,
                                  Identifier a4,
                                  int aa,
                                  int b,
                                  int c,
                                  int d,
                                  int offset, int alpha, RenderPipeline renderPipeline
    ){

        if (offset > 10) {
            offset = 10;
        }
        if (i > 0) {
            int xx = (x) + offset * 8- 1;
            if (i > aa + 3) {
                guiGraphics.blit(renderPipeline, a1,
                        9 + ((x) + (offset - 1) * 8- 1), yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
                ShieldRenderHandler.rednerPart(xx,yy,ShieldRenderHandler.doChaosWindsParticle,new BlackKey.ColorImage(255, 255, 75, 255));
            }
            if (i == aa) {
                guiGraphics.blit(renderPipeline, a4, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
                ShieldRenderHandler.rednerPart(xx,yy,ShieldRenderHandler.doChaosWindsParticle,new BlackKey.ColorImage(255, 255, 75, 255));
            }
            if (i == b) {
                guiGraphics.blit(renderPipeline, a3, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
                ShieldRenderHandler.rednerPart(xx,yy,ShieldRenderHandler.doChaosWindsParticle,new BlackKey.ColorImage(255, 255, 75, 255));
            }
            if (i == c) {
                guiGraphics.blit(renderPipeline, a2, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
                ShieldRenderHandler.rednerPart(xx,yy,ShieldRenderHandler.doChaosWindsParticle,new BlackKey.ColorImage(255, 255, 75, 255));
            }
            if (i == d) {
                guiGraphics.blit(renderPipeline, a1, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
                ShieldRenderHandler.rednerPart(xx,yy,ShieldRenderHandler.doChaosWindsParticle,new BlackKey.ColorImage(255, 255, 75, 255));
            }
        }
    }
}
