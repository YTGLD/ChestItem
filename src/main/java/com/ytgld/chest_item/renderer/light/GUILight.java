package com.ytgld.chest_item.renderer.light;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

import java.util.Map;

public record GUILight(Map<Integer,Integer> listGUIColor,
                       Map<Integer, Vec2> listPosOffset,
                       Map<Integer, ResourceLocation> listImg,
                       boolean doLight,
                       int listNumber ) {



}
