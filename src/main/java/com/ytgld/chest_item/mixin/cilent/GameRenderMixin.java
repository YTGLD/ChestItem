package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.renderer.IGameRenderer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameRenderer.class)
public class GameRenderMixin implements IGameRenderer {
    @Shadow @Final private RenderBuffers renderBuffers;

    @Override
    public RenderBuffers chest_item$r() {
        return this.renderBuffers;
    }
}
