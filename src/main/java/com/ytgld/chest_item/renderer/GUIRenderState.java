package com.ytgld.chest_item.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.renderer.RenderPipelines;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix3x2f;

import javax.annotation.Nullable;

public record GUIRenderState(RenderPipeline pipeline,
                             TextureSetup textureSetup,
                             Matrix3x2f pose,
                             int x0, int y0,
                             int x1, int y1,
                             float u0, float u1,
                             float v0, float v1,
                             int color,
                             @Nullable ScreenRectangle scissorArea,
                             @Nullable ScreenRectangle bounds) implements GuiElementRenderState {
    public GUIRenderState(RenderPipeline p_415865_, TextureSetup p_416394_, Matrix3x2f p_415848_, int p_416365_, int p_416414_, int p_416112_, int p_416519_, float p_416693_, float p_415641_, float p_416035_, float p_415841_, int p_415610_, @Nullable ScreenRectangle p_415846_) {
        this(p_415865_, p_416394_, p_415848_, p_416365_, p_416414_, p_416112_, p_416519_, p_416693_, p_415641_, p_416035_, p_415841_, p_415610_, p_415846_, getBounds(p_416365_, p_416414_, p_416112_, p_416519_, p_415848_, p_415846_));
    }

    public void buildVertices(VertexConsumer p_415779_, float p_418375_) {
        p_415779_.addVertexWith2DPose(this.pose(), (float)this.x0(), (float)this.y0(), p_418375_).setUv2(255,255).setLight(255).setNormal(0,0,0).setUv(this.u0(), this.v0()).setColor(this.color());
        p_415779_.addVertexWith2DPose(this.pose(), (float)this.x0(), (float)this.y1(), p_418375_).setUv2(255,255).setLight(255).setNormal(0,0,0).setUv(this.u0(), this.v1()).setColor(this.color());
        p_415779_.addVertexWith2DPose(this.pose(), (float)this.x1(), (float)this.y1(), p_418375_).setUv2(255,255).setLight(255).setNormal(0,0,0).setUv(this.u1(), this.v1()).setColor(this.color());
        p_415779_.addVertexWith2DPose(this.pose(), (float)this.x1(), (float)this.y0(), p_418375_).setUv2(255,255).setLight(255).setNormal(0,0,0).setUv(this.u1(), this.v0()).setColor(this.color());
    }

    @Nullable
    private static ScreenRectangle getBounds(int x0, int y0, int x1, int y1, Matrix3x2f pose, @Nullable ScreenRectangle scissorArea) {
        ScreenRectangle screenrectangle = (new ScreenRectangle(x0, y0, x1 - x0, y1 - y0)).transformMaxBounds(pose);
        return scissorArea != null ? scissorArea.intersection(screenrectangle) : screenrectangle;
    }

    public RenderPipeline pipeline() {
        return this.pipeline;
    }

    public TextureSetup textureSetup() {
        return this.textureSetup;
    }

    public Matrix3x2f pose() {
        return this.pose;
    }

    public int x0() {
        return this.x0;
    }

    public int y0() {
        return this.y0;
    }

    public int x1() {
        return this.x1;
    }

    public int y1() {
        return this.y1;
    }

    public float u0() {
        return this.u0;
    }

    public float u1() {
        return this.u1;
    }

    public float v0() {
        return this.v0;
    }

    public float v1() {
        return this.v1;
    }

    public int color() {
        return this.color;
    }

    @Nullable
    public ScreenRectangle scissorArea() {
        return this.scissorArea;
    }

    @Nullable
    public ScreenRectangle bounds() {
        return this.bounds;
    }
}
