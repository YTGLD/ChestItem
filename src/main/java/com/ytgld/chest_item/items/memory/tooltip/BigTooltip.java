package com.ytgld.chest_item.items.memory.tooltip;

import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.IBigTooltip;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Matrix3x2fStack;

public class BigTooltip implements ClientTooltipComponent, TooltipComponent {
    private final IBigTooltip baseTooltip;
    private final int xTTT;
    public BigTooltip(IBigTooltip bBaseTooltip) {
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
        int time = EventMain.time;

        stack.pushMatrix();
        stack.translate(x, y);
        stack.scale(1.35f, 1.35f);

        String text = baseTooltip.doTextOne().getString();
        int baseColor = baseTooltip.color();
        int length = text.length();

        float firstCharX = 0;
        float firstCharY = 0;
        float angle = time / 20f;
        stack.pushMatrix();
        stack.translate(firstCharX + 4, firstCharY);
        stack.translate(-(firstCharX + 4), -firstCharY);
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            float ratio = i / (float)(length - 1);
            int a = 255;
            int r = (int)((((baseColor >> 16) & 0xFF) * (1 - ratio)) + (255 * ratio) + Math.sin(angle ) * 40f);
            int g = (int)(((baseColor >> 8) & 0xFF)  + Math.sin(angle ) * 40f);
            int b = (int)((((baseColor) & 0xFF) * (1 - ratio)) + (255 * ratio) + Math.sin(angle ) * 40f);
            if (r > 255) {
                r = 255;
            }
            if (g > 255) {
                g = 255;
            }
            if (b > 255) {
                b = 255;
            }
            if (r < 0) {
                r = 0;
            }
            if (g < 0) {
                g = 0;
            }
            if (b < 0) {
                b = 0;
            }


            int color = (a << 24) | (r << 16) | (g << 8) | b;

            guiGraphics.centeredText(font, Component.literal(String.valueOf(c)), i * font.width(String.valueOf(c)) + 4, 0, color);
        }

        stack.popMatrix();

        stack.popMatrix();
    }
}
