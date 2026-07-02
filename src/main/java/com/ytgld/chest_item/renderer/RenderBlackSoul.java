package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.black.celestial.EternalVows;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.joml.Matrix3x2fStack;

import java.util.HashMap;
import java.util.Map;

public class RenderBlackSoul {
    private static final Map<Item, AlphaItem> itemStackIntegerHashMap = new HashMap<>();

    public static void clientTick(ClientTickEvent.Pre event){
        for (AlphaItem alphaItem : itemStackIntegerHashMap.values()){
            if (alphaItem.look) {
                if (alphaItem.alpha < 200) {
                    alphaItem.setAlpha(alphaItem.alpha + 40);
                }
                alphaItem.setLook(false);
            }else {
                if (alphaItem.alpha > 0) {
                    alphaItem.setAlpha(alphaItem.alpha - 20);
                    if (alphaItem.alpha < 0) {
                        alphaItem.setAlpha(0);
                    }
                }
            }
        }
    }

    public static void renderItem(GuiGraphicsExtractor guiGraphics,Matrix3x2fStack pose, ItemStack stack, int x, int y,int seed) {
        if (stack.getItem() instanceof EternalVows celestial) {
            if (!itemStackIntegerHashMap.containsKey(celestial)) {
                itemStackIntegerHashMap.put(celestial, new AlphaItem(0, true));
            }
            itemStackIntegerHashMap.get(celestial).setLook(true);
            addBlackLight(guiGraphics, pose, celestial, x, y, seed);
            for (int i = 0; i < celestial.colorAndImage().size(); i++) {
                ColorAndImage colorAndImage = celestial.colorAndImage().get(i);
                Identifier image = colorAndImage.image;
                int color = colorAndImage.color;
                pose.pushMatrix();
                pose.translate(x, y);
                guiGraphics.blit(MRender.RenderPs.LiveTImageRenderPipe, image, 0, 0, 0, 0, 16, 16, 16, 16,
                        color);
                pose.popMatrix();
            }
        }
    }

    private static void  addBlackLight(GuiGraphicsExtractor guiGraphics,Matrix3x2fStack pose, EternalVows celestial,int x, int y,int seed){
        if (!ConfigC.config.RenderItemTooltip.get()) {
            return;
        }
        int r = 20;
        int g = 0;
        int b = 10;
        int a = (int) (itemStackIntegerHashMap.get(celestial).alpha / 1.5f);
        Identifier base = Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/shadow/black.png");
        float timeBase = EventMain.time / 150f;
        addCom(32,timeBase,Light.ARGB.color(Math.min(255,a), r, g, b), guiGraphics, pose, base, x, y, seed);
        addCom(32,timeBase,Light.ARGB.color(Math.min(255,a / 2), r, g, b), guiGraphics, pose, base, x, y, seed);
        addCom(32,timeBase,Light.ARGB.color(Math.min(255,a / 4), r, g, b), guiGraphics, pose, base, x, y, seed);

        addCom(32,timeBase,Light.ARGB.color(a, r, g, b), guiGraphics, pose, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/shadow/ci_star.png"), x, y, seed);
        addCom(18,-timeBase * 2  + (float)Math.PI / 2  ,Light.ARGB.color(a, r, g, b), guiGraphics, pose, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/shadow/ci_star.png"), x, y, seed);
        addCom(18,-timeBase * 2  + (float)Math.PI / 8  ,Light.ARGB.color(a, r, g, b), guiGraphics, pose, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/shadow/ci_star.png"), x, y, seed);
    }
    private static void addCom(int size,float time , int color,GuiGraphicsExtractor guiGraphics,Matrix3x2fStack pose, Identifier fire, int x, int y,int seed){
        RenderPipeline renderPipeline = MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction;
        pose.pushMatrix();
        pose.translate(8,8);
        pose.pushMatrix();
        pose.translate(x, y);
        pose.rotate(time);
        pose.translate(-x, -y);
        guiGraphics.blit(renderPipeline, fire,
                x - size/ 2, y - size /2, 0, 0,
                size, size, size, size,
                color);
        pose.popMatrix();

        pose.popMatrix();
    }

    public record ColorAndImage(int color , Identifier image){}
    private static class AlphaItem {
        public int alpha;
        public boolean look;
        public AlphaItem(int alpha,boolean look ){
            this.alpha = alpha;
            this.look = look;
        }

        public void setLook(boolean look) {
            this.look = look;
        }

        public void setAlpha(int a){
            this.alpha = a;
        }
    }
}
