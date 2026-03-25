package com.ytgld.chest_item.renderer.i;

import net.minecraft.world.item.ItemStack;

public interface IGuiGraphics {
    void chest_item$addW(ItemStack stack);
    net.minecraft.client.renderer.state.gui.GuiRenderState cI1_21_11$guiRenderState();
    void cI1_21_11$stringBlack(ItemStack stack, int x, int y);
}
