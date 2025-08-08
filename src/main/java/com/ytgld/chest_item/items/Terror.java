package com.ytgld.chest_item.items;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;

public interface Terror {
    RenderType getTrailRenderType(ItemStack stack);
    int color(ItemStack stack);

}
