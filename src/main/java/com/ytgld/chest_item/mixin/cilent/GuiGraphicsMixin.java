package com.ytgld.chest_item.mixin.cilent;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.items.black.celestial.TheCelestial;
import com.ytgld.chest_item.items.black.soul.NotLight;
import com.ytgld.chest_item.items.black.soul.chaos.TheChaos;
import com.ytgld.chest_item.items.condensebone.ItemBone;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.RendererFarm;
import com.ytgld.chest_item.renderer.i.IAbstractContainerScreen;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
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

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements IGuiGraphics {
    @Shadow @Final
    Minecraft minecraft;
    @Shadow @Final
    GuiRenderState guiRenderState;
    @Shadow @Final private Matrix3x2fStack pose;
    @Shadow public abstract int guiWidth();
    @Shadow public abstract int guiHeight();
    @Override
    public void chest_item$addW(ItemStack stack) {
        GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
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


                        new RendererFarm(pose, guiRenderState, Light.ARGB.color((int) (alpha * as), (int) (alpha * rs), (int) ((alpha) * gs), (int) (bs * alpha)))
                                .chest_item$blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                                "textures/gui/tooltip/fire.png"),
                                        (int) adjustedCurrPos.x - 8, (int) adjustedCurrPos.y - 8, 0, 0, 16, 16, 16, 16);

                        new RendererFarm(pose, guiRenderState, Light.ARGB.color((int) (alpha * as), rs, gs, (int) (bs * alpha)))
                                .chest_item$blit(MRender.RenderPs.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                                "textures/gui/tooltip/small_fire.png"),
                                        (int) adjustedPrevPos.x - 8, (int) adjustedPrevPos.y - 8, 0, 0, 16, 16, 16, 16);

                        pose.popMatrix();

                    }
                }
            }
        }
        guiGraphics.pose().popMatrix();
    }
    @Inject(at = @At(value = "RETURN"),method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/Identifier;Lnet/minecraft/world/item/ItemStack;)V")
    public void ytgld$ClientTooltipPositioner(Font font, List<ClientTooltipComponent> components, int x, int y, ClientTooltipPositioner positioner, Identifier background, ItemStack tooltipStack, CallbackInfo ci) {
        if (!ConfigC.config.RenderItemTooltip.get()){
            return;
        }

        if (tooltipStack.getItem() instanceof ItemBase)  {
            RenderTooltipEvent.Pre preEvent = ClientHooks.onRenderTooltipPre(tooltipStack,  (GuiGraphics) (Object) this, x, y, this.guiWidth(), this.guiHeight(), components, font, positioner);
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
                if (tooltipStack.getItem()instanceof ItemBlackShadow){
                    chest_item$renderItemBlackShadowTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j);
                }else if (tooltipStack.getItem() instanceof ItemBone){
                    chest_item$renderItemBoneTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j);
                }else {
                    chest_item$renderTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j);
                }
                this.pose.popMatrix();
                if (tooltipStack.getItem()instanceof Meat){
                    this.pose.pushMatrix();
                    si1_21_4$renderTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j,400);
                    this.pose.popMatrix();
                }
                if (tooltipStack.getItem()instanceof ItemBlackShadow){
                    if (tooltipStack.getItem() instanceof TheChaos){
                        this.pose.pushMatrix();
                        si1_21_4$renderItemBlackShadowTooltipBackground_CHAOS((GuiGraphics) (Object) this, l, i1, i, j, 400);
                        this.pose.popMatrix();
                    }else {
                        this.pose.pushMatrix();
                        si1_21_4$renderItemBlackShadowTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j, 400);
                        this.pose.popMatrix();
                    }
                }

            }
        }
    }
    @Unique
    public void si1_21_4$renderTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int z) {
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
    public void si1_21_4$renderItemBlackShadowTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int z) {
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
    public void si1_21_4$renderItemBlackShadowTooltipBackground_CHAOS(GuiGraphics guiGraphics, int x, int y, int width, int height, int z) {
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(-8, -8);
        {
            // 左上角
            int topLeftX = x - 3 - 9 + 3;
            int topLeftY = y - 3 - 9;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -2);
            guiGraphics.blit(MRender.RenderPs.LightSlowness(false, 0.02f,1111),
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_0_0.png"), topLeftX, topLeftY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();

            // 中间位置
            int middleX = x + (width - 48) / 2;
            int middleY = y - 3 - 14;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -7);
            guiGraphics.blit(MRender.RenderPs.LightSlowness(false, 0.02f,2222),
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_middle_0.png"), middleX, middleY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();


            // 右上角
            int topRightX = x + width + 3 - 48 + 6;
            int topRightY = y - 3 - 9;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, -2);
            guiGraphics.blit(MRender.RenderPs.LightSlowness(false, 0.02f,3333),
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_0_1.png"), topRightX, topRightY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();

            // 左下角
            int bottomLeftX = x - 3 - 9 ;
            int bottomLeftY = y + height + 3 - 48 + 4;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, 4);
            guiGraphics.blit(MRender.RenderPs.LightSlowness(false, 0.02f,4444),
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_1_0.png"), bottomLeftX, bottomLeftY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();

            // 右下角
            int bottomRightX = x + width + 3 - 48 + 6;
            int bottomRightY = y + height + 3 - 48 + 4;
            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().translate(0.0F, 4);
            guiGraphics.blit(MRender.RenderPs.LightSlowness(false, 0.02f,5555),
                    Identifier.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_1_1.png"), bottomRightX, bottomRightY, 0, 0, 64, 64, 64, 64);
            guiGraphics.pose().popMatrix();
        }
        guiGraphics.pose().popMatrix();
    }
    @Unique
    private  void chest_item$renderTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        int i = x - 3 - 9;
        int j = y - 3 - 9;
        int k = width + 3 + 3 + 18;
        int l = height + 3 + 3 + 18;
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,"tooltip/frame"), i, j, k, l);
    }

    @Unique
    private  void chest_item$renderItemBlackShadowTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height) {
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
    private  void chest_item$renderItemBoneTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        int i = x - 3 - 9;
        int j = y - 3 - 9;
        int k = width + 3 + 3 + 18;
        int l = height + 3 + 3 + 18;
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "tooltip/bone/frame"), i, j, k, l);
        guiGraphics.blitSprite(MRender.RenderPs.GUI_TEXTURED,Identifier.fromNamespaceAndPath(Chestitem.MODID,
                "tooltip/bone/background"), i, j, k, l);
    }
    @Inject(at = @At(value = "RETURN"),method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void TheImprintOfTheSoulBlackLight(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (!ConfigC.config.RenderItemTooltip.get()){
            return;
        }
        if (stack.getItem() instanceof TheChaos soul) {
            Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/shadow/black.png");
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
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

                guiGraphics.blit(MRender.RenderPs.LightSlowness(true, 0.1f, 1), identifier, x - size / 3, y - size / 3, 0, 0, size, size, size, size,
                        Light.ARGB.color((int) aFloat / 2, rs, gs, bs));

                guiGraphics.blit(MRender.RenderPs.LightSlowness(true, 0.1f, 2), Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_2.png"),
                        x - 128 + 72, y - 128 + 72,
                        0, 0,
                        128, 128, 128, 128,
                        Light.ARGB.color((int) aFloat, rs, gs, bs));

                guiGraphics.blit(MRender.RenderPs.LightSlowness(true, 0.1f, 3), Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_3.png"),
                        x - 96 / 3 - 8, y - 96 / 3 - 8, 0, 0, 96, 96, 96, 96,
                        Light.ARGB.color((int) aFloat, rs, gs, bs));
            }
        }
    }
    @Inject(at = @At(value = "HEAD"),method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
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
        if (stack.getItem() instanceof ItemBlackShadow soul && !(stack.getItem() instanceof ILight)) {
            Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/shadow/black.png");
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
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
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
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
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
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
                for (int i = 1; i < 3; i++) {
                    guiGraphics.blit(MRender.RenderPs.whirlpool(true, 1), Identifier.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/shadow/big/black_3.png"),
                            x - size / 3, y - size / 3, 0, 0, size, size, size, size,
                            Light.ARGB.color((int) aFloat / 2, rs, gs, bs));
                }
            }
        }
    }
    @Unique
    private int cI1_21_11$time;

    @Inject(at = @At(value = "RETURN"),method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void renderItem(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (stack.getItem() instanceof TheCelestial celestial){
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
            Identifier Identifier = celestial.img(stack);
            int color = celestial.soulColor(stack);
            int as = (color >> 24) & 0xFF;
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;
            guiGraphics.blit(MRender.RenderPs.LightSlowness(true, 0.08f,1111), Identifier, x, y, 0, 0, 16, 16, 16, 16,
                    Light.ARGB.color(as, rs, gs, bs));

        }
        if (stack.getItem() instanceof TheImprintOfTheSoul soul) {
            Identifier Identifier = soul.Identifier();
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
            int color = soul.soulColor();
            int as = (color >> 24) & 0xFF;
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;

            guiGraphics.blit(MRender.RenderPs.LightSlowness(true, 0.05f * (1.4f),1111), Identifier, x, y, 0, 0, 16, 16, 16, 16,
                    Light.ARGB.color(as, rs, gs - 20, bs - 30));

        }
    }

    @Inject(at = @At(value = "RETURN"),method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void IGUILight(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
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
