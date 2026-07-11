package com.ytgld.chest_item.items.evil_mother.decay;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Vector2f;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

public interface IDecay extends IEvil {
    @Override
    default boolean isDecay() {
        return true;
    }

    @Override
    default int theColor() {
        return Light.ARGB.color(255,150,150,90);
    }

    class ShowBackAndFarm{
        public static void renderBack(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height) {
            int i = x - 3 - 9;
            int j = y - 3 - 9;
            int k = width + 3 + 3 + 18;
            int l = height + 3 + 3 + 18;
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                    "tooltip/evil_mother/decay/frame"), i, j, k, l);
            guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                    "tooltip/evil_mother/decay/background"), i, j, k, l);
        }
        public static RandomSource random = RandomSource.create();
        public static void renderTooltip(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int z) {
            // 左上角
            int topLeftX = x - 3 - 9+2;
            int topLeftY = y - 3 - 9;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -2);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/evil_mother/tool_0_0.png"), topLeftX, topLeftY, 0, 0,48, 48, 48, 48,0xffffffff);
            guiGraphics.pose().popMatrix();

            // 中间位置
            int middleX = x + (width - 48) / 2;
            int middleY = y - 3 - 6;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -7);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "tooltip/evil_mother/tool_middle_0"),48,48, 0, 0,  middleX, middleY, 48, 48);
            guiGraphics.pose().popMatrix();

            //中下
            int xXX = x + (width - 48) / 2;
            int yYY =  y + height + 3 - 9 + 4;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, 0);
            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "tooltip/evil_mother/tool_down_0"),48,48, 0, 0,  xXX, yYY, 48, 48);
            guiGraphics.pose().popMatrix();

            // 右上角
            int topRightX = x + width + 3 - 48+6;
            int topRightY = y - 3 - 9;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -2);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/evil_mother/tool_0_1.png"), topRightX, topRightY, 0, 0,48, 48, 48, 48,0xffffffff);
            guiGraphics.pose().popMatrix();

        }
    }
}
