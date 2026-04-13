package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public interface IEvil extends IBlackLight {
    int color = Light.ARGB.color(255,80,120,105);
    @Override
    default DoBlack colorBlack() {
        return new DoBlack(150,80,120,105,
                RenderPipelines.GUI_TEXTURED
        );
    }

    @Override
    default Identifier blackFire() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/evil_mother/small_fire.png");
    }

    @Override
    default Identifier blackStar() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/evil_mother/cube.png");
    }
}