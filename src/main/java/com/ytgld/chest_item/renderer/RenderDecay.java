package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class RenderDecay {
    public static void renderItem(GuiGraphicsExtractor guiGraphicsExtractor, ItemStack stack, int x, int y) {
        Identifier itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        Identifier texId = Identifier.fromNamespaceAndPath(itemId.getNamespace(),
                "textures/item/" + itemId.getPath() + ".png");
        if (stack.get(DataReg.attributeType) != null) {
            guiGraphicsExtractor.blit(MRender.RenderPs.GUI_TEXTURED, texId, x - 2, y - 2, 0, 0, 20, 20, 20, 20, Light.ARGB.color(255, 150, 255, 200));
        }
    }
}
