package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class RenderEvilGift implements ClientTooltipComponent, TooltipComponent {
    private final ItemStack stack;
    private final IEvilGift iEvilGift;
    public RenderEvilGift(ItemStack stack,IEvilGift iEvilGift) {
        this.stack = stack;
        this.iEvilGift = iEvilGift;
    }

    @Override
    public int getHeight(Font font) {
        int a = 0;
        if (!iEvilGift.canHasEvilGift().isEmpty()) {
            a += 24;
        }
        return a;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return this.backgroundWidth();
    }

    private int backgroundWidth() {
        return iEvilGift.maxGiftNumber(stack) * 16;
    }

    public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
        int imageSize= 16;
        graphics.pose().pushMatrix();
        for (int j = 0; j < iEvilGift.maxGiftNumber(stack); j++) {

            graphics.blit(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                    "textures/evil_mother/back.png"),
                    x + j * 16, y ,
                    0, 0,
                    imageSize, imageSize, imageSize, imageSize);

        }
        HashSet<EvilGiftBase> strings = iEvilGift.theGiftBase(stack);
        if (!strings.isEmpty()) {
            for (int j = 0; j < strings.size(); j++) {
                graphics.blit(RenderPipelines.GUI_TEXTURED,
                        strings.stream().toList().get(j).image(),
                        x + j * 16, y ,
                        0, 0,
                        imageSize, imageSize, imageSize, imageSize);
            }
        }
        graphics.pose().popMatrix();
    }
}


