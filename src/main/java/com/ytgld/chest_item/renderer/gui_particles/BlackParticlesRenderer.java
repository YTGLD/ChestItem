package com.ytgld.chest_item.renderer.gui_particles;

import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

import java.util.Map;
public class BlackParticlesRenderer {
    public static void onRenderGui(GuiGraphicsExtractor guiGraphics ,Matrix3x2fStack stack) {
        for (Map.Entry<BlackKey, BlackState> entry : BlackParticlesAdd.all().entrySet()) {
            BlackState state = entry.getValue();
            stack.pushMatrix();
            addBlackLight(guiGraphics,stack, state.alpha,state.screenX,state.screenY,state);
            render(
                    guiGraphics,
                    stack,
                    state,
                    state.screenX - 8,
                    state.screenY - 8,
                    state.imageColorAndRenderPipeline.size()
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
        draw(gui, pose, x,y,size,state);
    }

    private static void draw(
            GuiGraphicsExtractor gui,
            Matrix3x2fStack pose,
            int x,
            int y,
            int size,
            BlackState state

    ) {
        BlackKey.ColorImage colorImage = state.imageColorAndRenderPipeline.color();
        pose.pushMatrix();
        gui.blit(
                state.imageColorAndRenderPipeline.renderPipeline(),
                state.imageColorAndRenderPipeline.identifier(),
                x, y,
                0, 0,
                size, size, size, size,
                Light.ARGB.color(state.alpha,colorImage.r(),colorImage.g(),colorImage.b())
        );
        pose.popMatrix();
    }
    private static void  addBlackLight(GuiGraphicsExtractor guiGraphics,Matrix3x2fStack pose, int alpha,int x, int y,BlackState state){
        float timeBase = EventMain.time / 75F;
        addCom(timeBase, guiGraphics, pose, x, y,state);
    }

    private static void addCom(float time,GuiGraphicsExtractor guiGraphics,Matrix3x2fStack pose, int x, int y, BlackState state){
        int size = state.imageColorAndRenderPipeline.size();
        BlackKey.ColorImage color = state.imageColorAndRenderPipeline.color();
        Identifier identifier = state.imageColorAndRenderPipeline.identifier();
        pose.pushMatrix();
        pose.translate((float) size / 4, (float) size /4);
        pose.pushMatrix();
        pose.translate(x, y);
        pose.rotate(time);
        pose.translate(-x, -y);
        guiGraphics.blit(state.imageColorAndRenderPipeline.renderPipeline(),
                identifier,
                (int) (x - size / 2f), (int) (y - size /2f), 0, 0,
                size, size, size, size,
                Light.ARGB.color(state.alpha,color.r(),color.g(),color.b()));
        pose.popMatrix();

        pose.popMatrix();
    }

}