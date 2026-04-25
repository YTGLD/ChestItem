package com.ytgld.chest_item.mixin.cilent;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.MGuiGraphicsCI_Life;
import com.ytgld.chest_item.renderer.MGuiGraphics;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    @Nullable
    protected abstract Player getCameraPlayer();

    @Shadow
    public int leftHeight;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow public int rightHeight;

    @Inject(at = @At("RETURN"), method = "renderArmorLevel")
    private void renderArmorLevel(GuiGraphics p_283143_, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            float i = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
            int l = p_283143_.guiWidth() / 2 - 91;
            this.minecraft.getProfiler().push("hyperplasia");
            cI1_21_9$renderArmor(p_283143_, player, p_283143_.guiHeight() - this.leftHeight + 10, 1, 0, l);
            this.minecraft.getProfiler().pop();
            if (i > 0&&cI1_21_9$showAlpha > 0) {
                this.leftHeight += 20;
            }
        }
    }
    @Inject(at = @At("RETURN"), method = "renderFoodLevel")
    private void renderFoodLevel(GuiGraphics p_283143_, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            float i = player.getData(AttReg.chaosWinds);
            int l = p_283143_.guiWidth() / 2 + 10;
            this.minecraft.getProfiler().push("chaos_winds");
            cI1_21_9$render_chaosWinds(p_283143_, player, p_283143_.guiHeight() - this.rightHeight + 10, 1, 0, l);
            this.minecraft.getProfiler().pop();
            if (i > 0&&cI1_21_9$showAlpha_chaosWinds > 0) {
                this.rightHeight += 10;
            }
        }
    }

    @Unique
    private void cI1_21_9$drawA1234(int i,int x,GuiGraphics guiGraphics,int yy,
                                         ResourceLocation a1,
                                         ResourceLocation a2,
                                         ResourceLocation a3,
                                         ResourceLocation a4,
                                         int aa,
                                         int b,
                                         int c,
                                         int d,

                                         int offset,float a,float light
    ){
        if (i > 0) {
            int xx = (x) + offset * 8- 1;
            if (i > aa + 3) {
                MGuiGraphics.blit(guiGraphics, a1,
                        9 + ((x) + (offset - 1) * 8- 1), yy, 0, 0, 9, 9, 9, 9, 1, 1, 1, a);
            }
            if (i == aa) {
                MGuiGraphics.blit( guiGraphics,a4, xx, yy, 0, 0, 9, 9, 9, 9,1, 1,1,a);
            }
            if (i == b) {
                MGuiGraphics.blit(guiGraphics, a3, xx, yy, 0, 0, 9, 9, 9, 9,1, 1,1,a);
            }
            if (i == c) {
                MGuiGraphics.blit( guiGraphics,a2, xx, yy, 0, 0, 9, 9, 9, 9,1, 1,1,a);
            }
            if (i == d) {
                MGuiGraphics.blit( guiGraphics,a1, xx, yy, 0, 0, 9, 9, 9, 9,1, 1,1,a);
            }
        }
    }
    @Unique
    private float cI1_21_9$showAlpha = 255;
    @Unique
    private float cI1_21_9$lightAmout = 0;

    @Unique
    private  void cI1_21_9$renderArmor(GuiGraphics guiGraphics, Player player, int y, int heartRows, int height, int x) {
        float is = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
        int i = (int) is;
        if (i > 0) {
            int hurtTime = player.hurtTime;
            if (hurtTime > 0) {
                cI1_21_9$showAlpha = 255;
            }
            if (i>=player.getAttributeValue(AttReg.hyperplasia)){
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
            float alpha = cI1_21_9$showAlpha;
            float light = Math.min(0.45f,cI1_21_9$lightAmout);
            if (cI1_21_9$showAlpha < 0) {
                cI1_21_9$showAlpha = 0;
            }


            int yy = y - (heartRows - 1) * height - 10;

            ResourceLocation a1 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_1.png");
            ResourceLocation a2 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_2.png");
            ResourceLocation a3 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_3.png");
            ResourceLocation a4 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_4.png");


            if (i <= 20) {
                for (int offset = 0; offset < 5; offset++) {
                    cI1_21_9$drawA1234((int) i, x, guiGraphics, yy, a1, a2, a3, a4,

                            1 + offset * 4, 2 + offset * 4,
                            3 + offset * 4, 4 + offset * 4,

                            offset,alpha/255f,light);
                }
            }else {
                for (int j = 0; j < 5; j++) {
                    MGuiGraphics.blit(guiGraphics, a1,
                            (x) + j * 8- 1, yy,
                            0, 0, 9, 9, 9, 9,
                            1,1,1,alpha/255f);
                }

                guiGraphics.drawString(Minecraft.getInstance().font,String.valueOf((int) i/4),(x) + 6 * 8- 3,yy ,
                        Light.ARGB.color((int) alpha,255,100,100));
            }


        }
    }


    @Unique
    private float cI1_21_9$showAlphaShadow = 255;
    @Unique
    private float cI1_21_9$lightAmoutShadow = 0;


    @Unique
    private float cI1_21_1$blackShadowAlpha = 1;
    @Unique
    private float cI1_21_1$blackHurtOfSize = 1;
    @Unique
    private void cI1_21_1$renderBoomBlackShadow(GuiGraphics guiGraphics, Player player, int x, int y,float alpha,float size){
        var pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(x,y,0);
        pose.scale(size,size,size);
        MGuiGraphicsCI_Life.blit(guiGraphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/shadow_black_1.png"),
                0, 0,
                0, 0, 12, 12, 12, 12,
                1, 1, 1, alpha);
        pose.popPose();
    }

    @Unique
    private void cI1_21_9$renderShadowBlackArmor(GuiGraphics guiGraphics, Player player, int y, int heartRows, int height, int x) {
        float is = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
        int i = (int) is;
        if (i > 0) {
            int hurtTime = player.hurtTime;
            if (hurtTime > 0) {
                cI1_21_9$showAlphaShadow = 255;
                cI1_21_1$blackHurtOfSize = 0.5f;
            }
            if (cI1_21_1$blackHurtOfSize < 1) {
                cI1_21_1$blackHurtOfSize += 0.025f;
            }
            if (i>=player.getAttributeValue(AttReg.shadow_shield)){
                if (hurtTime <= 0) {
                    if (cI1_21_9$lightAmoutShadow >=1) {
                        if (cI1_21_9$showAlphaShadow > 0) {
                            cI1_21_9$showAlphaShadow -= 2.5f;
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
            if (alpha <= 0) {
                return;
            }
            if (cI1_21_9$showAlphaShadow < 0) {
                cI1_21_9$showAlphaShadow = 0;
            }
            RenderSystem.enableBlend();
            int j = y - (heartRows - 1) * height - 10;
            int maxIcons = (int) Math.min(i, 3);

            for (int k = 0; k < maxIcons; k++) {
                int l = x + k * 8;
                if (k * 2 + 1 < i) {
                    MGuiGraphicsCI_Life.blit(guiGraphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/shadow_black_1.png"),
                            l, j,
                            0, 0, 12, 12, 12, 12,
                            1, 1, 1, alpha/255f);
                }
                if (k * 2 + 1 == i) {
                    MGuiGraphicsCI_Life.blit(guiGraphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/shadow_black_2.png"),
                            l, j,
                            0, 0, 12, 12, 12, 12,
                            1, 1, 1, alpha/255f);
                }

            }
            if (i > 3){
                guiGraphics.drawString(Minecraft.getInstance().font,String.valueOf((int) i),(x) + 4 * 8- 3,j ,
                        Light.ARGB.color(alpha,100,50,255));
            }
        }

    }







    @Unique
    private float cI1_21_9$showAlpha_chaosWinds = 255;
    @Unique
    private float cI1_21_9$lightAmout_chaosWinds = 0;

    @Unique
    private  void cI1_21_9$render_chaosWinds(GuiGraphics guiGraphics, Player player, int y, int heartRows, int height, int x) {
        float is = player.getData(AttReg.chaosWinds);
        int i = (int) is;
        if (i > 0) {
            int hurtTime = player.hurtTime;
            if (hurtTime > 0) {
                cI1_21_9$showAlpha_chaosWinds = 255;
            }
            if (i>=player.getAttributeValue(AttReg.chaos_armor)){
                if (hurtTime <= 0) {
                    if (cI1_21_9$lightAmout_chaosWinds >=0.45f) {
                        if (cI1_21_9$showAlpha_chaosWinds > 0) {
                            cI1_21_9$showAlpha_chaosWinds -= 2.5f;
                        }
                    }else {
                        cI1_21_9$lightAmout_chaosWinds += 0.0125f;
                    }
                }else {
                    cI1_21_9$lightAmout_chaosWinds = 0;
                }
            }else {
                cI1_21_9$lightAmout_chaosWinds = 0;
            }
            float alpha = cI1_21_9$showAlpha_chaosWinds;
            float light = Math.min(0.45f,cI1_21_9$lightAmout_chaosWinds);
            if (cI1_21_9$showAlpha_chaosWinds < 0) {
                cI1_21_9$showAlpha_chaosWinds = 0;
            }


            int yy = y - (heartRows - 1) * height - 10;

            ResourceLocation a1 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/chaos_wind_1.png");
            ResourceLocation a2 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/chaos_wind_2.png");
            ResourceLocation a3 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/chaos_wind_3.png");
            ResourceLocation a4 = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/chaos_wind_4.png");


            if (i <= 20) {
                for (int offset = 0; offset < 5; offset++) {
                    cI1_21_9$drawA1234Chaos((int) i, x, guiGraphics, yy, a1, a2, a3, a4,

                            1 + offset * 4, 2 + offset * 4,
                            3 + offset * 4, 4 + offset * 4,

                            offset,alpha/255f,light);
                }
            }else {
                for (int j = 0; j < 5; j++) {
                    MGuiGraphics.blit(guiGraphics, a1,
                            (x) + j * 8- 1, yy,
                            0, 0, 9, 9, 9, 9,
                            1,1,1,alpha/255f);
                }

                guiGraphics.drawString(Minecraft.getInstance().font,String.valueOf((int) i/4),(x) + 6 * 8- 3,yy ,
                        Light.ARGB.color((int) alpha,200,50,200));
            }


        }
    }
    @Unique
    private void cI1_21_9$drawA1234Chaos(int i,int x,GuiGraphics guiGraphics,int yy,
                                         ResourceLocation a1,
                                         ResourceLocation a2,
                                         ResourceLocation a3,
                                         ResourceLocation a4,
                                         int aa,
                                         int b,
                                         int c,
                                         int d,

                                         int offset,float a,float light
    ){
        if (i > 0) {
            int xx = (x) + offset * 8- 1;
            if (i > aa + 3) {
                MGuiGraphics.blit(guiGraphics, a1,
                        9 + ((x) + (offset - 1) * 8- 1), yy, 0, 0, 9, 9, 9, 9, 1, 1, 1, a);
            }
            if (i == aa) {
                MGuiGraphics.blit( guiGraphics,a4, xx, yy, 0, 0, 9, 9, 9, 9,1, 1,1,a);
            }
            if (i == b) {
                MGuiGraphics.blit(guiGraphics, a3, xx, yy, 0, 0, 9, 9, 9, 9,1, 1,1,a);
            }
            if (i == c) {
                MGuiGraphics.blit( guiGraphics,a2, xx, yy, 0, 0, 9, 9, 9, 9,1, 1,1,a);
            }
            if (i == d) {
                MGuiGraphics.blit( guiGraphics,a1, xx, yy, 0, 0, 9, 9, 9, 9,1, 1,1,a);
            }
        }
    }
}
