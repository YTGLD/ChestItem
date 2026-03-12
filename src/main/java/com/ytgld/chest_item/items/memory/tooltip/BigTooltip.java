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
    public BigTooltip(MemoryBase.BaseTooltip bBaseTooltip) {
        this.baseTooltip = bBaseTooltip;
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth(Font font) {
        return 18;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {
        PoseStack stack = guiGraphics.pose();
        stack.pushPose();
        stack.translate(x,y,0);
        stack.scale(1.35f,1.35f,0);
        guiGraphics.drawString(font, baseTooltip.doTextOne(), 0, 0, baseTooltip.color(), false);
        stack.popPose();
    }
}
