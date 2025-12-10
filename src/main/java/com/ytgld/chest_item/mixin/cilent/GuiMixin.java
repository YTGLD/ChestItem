package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow @Nullable protected abstract Player getCameraPlayer();

    @Shadow public int leftHeight;

    @Inject(at = @At("RETURN"), method = "renderArmorLevel")
    private void renderArmorLevel(GuiGraphics p_283143_, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            float i = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
            int l = p_283143_.guiWidth() / 2 - 91;
            Profiler.get().push("hyperplasia");
            cI1_21_9$renderArmor(p_283143_, player, p_283143_.guiHeight() - this.leftHeight + 10, 1, 0, l);
            Profiler.get().pop();
            if (i > 0&&cI1_21_9$showAlpha > 0) {
                this.leftHeight += 20;
            }
        }
        if (player != null) {
            float i = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
            int l = p_283143_.guiWidth() / 2 - 91;
            Profiler.get().push("shadow_shield");
            cI1_21_9$renderShadowBlackArmor(p_283143_, player, p_283143_.guiHeight() - this.leftHeight + 10, 1, 0, l);
            Profiler.get().pop();
            if (i > 0&&cI1_21_9$showAlphaShadow > 0) {
                this.leftHeight += 20;
            }
        }
    }



    @Unique
    private float cI1_21_9$showAlphaShadow = 255;
    @Unique
    private float cI1_21_9$lightAmoutShadow = 0;

    @Unique
    private void cI1_21_9$renderShadowBlackArmor(GuiGraphics guiGraphics, Player player, int y, int heartRows, int height, int x) {
        float i = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
        if (i > 0) {
            int hurtTime = player.hurtTime;
            if (hurtTime > 0) {
                cI1_21_9$showAlphaShadow = 255;
            }
            if (i>=player.getAttributeValue(AttReg.shadow_shield) - 1){
                if (hurtTime <= 0) {
                    if (cI1_21_9$lightAmoutShadow >=1) {
                        if (cI1_21_9$showAlphaShadow > 0) {
                            cI1_21_9$showAlphaShadow -= 2.55f;
                        }
                    }
                    cI1_21_9$lightAmoutShadow += 0.0125f;
                }else {
                    cI1_21_9$lightAmoutShadow = 0;
                }
            }else {
                cI1_21_9$lightAmoutShadow = 0;
            }
            int alpha = (int) cI1_21_9$showAlphaShadow;
            if (cI1_21_9$showAlphaShadow < 0) {
                cI1_21_9$showAlphaShadow = 0;
            }


            int yy = y - (heartRows - 1) * height - 10;
            int maxIcons = (int) Math.min(i, 3);

            for (int k = 0; k < maxIcons; k++) {
                int xx = (x + k * 8) - 1;
                if (k * 2 + 1 < i) {
                    guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED_CI, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/shadow_black_1.png"),
                            xx, yy, 0, 0, 11, 11, 11, 11, Light.ARGB.color(alpha, 255, 255, 255));
                }
                if (k * 2 + 1 == i) {
                    guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED_CI, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/shadow_black_2.png"),
                            xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(alpha, 255, 255, 255));
                }
            }
            if (i > 3){
                guiGraphics.drawString(Minecraft.getInstance().font,String.valueOf((int) i),(x) + 4 * 8- 3,yy ,
                        Light.ARGB.color(alpha,100,50,255));
            }

        }
    }


    @Unique
    private void cI1_21_9$drawA1234(int i,int x,GuiGraphics guiGraphics,int yy,
                                    Identifier a1,
                                    Identifier a2,
                                    Identifier a3,
                                    Identifier a4,
                                    int aa,
                                    int b,
                                    int c,
                                    int d,

                                    int offset,int a,float light
    ){
        if (i > 0) {
            int xx = (x) + offset * 8- 1;
            if (i > aa + 3) {
                guiGraphics.blit(MRender.RenderPs.LightSlownessHasLight(false, 0.125F,10000,light), a1,
                        9 + ((x) + (offset - 1) * 8- 1), yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(a, 255, 255, 255));
            }
            if (i == aa) {
                guiGraphics.blit(MRender.RenderPs.LightSlownessHasLight(false, 0.125F,2222,light), a4, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(a, 255, 255, 255));
            }
            if (i == b) {
                guiGraphics.blit(MRender.RenderPs.LightSlownessHasLight(false, 0.125F,3333,light), a3, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(a, 255, 255, 255));
            }
            if (i == c) {
                guiGraphics.blit(MRender.RenderPs.LightSlownessHasLight(false, 0.125F,4444,light), a2, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(a, 255, 255, 255));
            }
            if (i == d) {
                guiGraphics.blit(MRender.RenderPs.LightSlownessHasLight(false, 0.125F,5555,light), a1, xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(a, 255, 255, 255));
            }
        }

    }

    @Unique
    private float cI1_21_9$showAlpha = 255;
    @Unique
    private float cI1_21_9$lightAmout = 0;

    @Unique
    private  void cI1_21_9$renderArmor(GuiGraphics guiGraphics, Player player, int y, int heartRows, int height, int x) {
        float i = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
        if (i > 0) {
            int hurtTime = player.hurtTime;
            if (hurtTime > 0) {
                cI1_21_9$showAlpha = 255;
            }
            if (i>=player.getAttributeValue(AttReg.hyperplasia) - 1){
                if (hurtTime <= 0) {
                    if (cI1_21_9$lightAmout >=0.45f) {
                        if (cI1_21_9$showAlpha > 0) {
                            cI1_21_9$showAlpha -= 2.5f;
                        }
                    }else {
                        cI1_21_9$lightAmout += 0.0125f;
                    }
                }else {
                    cI1_21_9$lightAmout = 0;
                }
            }else {
                cI1_21_9$lightAmout = 0;
            }
            int alpha = (int) cI1_21_9$showAlpha;
            float light = Math.min(0.45f,cI1_21_9$lightAmout);
            if (cI1_21_9$showAlpha < 0) {
                cI1_21_9$showAlpha = 0;
            }


            int yy = y - (heartRows - 1) * height - 10;

            Identifier a1 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_1.png");
            Identifier a2 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_2.png");
            Identifier a3 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_3.png");
            Identifier a4 = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_4.png");


            if (i <= 20) {
                for (int offset = 0; offset < 5; offset++) {
                    cI1_21_9$drawA1234((int) i, x, guiGraphics, yy, a1, a2, a3, a4,

                            1 + offset * 4, 2 + offset * 4,
                            3 + offset * 4, 4 + offset * 4,

                            offset,alpha,light);
                }
            }else {
                for (int j = 0; j < 5; j++) {
                    guiGraphics.blit(MRender.RenderPs.LightSlownessHasLight(false, 0.08f,i*1000,light), a1,
                            (x) + j * 8- 1, yy,
                            0, 0, 9, 9, 9, 9,
                            Light.ARGB.color(alpha, 255, 255, 255));
                }

                guiGraphics.drawString(Minecraft.getInstance().font,String.valueOf((int) i/4),(x) + 6 * 8- 3,yy ,
                        Light.ARGB.color(alpha,255,100,100));
            }


        }
    }
}
