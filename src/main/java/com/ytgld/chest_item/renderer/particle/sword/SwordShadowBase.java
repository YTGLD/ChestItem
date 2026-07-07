package com.ytgld.chest_item.renderer.particle.sword;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class SwordShadowBase extends TextureSheetParticle {
    public SwordShadowBase(ClientLevel level, double x, double y, double z, float movementX, float movementY, float movementZ) {
        super(level,x,y,z,0,0,0);
        this.setParticleSpeed(0,0,0);
        this.lifetime = 100;
        alpha = 0;
        this.setColor(165/255f,215/255f,230/255f);
        this.scale(Mth.nextFloat(this.random,15,17));
    }

    @Override
    protected int getLightColor(float partialTick) {
        return 255;
    }

    public float aMax = 1;
    public float aMin = 0;
    public void tick() {
        super.tick();
        if (aMin < 0.9f) {
            aMin += 0.1f;
        }
        if (aMax > 0) {
            aMax -= 0.033f;
        }
        this.alpha = Math.max(0,Math.min(aMin,aMax));
    }
    @Override
    public ParticleRenderType getRenderType() {
        return (tesselator, manager) -> {
            RenderSystem.depthMask(true);
            RenderSystem.setShader(CIStateShardsHasBlack::getHasBlock);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(
                    GlStateManager.SourceFactor.SRC_ALPHA,
                    GlStateManager.DestFactor.ONE,
                    GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO
            );
            return tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        };
    }
}

