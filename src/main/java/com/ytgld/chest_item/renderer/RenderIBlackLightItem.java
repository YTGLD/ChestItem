package com.ytgld.chest_item.renderer;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.joml.Matrix3x2fStack;

import java.util.HashMap;
import java.util.Map;

public class RenderIBlackLightItem {
    private static final Map<IBlackLight, AlphaItem> itemStackIntegerHashMap = new HashMap<>();
    private static int time = 0;
    public static void clientTick(ClientTickEvent.Pre event){
        time++;
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

    public static void renderItem(GuiGraphicsExtractor guiGraphics, Matrix3x2fStack pose, ItemStack stack, int x, int y, int seed) {
        if (stack.getItem() instanceof IBlackLight item) {
            if (!itemStackIntegerHashMap.containsKey(item)) {
                itemStackIntegerHashMap.put(item, new AlphaItem(0, true));
            }
            itemStackIntegerHashMap.get(item).setLook(true);
            addBlackLight(guiGraphics, pose, item, x, y, seed);
        }
    }

    private static void  addBlackLight(GuiGraphicsExtractor guiGraphics, Matrix3x2fStack pose, IBlackLight item, int x, int y, int seed){
        int a = (int) (itemStackIntegerHashMap.get(item).alpha / 1.5f);
        int color = item.colorBlack().color();
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        Identifier base = item.blackFire();
        Identifier star = item.blackStar();
        float stronger = item.stronger();
        float timeBase = time / 75F;
        RenderPipeline renderPipeline = item.colorBlack().renderPipeline();
        addCom((int) (48 * stronger),timeBase,Light.ARGB.color((int) (a / 2f), r, g, b), guiGraphics, pose, base, x, y, seed,renderPipeline);
        addCom((int) (32 * stronger),timeBase,Light.ARGB.color(a, r, g, b), guiGraphics, pose, star, x, y, seed,renderPipeline);
        addCom((int) (24 * stronger),-timeBase * 2  + (float)Math.PI / 2,
                Light.ARGB.color(a, r * 2, g * 2, b * 2), guiGraphics, pose, star, x, y, seed,renderPipeline);
    }

    private static void addCom(int size, float time , int color,
                               GuiGraphicsExtractor guiGraphics,
                               Matrix3x2fStack pose, Identifier fire,
                               int x, int y, int seed, RenderPipeline renderPipeline){
        pose.pushMatrix();
        pose.translate(8,8);
        pose.pushMatrix();
        pose.translate(x, y);
        pose.rotate(time + seed);
        pose.translate(-x, -y);
        guiGraphics.blit(renderPipeline, fire,
                (int) (x - size / 2f), (int) (y - size /2f), 0, 0,
                size, size, size, size,
                color);
        pose.popMatrix();

        pose.popMatrix();
    }

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
