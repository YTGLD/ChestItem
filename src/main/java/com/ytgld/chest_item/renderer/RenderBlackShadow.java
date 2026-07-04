package com.ytgld.chest_item.renderer;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.renderer.gui_particles.BlackKey;
import com.ytgld.chest_item.renderer.gui_particles.BlackParticlesAdd;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2f;

public class RenderBlackShadow {
    public static void mouseUse(int x , int y, ItemStack stack){
        if (EventMain.time % 2 == 0) {
            if (stack.getItem() instanceof IBlackLight iBlackLight) {
                for (int i = 0; i < 25; i++) {
                    BlackParticlesAdd.markSeen(x-8, y-8,new BlackKey.ImageColorAndRenderPipeline(24,
                            new BlackKey.ColorImage(200, iBlackLight.colorBlack().r(), iBlackLight.colorBlack().g(), iBlackLight.colorBlack().b()),
                            Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png"), MRender.RenderPs.GUI_TEXTURED_BLACK_BlendFunction,
                            new Vector2f(),new Vector2f((float) (Math.cos(i) / 10f), (float) (Math.sin(i) / 10f)),new Vector2f(), false));
                }
            }
        }
    }
}
