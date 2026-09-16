package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.renderer.ChestItemLightRender;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class RenderBlackShadowMixin {
    @Inject(at = @At(value = "RETURN"),method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void TheImprintOfTheSoulBlackLight(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
        ChestItemLightRender.theImprintOfTheSoulBlackLight(guiGraphics,entity,level,stack,x,y,seed,ci);
    }

    @Inject(at = @At(value = "RETURN"),method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void renderItem(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
        ChestItemLightRender.renderCommonCelestialAndTheImprintOfTheSoul(guiGraphics,entity,level,stack,x,y,seed,ci);
    }
}
