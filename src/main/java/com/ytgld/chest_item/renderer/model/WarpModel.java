package com.ytgld.chest_item.renderer.model;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.feature.ItemFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WarpModel implements ItemModel{
    private final QuadCollection quads;
    private final Supplier<Vector3fc[]> extents;
    private final ModelRenderProperties properties;
    private final Matrix4fc transformation;

    public WarpModel(QuadCollection quads,
                           ModelRenderProperties properties, Matrix4fc transformation
    ) {
        this.quads = quads;
        this.properties = properties;
        this.transformation = transformation;
        this.extents = Suppliers.memoize(() -> computeExtents(quads.getAll()));
    }

    public static Vector3fc[] computeExtents(List<BakedQuad> quads) {
        Set<Vector3fc> result = new HashSet();

        for(BakedQuad quad : quads) {
            for(int vertex = 0; vertex < 4; ++vertex) {
                result.add(quad.position(vertex));
            }
        }

        return result.toArray(Vector3fc[]::new);
    }

    public void update(ItemStackRenderState output, ItemStack item, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        output.appendModelIdentityElement(this);
        ItemStackRenderState.LayerRenderState layer = output.newLayer();
        layer.setUsesBlockLight(false);
        layer.setExtents(this.extents);
        layer.setLocalTransform(this.transformation);
        this.properties.applyToLayer(layer, displayContext);
        layer.prepareQuadList().addAll(this.quads.getAll());
        var c  =new WarpModel.CISpecialModel(displayContext, quads);

        layer.setupSpecialModel(c,c.extractArgument(item));

        if (this.quads.hasMaterialFlag(2)) {
            output.setAnimated();
        }
    }

    private static void validateAtlasUsage(List<BakedQuad> quads) {
        Iterator<BakedQuad> quadIterator = quads.iterator();
        if (quadIterator.hasNext()) {
            Identifier expectedAtlas = ((BakedQuad)quadIterator.next()).materialInfo().sprite().atlasLocation();

            while(quadIterator.hasNext()) {
                BakedQuad quad = (BakedQuad)quadIterator.next();
                Identifier quadAtlas = quad.materialInfo().sprite().atlasLocation();
                if (!quadAtlas.equals(expectedAtlas)) {
                    String var10002 = String.valueOf(expectedAtlas);
                    throw new IllegalStateException("Multiple atlases used in model, expected " + var10002 + ", but also got " + String.valueOf(quadAtlas));
                }
            }

            if (!expectedAtlas.equals(TextureAtlas.LOCATION_ITEMS) && !expectedAtlas.equals(TextureAtlas.LOCATION_BLOCKS)) {
                throw new IllegalArgumentException("Atlas " + String.valueOf(expectedAtlas) + " can't be usef for item models");
            }
        }

    }

    public record Unbaked(Identifier model, Optional<Transformation> transformation,
                          List<ItemTintSource> tints) implements ItemModel.Unbaked {

        public static final MapCodec<WarpModel.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((i) ->
                i.group(Identifier.CODEC.fieldOf("model").forGetter(WarpModel.Unbaked::model),
                        Transformation.EXTENDED_CODEC.optionalFieldOf("transformation").
                                forGetter(WarpModel.Unbaked::transformation),
                        ItemTintSources.CODEC.listOf().optionalFieldOf("tints",
                                List.of()).forGetter(WarpModel.Unbaked::tints)).apply(i, WarpModel.Unbaked::new));

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(this.model);
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc transformation) {
            ModelBaker baker = context.blockModelBaker();
            ResolvedModel resolvedModel = baker.getModel(this.model);
            TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
            QuadCollection quads = resolvedModel.bakeTopGeometry(textureSlots, baker, BlockModelRotation.IDENTITY);
            ModelRenderProperties properties = ModelRenderProperties.fromResolvedModel(baker, resolvedModel, textureSlots);
            WarpModel.validateAtlasUsage(quads.getAll());
            Matrix4fc modelTransform = Transformation.compose(transformation, this.transformation);
            return new WarpModel(quads, properties, modelTransform);
        }

        @Override
        public MapCodec<WarpModel.Unbaked> type() {
            return MAP_CODEC;
        }
    }

    public static class CISpecialModel implements NoDataSpecialModelRenderer {
        private final ItemDisplayContext displayContext;
        @Nullable
        private final QuadCollection quads;

        public CISpecialModel(ItemDisplayContext displayContext,
                              QuadCollection quads) {

            this.displayContext = displayContext;
            this.quads = quads;
        }
        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
            if (quads != null && submitNodeCollector instanceof SubmitNodeStorage submitNodeStorage) {
                CIItemFeatureRenderer.Submit submit = new CIItemFeatureRenderer.Submit(poseStack.last(), displayContext,
                        255, OverlayTexture.NO_OVERLAY, 0, new int[]{}, quads.getAll(), ItemStackRenderState.FoilType.NONE, MRender.cutoutItemSheet());
                submitNodeStorage.order(0).solid.submit(
                        submit
                );
            }
        }

        @Override
        public void getExtents(Consumer<Vector3fc> consumer) {

        }
    }
}
