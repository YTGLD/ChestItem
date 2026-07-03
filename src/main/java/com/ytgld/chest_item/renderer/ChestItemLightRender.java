package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.items.black.celestial.CommonCelestial;
import com.ytgld.chest_item.items.black.soul.chaos.TheChaos;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ChestItemLightRender {
    public static void theImprintOfTheSoulBlackLight(GuiGraphicsExtractor guiGraphics,LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        if (!ConfigC.config.RenderItemTooltip.get()){
            return;
        }
        if (stack.getItem() instanceof TheChaos soul) {
            Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/shadow/black.png");
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
    public static void renderCommonCelestialAndTheImprintOfTheSoul(GuiGraphicsExtractor guiGraphics,LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, CallbackInfo ci) {
        var pose = guiGraphics.pose();
        if (stack.getItem() instanceof CommonCelestial celestial){
            Identifier Identifier = celestial.img(stack);
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
}
