package com.ytgld.chest_item.mixin;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.ytgld.ILevelRender;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalInt;

@Mixin(LevelRenderer.class)
public abstract class LightTextureMixin implements ILevelRender {
    @Shadow @Final private Minecraft minecraft;
    @Unique
    private RenderTarget ci1_21_9RenderTarget;
    @Unique
    private Matrix4f cI1_21_9$invertedModelviewProjectionMatrix;
    @Unique
    public Vector3f cI1_21_9$cameraPos = new Vector3f();
    @Unique
    private final Minecraft cI1_21_9$minecraft = Minecraft.getInstance();
    @Unique
    private MappableRingBuffer cI1_21_9$ubo = new MappableRingBuffer(() -> "Lightmap UBO ci", 130, cI1_21_9LIGHTMAP_UBO_SIZE);;
    @Unique
    private static final int cI1_21_9LIGHTMAP_UBO_SIZE =
            new Std140SizeCalculator()

                    .putFloat()
                    .putFloat()
                    .putFloat()
                    .putFloat()
                    .putFloat()
                    .putFloat()
                    .putFloat()
                    .putVec3()
                    .putVec3()
                    .putIVec3()
                    .putMat4f()
                    .putIVec3()
                    .get();


    @Inject(method = "resize", at = @At(value = "RETURN"))
    private void onResized(int width, int height, CallbackInfo ci) {
        if (this.ci1_21_9RenderTarget != null) {
            this.ci1_21_9RenderTarget.resize(width, height);
        }
    }
    @Inject(method = "close", at = @At(value = "RETURN"))
    private void close(CallbackInfo ci) {
        if (ci1_21_9RenderTarget != null) {
            ci1_21_9RenderTarget.destroyBuffers();
        }
    }

    @Override
    public void cI1_21_9$updateMatrices(final RenderLevelStageEvent event) {


        this.ci1_21_9RenderTarget = new TextureTarget(
                "DY LIGHTING CI", this.minecraft.getWindow().getWidth(),
                this.minecraft.getWindow().getHeight(), true);

        float partialTicks = event.getPartialTick().getGameTimeDeltaPartialTick(false);
        this.cI1_21_9$cameraPos = event.getCamera().entityPos.toVector3f();
        Matrix4f modelviewMatrix = event.getPoseStack().last().pose().transpose(new Matrix4f());
        Matrix4f projectionMatrix = event.getModelViewMatrix().transpose(new Matrix4f());

        this.cI1_21_9$invertedModelviewProjectionMatrix = new Matrix4f();

        Matrix4f MVP = modelviewMatrix.mul(projectionMatrix);
        MVP.invert(this.cI1_21_9$invertedModelviewProjectionMatrix);
        this.cI1_21_9$invertedModelviewProjectionMatrix = cI1_21_9$invertedModelviewProjectionMatrix.assume(Matrix4fc.PROPERTY_UNKNOWN);



        ProfilerFiller profilerfiller = Profiler.get();
        profilerfiller.push("lightTex");
        ClientLevel clientlevel = this.cI1_21_9$minecraft.level;
        if (clientlevel != null&&this.cI1_21_9$minecraft.player!=null
                &&cI1_21_9$invertedModelviewProjectionMatrix!=null
                &&cI1_21_9$cameraPos!=null
                &&ci1_21_9RenderTarget!=null
                &&ci1_21_9RenderTarget.getColorTextureView() != null) {
            float f = clientlevel.getSkyDarken(1.0F);
            float f1;
            Vector3f vector3f;
            vector3f = new Vector3f(1.0F, 1.0F, 1.0F);
            if (clientlevel.getSkyFlashTime() > 0) {
                f1 = 1.0F;
            } else {
                f1 = f * 0.95F + 0.05F;
            }

            float f9 = this.cI1_21_9$minecraft.options.darknessEffectScale().get().floatValue();
            float f10 = this.cI1_21_9$minecraft.player.getEffectBlendFactor(MobEffects.DARKNESS, partialTicks) * f9;
            float f3 = 0;
            float f5 = this.cI1_21_9$minecraft.player.getWaterVision();
            float f4;
            if (this.cI1_21_9$minecraft.player.hasEffect(MobEffects.NIGHT_VISION)) {
                f4 = GameRenderer.getNightVisionScale(this.cI1_21_9$minecraft.player, partialTicks);
            } else if (f5 > 0.0F && this.cI1_21_9$minecraft.player.hasEffect(MobEffects.CONDUIT_POWER)) {
                f4 = f5;
            } else {
                f4 = 0.0F;
            }

            Vector3f vector3f1;
            if (clientlevel.effects().hasEndFlashes()) {
                vector3f1 =  new Vector3f(0.9F, 0.5F, 1.0F);
            } else {
                vector3f1 = new Vector3f(f, f, 1.0F).lerp(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
            }
            float f7 = clientlevel.dimensionType().ambientLight();
            float f8 = this.cI1_21_9$minecraft.options.gamma().get().floatValue();
            CommandEncoder commandencoder = RenderSystem.getDevice().createCommandEncoder();

            Vector3f playerPos = new Vector3f((float) this.cI1_21_9$minecraft.player.getX(),(float) this.cI1_21_9$minecraft.player.getY(),(float) this.cI1_21_9$minecraft.player.getZ());
            Vector3f lightPos =new Vector3f(- this.cI1_21_9$cameraPos.x,- this.cI1_21_9$cameraPos.y,- this.cI1_21_9$cameraPos.z);
            try (GpuBuffer.MappedView gpubuffer$mappedview = commandencoder.mapBuffer(this.cI1_21_9$ubo.currentBuffer(), false, true)) {
                Std140Builder.intoBuffer(gpubuffer$mappedview.data())
                        .putFloat(f7)
                        .putFloat(f1)
                        .putFloat(0)
                        .putFloat(f4)
                        .putFloat(f3)
                        .putFloat(0)
                        .putFloat(Math.max(0.0F, f8 - f10))
                        .putVec3(vector3f1)
                        .putVec3(vector3f)
                        .putVec3(playerPos)
                        .putMat4f(cI1_21_9$invertedModelviewProjectionMatrix)
                        .putVec3(lightPos);

            }
            try (RenderPass renderpass = commandencoder.createRenderPass(() -> "Update light",
                    minecraft.gameRenderer.lightTexture().getTextureView(), OptionalInt.empty())
            ) {
                renderpass.setPipeline(MRender.RenderPs.LIGHTMAP);
                RenderSystem.bindDefaultUniforms(renderpass);
                renderpass.setUniform("LightmapInfo", this.cI1_21_9$ubo.currentBuffer());
                renderpass.bindSampler("s_diffuse_depth", Minecraft.getInstance().getMainRenderTarget().getColorTextureView());
                renderpass.draw(0, 3);
            }

            this.cI1_21_9$ubo.rotate();
            profilerfiller.pop();
        }
    }
}
