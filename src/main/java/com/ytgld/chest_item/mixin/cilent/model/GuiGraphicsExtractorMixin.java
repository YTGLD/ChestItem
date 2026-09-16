package com.ytgld.chest_item.mixin.cilent.model;

import com.ytgld.chest_item.items.InitItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.item.TrackingItemStackRenderState;
import net.minecraft.client.renderer.state.gui.GuiItemRenderState;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private GuiRenderState guiRenderState;

    @Shadow
    @Final
    private Matrix3x2fStack pose;

    @Shadow
    public abstract @Nullable ScreenRectangle peekScissorStack();

    @Inject
            (
                    at = @At(value = "HEAD"),
                    method = "item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V",
                    cancellable = true
            )
    public void IGUILight(LivingEntity owner, Level level, ItemStack itemStack, int x, int y, int seed, CallbackInfo ci) {
        if (itemStack.is(InitItems.WallowAxe_.asItem())) {
            ci.cancel();
            TrackingItemStackRenderState itemStackRenderState = new TrackingItemStackRenderState();
            this.minecraft.getItemModelResolver().updateForTopItem(itemStackRenderState, InitItems.WallowAxe_Small.asItem().getDefaultInstance()
                    , ItemDisplayContext.GUI, level, owner, seed);

            this.guiRenderState.addItem(new GuiItemRenderState(new Matrix3x2f(this.pose), itemStackRenderState, x, y,
                    this.peekScissorStack()));
        }
        if (itemStack.is(InitItems.EvilAxe_.asItem())) {
            ci.cancel();
            TrackingItemStackRenderState itemStackRenderState = new TrackingItemStackRenderState();
            this.minecraft.getItemModelResolver().updateForTopItem(itemStackRenderState, InitItems.EvilAxe_Small.asItem().getDefaultInstance()
                    , ItemDisplayContext.GUI, level, owner, seed);

            this.guiRenderState.addItem(new GuiItemRenderState(new Matrix3x2f(this.pose), itemStackRenderState, x, y,
                    this.peekScissorStack()));
        }

    }
}
