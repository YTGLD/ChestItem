package com.ytgld.chest_item.items;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.renderer.RenderPipelines;

public interface IBlackLight {
    String blackName = "BlackLightStingNBT";

    default DoBlack colorBlack(){
        return new DoBlack(200,20,0,10, MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction);
    };

    record DoBlack(int a,int r , int g , int b, RenderPipeline renderPipeline){

    }
}
