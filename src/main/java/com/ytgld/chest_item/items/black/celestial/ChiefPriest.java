package com.ytgld.chest_item.items.black.celestial;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.RenderBlackSoul;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.resources.Identifier;

import java.util.List;

public class ChiefPriest extends EternalVows{
    public ChiefPriest(Properties properties) {
        super(properties);
    }
    @Override
    public List<RenderBlackSoul.ColorAndImage> colorAndImage() {
        return List.of(
                new RenderBlackSoul.ColorAndImage(Light.ARGB.color(255,20,255,20),
                        Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/soul/soul_1.png")),
                new RenderBlackSoul.ColorAndImage(Light.ARGB.color(255,0,220,50),
                        Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/soul/soul_2.png")),
                new RenderBlackSoul.ColorAndImage(Light.ARGB.color(255,50,200,0),
                        Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/soul/soul_3.png")),
                new RenderBlackSoul.ColorAndImage(Light.ARGB.color(255,100,180,100),
                        Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/soul/soul_4.png")),
                new RenderBlackSoul.ColorAndImage(Light.ARGB.color(255,10,180,10),
                        Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/soul/soul_5.png"))
        );
    }
}
