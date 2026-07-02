package com.ytgld.chest_item.renderer.gui_particles;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

import java.util.Map;
public class BlackParticlesRenderer {

    private static final Identifier BASE =
            Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/black.png");

    public static void onRenderGui(GuiGraphicsExtractor guiGraphics ,Matrix3x2fStack stack) {
        for (Map.Entry<BlackKey, BlackState> entry : BlackParticlesAdd.all().entrySet()) {
            BlackState state = entry.getValue();
            stack.pushMatrix();
            addBlackLight(guiGraphics,stack, state.alpha,state.screenX,state.screenY,0);
            render(
                    guiGraphics,
                    stack,
                    state,
                    state.screenX - 8,
                    state.screenY - 8,
                    (int) (32 * ((float)BlackParticlesAdd.KEEP_ALIVE / (float)BlackParticlesAdd.all().size()))
            );
            stack.popMatrix();
        }
    }

    public static void render(
            GuiGraphicsExtractor gui,
            Matrix3x2fStack pose,
            BlackState state,
            int x,
            int y,int size
    ) {
        int a = state.alpha;
        int r = 20, g = 0, b = 10;

        draw(gui, pose, BASE, a, r, g, b, x,y,size);
    }

    private static void draw(
            GuiGraphicsExtractor gui,
            Matrix3x2fStack pose,
            Identifier base,
            int a,
            int r,
            int g,
            int b,
            int x,
            int y,int size
    ) {
        int color = Light.ARGB.color(Math.min(20,a), r, g, b);

        pose.pushMatrix();

        gui.blit(
                MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction,
                base,
                x, y,
                0, 0,
                size, size,
                size, size,
                color
        );

        pose.popMatrix();
    }
    private static void  addBlackLight(GuiGraphicsExtractor guiGraphics,Matrix3x2fStack pose, int alpha,int x, int y,int seed){
        int r = 20;
        int g = 0;
        int b = 10;
        float timeBase = EventMain.time / 75F;
        addCom(32,timeBase,Light.ARGB.color(alpha, r, g, b), guiGraphics, pose,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/ci_star.png"), x, y, seed);
        addCom(18,-timeBase * 2  + (float)Math.PI / 2  ,Light.ARGB.color(alpha, r, g, b), guiGraphics, pose,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/ci_star.png"), x, y, seed);
        addCom(18,-timeBase * 2  + (float)Math.PI / 8  ,Light.ARGB.color(alpha, r, g, b), guiGraphics, pose,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/ci_star.png"), x, y, seed);
    }

    private static void addCom(int size,float time , int color,GuiGraphicsExtractor guiGraphics,Matrix3x2fStack pose, Identifier fire, int x, int y,int seed){
        pose.pushMatrix();
        pose.translate(8,8);
        pose.pushMatrix();
        pose.translate(x, y);
        pose.rotate(time);
        pose.translate(-x, -y);
        guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction, fire,
                (int) (x - size / 2f), (int) (y - size /2f), 0, 0,
                size, size, size, size,
                color);
        pose.popMatrix();

        pose.popMatrix();
    }

}