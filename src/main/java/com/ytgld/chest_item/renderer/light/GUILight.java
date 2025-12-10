package com.ytgld.chest_item.renderer.light;


import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec2;

import java.util.Map;

public record GUILight(Map<Integer,Integer> listGUIColor,
                       Map<Integer, Vec2> listPosOffset,
                       Map<Integer, Identifier> listImg,
                       boolean doLight,
                       int listNumber ) {



}
