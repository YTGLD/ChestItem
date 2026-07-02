package com.ytgld.chest_item.renderer.gui_particles;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class BlackParticlesAdd {

    private static final Map<BlackKey, BlackState> STATES = new HashMap<>();
    private static int time = 0;

    public static final int KEEP_ALIVE = 10;

    public static void tick() {
        time += 2;
        for (BlackState s : STATES.values()) {
            if (time - s.lastSeenTick <= KEEP_ALIVE) {
                s.alpha = Math.min(200, s.alpha + 40);
            } else {
                s.alpha = Math.max(0, s.alpha - 30);
            }
        }

        STATES.entrySet().removeIf(e -> e.getValue().alpha <= 0);
    }

    public static void markSeen(int x, int y, BlackKey.ImageColorAndRenderPipeline imageColorAndRenderPipeline) {
        BlackKey key = new BlackKey(x, y,imageColorAndRenderPipeline);

        STATES.computeIfAbsent(key,
                k -> new BlackState(0, time, x, y,imageColorAndRenderPipeline)
        ).lastSeenTick = time;
    }

    public static Map<BlackKey, BlackState> all() {
        return STATES;
    }
}