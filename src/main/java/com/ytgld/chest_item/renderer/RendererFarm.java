package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;

public record RendererFarm(Matrix3x2fStack pose, GuiRenderState guiRenderState, int color) {

    public void chest_item$blit(RenderPipeline pipeline, Identifier atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        this.chest_item$blit(pipeline, atlas, x, y, u, v, width, height, width, height, textureWidth, textureHeight);
    }


    public void chest_item$blit(RenderPipeline pipeline, Identifier atlas, int x, int y, float u, float v, int width, int height, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        this.chest_item$blit(pipeline, atlas, x, y, u, v, width, height, uWidth, vHeight, textureWidth, textureHeight, color);
    }


    public void chest_item$blit(RenderPipeline pipeline, Identifier atlas, int x, int y, float u, float v, int width, int height, int uWidth, int vHeight, int textureWidth, int textureHeight, int color) {
        this.chest_item$innerBlit(pipeline, atlas, x, x + width, y, y + height, (u + 0.0F) / (float) textureWidth, (u + (float) uWidth) / (float) textureWidth, (v + 0.0F) / (float) textureHeight, (v + (float) vHeight) / (float) textureHeight, color);
    }
    private void chest_item$innerBlit(RenderPipeline renderPipeline, Identifier location, int x0, int x1, int y0, int y1, float u0, float u1, float v0, float v1, int color) {
        AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(location);
        this.chest_item$submitBlit(renderPipeline, texture.getTextureView(), texture.getSampler(), x0, y0, x1, y1, u0, u1, v0, v1, color);
    }
    private void chest_item$submitBlit(RenderPipeline pipeline, GpuTextureView textureView, GpuSampler sampler, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1, int color) {
        if (Minecraft.getInstance().screen != null) {
            this.guiRenderState.addGuiElement(new GUIRenderState(pipeline,
                    TextureSetup.singleTexture(textureView, sampler),
                    new Matrix3x2f(this.pose), x0, y0, x1, y1, u0, u1, v0, v1, color,
                    Minecraft.getInstance().screen.getRectangle()));
        }
    }
}
