package com.ytgld.chest_item.mixin.cilent.hud;

import com.ytgld.chest_item.renderer.ChaosArmorRender;
import com.ytgld.chest_item.renderer.HyperplasiaRender;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Hud.class)
public abstract class GuiMixin {

    @Shadow @Nullable protected abstract Player getCameraPlayer();

    @Shadow public int leftHeight;

    @Shadow public int rightHeight;

    @Inject(at = @At("RETURN"), method = "extractArmorLevel")
    private void renderArmorLevel(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            HyperplasiaRender.renderArmorLevel(graphics,player,leftHeight);
            leftHeight = HyperplasiaRender.setLeftHeight(leftHeight,player);
        }
    }

    @Inject(at = @At("RETURN"), method = "extractFoodLevel")
    private void renderFoodLevel(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        Player player = this.getCameraPlayer();
        if (player != null) {
            ChaosArmorRender.renderArmorLevel(graphics,player,rightHeight);
            rightHeight = ChaosArmorRender.setRightHeight(rightHeight,player);
        }
    }
}
