package com.ytgld.chest_item.mixin.cilent;

import com.mojang.datafixers.kinds.IdF;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.RenderBlackShadow;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import com.ytgld.chest_item.renderer.i.IAbstractContainerScreen;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import org.joml.Vector2f;
import org.jspecify.annotations.Nullable;
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
public abstract class AbstractContainerScreenMixin <T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {
    @Shadow
    @Final
    protected T menu;

    @Shadow
    @Nullable
    protected abstract Slot getHoveredSlot(double x, double y);

    protected AbstractContainerScreenMixin(Component title) {
        super(title);
    }

    @Inject(at = @At(value = "RETURN"), method = "mouseClicked")
    public void mouseClicked(MouseButtonEvent event, boolean doubleClick, CallbackInfoReturnable<Boolean> cir) {
        Slot slot = this.getHoveredSlot(event.x(), event.y());
        ItemStack carried = this.menu.getCarried();
        if (slot != null){
            ItemStack stack = slot.getItem();
            if (carried.is(InitItems.DecayFruit_.asItem()) && stack.getItem() instanceof ItemBase) {
                if (stack.get(DataReg.attributeType.get()) == null) {
                    for (int i = 0; i < 25; i++) {
                        BlackParticlesAdd.markSeen((int) (event.x() +8), (int) (event.y() +8),new BlackKey.ImageColorAndRenderPipeline(24,
                                new BlackKey.ColorImage(255,150,150,90),
                                Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png"),
                                MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction,
                                new Vector2f(),new Vector2f((float) (Math.cos(i) / 8f), (float) (Math.sin(i) / 8f)),new Vector2f(), false));
                    }
                }
            }
        }
    }
}
