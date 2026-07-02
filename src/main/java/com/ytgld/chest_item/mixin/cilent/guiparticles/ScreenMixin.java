package com.ytgld.chest_item.mixin.cilent.guiparticles;


import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesRenderer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Vector2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractContainerEventHandler {
    @Unique
    private RandomSource chest26_2$source = RandomSource.create();
    @Inject(at = @At(value = "RETURN"),method = "extractRenderState")
    private void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
        BlackParticlesRenderer.onRenderGui(graphics,graphics.pose());
        BlackParticlesAdd.markSeen(mouseX,mouseY,new BlackKey.ImageColorAndRenderPipeline(24,new BlackKey.ColorImage(255,255,180,100),
                Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/withe.png"), MRender.RenderPs.GUI_TEXTURED,
                new Vector2f(),new Vector2f(0,-0.045f),new Vector2f()));


    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        for (int i = 0; i < 33; i++) {
            BlackParticlesAdd.markSeen((int) event.x(), (int) event.y(),new BlackKey.ImageColorAndRenderPipeline(16,new BlackKey.ColorImage(255,120,60,30),
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/withe.png"), MRender.RenderPs.GUI_TEXTURED,
                    new Vector2f(),new Vector2f((float) (Math.cos(i) / 5f), (float) (Math.sin(i) / 5f)),new Vector2f()));
        }
        return super.mouseClicked(event, doubleClick);
    }
}
