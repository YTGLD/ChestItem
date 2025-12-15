package com.ytgld.chest_item.tip;

import net.minecraft.network.chat.Component;

import java.util.Map;

public record TipItem(Map<Integer, Component> componentMap, Map<Integer, Integer> color, int mapMax) {
}
