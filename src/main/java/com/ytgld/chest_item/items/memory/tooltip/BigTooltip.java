package com.ytgld.chest_item.items.memory.tooltip;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.tip.an_element.extend.BlackSkill;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

import java.util.Map;

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
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth(Font font) {
        return xTTT;
    }
    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        PoseStack stack = guiGraphics.pose();
        stack.pushPose();
        stack.translate(x, y, 0);
        stack.scale(1.35f, 1.35f, 1);

        String text = baseTooltip.doTextOne().getString();
        int baseColor = baseTooltip.color();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);

            float ratio = i / (float)(length - 1);
            int r = (int)((((baseColor >> 16) & 0xFF) * (1 - ratio)) + (255 * ratio));
            int g = (int)((((baseColor >> 8) & 0xFF)));
            int b = (int)((((baseColor) & 0xFF) * (1 - ratio)) + (255 * ratio));
            int color = (r << 16) | (g << 8) | b;

            guiGraphics.drawString(font, String.valueOf(c), i * font.width(String.valueOf(c)), 0, color, false);
        }

        stack.popPose();
    }
}
