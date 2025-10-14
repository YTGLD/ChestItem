package com.ytgld.chest_item.mixin.cilent;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.items.Terror;
import com.ytgld.chest_item.renderer.IGUI;
import com.ytgld.chest_item.renderer.MGuiGraphics;
import com.ytgld.chest_item.renderer.TooltipRenderUtil;
import com.ytgld.chest_item.renderer.i.IAbstractContainerScreen;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.joml.Matrix3x2fStack;
import org.joml.Matrix4f;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements IGuiGraphics, IGUI {
    @Shadow @Final private Minecraft minecraft;
    @Shadow public abstract int guiWidth();
    @Shadow public abstract int guiHeight();

    @Shadow public abstract void flush();

    @Shadow private boolean managed;

    @Shadow private ItemStack tooltipStack;

    @Shadow public abstract PoseStack pose();

    @Shadow @Final private GuiSpriteManager sprites;

    @Shadow @Final private PoseStack pose;

    @Override
    public GuiSpriteManager cI1_21_1$sprites() {
        return sprites;
    }

    @Override
    public void chest_item$addW(ItemStack stack) {
        GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
        if (stack.getItem() instanceof Terror terror) {
            guiGraphics.pose().pushPose();
            if (this.minecraft.screen instanceof IAbstractContainerScreen iAbstractContainerScreen) {
                List<Vec2> xy = iAbstractContainerScreen.chest_item$xy();
                if (xy != null) {
                    for (int i = 1; i < xy.size(); i++) {
                        Vec2 prevPos = xy.get(i - 1);
                        Vec2 currPos = xy.get(i);
                        if (prevPos.x != 0 && prevPos.y != 0 && currPos.x != 0 && currPos.y != 0) {
                            float alpha = (float) (i) / (xy.size());
                            Vec2 adjustedPrevPos = new Vec2(prevPos.x, prevPos.y);
                            Vec2 adjustedCurrPos = new Vec2(currPos.x, currPos.y);
                            pose.pushPose();
                            pose.translate(prevPos.x, prevPos.y,0);
                            pose.scale(alpha * 1.55f,alpha * 1.55f,alpha * 1.55f);
                            pose.translate(-prevPos.x, -prevPos.y,0);

                            int color = terror.color(stack);
                            int rs = (color >> 16) & 0xFF;
                            int gs = (color >> 8) & 0xFF;
                            int bs = color & 0xFF;

                            float r = rs / 255f;
                            float g = gs / 255f;
                            float b = bs / 255f;
                            MGuiGraphics.blit(guiGraphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/tooltip/fire.png"),
                                    adjustedCurrPos.x - 12, adjustedCurrPos.y - 12,
                                    0, 0, 24, 24, 24, 24,
                                    r, g, b * alpha, alpha);
                            MGuiGraphics.blit(guiGraphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/tooltip/fire.png"),
                                    adjustedPrevPos.x - 12, adjustedPrevPos.y - 12,
                                    0, 0, 24, 24, 24, 24,
                                    r, g, b * alpha, alpha);
                            pose.popPose();

                        }
                    }
                }
            }
            guiGraphics.pose().popPose();
        }
    }
    @Inject(at = @At(value = "RETURN"),method = "renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V")
    public void moonstone$ClientTooltipPositioner(Font p_282675_, List<ClientTooltipComponent> p_282615_, int x, int y, ClientTooltipPositioner p_282442_, CallbackInfo ci) {
        if (tooltipStack.getItem() instanceof ItemBase) {
            ci$drawManaged(() -> {
                RenderTooltipEvent.Pre preEvent = ClientHooks.onRenderTooltipPre(this.tooltipStack, (GuiGraphics) (Object) this, x, y, guiWidth(), guiHeight(), p_282615_, p_282675_, p_282442_);

                int i = 0;
                int j = p_282615_.size() == 1 ? -2 : 0;

                for (ClientTooltipComponent clienttooltipcomponent : p_282615_) {
                    int k = clienttooltipcomponent.getWidth(preEvent.getFont());
                    if (k > i) {
                        i = k;
                    }

                    j += clienttooltipcomponent.getHeight();
                }

                int i2 = i;
                int j2 = j;


                Vector2ic vector2ic = p_282442_.positionTooltip(this.guiWidth(), this.guiHeight(), preEvent.getX(), preEvent.getY(), i2, j2);

                int l = vector2ic.x();
                int i1 = vector2ic.y();
                this.pose.pushPose();
                RenderTooltipEvent.Color colorEvent = ClientHooks.onRenderTooltipColor(this.tooltipStack, (GuiGraphics) (Object) this, l, i1, preEvent.getFont(), p_282615_);
                TooltipRenderUtil.renderTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j, 400, colorEvent.getBackgroundStart(), colorEvent.getBackgroundEnd(),
                        Light.ARGB.color(255,218,165,32),
                        Light.ARGB.color(255,219,112,147 ));
                this.pose.popPose();
                if (tooltipStack.getItem() instanceof Meat) {
                    this.pose.pushPose();
                    si1_21_4$renderTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j, 400);
                    this.pose.popPose();
                }
            });
        }
    }
    @Unique
    public void ci$drawManaged(Runnable pRunnable) {
        this.flush();
        this.managed = true;
        pRunnable.run();
        this.managed = false;
        this.flush();
    }

    @Unique
    void cI1_21_1$innerBlit(ResourceLocation atlasLocation, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
        RenderSystem.setShaderTexture(0, atlasLocation);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );
        Matrix4f matrix4f = this.pose().last().pose();;
        BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.addVertex(matrix4f, (float)x1, (float)y1, (float)blitOffset).setUv(minU, minV);
        bufferbuilder.addVertex(matrix4f, (float)x1, (float)y2, (float)blitOffset).setUv(minU, maxV);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y2, (float)blitOffset).setUv(maxU, maxV);
        bufferbuilder.addVertex(matrix4f, (float)x2, (float)y1, (float)blitOffset).setUv(maxU, minV);
        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    }
    @Unique
    public void si1_21_4$renderTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int z) {
        // 左上角
        int topLeftX = x - 3 - 9+2;
        int topLeftY = y - 3 - 9;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -2,0);
        guiGraphics.blitSprite(
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_0_0"), 48, 48,  0, 0, topLeftX, topLeftY, 48, 48);
        guiGraphics.pose().popPose();

        // 中间位置
        int middleX = x + (width - 48) / 2;
        int middleY = y - 3 - 6;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -7,0);
        guiGraphics.blitSprite(
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_middle_0"),48,48, 0, 0,  middleX, middleY, 48, 48);
        guiGraphics.pose().popPose();


        // 右上角
        int topRightX = x + width + 3 - 48+6;
        int topRightY = y - 3 - 9;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -2,0);
        guiGraphics.blitSprite(
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_0_1"), 48, 48, 0, 0, topRightX, topRightY, 48, 48);
        guiGraphics.pose().popPose();

        // 左下角
        int bottomLeftX = x - 3 - 9 + 2;
        int bottomLeftY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 4,0);
        guiGraphics.blitSprite(
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_1_0"),48, 48,0, 0, bottomLeftX, bottomLeftY, 48, 48);
        guiGraphics.pose().popPose();

        // 右下角
        int bottomRightX = x + width + 3 - 48 + 6;
        int bottomRightY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 4,0);
        guiGraphics.blitSprite(
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_1_1"),48, 48, 0, 0, bottomRightX, bottomRightY, 48, 48);
        guiGraphics.pose().popPose();
    }
    @Unique
    private  void chest_item$renderTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        int i = x - 3 - 9;
        int j = y - 3 - 9;
        int k = width + 3 + 3 + 18;
        int l = height + 3 + 3 + 18;
        guiGraphics.blitSprite(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"tooltip/frame"), i, j, k, l);
    }
}
