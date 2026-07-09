package com.ytgld.chest_item.items.evil_mother;

import com.mojang.blaze3d.platform.GlStateManager;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.black.soul.NotLight;import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import com.ytgld.chest_item.renderer.light.Light;import net.minecraft.resources.ResourceLocation;

public interface IEvil extends NotLight {
    int color = Light.ARGB.color(255,80,120,105);
    default int theColor(){
        return Light.ARGB.color(255,80,120,105);
    }
}