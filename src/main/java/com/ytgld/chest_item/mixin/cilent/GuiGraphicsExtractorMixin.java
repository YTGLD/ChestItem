package com.ytgld.chest_item.mixin.cilent;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.items.black.chaos_item.ITheChaos;
import com.ytgld.chest_item.items.black.soul.chaos.TheChaos;
import com.ytgld.chest_item.items.condensebone.ItemBone;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.joml.Matrix3x2fStack;
import org.joml.Vector2f;
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
        guiGraphics.pose().popMatrix();
    }
    @Inject(at = @At(value = "RETURN"),method = "tooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/Identifier;Lnet/minecraft/world/item/ItemStack;)V")
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

                if (!(tooltipStack.getItem() instanceof MemoryBase.BaseTooltip)) {
                    if (!Handler.isBlackChaos(tooltipStack)) {


                        if (tooltipStack.getItem() instanceof ItemBlackShadow) {
                            this.pose.pushMatrix();
                            chest_item$renderItemBlackShadowTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                            this.pose.popMatrix();
                        } else if (tooltipStack.getItem() instanceof ItemBone) {
                            this.pose.pushMatrix();
                            chest_item$renderItemBoneTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                            this.pose.popMatrix();
                        } else if (!Handler.isBlackChaos(tooltipStack)) {
                            if (!(tooltipStack.getItem() instanceof IEvil)&& !(tooltipStack.getItem() instanceof com.ytgld.chest_item.items.black.ITheChaos)) {
                                this.pose.pushMatrix();
                                chest_item$renderTooltipBackground((GuiGraphicsExtractor) (Object) this, l, i1, i, j);
                                this.pose.popMatrix();
                            }
                        }

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
    public int chest26_2$timeEvil = 0;
    @Unique
    public void si1_21_4$renderTooltipBackground_EvilMother(GuiGraphicsExtractor guiGraphics, int x, int y, int width, int height, int z) {
        chest26_2$timeEvil++;
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
        BlackParticlesAdd.markSeen(middleX + 24, middleY,new BlackKey.ImageColorAndRenderPipeline(16,
                new BlackKey.ColorImage(200, 70,240,210),
                Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png"), MRender.RenderPs.GUI_TEXTURED,
                new Vector2f(),new Vector2f(0,-0.05f),new Vector2f(), false));
       if (chest26_2$timeEvil % 2 == 1){

           BlackParticlesAdd.markSeen(x, middleY + 4,new BlackKey.ImageColorAndRenderPipeline(8,
                   new BlackKey.ColorImage(200, 70,240,210),
                   Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png"), MRender.RenderPs.GUI_TEXTURED,
                   new Vector2f(),new Vector2f(0,-0.025f),new Vector2f(), false));

           BlackParticlesAdd.markSeen(x + width + 3 - 6, middleY + 4,new BlackKey.ImageColorAndRenderPipeline(8,
                   new BlackKey.ColorImage(200, 70,240,210),
                   Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png"), MRender.RenderPs.GUI_TEXTURED,
                   new Vector2f(),new Vector2f(0,-0.025f),new Vector2f(), false));
       }
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
        BlackParticlesAdd.markSeen(middleX + 24, middleY + 8,new BlackKey.ImageColorAndRenderPipeline(16,
                new BlackKey.ColorImage(255, 0,0,0),
                Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png"), MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction,
                new Vector2f(),new Vector2f(0,-0.05f),new Vector2f(), false));
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
}
