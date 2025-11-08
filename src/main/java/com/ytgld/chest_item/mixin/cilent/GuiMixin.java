package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.Profiler;
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
        if (player != null) {
            float i = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
            int l = p_283143_.guiWidth() / 2 - 91;
            Profiler.get().push("shadow_shield");
            cI1_21_9$renderShadowBlackArmor(p_283143_, player, p_283143_.guiHeight() - this.leftHeight + 20, 1, 0, l);
            Profiler.get().pop();
            if (i > 0) {
                this.leftHeight += 20;
            }
        }

    }
    @Unique
    private  void cI1_21_9$renderShadowBlackArmor(GuiGraphics guiGraphics, Player player, int y, int heartRows, int height, int x) {
        float i = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
        if (i > 0) {
            int a;
            a = 255;
            int yy = y - (heartRows - 1) * height - 10;
            for (int k = 0; k < 10; k++) {
                int xx = (x + k * 8) - 1;
                if (k * 2 + 1 < i) {
                    guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED_CI, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/shadow_black_1.png"),
                            xx, yy, 0, 0, 11, 11, 11, 11, Light.ARGB.color(a, 255, 255, 255));
                }
                if (k * 2 + 1 == i) {
                    guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED_CI, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/gui/shadow_black_2.png"),
                            xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(a, 255, 255, 255));
                }
            }

        }
    }

    @Unique
    private  void cI1_21_9$renderArmor(GuiGraphics guiGraphics, Player player, int y, int heartRows, int height, int x) {
        float i = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
        if (i > 0) {
            int a;
            a = 255;
            int yy = y - (heartRows - 1) * height - 10;
            for (int k = 0; k < 10; k++) {
                int xx = (x + k * 8) - 1;
                if (k * 2 + 1 < i) {
                    guiGraphics.blit(MRender.RenderPs.LightSlowness(false,0.135F), ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_1.png"),
                            xx, yy, 0, 0, 11, 11, 11, 11, Light.ARGB.color(a, 255, 255, 255));
                }
                if (k * 2 + 1 == i) {
                    guiGraphics.blit(MRender.RenderPs.LightSlowness(false,0.135F), ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/hyperplasia_2.png"),
                            xx, yy, 0, 0, 9, 9, 9, 9, Light.ARGB.color(a, 255, 255, 255));
                }
            }

        }
    }


}
