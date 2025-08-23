package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;

public class RendererFarm {
    private final   Matrix3x2fStack pose;
   public final GuiRenderState guiRenderState;

   private final int color;
    public RendererFarm(Matrix3x2fStack pose, GuiRenderState guiRenderState, int color){
        this.pose = pose;
        this.guiRenderState = guiRenderState;
        this.color = color;
    }

    public void chest_item$blit(RenderPipeline pipeline, ResourceLocation atlas, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight) {
        this.chest_item$blit(pipeline, atlas, x, y, u, v, width, height, width, height, textureWidth, textureHeight);
    }


    public void chest_item$blit(RenderPipeline pipeline, ResourceLocation atlas, int x, int y, float u, float v, int width, int height, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        this.chest_item$blit(pipeline, atlas, x, y, u, v, width, height, uWidth, vHeight, textureWidth, textureHeight, color);
    }


    public void chest_item$blit(RenderPipeline pipeline, ResourceLocation atlas, int x, int y, float u, float v, int width, int height, int uWidth, int vHeight, int textureWidth, int textureHeight, int color) {
        this.chest_item$innerBlit(pipeline, atlas, x, x + width, y, y + height, (u + 0.0F) / (float)textureWidth, (u + (float)uWidth) / (float)textureWidth, (v + 0.0F) / (float)textureHeight, (v + (float)vHeight) / (float)textureHeight, color);
    }

    private void chest_item$innerBlit(RenderPipeline pipeline, ResourceLocation atlas, int x0, int x1, int y0, int y1, float u0, float u1, float v0, float v1, int color) {
        GpuTextureView gputextureview = Minecraft.getInstance().getTextureManager().getTexture(atlas).getTextureView();
        this.chest_item$submitBlit(pipeline, gputextureview, x0, y0, x1, y1, u0, u1, v0, v1, color);
    }

    private void chest_item$submitBlit(RenderPipeline pipeline, GpuTextureView atlasTexture, int x0, int y0, int x1, int y1, float u0, float u1, float v0, float v1, int color) {
        if (Minecraft.getInstance().screen != null) {
            this.guiRenderState.submitGuiElement(new GUIRenderState(pipeline, TextureSetup.singleTexture(atlasTexture), new Matrix3x2f(this.pose), x0, y0, x1, y1, u0, u1, v0, v1, color, Minecraft.getInstance().screen.getRectangle()));
        }
    }
}
