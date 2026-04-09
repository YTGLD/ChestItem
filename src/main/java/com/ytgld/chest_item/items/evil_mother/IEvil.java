package com.ytgld.chest_item.items.evil_mother;

import com.mojang.blaze3d.platform.GlStateManager;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import com.ytgld.chest_item.renderer.light.Light;import net.minecraft.resources.ResourceLocation;

public interface IEvil extends IBlackLight {
    int color = Light.ARGB.color(50,80,120,105);
    @Override
    default DoBlack colorBlack() {
        return new DoBlack(50,80,120,105,
                new CIStateShardsHasBlack.CIFunc(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO
                )
        );
    }
    @Override
    default ResourceLocation blackStar() {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                "textures/evil_mother/cube.png");
    }
    @Override
    default ResourceLocation blackFire() {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                "textures/evil_mother/cube.png");
    }
}