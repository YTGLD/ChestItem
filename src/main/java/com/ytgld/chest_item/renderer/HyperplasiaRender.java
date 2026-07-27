package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.joml.Vector2f;

import java.util.function.Supplier;

public class HyperplasiaRender {
    public static void renderArmorLevel(GuiGraphics graphics, Player player,int leftHeight) {
        if (player != null) {
            int l = graphics.guiWidth() / 2 - 91;
            renderHyperplasia(graphics, player, graphics.guiHeight() - leftHeight + 10, l);
        }
    }
    public static int setLeftHeight(int value,Player player){
        float i = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
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

    private static void renderHyperplasia(GuiGraphics guiGraphics, Player player, int y, int x) {
        float i = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
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
            if (i>=player.getAttributeValue(AttReg.hyperplasia) ){
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



            ResourceLocation a1 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_1.png");
            ResourceLocation a2 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_2.png");
            ResourceLocation a3 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_3.png");
            ResourceLocation a4 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_4.png");

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
                            CIStateShardsHasBlack::getHasBlock,false
                            ,player
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
                            CIStateShardsHasBlack::getHasBlock,true,
                            player
                    );
                }
            }
            lastShield = i;
        }
    }


    private static void drawArmor(int i, int x, GuiGraphics guiGraphics, int yy,
                                  ResourceLocation a1,
                                  ResourceLocation a2,
                                  ResourceLocation a3,
                                  ResourceLocation a4,
                                  int aa,
                                  int b,
                                  int c,
                                  int d,
                                  int offset, int alpha, Supplier<ShaderInstance> shaderSupplier, boolean isLight,Player player
    ){
        if (offset > 10) {
            offset = 10;
        }

        if (i > 0) {
            int xx = (x) + offset * 8- 1;
            if (i > aa + 3) {
                new MGuiGraphics.GUI(shaderSupplier,isLight).blit(
                        guiGraphics,a1, 9 + ((x) + (offset - 1) * 8- 1), yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
            }
            if (i == aa) {
                new MGuiGraphics.GUI(shaderSupplier,isLight).blit(guiGraphics,
                        a4, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));

                if (lastShield != player.getData(AttReg.hyperplasiaATTACHMENT_TYPES)) {
                    for (int size = 0; size < 25; size++) {
                        BlackParticlesAdd.markSeen(xx + 4, yy + 4, new BlackKey.ImageColorAndRenderPipeline(8,
                                new BlackKey.ColorImage(255, 185, 75, 105),
                                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png"), new BlackKey.ShadowImage(CIStateShardsHasBlack::getHasBlock,true),
                                new Vector2f(), new Vector2f((float) (Math.cos(size) / 33f), (float) (Math.sin(size) / 33f)), new Vector2f(), false));
                    }
                }
            }
            if (i == b) {
                new MGuiGraphics.GUI(shaderSupplier,isLight).blit(guiGraphics,
                        a3, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
            }
            if (i == c) {
                new MGuiGraphics.GUI(shaderSupplier,isLight).blit(guiGraphics,
                        a2, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
            }
            if (i == d) {
                new MGuiGraphics.GUI(shaderSupplier,isLight).blit(guiGraphics,
                        a1, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
            }
        }
    }

}
