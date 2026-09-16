package com.ytgld.chest_item.mixin.outline;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ytgld.chest_item.renderer.outline.ISubmitNodeCollection;
import com.ytgld.chest_item.renderer.outline.ISubmitReactorGlow;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.CustomFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SubmitNodeStorage.class)
public abstract class SubmitNodeStorageMixin
        implements ISubmitReactorGlow {

    @Shadow
    public abstract SubmitNodeCollection order(int order);

    @Override
    public void chest_item$submitReactorGlow(
            PoseStack poseStack,
            RenderType renderType,
            SubmitNodeCollector.CustomGeometryRenderer renderer) {

        CustomFeatureRenderer.Submit submit =
                new CustomFeatureRenderer.Submit(
                        poseStack.last().copy(),
                        renderType,
                        renderer
                );

        ((ISubmitNodeCollection) this.order(0))
                .reactorGlow()
                .submit(submit);
    }
}