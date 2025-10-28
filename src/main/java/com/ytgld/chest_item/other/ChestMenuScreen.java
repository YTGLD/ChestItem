package com.ytgld.chest_item.other;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ChestMenuScreen extends AbstractContainerScreen<ChestItemMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND = ResourceLocation.withDefaultNamespace(
            "textures/gui/container/generic_54.png");


    private static final ResourceLocation myResourceLocation = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
            "textures/gui/container/generic.png");
    private final int containerRows;

    public ChestMenuScreen(ChestItemMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.containerRows = menu.getRowCount();
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, myResourceLocation, i, j, 0.0F, 0.0F, 256, this.containerRows * 18 + 17, 256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, myResourceLocation, i, j + this.containerRows * 18 + 17, 0.0F, 126.0F, 256, 96, 256, 256);

    }
}

