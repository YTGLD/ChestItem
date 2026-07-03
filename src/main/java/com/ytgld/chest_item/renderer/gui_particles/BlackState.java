package com.ytgld.chest_item.renderer.gui_particles;

public class BlackState {
    public int alpha;
    public int lifeTime = 0;
    public int lastSeenTick;

    public final int screenX;
    public final int screenY;
    public final BlackKey.ImageColorAndRenderPipeline imageColorAndRenderPipeline;


    public BlackState(int alpha, int lastSeenTick, int x, int y, BlackKey.ImageColorAndRenderPipeline imageColorAndRenderPipeline) {
        this.alpha = alpha;
        this.lastSeenTick = lastSeenTick;
        this.screenX = x;
        this.screenY = y;
        this.imageColorAndRenderPipeline = imageColorAndRenderPipeline;
    }
}