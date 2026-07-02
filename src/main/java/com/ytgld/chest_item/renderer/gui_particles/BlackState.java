package com.ytgld.chest_item.renderer.gui_particles;

public class BlackState {
    public int alpha;
    public int lastSeenTick;

    public final int screenX;
    public final int screenY;

    public BlackState(int alpha, int lastSeenTick, int x, int y) {
        this.alpha = alpha;
        this.lastSeenTick = lastSeenTick;
        this.screenX = x;
        this.screenY = y;
    }
}