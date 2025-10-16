package com.ytgld.chest_item.items.black.soul;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.resources.ResourceLocation;

public class TheOrderOfTheUndead extends TheImprintOfTheSoul {
    public TheOrderOfTheUndead(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation resourceLocation() {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/the_order_of_the_undead.png");
    }

    @Override
    public int soulColor() {
        return 0XFF6A5ACD;
    }
}
