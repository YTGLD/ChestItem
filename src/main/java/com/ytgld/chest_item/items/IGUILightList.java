package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

public interface IGUILightList extends IGUILight{
    GUILight guiLight();
    default Vec2 posOffset(){
        return new Vec2(0,0);
    };
    default int guiColor(ItemStack stack){
        return Light.ARGB.color(150,255,255,100);
    };
    default ResourceLocation img() {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png");
    }


}
