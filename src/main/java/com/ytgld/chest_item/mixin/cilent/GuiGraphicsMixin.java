package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Terror;
import com.ytgld.chest_item.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements IGuiGraphics {
    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private GuiRenderState guiRenderState;
    @Shadow @Final private Matrix3x2fStack pose;
    @Shadow public abstract int guiWidth();
    @Shadow public abstract int guiHeight();
    @Override
    public  void seekingImmortals$addW( ItemStack stack) {
        GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
        if (stack.getItem() instanceof Terror terror) {
            guiGraphics.pose().pushMatrix();
            if (this.minecraft.screen instanceof IAbstractContainerScreen iAbstractContainerScreen) {
                List<Vec2> xy = iAbstractContainerScreen.seekingImmortals$xy();
                if (xy != null) {
                    for (int i = 1; i < xy.size(); i++) {
                        Vec2 prevPos = xy.get(i - 1);
                        Vec2 currPos = xy.get(i);
                        if (prevPos.x != 0 && prevPos.y != 0 && currPos.x != 0 && currPos.y != 0) {
                            float alpha = (float) (i) / (xy.size());
                            Vec2 adjustedPrevPos = new Vec2(prevPos.x, prevPos.y);
                            Vec2 adjustedCurrPos = new Vec2(currPos.x, currPos.y);
                            pose.pushMatrix();
                            pose.translate(prevPos.x, prevPos.y);
                            pose.scale(alpha * 1.55f);
                            pose.translate(-prevPos.x, -prevPos.y);

                            int color = terror.color(stack);
                            int as = (color >> 24) & 0xFF;
                            int rs = (color >> 16) & 0xFF;
                            int gs = (color >> 8) & 0xFF;
                            int bs = color & 0xFF;



                            new RendererFarm(pose, guiRenderState, Light.ARGB.color((int) (alpha * as), rs, gs, (int) (bs * alpha))).chest_item$blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/tooltip/fire.png"),
                                    (int) adjustedCurrPos.x - 8, (int) adjustedCurrPos.y - 8, 0, 0, 16, 16, 16, 16);
                            new RendererFarm(pose, guiRenderState, Light.ARGB.color((int) (alpha * as), rs, gs, (int) (bs * alpha))).chest_item$blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/tooltip/small_fire.png"),
                                    (int) adjustedPrevPos.x - 8, (int) adjustedPrevPos.y - 8, 0, 0, 16, 16, 16, 16);
                            pose.popMatrix();

                        }
                    }
                }
            }
            guiGraphics.pose().popMatrix();
        }
    }
    @Inject(at = @At(value = "HEAD"),method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/world/item/ItemStack;)V")
    public void moonstone$ClientTooltipPositioner(Font font, List<ClientTooltipComponent> components, int x, int y, ClientTooltipPositioner positioner, ResourceLocation background, ItemStack tooltipStack, CallbackInfo ci) {
        if (tooltipStack.getItem() instanceof ItemBase) {
            RenderTooltipEvent.Pre preEvent = ClientHooks.onRenderTooltipPre(tooltipStack, (GuiGraphics) (Object) this, x, y, this.guiWidth(), this.guiHeight(), components, font, positioner);

            if (!preEvent.isCanceled()) {
                font = preEvent.getFont();
                x = preEvent.getX();
                y = preEvent.getY();
                int i = 0;
                int j = components.size() == 1 ? -2 : 0;

                ClientTooltipComponent clienttooltipcomponent;
                for (Iterator<ClientTooltipComponent> var11 = components.iterator(); var11.hasNext(); j += clienttooltipcomponent.getHeight(font)) {
                    clienttooltipcomponent = var11.next();
                    int k = clienttooltipcomponent.getWidth(font);
                    if (k > i) {
                        i = k;
                    }
                }

                Vector2ic vector2ic = positioner.positionTooltip(this.guiWidth(), this.guiHeight(), x, y, i, j);
                int l = vector2ic.x();
                int i1 = vector2ic.y();
                this.pose.pushMatrix();

                chest_item$renderTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j);
                this.pose.popMatrix();
            }
        }
    }
    @Unique
    private  void chest_item$renderTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        int i = x - 3 - 4;
        int j = y - 3 - 4;
        int k = width + 3 + 3 + 8;
        int l = height + 3 + 3 + 8;
        guiGraphics.blitSprite(MRender.RenderPs.BACK,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"null"), i, j, k, l);
        guiGraphics.blitSprite(MRender.RenderPs.BACK,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"null"), i, j, k, l);
    }
}
