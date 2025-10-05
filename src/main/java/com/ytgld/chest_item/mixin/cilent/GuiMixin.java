package com.ytgld.chest_item.mixin.cilent;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.RendererFarm;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix3x2fStack;
import org.joml.Vector3f;
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

    @Shadow @Final private static ResourceLocation ARMOR_FULL_SPRITE;

    @Shadow @Final private static ResourceLocation ARMOR_HALF_SPRITE;

    @Shadow @Final private static ResourceLocation ARMOR_EMPTY_SPRITE;

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
            if (i > 0) {
                this.leftHeight += 20;
            }
        }

    }
    @Unique
    private  void cI1_21_9$renderArmor(GuiGraphics guiGraphics, Player player, int y, int heartRows, int height, int x) {
        float i = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
        if (i > 0) {
            int a;
            a = 80;
            Matrix3x2fStack pose = guiGraphics.pose();
            float age = player.tickCount/1.5f;

            int yy = y - (heartRows - 1) * height - 10;
            for (int k = 0; k < 10; k++) {
                int xx = (x + k * 8) - 1;
                int centerX = xx + 1;
                int centerY = yy + 1;
                int radius = 1;
                if (k * 2 + 1 < i) {
                    for (int j = 0; j < 8; j++) {
                        float s = (float) Math.sin(age*j)/10f;
                        double angle = 2 * Math.PI * j / 8;
                        int newX = (int) (centerX + Math.cos(angle) * radius);
                        int newY = (int) (centerY + Math.sin(angle) * radius);

                        pose.pushMatrix();
                        if (j==0||j==1) {
                            pose.translateLocal(s, s);
                        }else if (j==2||j==3){
                            pose.translateLocal(s, -s);
                        }if (j==4||j==5){
                            pose.translateLocal(-s,s);
                        }if (j==6||j==7){
                            pose.translateLocal(-s, -s);
                        }
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_1.png"),
                                newX, newY, 0, 0, 9, 9, 9, 9, Light.ARGB.color(a, 255, 255, 255));
                        pose.popMatrix();
                    }
                }
                if (k * 2 + 1 == i) {
                    for (int j = 0; j < 8; j++) {
                        float s = (float) Math.sin(age*j)/10f;
                        double angle = 2 * Math.PI * j / 8;
                        int newX = (int) (centerX + Math.cos(angle) * radius);
                        int newY = (int) (centerY + Math.sin(angle) * radius);

                        pose.pushMatrix();
                        if (j==0||j==1) {
                            pose.translateLocal(s, s);
                        }else if (j==2||j==3){
                            pose.translateLocal(s, -s);
                        }if (j==4||j==5){
                            pose.translateLocal(-s,s);
                        }if (j==6||j==7){
                            pose.translateLocal(-s, -s);
                        }
                        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_2.png"),
                                newX, newY, 0, 0, 9, 9, 9, 9, Light.ARGB.color(a, 255, 255, 255));
                        pose.popMatrix();
                    }
                }
            }

        }
    }


}
