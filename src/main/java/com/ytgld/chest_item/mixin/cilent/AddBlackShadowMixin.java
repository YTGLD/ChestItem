package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Terror;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.RenderBlackShadow;
import com.ytgld.chest_item.renderer.i.IAbstractContainerScreen;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.model.IAvatarRenderState;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(AbstractContainerScreen.class)
public abstract class AddBlackShadowMixin<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {
    @Shadow
    @Final
    protected T menu;

    protected AddBlackShadowMixin(Component title) {
        super(title);
    }
    @Inject(at = @At(value = "RETURN"), method = "mouseClicked")
    public void mouseClicked(MouseButtonEvent event, boolean doubleClick, CallbackInfoReturnable<Boolean> cir) {
        RenderBlackShadow.mouseUse((int) (event.x() + 8), (int) (event.y() + 8),this.menu.getCarried());
    }
}
