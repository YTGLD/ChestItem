package com.ytgld.chest_item.items.memory.tooltip;

import com.ytgld.chest_item.items.memory.MemoryBase;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Matrix3x2fStack;

public class BigTooltip implements ClientTooltipComponent, TooltipComponent {
    private final MemoryBase.BaseTooltip baseTooltip;
    private final int xTTT;
    public BigTooltip(MemoryBase.BaseTooltip bBaseTooltip) {
        this.baseTooltip = bBaseTooltip;
        String text = baseTooltip.doTextOne().getString();
        int length = text.length();
        xTTT = length * 14;
    }
    @Override
    public int getHeight(Font font) {
        return 16;
    }

    @Override
    public int getWidth(Font font) {
        return xTTT;
    }

    @Override
    public void extractImage(Font font, int x, int y, int width, int height, net.minecraft.client.gui.GuiGraphicsExtractor guiGraphics) {
        Matrix3x2fStack stack = guiGraphics.pose();
        stack.pushMatrix();
        stack.translate(x, y);
        stack.scale(1.35f, 1.35f);

        String text = baseTooltip.doTextOne().getString();
        int baseColor = baseTooltip.color();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            float ratio = i / (float)(length - 1);
            int a = (int)((((baseColor >> 24) & 0xFF)));
            int r = (int)((((baseColor >> 16) & 0xFF) * (1 - ratio)) + (255 * ratio));
            int g = (int)((((baseColor >> 8) & 0xFF)));
            int b = (int)((((baseColor) & 0xFF) * (1 - ratio)) + (255 * ratio));
            int color =(a << 24) | (r << 16) | (g << 8) | b;
            guiGraphics.centeredText(font,
                    Component.literal(String.valueOf(c)), i * font.width(String.valueOf(c)), 0, color);
        }


//        if (guiGraphics instanceof IGuiGraphics iGuiGraphics) {
//            iGuiGraphics.cI1_21_11$stringBlack(baseTooltip.getDefaultInstance(),5, 12);
//            guiGraphics.renderItem(baseTooltip.getDefaultInstance(),5,12);
//        }
        stack.popMatrix();
    }
}
