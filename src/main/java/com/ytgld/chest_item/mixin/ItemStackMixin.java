package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.items.black.OneEyedSpider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "finishUsingItem", at = @At(value = "RETURN"))
    private void finishUsingItem(Level level, LivingEntity livingEntity, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = (ItemStack) (Object) this ;
        if (stack.get(DataComponents.FOOD) != null) {
            OneEyedSpider.hurtOfBlood(stack,livingEntity);
        }
    }
}
