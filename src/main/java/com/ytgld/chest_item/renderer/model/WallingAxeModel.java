package com.ytgld.chest_item.renderer.model;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.item.*;
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

public class WallingAxeModel implements ItemModel {
    private final QuadCollection quads;
    private final Supplier<Vector3fc[]> extents;
    private final QuadCollection quads2;
    private final ModelRenderProperties properties;
    private final Matrix4fc transformation;

    public WallingAxeModel(QuadCollection quads,
                           ModelRenderProperties properties, Matrix4fc transformation,
                           QuadCollection quads2
    ) {
        this.quads = quads;
        this.properties = properties;
        this.transformation = transformation;
        this.extents = Suppliers.memoize(() -> computeExtents(quads.getAll()));
        this.quads2 = quads2;
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
        var c  =new CISpecialModel(displayContext, quads,quads2);

        layer.setupSpecialModel(c,c.extractArgument(item));
//        layer.setupSpecialModel(b,b.extractArgument(item));


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

    public record Unbaked(Identifier model,Identifier model2, Optional<Transformation> transformation,
                          List<ItemTintSource> tints) implements ItemModel.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((i) ->
                i.group(Identifier.CODEC.fieldOf("model").forGetter(Unbaked::model),Identifier.CODEC.fieldOf("model2"
                        ).forGetter(Unbaked::model),
                        Transformation.EXTENDED_CODEC.optionalFieldOf("transformation").
                                forGetter(Unbaked::transformation),
                        ItemTintSources.CODEC.listOf().optionalFieldOf("tints",
                                List.of()).forGetter(Unbaked::tints)).apply(i, Unbaked::new));

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(this.model);
            resolver.markDependency(this.model2);
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc transformation) {
            ModelBaker baker = context.blockModelBaker();

            ResolvedModel resolvedModelGlow = baker.getModel(model2);
            TextureSlots textureSlotsGlow = resolvedModelGlow.getTopTextureSlots();
            QuadCollection quadsGlow = resolvedModelGlow.bakeTopGeometry(textureSlotsGlow, baker, BlockModelRotation.IDENTITY);
            ModelRenderProperties propertiesGlow = ModelRenderProperties.fromResolvedModel(baker, resolvedModelGlow, textureSlotsGlow);
            WallingAxeModel.validateAtlasUsage(quadsGlow.getAll());
            Matrix4fc modelTransformGlow = Transformation.compose(transformation, this.transformation);


            ResolvedModel resolvedModel = baker.getModel(this.model);
            TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
            QuadCollection quads = resolvedModel.bakeTopGeometry(textureSlots, baker, BlockModelRotation.IDENTITY);
            ModelRenderProperties properties = ModelRenderProperties.fromResolvedModel(baker, resolvedModel, textureSlots);
            WallingAxeModel.validateAtlasUsage(quads.getAll());
            Matrix4fc modelTransform = Transformation.compose(transformation, this.transformation);
            return new WallingAxeModel(quads, properties, modelTransform,
                    quadsGlow);
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }

    public static class CISpecialModel implements NoDataSpecialModelRenderer {
        private final ItemDisplayContext displayContext;
        @Nullable
        private final QuadCollection quads;
        private final QuadCollection quads2;

        public CISpecialModel(ItemDisplayContext displayContext,
                              QuadCollection quads, QuadCollection quads2) {

            this.displayContext = displayContext;
            this.quads = quads;
            this.quads2 = quads2;
        }
        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
            poseStack.pushPose();
            poseStack.scale(1.75f,1.75f,1.55f);
            poseStack.translate(-0.15f,-0.15f,-0.185);
            submitNodeCollector.submitItem(poseStack,displayContext,i,
                    OverlayTexture.NO_OVERLAY,0x00000000,new int[]{},quads.getAll(), ItemStackRenderState.FoilType.NONE);
            submitNodeCollector.submitItem(poseStack,displayContext,255,
                    OverlayTexture.NO_OVERLAY,0x00000000,new int[]{},quads2.getAll(), ItemStackRenderState.FoilType.NONE);
            poseStack.popPose();
        }

        @Override
        public void getExtents(Consumer<Vector3fc> consumer) {

        }
    }
}