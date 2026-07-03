package com.ytgld.chest_item.other;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.TheMemoryDataHandler;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedDataHandler;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ChestMenuScreen extends AbstractContainerScreen<ChestItemMenu> {
    private static final Identifier CONTAINER_BACKGROUND = Identifier.withDefaultNamespace(
            "textures/gui/container/generic_54.png");


    private static final Identifier myIdentifier = Identifier.fromNamespaceAndPath(Chestitem.MODID,
            "textures/gui/container/generic.png");
    private final int containerRows;
    private final Player player;

    public ChestMenuScreen(ChestItemMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        player = playerInventory.player;
        this.containerRows = menu.getRowCount();
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void renderMemory(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick){
        Set<String> strings = player.getData(TheMemoryDataHandler.mStringSetData);
        if (strings.isEmpty()) {
            return;
        }
        if (MemoryBase.getMemoryItem(player).isEmpty()) {
            return;
        }
        for (int i = 0; i < strings.size(); i++) {
            int guiLeft = (this.width - this.imageWidth) / 2 - 24;
            int guiTop = (this.height - this.imageHeight) / 2 + 20 * i;
            guiGraphics.item(MemoryBase.getMemoryItem(player).get(i), guiLeft, guiTop);


            int appleSize = 16;
            ItemStack itemstack = MemoryBase.getMemoryItem(player).get(i);
            if (mouseX >= guiLeft && mouseX < guiLeft + appleSize &&
                    mouseY >= guiTop && mouseY < guiTop + appleSize) {
                if (!itemstack.isEmpty()) {
                    Optional<TooltipComponent> image = itemstack.getTooltipImage();

                    List<Component> lines = Screen.getTooltipFromItem(minecraft, itemstack);

                    List<ClientTooltipComponent> components = new ArrayList<>();
                    image.ifPresent(img -> components.add(ClientTooltipComponent.create(img)));


                    for (Component line : lines) {
                        components.add(ClientTooltipComponent.create(line.getVisualOrderText()));
                    }

                    guiGraphics.tooltip(
                            font,
                            components,
                            mouseX,
                            mouseY,
                            DefaultTooltipPositioner.INSTANCE,
                            null,
                            itemstack
                    );
                }
            }
        }
    }
    public void renderReinforced(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick){
        Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
        if (strings.isEmpty()) {
            return;
        }
        if (ReinforcedBaseItem.getItems(player).isEmpty()) {
            return;
        }
        for (int i = 0; i < strings.size(); i++) {
            int guiLeft = (this.width - this.imageWidth) / 2 - 8;
            int guiTop = (this.height - this.imageHeight) / 2 + 20 * i;
            guiGraphics.item(ReinforcedBaseItem.getItems(player).get(i).getDefaultInstance(), guiLeft, guiTop);
            int appleSize = 16;
            ItemStack itemstack = ReinforcedBaseItem.getItems(player).get(i).getDefaultInstance();
            if (mouseX >= guiLeft && mouseX < guiLeft + appleSize &&
                    mouseY >= guiTop && mouseY < guiTop + appleSize) {
                if (!itemstack.isEmpty()) {
                    Optional<TooltipComponent> image = itemstack.getTooltipImage();

                    List<Component> lines = Screen.getTooltipFromItem(minecraft, itemstack);

                    List<ClientTooltipComponent> components = new ArrayList<>();
                    image.ifPresent(img -> components.add(ClientTooltipComponent.create(img)));


                    for (Component line : lines) {
                        components.add(ClientTooltipComponent.create(line.getVisualOrderText()));
                    }

                    guiGraphics.tooltip(
                            font,
                            components,
                            mouseX,
                            mouseY,
                            DefaultTooltipPositioner.INSTANCE,
                            null,
                            itemstack
                    );
                }
            }
        }
    }
    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(guiGraphics, mouseX, mouseY,a );
        this.extractTooltip(guiGraphics, mouseX, mouseY);
        renderReinforced(guiGraphics, mouseX, mouseY, a);
        renderMemory(guiGraphics, mouseX, mouseY, a);
    }
    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        super.extractBackground(guiGraphics, mouseX, mouseY, a);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, myIdentifier, i, j, 0.0F, 0.0F, 256, this.containerRows * 18 + 17, 256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, myIdentifier, i, j + this.containerRows * 18 + 17, 0.0F, 126.0F, 256, 96, 256, 256);

    }
}

