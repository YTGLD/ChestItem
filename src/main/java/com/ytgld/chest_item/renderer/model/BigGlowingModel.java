package com.ytgld.chest_item.renderer.model;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.renderer.MRender;
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
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BigGlowingModel implements ItemModel {
    private final QuadCollection quads;
    private final Supplier<Vector3fc[]> extents;
    private final Identifier identifier;
    private final ModelRenderProperties properties;
    private final Matrix4fc transformation;

    public BigGlowingModel(QuadCollection quads,
                           ModelRenderProperties properties, Matrix4fc transformation
                           ,Identifier identifier
    ) {
        this.quads = quads;
        this.properties = properties;
        this.transformation = transformation;
        this.extents = Suppliers.memoize(() -> computeExtents(quads.getAll()));
        this.identifier = identifier;
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
        var c  =new CISpecialModel(displayContext, quads,identifier);

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

    public record Unbaked(Identifier model,Identifier identifier, Optional<Transformation> transformation,
                          List<ItemTintSource> tints) implements ItemModel.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((i) ->
                i.group(Identifier.CODEC.fieldOf("model").forGetter(Unbaked::model),Identifier.CODEC.fieldOf("identifier"
                        ).forGetter(Unbaked::model),
                        Transformation.EXTENDED_CODEC.optionalFieldOf("transformation").
                                forGetter(Unbaked::transformation),
                        ItemTintSources.CODEC.listOf().optionalFieldOf("tints",
                                List.of()).forGetter(Unbaked::tints)).apply(i, Unbaked::new));

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
            BigGlowingModel.validateAtlasUsage(quads.getAll());
            Matrix4fc modelTransform = Transformation.compose(transformation, this.transformation);
            return new BigGlowingModel(quads, properties, modelTransform,identifier);
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }

    public class CISpecialModel implements NoDataSpecialModelRenderer {
        private final ItemDisplayContext displayContext;
        @Nullable
        private final QuadCollection quads;

        public CISpecialModel(ItemDisplayContext displayContext,
                              QuadCollection quads,Identifier identifier) {

            this.displayContext = displayContext;
            this.quads = quads;
        }
        @Override
        public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, int i1, boolean b, int i2) {
            HandlerClient.doPassWarped = true;
            HandlerClient.showRenderWarped = true;

            poseStack.pushPose();
            poseStack.scale(1.75f,1.75f,1.75f);
            poseStack.translate(-0.15f,-0.15f,-0.185);
            submitNodeCollector.submitItem(poseStack,displayContext,i,
                    OverlayTexture.NO_OVERLAY,0x00000000,new int[]{},quads.getAll(), ItemStackRenderState.FoilType.NONE);
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(-0.01375f,-0.01375f,0.0375f);
            render(poseStack,submitNodeCollector,
                    new Vec3(1 - 0.15f * 2,1 - 0.15f * 2,1 - 0.185 * 2),
                    new Vec3(-0.15f / 2,-0.15f / 2,-0.185 / 2),
                    1 - 0.25f / 2f ,
                    identifier);
            poseStack.popPose();

        }
        private void render(PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
                            Vec3 vec3 , Vec3 vec3A,float size,Identifier identifier){
            poseStack.pushPose();
            poseStack.translate(vec3.x,vec3.y,vec3.z);
            poseStack.translate(vec3A.x,vec3A.y,vec3A.z);
            submitNodeCollector.submitCustomGeometry(poseStack, MRender.itemCutout(identifier, ConfigC.config.Render.get()),
                    (pose, vc) -> {
                        Matrix4f mat = pose.pose();
                        vc.addVertex(mat, -size, -size, 0)
                                .setColor(255, 255, 255, 255)
                                .setUv(0, 1)
                                .setOverlay(OverlayTexture.NO_OVERLAY)
                                .setUv2(255,255)
                                .setNormal(0, 0, 1);

                        vc.addVertex(mat, size, -size, 0)
                                .setColor(255, 255, 255, 255)
                                .setUv(1, 1)
                                .setOverlay(OverlayTexture.NO_OVERLAY)
                                .setUv2(255,255)
                                .setNormal(0, 0, 1);

                        vc.addVertex(mat, size, size, 0)
                                .setColor(255, 255, 255, 255)
                                .setUv(1, 0)
                                .setOverlay(OverlayTexture.NO_OVERLAY)
                                .setUv2(255,255)
                                .setNormal(0, 0, 1);

                        vc.addVertex(mat, -size, size, 0)
                                .setColor(255, 255, 255, 255)
                                .setUv(0, 0)
                                .setOverlay(OverlayTexture.NO_OVERLAY)
                                .setUv2(255,255)
                                .setNormal(0, 0, 1);
                    });
            poseStack.popPose();
        }

        @Override
        public void getExtents(Consumer<Vector3fc> consumer) {

        }
    }
}