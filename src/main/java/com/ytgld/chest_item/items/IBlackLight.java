package com.ytgld.chest_item.items;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.resources.Identifier;

public interface IBlackLight {
    String blackName = "BlackLightStingNBT";
    default Identifier blackFire(){
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/gui/tooltip/fire_black.png");
    }
    default Identifier blackStar(){
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/shadow/ci_star.png");
    }

    default DoBlack colorBlack(){
        return new DoBlack(200,20,0,10, MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction);
    };
    record DoBlack(int a,int r , int g , int b, RenderPipeline renderPipeline){
        public int color(){
            return Light.ARGB.color(a,r,g,b);
        }
    }
}
