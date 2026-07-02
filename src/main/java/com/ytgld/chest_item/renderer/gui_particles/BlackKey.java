package com.ytgld.chest_item.renderer.gui_particles;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.resources.Identifier;

public record BlackKey(int x, int y,ImageColorAndRenderPipeline imageColorAndRenderPipeline) {
    public record ImageColorAndRenderPipeline(int size, ColorImage color, Identifier identifier, RenderPipeline renderPipeline){}
    public record ColorImage(int r,int g ,int b ){}
}