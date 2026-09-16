package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.renderer.particle.IParticleEngine;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleGroup;
import net.minecraft.client.particle.ParticleRenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ParticleEngine.class)
public class ParticleEngineMixin implements IParticleEngine {
    @Shadow @Final private Map<ParticleRenderType, ParticleGroup<?>> particles;

    @Override
    public Map<ParticleRenderType, ParticleGroup<?>> cI1_21_11$particles() {
        return particles;
    }
}
