package com.ytgld.chest_item.items;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

public interface IGUILight {
    int guiColor(ItemStack stack);
    Vec2 posOffset();
   default Identifier img(){
       return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png");
   };
    default  RenderPipeline renderType(){
        return MRender.RenderPs.GUI_TEXTURED;
    };
}
