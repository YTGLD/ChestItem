package com.ytgld.chest_item.renderer.text;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4f;
import org.jspecify.annotations.Nullable;

public record CIGlyphRenderState (Matrix3x2fc pose, TextRenderable textRenderable, @Nullable ScreenRectangle scissorArea) implements GuiElementRenderState {

    public void buildVertices(@NotNull VertexConsumer vertexConsumer) {
        this.textRenderable.render((new Matrix4f()).mul(this.pose), vertexConsumer, 15728880, true);
    }

    public @NotNull RenderPipeline pipeline() {
        return MRender.RenderPs.LightSlowness(true,0.01f,1);
    }

    public @NotNull TextureSetup textureSetup() {
        return TextureSetup.singleTextureWithLightmap(this.textRenderable.textureView(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST));
    }
    public @Nullable ScreenRectangle bounds() {
        return null;
    }

    public @Nullable ScreenRectangle scissorArea() {
        return this.scissorArea;
    }
}

