package com.ytgld.chest_item.renderer.i;

import net.minecraft.client.gui.GuiGraphics;

public interface IAdvancementWidget {
    void CI$draw(GuiGraphics guiGraphics, int x, int y);

    void CI$drawHover(GuiGraphics guiGraphics, int x, int y, float fade, int width, int height);
}
