package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public interface IEvil {
    int color = Light.ARGB.color(255,80,120,105);
    default int theColor(){
        return Light.ARGB.color(50,80,120,105);
    }
}