package com.ytgld.chest_item.items;

import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import javax.imageio.spi.IIORegistry;

public interface IBigTooltip {
    default int color(){
        return Light.ARGB.color(255,255,0,0);
    };
    Component doTextOne();
    Item item();
}
