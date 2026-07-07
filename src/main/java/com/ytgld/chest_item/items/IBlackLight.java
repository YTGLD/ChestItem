package com.ytgld.chest_item.items;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.GlStateManager;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface IBlackLight {
    default DoBlack colorBlack(){
        return new DoBlack(50,20,0,10,
                new CIStateShardsHasBlack.CIFunc(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO
                )
        );
    };
    default ResourceLocation blackFire(){
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                "textures/gui/tooltip/fire_black.png");
    }
    default ResourceLocation blackStar(){
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                "textures/shadow/ci_star.png");
    }


    record DoBlack(int a,int r , int g , int b, CIStateShardsHasBlack.CIFunc ciFunc){
        public int color(){
            return Light.ARGB.color(a,r,g,b);
        }
    }
}
