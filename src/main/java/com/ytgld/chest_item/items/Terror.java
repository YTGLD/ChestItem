package com.ytgld.chest_item.items;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemStack;

public interface Terror {
    RenderType getTrailRenderType(ItemStack stack);
    int color(ItemStack stack);

}
