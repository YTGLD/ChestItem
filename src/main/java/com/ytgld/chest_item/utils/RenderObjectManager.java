package com.ytgld.chest_item.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.Iterator;

public final class RenderObjectManager {

    private static final ArrayList<WorldRenderObject> OBJECTS =
            new ArrayList<>();

    private RenderObjectManager() {
    }

    public static void add(WorldRenderObject object) {
        OBJECTS.add(object);
    }

    private static void tick() {
        Iterator<WorldRenderObject> iterator = OBJECTS.iterator();

        while (iterator.hasNext()) {
            WorldRenderObject object = iterator.next();

            object.tick();
            object.age++;

            if (object.age >= object.maxTime) {
                iterator.remove();
            }
        }
    }

    public static void event(LevelTickEvent.Pre event) {
        tick();
    }

    public static void render(
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState cameraRenderState,
            float partialTick
    ) {
        for (WorldRenderObject object : new ArrayList<>(OBJECTS)) {
            object.render(
                    poseStack,
                    submitNodeCollector,
                    cameraRenderState,
                    partialTick
            );
        }
    }
}