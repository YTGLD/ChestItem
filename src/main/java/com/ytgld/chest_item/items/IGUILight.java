package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

public interface IGUILight {
    int guiColor(ItemStack stack);
    Vec2 posOffset();
   default ResourceLocation img(){
       return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png");
   };;
}
