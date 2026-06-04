package com.ytgld.chest_item.mixin.outline;

import com.mojang.blaze3d.resource.CrossFrameResourcePool;
import com.ytgld.chest_item.renderer.outline.IGameRenderer;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GameRenderer.class)
public class GameRendererMixin implements IGameRenderer {
    @Shadow
    @Final
    private CrossFrameResourcePool resourcePool;

    @Override
    public CrossFrameResourcePool cI26_1Pre3$resourcePool() {
        return resourcePool;
    }
}
