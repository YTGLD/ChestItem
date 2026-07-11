package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.renderer.light.Light;

public interface IEvil {
    int color = Light.ARGB.color(255,80,120,105);
    default int theColor(){
        return Light.ARGB.color(50,80,120,105);
    }
    default boolean isDecay(){
        return false;
    }
}