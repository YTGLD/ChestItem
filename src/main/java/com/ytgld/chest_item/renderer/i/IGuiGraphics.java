package com.ytgld.chest_item.renderer.i;

import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IGuiGraphics {
    void chest_item$addW(ItemStack stack);
    GuiRenderState cI1_21_11$guiRenderState();

    void cI1_21_11$stringBlack(ItemStack stack, int x, int y);

}
