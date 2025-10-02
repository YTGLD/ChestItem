package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.WidgetTypes;
import com.ytgld.chest_item.renderer.i.IAdvancementWidget;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(AdvancementWidget.class)
public abstract class AdvancementWidgetMixin implements IAdvancementWidget {
    @Shadow @Final private int x;

    @Shadow @Final private int width;

    @Shadow @Final private AdvancementTab tab;

    @Shadow @Nullable private AdvancementProgress progress;

    @Shadow @Final private List<FormattedCharSequence> description;

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private int y;

    @Shadow @Final private DisplayInfo display;

    @Shadow @Final private List<AdvancementWidget> children;
    @Shadow @Final private List<FormattedCharSequence> titleLines;

    @Shadow protected abstract void drawMultilineText(GuiGraphics guiGraphics, List<FormattedCharSequence> text, int x, int y, int color);

    @Override
    public void seekingImmortals$draw(GuiGraphics guiGraphics, int x, int y) {
        if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
            float f = this.progress == null ? 0.0F : this.progress.getPercent();
            WidgetTypes advancementwidgettype;
            if (f >= 1.0F) {
                advancementwidgettype = WidgetTypes.OBTAINED;
            } else {
                advancementwidgettype = WidgetTypes.UNOBTAINED;
            }

            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, advancementwidgettype.frameSprite(this.display.getType()), x + this.x + 3, y + this.y, 26, 26);
            guiGraphics.renderFakeItem(this.display.getIcon(), x + this.x + 8, y + this.y + 5);
        }
        for (AdvancementWidget advancementwidget : this.children) {
            if (advancementwidget instanceof IAdvancementWidget advancementWidget) {
                advancementWidget.seekingImmortals$draw(guiGraphics, x, y);
            }
        }


    }

    @Override
    public void seekingImmortals$drawHover(GuiGraphics guiGraphics, int x, int y, float fade, int width, int height) {
        Font font = this.minecraft.font;
        int i = 9 * this.titleLines.size() + 9 + 8;
        int j = y + this.y + (26 - i) / 2;
        int k = j + i;
        int l = this.description.size() * 9;
        int i1 = 6 + l;
        boolean flag = width + x + this.x + this.width + 26 >= this.tab.getScreen().width;
        Component component = this.progress == null ? null : this.progress.getProgressText();
        int j1 = component == null ? 0 : font.width(component);
        boolean flag1 = k + i1 >= 113;
        float f = this.progress == null ? 0.0F : this.progress.getPercent();
        int k1 = Mth.floor(f * (float)this.width);
        WidgetTypes advancementwidgettype;
        WidgetTypes advancementwidgettype1;
        WidgetTypes advancementwidgettype2;
        if (f >= 1.0F) {
            k1 = this.width / 2;
            advancementwidgettype = WidgetTypes.OBTAINED;
            advancementwidgettype1 = WidgetTypes.OBTAINED;
            advancementwidgettype2 = WidgetTypes.OBTAINED;
        } else if (k1 < 2) {
            k1 = this.width / 2;
            advancementwidgettype = WidgetTypes.UNOBTAINED;
            advancementwidgettype1 = WidgetTypes.UNOBTAINED;
            advancementwidgettype2 = WidgetTypes.UNOBTAINED;
        } else if (k1 > this.width - 2) {
            k1 = this.width / 2;
            advancementwidgettype = WidgetTypes.OBTAINED;
            advancementwidgettype1 = WidgetTypes.OBTAINED;
            advancementwidgettype2 = WidgetTypes.UNOBTAINED;
        } else {
            advancementwidgettype = WidgetTypes.OBTAINED;
            advancementwidgettype1 = WidgetTypes.UNOBTAINED;
            advancementwidgettype2 = WidgetTypes.UNOBTAINED;
        }

        int l1 = this.width - k1;
        int i2;
        if (flag) {
            i2 = x + this.x - this.width + 26 + 6;
        } else {
            i2 = x + this.x;
        }

        int j2 = i + i1;
        if (!this.description.isEmpty()) {
            if (flag1) {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/box"), i2, k - j2, this.width, j2);
            } else {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"advancements/box"), i2, j, this.width, j2);
            }
        }

        if (advancementwidgettype != advancementwidgettype1) {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, advancementwidgettype.boxSprite(), 200, i, 0, 0, i2, j, k1, i);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, advancementwidgettype1.boxSprite(), 200, i, 200 - l1, 0, i2 + k1, j, l1, i);
        } else {
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, advancementwidgettype.boxSprite(), i2, j, this.width, i);
        }

        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, advancementwidgettype2.frameSprite(this.display.getType()), x + this.x + 3, y + this.y, 26, 26);
        int k2 = i2 + 5;
        if (flag) {
            this.drawMultilineText(guiGraphics, this.titleLines, k2, j + 9, -1);
            if (component != null) {
                guiGraphics.drawString(font, component, x + this.x - j1, j + 9, -1);
            }
        } else {
            this.drawMultilineText(guiGraphics, this.titleLines, x + this.x + 32, j + 9, -1);
            if (component != null) {
                guiGraphics.drawString(font, component, x + this.x + this.width - j1 - 5, j + 9, -1);
            }
        }

        if (flag1) {
            this.drawMultilineText(guiGraphics, this.description, k2, j - l + 1, -16711936);
        } else {
            this.drawMultilineText(guiGraphics, this.description, k2, k, -16711936);
        }

        guiGraphics.renderFakeItem(this.display.getIcon(), x + this.x + 8, y + this.y + 5);
    }

}
