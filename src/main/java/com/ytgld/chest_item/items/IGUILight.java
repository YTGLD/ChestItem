package com.ytgld.chest_item.items;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

public interface IGUILight {
    int guiColor(ItemStack stack);
    Vec2 posOffset();
    ResourceLocation img();
    RenderPipeline renderType();
}
