package com.ytgld.chest_item.items;

import com.ytgld.chest_item.crafting.SetSoulData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ToolTipSpiritItem implements ClientTooltipComponent, TooltipComponent {
    private final SetSoulData setSoulData;
    private final ItemStack stack;
    public ToolTipSpiritItem(SetSoulData setSoulData, ItemStack stack) {
        this.setSoulData = setSoulData;
        this.stack = stack;
    }
    @Override
    public int getHeight() {
        int a = 0;
        if (!setSoulData.soulMap().isEmpty()) {
            a += 24;
        }
        return a;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return this.backgroundWidth();
    }

    private int backgroundWidth() {
        return setSoulData.soulMap().size() * 16;
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        Set<Item> getAll = SetSoulData.getAllSpirit(stack);
        graphics.pose().pushPose();
        if (!getAll.isEmpty()) {
            for (int j = 0; j < getAll.size(); j++) {
                graphics.renderItem(getAll.stream().toList().get(j).getDefaultInstance(), x + j * 16, y);
                int io = setSoulData.soulMap().get(BuiltInRegistries.ITEM.getKey(getAll.stream().toList().get(j)).toString());
                graphics.drawString(Minecraft.getInstance().font, Component.literal(String.valueOf(io)),
                        x + j * 16, y + 12,0xffffffff,true);
            }
        }
        graphics.pose().popPose();
    }
}

