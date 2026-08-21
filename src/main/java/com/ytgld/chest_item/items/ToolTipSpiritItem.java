package com.ytgld.chest_item.items;

import com.ytgld.chest_item.other.SetSoulData;import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
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
    public int getHeight(Font font) {
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

    public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
        Set<Item> getAll = SetSoulData.getAllSpirit(stack);
        int offset = -1;
        graphics.pose().pushMatrix();
        if (!getAll.isEmpty()) {
            for (int j = 0; j < getAll.size(); j++) {
                if (!getAll.stream().toList().get(j).getDefaultInstance().isEmpty()) {
                    offset++;
                    graphics.item(getAll.stream().toList().get(j).getDefaultInstance(), x + offset * 16, y);
                    int io = setSoulData.soulMap().get(BuiltInRegistries.ITEM.getKey(getAll.stream().toList().get(j)).toString());
                    graphics.text(Minecraft.getInstance().font, Component.literal(String.valueOf(io)),
                            x + offset * 16, y + 12, 0xffffffff, true);
                }
            }
        }
        graphics.pose().popMatrix();
    }
}

