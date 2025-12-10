package com.ytgld.chest_item.event;

import com.mojang.blaze3d.platform.InputConstants;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class Keys {
    public static KeyMapping.Category chest = new KeyMapping.Category(Identifier.fromNamespaceAndPath(Chestitem.MODID,"chest"));


    public static final KeyMapping KEY_MAPPING_LAZY_R =
            (new KeyMapping("key.chest_item.r", InputConstants.KEY_R, chest));
    public static final KeyMapping KEY_MAPPING_LAZY_C =
            (new KeyMapping("key.chest_item.c", InputConstants.KEY_C, chest));



}
