package com.ytgld.chest_item.other;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.TheMemoryDataHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ChestMenuScreen extends AbstractContainerScreen<ChestItemMenu> {
    private static final ResourceLocation myResourceLocation = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
            "textures/gui/container/generic.png");
    private final int containerRows;
    private final Player player;
    public ChestMenuScreen(ChestItemMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        player = playerInventory.player;
        this.containerRows = menu.getRowCount();
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        Set<String> strings = player.getData(TheMemoryDataHandler.mStringSetData);

        for (int i = 0; i < strings.size(); i++) {
            int guiLeft = (this.width - this.imageWidth) / 2 - 20;
            int guiTop = (this.height - this.imageHeight) / 2 + 10 * i;
            guiGraphics.renderItem(MemoryBase.getMemoryItem(player), guiLeft, guiTop);
            int appleSize = 16;
            if (mouseX >= guiLeft && mouseX < guiLeft + appleSize &&
                    mouseY >= guiTop && mouseY < guiTop + appleSize) {
                guiGraphics.renderTooltip(this.font, MemoryBase.getMemoryItem(player), mouseX, mouseY);
            }
        }
    }
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(myResourceLocation, i, j, 0.0F, 0.0F, 256, this.containerRows * 18 + 17, 256, 256);
        guiGraphics.blit(myResourceLocation, i, j + this.containerRows * 18 + 17, 0.0F, 126.0F, 256, 96, 256, 256);

    }
}

