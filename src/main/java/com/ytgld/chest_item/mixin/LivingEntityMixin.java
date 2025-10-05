package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.items.AttReg;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.w3c.dom.Attr;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
}
