package com.ytgld.chest_item.items.memory.tooltip;

import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Matrix3x2fStack;

public class ImageTooltip implements ClientTooltipComponent, TooltipComponent {
    private final MemoryBase.BaseTooltip targetItem;
    public ImageTooltip(MemoryBase.BaseTooltip bBaseTooltip) {
        this.targetItem = bBaseTooltip;
    }
    @Override
    public int getHeight(Font font) {
        return 48;
    }

    @Override
    public int getWidth(Font font) {
        return 32;
    }

    @Override
    public void extractImage(Font font, int x, int y, int width, int height, net.minecraft.client.gui.GuiGraphicsExtractor guiGraphics) {
        Matrix3x2fStack stack = guiGraphics.pose();
        stack.pushMatrix();
        stack.translate(x, y + 8);
        stack.scale(2, 2);
        if (guiGraphics instanceof IGuiGraphics iGuiGraphics) {
            iGuiGraphics.cI1_21_11$stringBlack(0,0);
        }
        guiGraphics.item(targetItem.getDefaultInstance(),0,0);
        stack.popMatrix();
    }
}
