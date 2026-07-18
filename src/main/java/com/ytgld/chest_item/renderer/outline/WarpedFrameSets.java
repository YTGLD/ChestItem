package com.ytgld.chest_item.renderer.outline;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public class WarpedFrameSets implements PostChain.TargetBundle{
    public  ResourceHandle<RenderTarget> WarpedScreenFramebuffer;



    public  ResourceHandle<RenderTarget> mainFramebuffer;
    public static final Identifier MAIN =Identifier.fromNamespaceAndPath(Chestitem.MODID,"main");
    public static final Identifier WARPED = Identifier.fromNamespaceAndPath(Chestitem.MODID,"warp");
    @Override
    public ResourceHandle<RenderTarget> getOrThrow(Identifier id) {
        if (id .equals(WARPED) ) {
            return WarpedScreenFramebuffer;
        }else if (id.equals(MAIN)){
            return mainFramebuffer;
        }
        return null;
    }


    @Override
    public void replace(Identifier id, ResourceHandle<RenderTarget> framebuffer) {
        if (id.equals(WARPED) ) {
            WarpedScreenFramebuffer = framebuffer;
        }else if (id.equals(MAIN)){
            mainFramebuffer = framebuffer;
        }else {
            System.out.println(id);
        }
    }

    @Nullable
    @Override
    public ResourceHandle<RenderTarget> get(Identifier id) {
        if (id .equals(WARPED) ) {
            return WarpedScreenFramebuffer;
        }else if (id.equals(MAIN)){
            return mainFramebuffer;
        }
        return null;
    }

}
