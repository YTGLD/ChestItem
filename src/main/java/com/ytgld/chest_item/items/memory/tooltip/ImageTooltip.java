package com.ytgld.chest_item.items.memory.tooltip;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;

public class ImageTooltip implements ClientTooltipComponent, TooltipComponent {
    private final MemoryBase.BaseTooltip targetItem;
    public ImageTooltip(MemoryBase.BaseTooltip bBaseTooltip) {
        this.targetItem = bBaseTooltip;
    }
    @Override
    public int getHeight(Font font) {
        return 48;
    }

    @Override
    public int getWidth(Font font) {
        return 32;
    }

    @Override
    public void extractImage(Font font, int x, int y, int width, int height, net.minecraft.client.gui.GuiGraphicsExtractor guiGraphics) {
        float time = EventMain.time / 10f;
        Matrix3x2fStack stack = guiGraphics.pose();
        stack.pushMatrix();
        stack.translate(x, y + 8);
        stack.scale(2, 2);
        for (int i = 0; i < 16; i++) {
            BlackParticlesAdd.markSeen((int) (x + Math.cos(i + time) * 20) + 16, (int) (y + Math.sin(i + time) * 20) + 24,
                    new BlackKey.ImageColorAndRenderPipeline(8,new BlackKey.ColorImage(255,20,5,5),
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png"), MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction,
                    new Vector2f(),new Vector2f(0,0),new Vector2f()));

        }

        guiGraphics.item(targetItem.getDefaultInstance(),0,0);
        stack.popMatrix();
    }
}
