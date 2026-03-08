package com.ytgld.chest_item.mixin.cilent;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.items.black.ITheChaos;
import com.ytgld.chest_item.items.black.celestial.TheCelestial;
import com.ytgld.chest_item.items.black.soul.NotLight;
import com.ytgld.chest_item.items.black.soul.chaos.TheChaos;
import com.ytgld.chest_item.items.condensebone.ItemBone;
import com.ytgld.chest_item.renderer.*;
import com.ytgld.chest_item.renderer.i.IAbstractContainerScreen;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.GuiSpriteManager;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements IGuiGraphics {
    @Shadow @Final private Minecraft minecraft;
    @Shadow public abstract int guiWidth();
    @Shadow public abstract int guiHeight();

    @Shadow public abstract void flush();

    @Shadow private boolean managed;

    @Shadow private ItemStack tooltipStack;

    @Shadow public abstract PoseStack pose();

    @Shadow @Final private GuiSpriteManager sprites;

    @Shadow @Final private PoseStack pose;

    @Unique
    ItemStack cI1_21_9$itemstack = ItemStack.EMPTY;
    @Override
    public void chest_item$addW(ItemStack stack) {
        GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
        if (stack.getItem() instanceof Terror){
            cI1_21_9$itemstack = stack;
        }
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
                        {
                            //随机位置
                            pose.translate((float) Math.sin(adjustedPrevPos.x) * 4, (float) Math.sin(adjustedCurrPos.y) * 4,0);
                            //位置改变
                            if (!adjustedPrevPos.equals(adjustedCurrPos)) {
                                pose.translate(0, -13,0);
                            }else {
                                pose.translate(0, -4,0);
                            }
                            pose.scale(alpha * 1.55f,alpha * 1.55f,alpha * 1.55f);
                            //上升
                            if (!adjustedPrevPos.equals(adjustedCurrPos)) {
                                pose.translate(0, (alpha) * 10,0);
                            }else {
                                pose.translate(0, (alpha) * 3,0);
                            }
                        }

                        pose.translate(-prevPos.x, -prevPos.y,0);

                        int color = iAbstractContainerScreen.cI1_21_9$color();
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
    @Inject(at = @At(value = "RETURN"),method = "renderTooltipInternal(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)V")
    public void moonstone$ClientTooltipPositioner(Font p_282675_, List<ClientTooltipComponent> p_282615_, int x, int y, ClientTooltipPositioner p_282442_, CallbackInfo ci) {
        if (!ConfigC.config.RenderItemTooltip.get()){
            return;
        }
        if (tooltipStack.getItem() instanceof ItemBase) {
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
            if (tooltipStack.getItem() instanceof ItemBlackShadow) {
                TooltipRenderUtil.renderTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j, 400,
                        Light.ARGB.color(0, 72/3,61/3,139/3),
                        Light.ARGB.color(0, 72/3,61/3,139/3),
                        Light.ARGB.color(255, 106, 90, 205),
                        Light.ARGB.color(255, 72, 61, 139));

            } else if (tooltipStack.getItem() instanceof ItemBone){
                TooltipRenderUtil.renderTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j, 400,
                        Light.ARGB.color(0, 0, 10, 33),
                        Light.ARGB.color(0, 0, 10, 33),
                        Light.ARGB.color(255, 50, 255, 20),
                        Light.ARGB.color(255, 50, 50, 100));
            }else {
                TooltipRenderUtil.renderTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j, 400,
                        Light.ARGB.color(0, 218, 165, 32),
                        Light.ARGB.color(0, 218, 165, 32),
                        Light.ARGB.color(255, 218, 165, 32),
                        Light.ARGB.color(255, 219, 112, 147));
            }
            this.pose.popPose();
            if (tooltipStack.getItem() instanceof Meat) {
                this.pose.pushPose();
                si1_21_4$renderTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j, 1000);
                this.pose.popPose();
            }
            if (tooltipStack.getItem() instanceof ItemBlackShadow) {
                if (tooltipStack.getItem() instanceof TheChaos||tooltipStack.getItem() instanceof ITheChaos){
                    this.pose.pushPose();
                    si1_21_4$renderItemBlackShadowTooltipBackground_CHAOS((GuiGraphics) (Object) this, l, i1, i, j, 1000);
                    this.pose.popPose();
                }else {
                    this.pose.pushPose();
                    si1_21_4$renderItemBlackShadowTooltipBackground((GuiGraphics) (Object) this, l, i1, i, j, 1000);
                    this.pose.popPose();
                }
            }
        }
    }
    @Unique
    public void si1_21_4$renderItemBlackShadowTooltipBackground_CHAOS(GuiGraphics guiGraphics, int x, int y, int width, int height, int z) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(-8, -8,0);
        {
            // 左上角
            int topLeftX = x - 3 - 9 + 3;
            int topLeftY = y - 3 - 9;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, -2,z);
            MGuiGraphicsCI_LifeSlowness.blit(guiGraphics,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_0_0.png"), topLeftX, topLeftY, 0, 0, 64, 64, 64, 64,1,1,1,1);
            guiGraphics.pose().popPose();

            // 中间位置
            int middleX = x + (width - 48) / 2;
            int middleY = y - 3 - 14;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, -7,z);
            MGuiGraphicsCI_LifeSlowness.blit(guiGraphics,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_middle_0.png"), middleX, middleY, 0, 0,64, 64, 64, 64,1,1,1,1);
            guiGraphics.pose().popPose();


            // 右上角
            int topRightX = x + width + 3 - 48 + 6;
            int topRightY = y - 3 - 9;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, -2,z);
            MGuiGraphicsCI_LifeSlowness.blit(guiGraphics,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_0_1.png"), topRightX, topRightY, 0, 0,64, 64, 64, 64,1,1,1,1);
            guiGraphics.pose().popPose();

            // 左下角
            int bottomLeftX = x - 3 - 9 ;
            int bottomLeftY = y + height + 3 - 48 + 4;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, -2,z);
            MGuiGraphicsCI_LifeSlowness.blit(guiGraphics,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_1_0.png"), bottomLeftX, bottomLeftY, 0, 0,64, 64, 64, 64,1,1,1,1);
            guiGraphics.pose().popPose();

            // 右下角
            int bottomRightX = x + width + 3 - 48 + 6;
            int bottomRightY = y + height + 3 - 48 + 4;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, 4,z);
            MGuiGraphicsCI_LifeSlowness.blit(guiGraphics,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                            "textures/gui/tooltip/chaos/tool_1_1.png"), bottomRightX, bottomRightY, 0, 0,64, 64, 64, 64,1,1,1,1);
            guiGraphics.pose().popPose();
        }
        guiGraphics.pose().popPose();
    }
    @Unique
    public void si1_21_4$renderTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int z) {
        // 左上角
        int topLeftX = x - 3 - 9+2;
        int topLeftY = y - 3 - 9;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -2,z);
        MGuiGraphics.blit(guiGraphics,
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/tool_0_0.png"), topLeftX, topLeftY, 0, 0,48, 48, 48, 48,1,1,1,1);
        guiGraphics.pose().popPose();

        // 中间位置
        int middleX = x + (width - 48) / 2;
        int middleY = y - 3 - 6;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -7,z);
        guiGraphics.blitSprite(
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/tool_middle_0"),48,48, 0, 0,  middleX, middleY, 48, 48);
        guiGraphics.pose().popPose();


        // 右上角
        int topRightX = x + width + 3 - 48+6;
        int topRightY = y - 3 - 9;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -2,z);
        MGuiGraphics.blit(guiGraphics,
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/tool_0_1.png"), topRightX, topRightY, 0, 0,48, 48, 48, 48,1,1,1,1);
        guiGraphics.pose().popPose();

        // 左下角
        int bottomLeftX = x - 3 - 9 + 2;
        int bottomLeftY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 4,z);

        MGuiGraphics.blit(guiGraphics,
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/tool_1_0.png"), bottomLeftX, bottomLeftY, 0, 0,48, 48, 48, 48,1,1,1,1);
        guiGraphics.pose().popPose();

        // 右下角
        int bottomRightX = x + width + 3 - 48 + 6;
        int bottomRightY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 4,z);
        MGuiGraphics.blit(guiGraphics,
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/tool_1_1.png"), bottomRightX, bottomRightY, 0, 0,48, 48, 48, 48,1,1,1,1);
        guiGraphics.pose().popPose();
    }
    @Unique
    public void si1_21_4$renderItemBlackShadowTooltipBackground(GuiGraphics guiGraphics, int x, int y, int width, int height, int z) {
        // 左上角
        int topLeftX = x - 3 - 9+2;
        int topLeftY = y - 3 - 9;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -2,z);
        MGuiGraphics.blit(guiGraphics,
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/black_shadow/tool_0_0.png"), topLeftX, topLeftY, 0, 0,48, 48, 48, 48,1,1,1,1);

        guiGraphics.pose().popPose();

        // 中间位置
        int middleX = x + (width - 48) / 2;
        int middleY = y - 3 - 6;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -7,z);
        guiGraphics.blitSprite(
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "tooltip/black_shadow/tool_middle_0"),48,48, 0, 0,  middleX, middleY, 48, 48);
        guiGraphics.pose().popPose();


        // 右上角
        int topRightX = x + width + 3 - 48+6;
        int topRightY = y - 3 - 9;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, -2,z);
        MGuiGraphics.blit(guiGraphics,
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/black_shadow/tool_0_1.png"), topRightX, topRightY, 0, 0,48, 48, 48, 48,1,1,1,1);
        guiGraphics.pose().popPose();

        // 左下角
        int bottomLeftX = x - 3 - 9 + 2;
        int bottomLeftY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 4,z);
        MGuiGraphics.blit(guiGraphics,
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/black_shadow/tool_1_0.png"), bottomLeftX, bottomLeftY, 0, 0,48, 48, 48, 48,1,1,1,1);
        guiGraphics.pose().popPose();

        // 右下角
        int bottomRightX = x + width + 3 - 48 + 6;
        int bottomRightY = y + height + 3 - 48 + 4;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 4,z);
        MGuiGraphics.blit(guiGraphics,
                ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                        "textures/gui/tooltip/black_shadow/tool_1_1.png"), bottomRightX, bottomRightY, 0, 0,48, 48, 48, 48,1,1,1,1);

        guiGraphics.pose().popPose();
    }
    @Inject(at = @At(value = "RETURN"),method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void TheImprintOfTheSoulBlackLight(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (!ConfigC.config.RenderItemTooltip.get()){
            return;
        }
        if (stack.getItem() instanceof TheChaos soul) {
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/shadow/black.png");
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
            if (entity instanceof Player player) {
                float aFloat = player.getData(AttReg.black_shadowAttachmentType.get());
                int size = 48;
                if (aFloat > 255) {
                    aFloat = 255;
                }

                if (aFloat == 0) {
                    return;
                }
                int color = soul.soulColor();
                int as = ((color >> 24) & 0xFF) /255;
                int rs = ((color >> 16) & 0xFF) /255;
                int gs = ((color >> 8) & 0xFF) /255;
                int bs = (color & 0xFF) /255;
                aFloat /= 255;


                MGuiGraphicsCI_Life.blit(guiGraphics,resourceLocation, x - size / 3f, y - size / 3f, 0, 0, size, size, size, size,
                        rs, gs, bs,aFloat);

                MGuiGraphicsCI_Life.blit(guiGraphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_2.png"),
                        x - 128 + 72 , y - 128 + 72,
                        0, 0,
                        128, 128, 128, 128,
                        rs, gs, bs,aFloat);

                MGuiGraphicsCI_Life.blit(guiGraphics,ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_3.png"),
                        x - 96 / 3f - 8, y - 96 / 3f - 8, 0, 0, 96, 96, 96, 96,
                        rs, gs, bs,aFloat);
            }
        }
    }
    @Inject(at = @At(value = "HEAD"),method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void TheImprintOfTheSoulBlackLightHEAD(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (true) {
            return;
        }
        if (!ConfigC.config.RenderItemTooltip.get()){
            return;
        }
        if (stack.getItem() instanceof NotLight) {
            return;
        }
        if (stack.getItem() instanceof IGUILightList lightList){
            if (lightList.guiLight()!=null) {
                if (lightList.guiLight().doLight()) {
                    return;
                }
            }
        }
        if (stack.getItem() instanceof IGUILight){
            return;
        }
        if (stack.getItem() instanceof ItemBone bone) {
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/shadow/black.png");
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
            if (entity instanceof Player player) {
                float aFloat = player.getData(AttReg.black_shadowAttachmentType.get());
                int size = 48;
                if (aFloat > 255) {
                    aFloat = 255;
                }
                if (aFloat == 0) {
                    return;
                }
                int color = bone.color(stack);
                int as = ((color >> 24) & 0xFF) /255;
                int rs = ((color >> 16) & 0xFF) /255;
                int gs = ((color >> 8) & 0xFF) /255;
                int bs = (color & 0xFF) /255;
                aFloat /= 255;





                MGuiGraphics.blit(guiGraphics, resourceLocation, x - (float) size / 3, y - (float) size / 3, 0, 0, size, size, size, size,
                        rs, gs, bs,aFloat);

                MGuiGraphics.blit(guiGraphics, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_2.png"),
                        x - (float) 96 / 3 - 8, y - (float) 96 / 3 - 8,
                        0, 0,
                        96, 96, 96, 96,
                        rs, gs, bs,aFloat);

                MGuiGraphics.blit(guiGraphics, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_3.png"),
                        x - (float) size / 3, y - (float) size / 3, 0, 0, size, size, size, size,
                        rs, gs, bs,aFloat);
            }
        }
        if (stack.getItem() instanceof ItemBase base && stack.getItem() instanceof ILight light) {
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
            float aFloat = 255;
            int size = 48;
            int color = base.color(stack);
            int as = ((color >> 24) & 0xFF) /255;
            int rs = ((color >> 16) & 0xFF) /255;
            int gs = ((color >> 8) & 0xFF) /255;
            int bs = (color & 0xFF) /255;
            aFloat /= 255;

            if (!light.isWhirlpool()) {

                guiGraphics.blit( ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/big/black_2.png"),
                        x - 96 / 3 - 8, y - 96 / 3 - 8,
                        0, 0,
                        96, 96, 96, 96,
                        Light.ARGB.color(0, rs, gs, bs));

                guiGraphics.blit( ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/big/black_3.png"),
                        x - size / 3, y - size / 3, 0, 0, size, size, size, size,
                        Light.ARGB.color(0, rs, gs, bs));
            }else {
                for (int i = 1; i < 3; i++) {
                    MGuiGraphics.GUI_whirlpool.blit(guiGraphics, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                    "textures/shadow/big/black_3.png"),
                            x - (float) size / 3, y - (float) size / 3, 0, 0, size, size, size, size,
                            rs, gs, bs,aFloat);
                }
            }
        }
        if (stack.getItem() instanceof ItemBlackShadow soul && !(stack.getItem() instanceof ILight)) {
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/shadow/black.png");
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
            if (entity instanceof Player player) {
                float aFloat = player.getData(AttReg.black_shadowAttachmentType.get());
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
                int as = ((color >> 24) & 0xFF) /255;
                int rs = ((color >> 16) & 0xFF) /255;
                int gs = ((color >> 8) & 0xFF) /255;
                int bs = (color & 0xFF) /255;
                aFloat /= 255;





                MGuiGraphics.blit(guiGraphics, resourceLocation, x - (float) size / 3, y - (float) size / 3, 0, 0, size, size, size, size,
                      rs, gs, bs,aFloat);

                MGuiGraphics.blit(guiGraphics, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_2.png"),
                        x - (float) 96 / 3 - 8, y - (float) 96 / 3 - 8,
                        0, 0,
                        96, 96, 96, 96,
                        rs, gs, bs,aFloat);

                MGuiGraphics.blit(guiGraphics, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                                "textures/shadow/black_3.png"),
                        x - (float) size / 3, y - (float) size / 3, 0, 0, size, size, size, size,
                        rs, gs, bs,aFloat);
            }
        }
    }

    @Inject(at = @At(value = "RETURN"),method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;III)V")
    public void renderItem(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (stack.getItem() instanceof IGUILightList lightList){
            if (lightList.guiLight()!=null) {
                if (lightList.guiLight().doLight()) {
                    return;
                }
            }
        }
        if (stack.getItem() instanceof IGUILight){
            return;
        }
        if (stack.getItem() instanceof TheCelestial celestial){
            GuiGraphics guiGraphics = (GuiGraphics) (Object) this;
            ResourceLocation Identifier = celestial.img(stack);
            int color = celestial.soulColor(stack);
            int as = (color >> 24) & 0xFF;
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;

            float r = rs / 255f;
            float g = gs / 255f;
            float b = bs / 255f;

            MGuiGraphicsCI_LifeSlowness.blit(guiGraphics,Identifier, x, y,
                    0, 0,16,16,16,16,
                    r,g,b,1);

        }
        if (stack.getItem() instanceof TheImprintOfTheSoul soul){
            ResourceLocation resourceLocation = soul.resourceLocation();
            GuiGraphics guiGraphics =(GuiGraphics) (Object) this;
            int color = soul.soulColor();
            int rs = (color >> 16) & 0xFF;
            int gs = (color >> 8) & 0xFF;
            int bs = color & 0xFF;

            float r = rs / 255f;
            float g = gs / 255f;
            float b = bs / 255f;

            MGuiGraphicsCI_LifeSlowness.blit(guiGraphics,resourceLocation, x, y, 0, 0,16,16,16,16,
                    r,g,b-0.2f,1);
        }
    }
}
