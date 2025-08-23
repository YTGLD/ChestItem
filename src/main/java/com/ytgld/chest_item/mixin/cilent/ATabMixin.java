package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.i.IAdvancementWidget;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(AdvancementTab.class)
public abstract class ATabMixin {
    @Shadow @Final private AdvancementWidget root;

    @Shadow private double scrollX;

    @Shadow private double scrollY;

    @Shadow @Final private DisplayInfo display;

    @Shadow @Final private AdvancementNode rootNode;


    @Shadow @Final private Map<AdvancementHolder, AdvancementWidget> widgets;

    @Shadow private float fade;

    @Inject(at = @At("RETURN"), method = "drawContents(Lnet/minecraft/client/gui/GuiGraphics;II)V")
    public void drawContents(GuiGraphics guiGraphics, int x, int y, CallbackInfo ci) {
        if (this.rootNode.holder().id().equals(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "chest_item/root"))) {
            guiGraphics.enableScissor(x, y, x + 234, y + 113);
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate((float) x, (float) y);
            ResourceLocation resourcelocation = this.display.getBackground().map(ClientAsset::texturePath).orElse(TextureManager.INTENTIONAL_MISSING_TEXTURE);
            int i = Mth.floor(this.scrollX);
            int j = Mth.floor(this.scrollY);
            int k = i % 16;
            int l = j % 16;

            for (int i1 = -1; i1 <= 15; ++i1) {
                for (int j1 = -1; j1 <= 8; ++j1) {
                    guiGraphics.blit(RenderPipelines.GUI_TEXTURED, resourcelocation, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
                }
            }

            this.root.drawConnectivity(guiGraphics, i, j, true);
            this.root.drawConnectivity(guiGraphics, i, j, false);
            if (root instanceof IAdvancementWidget iAdvancementWidget) {
                iAdvancementWidget.seekingImmortals$draw(guiGraphics, i, j);
            }
            guiGraphics.pose().popMatrix();
            guiGraphics.disableScissor();
        }
    }

    @Inject(at = @At("RETURN"), method = "drawTooltips")
    public void drawTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY, int width, int height, CallbackInfo ci) {

        if (this.rootNode.holder().id().equals(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "chest_item/root"))) {

            guiGraphics.fill(0, 0, 234, 113, Mth.floor(this.fade * 255.0F) << 24);
            int i = Mth.floor(this.scrollX);
            int j = Mth.floor(this.scrollY);
            if (mouseX > 0 && mouseX < 234 && mouseY > 0 && mouseY < 113) {
                for (AdvancementWidget advancementwidget : this.widgets.values()) {
                    if (advancementwidget.isMouseOver(i, j, mouseX, mouseY)) {
                        if (advancementwidget instanceof IAdvancementWidget iAdvancementWidget) {
                            iAdvancementWidget.seekingImmortals$drawHover(guiGraphics, i, j, this.fade, width, height);
                            break;
                        }
                    }
                }
            }
        }
    }


}
