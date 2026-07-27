package com.ytgld.chest_item.renderer.outline;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.resource.ResourceHandle;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public class BlackFramebufferSets implements PostChain.TargetBundle  {

    public  ResourceHandle<RenderTarget> entityOutlineFramebuffer;
    public  ResourceHandle<RenderTarget> WarpedFramebuffer;
    public  ResourceHandle<RenderTarget> resourceHandleDecay;



    public  ResourceHandle<RenderTarget> mainFramebuffer = ResourceHandle.invalid();
    public static final Identifier MAIN =Identifier.fromNamespaceAndPath(Chestitem.MODID,"main");
    public static final Identifier ENTITY_OUTLINE = Identifier.fromNamespaceAndPath(Chestitem.MODID,"black");
    public static final Identifier WARPED = Identifier.fromNamespaceAndPath(Chestitem.MODID,"warped");
    public static final Identifier decay = Identifier.fromNamespaceAndPath(Chestitem.MODID,"decay");
    @Override
    public ResourceHandle<RenderTarget> getOrThrow(Identifier id) {
        if (id .equals(WARPED) ) {
            return WarpedFramebuffer;
        }else if (id .equals(ENTITY_OUTLINE) ) {
            return entityOutlineFramebuffer;
        }else if (id .equals(decay) ) {
            return resourceHandleDecay;
        }else if (id.equals(MAIN)){
            return mainFramebuffer;
        }
        return null;
    }


    @Override
    public void replace(Identifier id, ResourceHandle<RenderTarget> framebuffer) {
        if (id.equals(WARPED) ) {
            WarpedFramebuffer = framebuffer;
        }else if (id.equals(ENTITY_OUTLINE) ) {
            entityOutlineFramebuffer = framebuffer;
        }else if (id.equals(decay) ) {
            resourceHandleDecay = framebuffer;
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
            return WarpedFramebuffer;
        }else if (id .equals(ENTITY_OUTLINE) ) {
            return entityOutlineFramebuffer;
        }else if (id .equals(decay) ) {
            return resourceHandleDecay;
        }else if (id.equals(MAIN)){
            return mainFramebuffer;
        }
        return null;
    }

}
