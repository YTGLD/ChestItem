package com.ytgld.chest_item.items;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.GlStateManager;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.renderer.ShaderInstance;

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
    record DoBlack(int a,int r , int g , int b, CIStateShardsHasBlack.CIFunc ciFunc){ }
}
