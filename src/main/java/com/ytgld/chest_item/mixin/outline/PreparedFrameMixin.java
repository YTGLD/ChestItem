package com.ytgld.chest_item.mixin.outline;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.ytgld.chest_item.renderer.outline.IPreparedFrame;
import com.ytgld.chest_item.renderer.outline.ISubmitNodeCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.feature.CustomFeatureRenderer;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
import net.minecraft.client.renderer.feature.phase.FeatureRenderPhase;
import net.minecraft.client.renderer.feature.phase.SimpleFeatureRenderPhase;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;
@Mixin(FeatureRenderDispatcher.PreparedFrame.class)
public abstract class PreparedFrameMixin
        implements IPreparedFrame {

    @Shadow
    private @Nullable FeatureFrameContext context;

    @Shadow
    private @Nullable SubmitNodeStorage submitNodeStorage;

    @Shadow
    protected abstract void executePhase(
            FeatureRenderPhase<?> phase,
            FeatureFrameContext context,
            RenderPass renderPass
    );

    @Unique
    @Override
    public void chest_item$executeReactorGlow(RenderPass renderPass) {
        FeatureFrameContext context =
                Objects.requireNonNull(this.context);

        SubmitNodeStorage storage =
                Objects.requireNonNull(this.submitNodeStorage);

        for (SubmitNodeCollection collection :
                storage.getSubmitsPerOrder().values()) {

            if (collection instanceof ISubmitNodeCollection custom) {
                this.executePhase(
                        custom.reactorGlow(),
                        context,
                        renderPass
                );

            }
        }
    }
}