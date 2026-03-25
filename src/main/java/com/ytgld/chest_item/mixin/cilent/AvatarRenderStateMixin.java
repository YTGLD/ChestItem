package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.renderer.model.IAvatarRenderState;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(AvatarRenderState.class)
public class AvatarRenderStateMixin implements IAvatarRenderState {
    @Unique
    private final AtomicReference<Avatar> cI1_21_11$avatarAtomicReference = new AtomicReference<>();

    @Override
    public AtomicReference<Avatar> ci$Avatar() {
        return cI1_21_11$avatarAtomicReference;
    }
}