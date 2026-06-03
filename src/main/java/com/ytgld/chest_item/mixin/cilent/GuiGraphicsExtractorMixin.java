package com.ytgld.chest_item.mixin.cilent;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.items.black.celestial.TheCelestial;
import com.ytgld.chest_item.items.black.chaos_item.ITheChaos;
import com.ytgld.chest_item.items.black.soul.NotLight;
import com.ytgld.chest_item.items.black.soul.chaos.TheChaos;
import com.ytgld.chest_item.items.condensebone.ItemBone;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.i.IAbstractContainerScreen;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(GuiGraphicsExtractor.class)
public abstract class GuiGraphicsExtractorMixin implements IGuiGraphics {
    @Shadow @Final
    private Minecraft minecraft;
    @Shadow @Final
    private GuiRenderState guiRenderState;
    @Shadow @Final private Matrix3x2fStack pose;
    @Shadow public abstract int guiWidth();
    @Shadow public abstract int guiHeight();
    @Override
    public GuiRenderState cI1_21_11$guiRenderState() {
        return guiRenderState;
    }

    @Override
    public void chest_item$addW(ItemStack stack) {
        GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
        guiGraphics.pose().pushMatrix();
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


                        pose.pushMatrix();


                        pose.translate(prevPos.x, prevPos.y);
                        {
                            //随机位置
                            pose.translate((float) Math.sin(adjustedPrevPos.x) * 4, (float) Math.sin(adjustedCurrPos.y) * 4);
                            //位置改变
                            if (!adjustedPrevPos.equals(adjustedCurrPos)) {
                                pose.translate(0, -13);
                            }else {
                                pose.translate(0, -4);
                            }
                            pose.scale(alpha * 1.55f);
                            //上升
                            if (!adjustedPrevPos.equals(adjustedCurrPos)) {
                                pose.translate(0, (alpha) * 10);
                            }else {
                                pose.translate(0, (alpha) * 3);
                            }
                        }

                        pose.translate(-prevPos.x, -prevPos.y);

                        int color = iAbstractContainerScreen.cI1_21_9$color();

                        int as = (color >> 24) & 0xFF;
                        int rs = (color >> 16) & 0xFF;
                        int gs = (color >> 8) & 0xFF;
                        int bs = color & 0xFF;

                        guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/gui/tooltip/fire.png"),
                                (int) adjustedCurrPos.x - 8, (int) adjustedCurrPos.y - 8,
                                0, 0,
                                16, 16,
                                16, 16,Light.ARGB.color((int) (alpha * as), (int) (alpha * rs), (int) ((alpha) * gs), (int) (bs * alpha)));

                        guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                        "textures/gui/tooltip/fire.png"),
                                (int) adjustedPrevPos.x - 8, (int) adjustedPrevPos.y - 8,
                                0, 0,
                                16, 16,
                                16, 16,Light.ARGB.color((int) (alpha * as), (int) (alpha * rs), (int) ((alpha) * gs), (int) (bs * alpha)));


                        pose.popMatrix();

                    }
                }
            }
        }
        guiGraphics.pose().popMatrix();
    }
    @Inject(at = @At(value = "RETURN"),method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;tooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/Identifier;Lnet/minecraft/world/item/ItemStack;)V")
    public void ytgld$ClientTooltipPositioner(Font font, List<ClientTooltipComponent> components, int x, int y, ClientTooltipPositioner positioner, Identifier background, ItemStack tooltipStack, CallbackInfo ci) {
        if (!ConfigC.config.RenderItemTooltip.get()){
            return;
        }

        if (tooltipStack.getItem() instanceof com.ytgld.chest_item.items.black.ITheChaos || tooltipStack.getItem() instanceof ItemBase || tooltipStack.getItem() instanceof MemoryBase.BaseTooltip || tooltipStack.getItem() instanceof IEvil)  {
            RenderTooltipEvent.Pre preEvent = ClientHooks.onRenderTooltipPre(tooltipStack,  (GuiGraphicsExtractor) (Object) this, x, y, this.guiWidth(), this.guiHeight(), components, font, positioner);
            if (!preEvent.isCanceled()) {
                font = preEvent.getFont();
                x = preEvent.getX();
                y = preEvent.getY();
                int i = 0;
                int j = components.size() == 1 ? -2 : 0;

                ClientTooltipComponent clienttooltipcomponent;
                for(Iterator<ClientTooltipComponent> var11 = components.iterator(); var11.hasNext(); j += clienttooltipcomponent.getHeight(font)) {
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
                if (!(tooltipStack.getItem() instanceof MemoryBase.BaseTooltip)) {
                    if (!Handler.isBlackChaos(tooltipStack)) {
                        if (tooltipStack.getItem() instanceof ItemBlackShadow) {
                            chest_item$renderItemBlackShadowTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                        } else if (tooltipStack.getItem() instanceof ItemBone) {
                            chest_item$renderItemBoneTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                        } else if (!Handler.isBlackChaos(tooltipStack)) {
                            if (!(tooltipStack.getItem() instanceof IEvil)&& !(tooltipStack.getItem() instanceof com.ytgld.chest_item.items.black.ITheChaos)) {
                                chest_item$renderTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                            }
                        }
                        this.pose.popMatrix();
                        if (tooltipStack.getItem() instanceof Meat) {
                            this.pose.pushMatrix();
                            si1_21_4$renderTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j, 400);
                            this.pose.popMatrix();
                        }
                        if (tooltipStack.getItem() instanceof IEvil) {
                            this.pose.pushMatrix();
                            chest_item$renderItemBlackShadowTooltipBackground_EvilMother((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                            si1_21_4$renderTooltipBackground_EvilMother((GuiGraphicsExtractor) (Object) this, l, i1, i, j, 1000);
                            this.pose.popMatrix();
                        }
                        if (tooltipStack.getItem() instanceof ItemBlackShadow) {
                            if (tooltipStack.getItem() instanceof TheChaos || tooltipStack.getItem() instanceof ITheChaos) {
                                this.pose.pushMatrix();
                                si1_21_4$renderItemBlackShadowTooltipBackground_CHAOS((GuiGraphicsExtractor) (Object) this, l, i1, i, j, 400);
                                this.pose.popMatrix();
                            } else {
                                this.pose.pushMatrix();
                                si1_21_4$renderItemBlackShadowTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j, 400);
                                this.pose.popMatrix();
                            }
                        }
                    }
                }else {
                    this.pose.pushMatrix();
                    si1_21_4$renderItemBlackShadowTooltipBackground_CHAOS((GuiGraphicsExtractor) (Object) this, l, i1, i, j, 400);
                    chest_item$renderItemBlackShadowTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                    this.pose.popMatrix();
                }
                if (tooltipStack.getItem() instanceof com.ytgld.chest_item.items.black.ITheChaos) {
                    this.pose.pushMatrix();
                    si1_21_4$renderItemBlackShadowTooltipBackground_CHAOS((GuiGraphicsExtractor) (Object) this, l, i1, i, j, 400);
                    chest_item$renderItemBlackShadowTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                    this.pose.popMatrix();
                }
                if (Handler.isBlackChaos(tooltipStack)) {
                    this.pose.pushMatrix();
                    chest_item$renderTooltipBackground_red((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                    this.pose.popMatrix();
                }
            }
        }
    }
    @Unique
    public void si1_21_4$renderTooltipBackground_EvilMother(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int z) {
        // 左上角
        int topLeftX = x - 3 - 9+2;
        int topLeftY = y - 3 - 9;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, -2);
        guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/evil_mother/tool_0_0.png"), topLeftX, topLeftY, 0, 0,48, 48, 48, 48,0xffffffff);
        guiGraphics.pose().popMatrix();

        // 中间位置
        int middleX = x + (width - 48) / 2;
        int middleY = y - 3 - 6;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, -7);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/evil_mother/tool_middle_0"),48,48, 0, 0,  middleX, middleY, 48, 48);
        guiGraphics.pose().popMatrix();

        //中下
        int xXX = x + (width - 48) / 2;
        int yYY =  y + height + 3 - 9 + 4;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, 0);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/evil_mother/tool_down_0"),48,48, 0, 0,  xXX, yYY, 48, 48);
        guiGraphics.pose().popMatrix();

        // 右上角
        int topRightX = x + width + 3 - 48+6;
        int topRightY = y - 3 - 9;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, -2);
        guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/evil_mother/tool_0_1.png"), topRightX, topRightY, 0, 0,48, 48, 48, 48,0xffffffff);
        guiGraphics.pose().popMatrix();

    }
    @Unique
    private  void chest_item$renderTooltipBackground_red(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height) {
        int i = x - 3 - 9;
        int j = y - 3 - 9;
        int k = width + 3 + 3 + 18;
        int l = height + 3 + 3 + 18;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,"tooltip/red"), i, j, k, l);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "tooltip/background"), i, j, k, l);
    }
    @Unique
    public void si1_21_4$renderTooltipBackground(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int z) {
        // 左上角
        int topLeftX = x - 3 - 9+2;
        int topLeftY = y - 3 - 9;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, -2);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_0_0"), 48, 48,  0, 0, topLeftX, topLeftY, 48, 48);
        guiGraphics.pose().popMatrix();

        // 中间位置
        int middleX = x + (width - 48) / 2;
        int middleY = y - 3 - 6;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, -7);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_middle_0"),48,48, 0, 0,  middleX, middleY, 48, 48);
        guiGraphics.pose().popMatrix();


        // 右上角
        int topRightX = x + width + 3 - 48+6;
        int topRightY = y - 3 - 9;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, -2);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_0_1"), 48, 48, 0, 0, topRightX, topRightY, 48, 48);
        guiGraphics.pose().popMatrix();

        // 左下角
        int bottomLeftX = x - 3 - 9 + 2;
        int bottomLeftY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, 4);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_1_0"),48, 48,0, 0, bottomLeftX, bottomLeftY, 48, 48);
        guiGraphics.pose().popMatrix();

        // 右下角
        int bottomRightX = x + width + 3 - 48 + 6;
        int bottomRightY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, 4);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_1_1"),48, 48, 0, 0, bottomRightX, bottomRightY, 48, 48);
        guiGraphics.pose().popMatrix();
    }


    @Unique
    public void si1_21_4$renderItemBlackShadowTooltipBackground(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int z) {
        // 左上角
        int topLeftX = x - 3 - 9+2;
        int topLeftY = y - 3 - 9;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, -2);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/black_shadow/tool_0_0"), 48, 48,  0, 0, topLeftX, topLeftY, 48, 48);
        guiGraphics.pose().popMatrix();

        // 中间位置
        int middleX = x + (width - 48) / 2;
        int middleY = y - 3 - 6;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, -7);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/black_shadow/tool_middle_0"),48,48, 0, 0,  middleX, middleY, 48, 48);
        guiGraphics.pose().popMatrix();


        // 右上角
        int topRightX = x + width + 3 - 48+6;
        int topRightY = y - 3 - 9;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, -2);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/black_shadow/tool_0_1"), 48, 48, 0, 0, topRightX, topRightY, 48, 48);
        guiGraphics.pose().popMatrix();

        // 左下角
        int bottomLeftX = x - 3 - 9 + 2;
        int bottomLeftY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, 4);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/black_shadow/tool_1_0"),48, 48,0, 0, bottomLeftX, bottomLeftY, 48, 48);
        guiGraphics.pose().popMatrix();

        // 右下角
        int bottomRightX = x + width + 3 - 48 + 6;
        int bottomRightY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(0.0F, 4);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,
                Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/black_shadow/tool_1_1"),48, 48, 0, 0, bottomRightX, bottomRightY, 48, 48);
        guiGraphics.pose().popMatrix();
    }


    @Unique
    public void si1_21_4$renderItemBlackShadowTooltipBackground_CHAOS(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int z) {
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(-8, -8);
        {
            // 左上角
            int topLeftX = x - 3 - 9 + 3;
            int topLeftY = y - 3 - 9;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -2);
            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_0_0.png"), topLeftX, topLeftY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();

            // 中间位置
            int middleX = x + (width - 48) / 2;
            int middleY = y - 3 - 14;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -7);
            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_middle_0.png"), middleX, middleY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();


            // 右上角
            int topRightX = x + width + 3 - 48 + 6;
            int topRightY = y - 3 - 9;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -2);
            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_0_1.png"), topRightX, topRightY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();

            // 左下角
            int bottomLeftX = x - 3 - 9 ;
            int bottomLeftY = y + height + 3 - 48 + 4;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -2);
            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_1_0.png"), bottomLeftX, bottomLeftY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();

            // 右下角
            int bottomRightX = x + width + 3 - 48 + 6;
            int bottomRightY = y + height + 3 - 48 + 4;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, 4);
            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_1_1.png"), bottomRightX, bottomRightY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();
        }
        guiGraphics.pose().popMatrix();
    }
    @Unique
    private  void chest_item$renderTooltipBackground(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height) {
        int i = x - 3 - 9;
        int j = y - 3 - 9;
        int k = width + 3 + 3 + 18;
        int l = height + 3 + 3 + 18;
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,"tooltip/frame"), i, j, k, l);
    }
    @Unique
    private  void chest_item$renderItemBlackShadowTooltipBackground_EvilMother(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height) {
        int i = x - 3 - 9;
        int j = y - 3 - 9;
        int k = width + 3 + 3 + 18;
        int l = height + 3 + 3 + 18;
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "tooltip/evil_mother/frame"), i, j, k, l);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "tooltip/evil_mother/background"), i, j, k, l);
    }
    @Unique
    private  void chest_item$renderItemBlackShadowTooltipBackground(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height) {
        int i = x - 3 - 9;
        int j = y - 3 - 9;
        int k = width + 3 + 3 + 18;
        int l = height + 3 + 3 + 18;
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "tooltip/black_shadow/frame"), i, j, k, l);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "tooltip/black_shadow/background"), i, j, k, l);
    }
    @Unique
    private  void chest_item$renderItemBoneTooltipBackground(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height) {
        int i = x - 3 - 9;
        int j = y - 3 - 9;
        int k = width + 3 + 3 + 18;
        int l = height + 3 + 3 + 18;
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "tooltip/bone/frame"), i, j, k, l);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "tooltip/bone/background"), i, j, k, l);
    }
    @Inject(at = @At(value = "RETURN"),method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void TheImprintOfTheSoulBlackLight(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (!ConfigC.config.RenderItemTooltip.get()){
            return;
        }
        if (stack.getItem() instanceof TheChaos soul) {
            Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/shadow/black.png");
            GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
            if (entity instanceof Player player) {
                float aFloat = 0;
                if (player.isAlive()) {
                    aFloat = player.getData(AttReg.black_shadowAttachmentType.get());
                }
                int size = 48;
                if (aFloat > 255) {
                    aFloat = 255;
                }

                if (aFloat == 0) {
                    return;
                }
                int color = soul.soulColor();
                int as = (color >> 24) & 0xFF;
                int rs = (color >> 16) & 0xFF;
                int gs = (color >> 8) & 0xFF;
                int bs = color & 0xFF;

                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, identifier, x - size / 3, y - size / 3, 0, 0, size, size, size, size,
                        Light.ARGB.color((int) aFloat / 2, rs, gs, bs));

                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_2.png"),
                        x - 128 + 72, y - 128 + 72,
                        0, 0,
                        128, 128, 128, 128,
                        Light.ARGB.color((int) aFloat, rs, gs, bs));

                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_3.png"),
                        x - 96 / 3 - 8, y - 96 / 3 - 8, 0, 0, 96, 96, 96, 96,
                        Light.ARGB.color((int) aFloat, rs, gs, bs));
            }
        }
    }
    @Inject(at = @At(value = "HEAD"),method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void TheImprintOfTheSoulBlackLightHEAD(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (!ConfigC.config.RenderItemTooltip.get()){
            return;
        }

        if (stack.getItem() instanceof IGUILightList lightList){
            if (lightList.guiLight(entity)!=null) {
                if (lightList.guiLight(entity).doLight()) {
                    return;
                }
            }
        }
        if (stack.getItem() instanceof NotLight ){
            return;
        }
        if (stack.getItem() instanceof IBlackLight ){
            return;
        }
        CompoundTag tag = stack.get(DataReg.tag);
        if (tag!= null) {
            if (tag.getBooleanOr(IBlackLight.blackName,false)) {
                return;
            }
        }
        if (stack.getItem() instanceof ItemBlackShadow soul && !(stack.getItem() instanceof ILight)) {
            Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/shadow/black.png");
            GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
            if (entity instanceof Player player) {
                float aFloat = 0;
                if (player.isAlive()) {
                    aFloat = player.getData(AttReg.black_shadowAttachmentType.get());
                }
                int size = 48;
                if (aFloat > 255) {
                    aFloat = 255;
                }
                if (aFloat == 0) {
                    return;
                }
                int color = soul.color(stack);
                if (stack.getItem() instanceof TheImprintOfTheSoul theImprintOfTheSoul){
                    color = theImprintOfTheSoul.soulColor();
                }
                int as = (color >> 24) & 0xFF;
                int rs = (color >> 16) & 0xFF;
                int gs = (color >> 8) & 0xFF;
                int bs = color & 0xFF;

                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, identifier, x - size / 3, y - size / 3, 0, 0, size, size, size, size,
                        Light.ARGB.color((int) aFloat/5, rs, gs, bs));

                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_2.png"),
                        x - 96 / 3 - 8, y - 96 / 3 - 8,
                        0, 0,
                        96, 96, 96, 96,
                        Light.ARGB.color((int) ((int) aFloat/2.5), rs, gs, bs));

                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_3.png"),
                        x - size / 3, y - size / 3, 0, 0, size, size, size, size,
                        Light.ARGB.color((int) aFloat/2, rs, gs, bs));
            }
        }
        if (stack.getItem() instanceof ItemBone bone) {
            Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/shadow/black.png");
            GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
            if (entity instanceof Player player) {
                float aFloat = 0;
                if (player.isAlive()) {
                    aFloat = player.getData(AttReg.black_shadowAttachmentType.get());
                }
                int size = 48;
                if (aFloat > 255) {
                    aFloat = 255;
                }
                if (aFloat == 0) {
                    return;
                }
                int color = bone.color(stack);
                int as = (color >> 24) & 0xFF;
                int rs = (color >> 16) & 0xFF;
                int gs = (color >> 8) & 0xFF;
                int bs = color & 0xFF;

                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, identifier, x - size / 3, y - size / 3, 0, 0, size, size, size, size,
                        Light.ARGB.color((int) aFloat/5, rs, gs, bs));

                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_2.png"),
                        x - 96 / 3 - 8, y - 96 / 3 - 8,
                        0, 0,
                        96, 96, 96, 96,
                        Light.ARGB.color((int) ((int) aFloat/2.5), rs, gs, bs));

                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_3.png"),
                        x - size / 3, y - size / 3, 0, 0, size, size, size, size,
                        Light.ARGB.color((int) aFloat/2, rs, gs, bs));
            }
        }
        if (stack.getItem() instanceof ItemBase base && stack.getItem() instanceof ILight light) {
            GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
            float aFloat = 255;
            int size = 48;
            int color = base.color(stack);
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;
            if (!light.isWhirlpool()) {
                RenderPipeline renderPipeline = MRender.RenderPs.GUI_TEXTURED;

                guiGraphics.blit(renderPipeline, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/big/black_2.png"),
                        x - 96 / 3 - 8, y - 96 / 3 - 8,
                        0, 0,
                        96, 96, 96, 96,
                        Light.ARGB.color((int) ((int) aFloat / 2.5), rs, gs, bs));

                guiGraphics.blit(renderPipeline, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/big/black_3.png"),
                        x - size / 3, y - size / 3, 0, 0, size, size, size, size,
                        Light.ARGB.color((int) aFloat / 2, rs, gs, bs));
            } else {
//                for (int i = 1; i < 3; i++) {
//                    guiGraphics.blit(MRender.RenderPs.whirlpool(true, 1), Identifier.fromNamespaceAndPath(Chestitem.MODID,
//                                    "textures/shadow/big/black_3.png"),
//                            x - size / 3, y - size / 3, 0, 0, size, size, size, size,
//                            Light.ARGB.color((int) aFloat / 2, rs, gs, bs));
//                }
            }
        }
    }
    @Inject(at = @At(value = "HEAD"),method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void TheBlackHEAD(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (!ConfigC.config.RenderItemTooltip.get()) {
            return;
        }

        if (entity == null) {
            return;
        }
        int r = 20;
        int g = 0;
        int b = 10;
        RenderPipeline renderPipeline = MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction;
        int a = 200;

        if (stack.getItem() instanceof IBlackLight iBlackLight) {
            r = iBlackLight.colorBlack().r();
            g = iBlackLight.colorBlack().g();
            b = iBlackLight.colorBlack().b();
            renderPipeline = iBlackLight.colorBlack().renderPipeline();
            a = iBlackLight.colorBlack().a();
        }
        if ((stack.getItem() instanceof IBlackLight)  || Handler.isBlackChaos(stack)) {


            GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
            {
                Identifier fire = Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/fire_black.png");
                float alpha = (float) (50 * Math.sin((EventMain.time + seed)));
                guiGraphics.blit(renderPipeline, fire,
                        x - 8, y - 8, 0, 0,
                        32, 32, 32, 32,
                        Light.ARGB.color((int) (a + alpha), r, g, b));

                float alpha1 = (float) (50 * Math.sin((EventMain.time + seed) / 2f));
                guiGraphics.blit(renderPipeline, fire,
                        x - 8, y - 8, 0, 0,
                        32, 32, 32, 32,
                        Light.ARGB.color(-((int) (a + alpha1)), r, g, b));


                float alpha2 = (float) (50 * Math.sin((EventMain.time + seed) * 2f));
                guiGraphics.blit(renderPipeline, fire,
                        x - 8, y - 8, 0, 0,
                        32, 32, 32, 32,
                        Light.ARGB.color(-((int) (a + alpha2)), r, g, b));

                float alpha3 = (float) (10 * Math.sin((EventMain.time + seed) /4F));
                guiGraphics.blit(renderPipeline, fire,
                        x - 8, y - 8, 0, 0,
                        32, 32, 32, 32,
                        Light.ARGB.color(-((int) (a + alpha3)), r, g, b));

            }

            {
                Identifier fire = Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/shadow/ci_star.png");

                pose.pushMatrix();
                pose.translate(8,8);
                {
                    float alphaOffset = (float) (10 * Math.sin(((EventMain.time + seed))/10f));
                    pose.pushMatrix();
                    pose.translate(x, y);
                    pose.rotate(EventMain.time / 25f);
                    pose.translate(-x, -y);
                    guiGraphics.blit(renderPipeline, fire,
                            x - 16, y - 16, 0, 0,
                            32, 32, 32, 32,
                            Light.ARGB.color((int) (a / 5f + alphaOffset*2), r, g, b));
                    pose.popMatrix();
                    {
                        for (int i = 0; i < 4; i++) {
                            pose.pushMatrix();
                            pose.translate(x, y);
                            pose.rotate((EventMain.time + i * 40F) / 25f);
                            pose.translate(-x, -y);
                            guiGraphics.blit(renderPipeline, fire,
                                    x - 16, y - 16, 0, 0,
                                    32, 32, 32, 32,
                                    Light.ARGB.color((int) (a / 5f +alphaOffset), r, g, b));
                            pose.popMatrix();
                        }
                        for (int i = 0; i < 4; i++) {
                            pose.pushMatrix();
                            pose.translate(x, y);
                            pose.rotate((EventMain.time - i * 40F) / 25f);
                            pose.translate(-x, -y);
                            guiGraphics.blit(renderPipeline, fire,
                                    x - 16, y - 16, 0, 0,
                                    32, 32, 32, 32,
                                    Light.ARGB.color((int) (a / 5f +alphaOffset), r, g, b));
                            pose.popMatrix();
                        }
                    }
                }

                pose.popMatrix();
            }
        }
    }
    @Override
    public void cI1_21_11$stringBlack( int x, int y) {
        if (!ConfigC.config.RenderItemTooltip.get()) {
            return;
        }
        int r = 20;
        int g = 0;
        int b = 10;
        RenderPipeline renderPipeline = MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction;
        int a = 200;
        Identifier star = Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/shadow/ci_star.png");
        Identifier fire = Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "textures/gui/tooltip/fire_black.png");


        GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
        {
            float alpha = (float) (50 * Math.sin((EventMain.time  + Mth.nextFloat(RandomSource.create(),-1,1))));
            guiGraphics.blit(renderPipeline, fire,
                    x - 8, y - 8, 0, 0,
                    32, 32, 32, 32,
                    Light.ARGB.color((int) (a + alpha), r, g, b));

            float alpha1 = (float) (50 * Math.sin((EventMain.time + Mth.nextFloat(RandomSource.create(),-1,1)) / 2f));
            guiGraphics.blit(renderPipeline, fire,
                    x - 8, y - 8, 0, 0,
                    32, 32, 32, 32,
                    Light.ARGB.color(-((int) (a + alpha1)), r, g, b));


            float alpha2 = (float) (50 * Math.sin((EventMain.time + Mth.nextFloat(RandomSource.create(),-1,1)) * 2f));
            guiGraphics.blit(renderPipeline, fire,
                    x - 8, y - 8, 0, 0,
                    32, 32, 32, 32,
                    Light.ARGB.color(-((int) (a + alpha2)), r, g, b));

            float alpha3 = (float) (10 * Math.sin((EventMain.time + Mth.nextFloat(RandomSource.create(),-1,1)) /4F));
            guiGraphics.blit(renderPipeline, fire,
                    x - 8, y - 8, 0, 0,
                    32, 32, 32, 32,
                    Light.ARGB.color(-((int) (a + alpha3)), r, g, b));

        }

        {
            pose.pushMatrix();
            pose.translate(8,8);
            {
                float alphaOffset = (float) (10 * Math.sin(((EventMain.time + Mth.nextFloat(RandomSource.create(),-1,1)))/10f));
                pose.pushMatrix();
                pose.translate(x, y);
                pose.rotate(EventMain.time / 25f);
                pose.translate(-x, -y);
                guiGraphics.blit(renderPipeline, star,
                        x - 16, y - 16, 0, 0,
                        32, 32, 32, 32,
                        Light.ARGB.color((int) (a / 5f + alphaOffset*2), r, g, b));
                pose.popMatrix();
                {
                    for (int i = 0; i < 4; i++) {
                        pose.pushMatrix();
                        pose.translate(x, y);
                        pose.rotate((EventMain.time + i * 40F) / 25f);
                        pose.translate(-x, -y);
                        guiGraphics.blit(renderPipeline, star,
                                x - 16, y - 16, 0, 0,
                                32, 32, 32, 32,
                                Light.ARGB.color((int) (a / 5f +alphaOffset), r, g, b));
                        pose.popMatrix();
                    }
                    for (int i = 0; i < 4; i++) {
                        pose.pushMatrix();
                        pose.translate(x, y);
                        pose.rotate((EventMain.time - i * 40F) / 25f);
                        pose.translate(-x, -y);
                        guiGraphics.blit(renderPipeline, star,
                                x - 16, y - 16, 0, 0,
                                32, 32, 32, 32,
                                Light.ARGB.color((int) (a / 5f +alphaOffset), r, g, b));
                        pose.popMatrix();
                    }
                }
            }

            pose.popMatrix();
        }
    }
    @Unique
    private int cI1_21_11$time;

    @Inject(at = @At(value = "RETURN"),method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void renderItem(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (stack.getItem() instanceof TheCelestial celestial){

            Identifier Identifier = celestial.img(stack);
            GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
            int color = celestial.soulColor(stack);
            int as = (color >> 24) & 0xFF;
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;

            float firstCharX = 0;
            float firstCharY = 0;
            float radius = 1f;
            float angle = EventMain.time / 20F;

            pose.pushMatrix();
            pose.translate( x, y);
            for (int iis = 0; iis < 8; iis++) {
                pose.pushMatrix();

                pose.translate(firstCharX + 4, firstCharY);
                pose.rotate((float) Math.sin(angle + iis) / 7.5f );
                pose.translate((float)(Math.sin(angle + iis) * radius), (float)(Math.cos(angle + iis) * radius));
                pose.translate(-(firstCharX + 4), -firstCharY);
                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier, 0, 0, 0, 0, 16, 16, 16, 16,
                        Light.ARGB.color(as, rs / 8, gs / 8, bs / 8));
                pose.popMatrix();
            }
            pose.popMatrix();

        }
        if (stack.getItem() instanceof TheImprintOfTheSoul soul) {

            Identifier Identifier = soul.Identifier();
            GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
            int color = soul.soulColor();
            int as = (color >> 24) & 0xFF;
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;

            float firstCharX = 0;
            float firstCharY = 0;
            float radius = 1.25F;
            float angle = EventMain.time / 20F;

            pose.pushMatrix();
            pose.translate( x, y);
            for (int iis = 0; iis < 15; iis++) {
                pose.pushMatrix();

                pose.translate(firstCharX + 4, firstCharY);
                pose.rotate((float) Math.sin(angle + iis) / 7.5f );
                pose.translate((float)(Math.sin(angle + iis) * radius), (float)(Math.cos(angle + iis) * radius));
                pose.translate(-(firstCharX + 4), -firstCharY);
                guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction, Identifier, 0, 0, 0, 0, 16, 16, 16, 16,
                        Light.ARGB.color(as / 5, rs, gs, bs));
                pose.popMatrix();
            }
            guiGraphics.blit(MRender.RenderPs.GUI_TEXTURED, Identifier, 0, 0, 0, 0, 16, 16, 16, 16,
                    Light.ARGB.color(as / 3 , rs, gs, bs));
            pose.popMatrix();

        }
    }

    @Inject(at = @At(value = "RETURN"),method = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;item(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void IGUILight(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        GuiGraphicsExtractor guiGraphics = (GuiGraphicsExtractor) (Object) this;
        if (stack.getItem() instanceof IGUILight iguiLight){
            Identifier Identifier = iguiLight.img();
            if (stack.getItem() instanceof IGUILightList ih) {
                GUILight guiLight = ih.guiLight(entity);
                if (guiLight!=null) {
                    if (guiLight.doLight()) {
                        Map<Integer, Integer> colorList = guiLight.listGUIColor();
                        Map<Integer, Identifier> IdentifierMap = guiLight.listImg();
                        Map<Integer, Vec2> vec2Map = guiLight.listPosOffset();

                        int number = guiLight.listNumber();

                        for (int i = 0; i < number; i++) {
                            Integer color = colorList.get(i);
                            Identifier img = IdentifierMap.get(i);
                            Vec2 posOffset = vec2Map.get(i);
                            int as = (color >> 24) & 0xFF;
                            int rs = (color >> 16) & 0xFF;
                            int gs = (color >> 8) & 0xFF;
                            int bs = color & 0xFF;

                            guiGraphics.blit(iguiLight.renderType(), img, (int) (x + posOffset.x), (int) (y + posOffset.y),
                                    0, 0, 16, 16, 16, 16,
                                    Light.ARGB.color(as, rs, gs, bs));
                        }
                    }
                }
            }else {
                int color = iguiLight.guiColor(stack);
                int as = (color >> 24) & 0xFF;
                int rs = (color >> 16) & 0xFF;
                int gs = (color >> 8) & 0xFF;
                int bs = color & 0xFF;
                guiGraphics.blit(iguiLight.renderType(), Identifier, (int) (x + iguiLight.posOffset().x), (int) (y + iguiLight.posOffset().y),
                        0, 0, 16, 16, 16, 16,
                        Light.ARGB.color(as, rs, gs, bs));
            }
        }
    }


}
