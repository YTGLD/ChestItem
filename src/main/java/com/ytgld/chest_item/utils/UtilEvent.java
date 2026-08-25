package com.ytgld.chest_item.utils;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

public class UtilEvent {
    @SubscribeEvent
    public void event(LevelTickEvent.Pre event){
        RenderObjectManager.event(event);
    }
}
