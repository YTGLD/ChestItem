package com.ytgld.chest_item.other;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.TheMemoryDataHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
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
            int guiTop = (this.height - this.imageHeight) / 2 + 20 * i;
            guiGraphics.renderItem(MemoryBase.getMemoryItem(player).get(i), guiLeft, guiTop);
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

                    guiGraphics.renderTooltip(
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
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, myIdentifier, i, j, 0.0F, 0.0F, 256, this.containerRows * 18 + 17, 256, 256);
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, myIdentifier, i, j + this.containerRows * 18 + 17, 0.0F, 126.0F, 256, 96, 256, 256);

    }
}

