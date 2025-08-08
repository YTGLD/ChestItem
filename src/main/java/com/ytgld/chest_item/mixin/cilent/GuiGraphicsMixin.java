package com.ytgld.chest_item.mixin.cilent;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.Terror;
import com.ytgld.chest_item.renderer.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin implements IGuiGraphics {
    @Shadow @Final private Minecraft minecraft;

    @Shadow public abstract void renderItem(ItemStack stack, int x, int y);


    @Shadow @Final private GuiRenderState guiRenderState;

    @Shadow @Final private Matrix3x2fStack pose;
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
                            pose.scale(alpha*1.55f);
                            pose.translate(-prevPos.x, -prevPos.y);
                            new RendererFarm(pose, guiRenderState, Light.ARGB.color((int) (alpha * 100), 255,0, (int) (255*alpha))).chest_item$blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/tooltip/fire.png"),
                                    (int) adjustedCurrPos.x-8, (int) adjustedCurrPos.y-8, 0, 0, 16, 16, 16, 16);

                            new RendererFarm(pose, guiRenderState, Light.ARGB.color((int) (alpha * 100), 255,0, (int) (255*alpha))).chest_item$blit(RenderPipelines.GUI_TEXTURED, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "textures/gui/tooltip/fire.png"),
                                    (int) adjustedPrevPos.x-8, (int) adjustedPrevPos.y-8, 0, 0, 16, 16, 16, 16);
                            pose.popMatrix();

                        }
                    }
                }
            }
            guiGraphics.pose().popMatrix();
        }
    }
}
