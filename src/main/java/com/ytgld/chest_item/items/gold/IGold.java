package com.ytgld.chest_item.items.gold;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.IGUILight;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

public interface IGold extends IGUILight {
    @Override
    default Vec2 posOffset(){
        return new Vec2(0,0);
    };

    @Override
    default int guiColor(ItemStack stack){
        return Light.ARGB.color(255,255,255,100);
    };

    @Override
    default Identifier img() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png");
    }

    @Override
    default RenderPipeline renderType() {
        return MRender.RenderPs.GUI_TEXTURED;
    }
}
