package com.ytgld.chest_item.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.QuadInstance;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.FeatureRendererType;
import net.minecraft.client.renderer.feature.RenderTypeFeatureRenderer;
import net.minecraft.client.renderer.feature.submit.TranslucentSubmit;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.world.item.ItemDisplayContext;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class CIItemFeatureRenderer extends RenderTypeFeatureRenderer<CIItemFeatureRenderer.Submit> {
    public static final FeatureRendererType<CIItemFeatureRenderer.Submit> TYPE = FeatureRendererType.create("Item");
    private final QuadInstance quadInstance = new QuadInstance();

    protected void buildGroup(@NonNull FeatureFrameContext context, List<Submit> submits) {
        for (Submit submit : submits) {
            prepareSubmit(submit);
        }
    }
    private void prepareSubmit(CIItemFeatureRenderer.Submit submit) {
        this.prepareMainSubmit(submit);
    }
    private void prepareMainSubmit(CIItemFeatureRenderer.Submit submit) {
        RenderType renderType = submit.renderType();

        for (BakedQuad quad : submit.quads()) {

            BakedQuad.MaterialInfo material = quad.materialInfo();

            QuadInstance qi = new QuadInstance();

            qi.setLightCoords(submit.lightCoords());
            qi.setOverlayCoords(submit.overlayCoords());

            qi.setColor(
                    getLayerColorSafe(submit.tintLayers(), material)
            );

            this.getVertexBuilder(renderType)
                    .putBakedQuad(submit.pose(), quad, qi);
        }
    }

    private static int getLayerColorSafe(int[] layers, int layer) {
        return layer >= 0 && layer < layers.length ? layers[layer] : -1;
    }

    private static int getLayerColorSafe(int[] tintLayers, BakedQuad.MaterialInfo material) {
        return material.isTinted() ? getLayerColorSafe(tintLayers, material.tintIndex()) : -1;
    }

    public record Submit(PoseStack.Pose pose, ItemDisplayContext displayContext, int lightCoords, int overlayCoords, int outlineColor, int[] tintLayers, List<BakedQuad> quads, ItemStackRenderState.FoilType foilType,RenderType renderType) implements TranslucentSubmit {
        public float distanceToCameraSq() {
            return TranslucentSubmit.computeDistanceToCameraSq(this.pose.pose());
        }

        public FeatureRendererType<CIItemFeatureRenderer.Submit> featureType() {
            return CIItemFeatureRenderer.TYPE;
        }
    }
}

