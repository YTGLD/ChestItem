package com.ytgld.chest_item.tip.an_element;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.RendererFarm;
import com.ytgld.chest_item.renderer.i.IGuiGraphics;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.tip.an_element.extend.BlackSkill;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class SkillTooltip implements ClientTooltipComponent, TooltipComponent {
    private final SkillList element;
    private final ItemBase itemBase;
    private final ItemStack stack;

    public SkillTooltip(SkillList contents, ItemBase itemBase, ItemStack stack) {
        this.element = contents;
        this.itemBase = itemBase;
        this.stack = stack;
    }

    @Override
    public int getHeight(Font font) {
        return  this.backgroundHeight() + 4;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return this.backgroundWidth();
    }

    private int backgroundWidth() {
        return this.gridSizeX() * 32;
    }

    private int backgroundHeight() {
        return this.gridSizeY() * 32;
    }
    @Override
    public void renderImage(Font font, int x, int y, int width, int height, GuiGraphics guiGraphics) {
        int i = this.gridSizeX();
        int j = this.gridSizeY();
        int s = 0;
        for (int l = 0; l < j; ++l) {
            for (int i1 = 0; i1 < i; ++i1) {
                int j1 = x + i1 * 32;
                int k1 = y + l * 32;
                s++;
                this.renderSlot(font, j1, k1, guiGraphics,s);
            }
        }
    }

    private void renderSlot(Font font,int x, int y, GuiGraphics guiGraphics,int i) {
        i --;
        Map<SkillBase, Identifier> IdentifierMap = element.name();
        Map<SkillBase, Integer> map = element.element(stack);
        Map<SkillBase, Component> stringMap = element.tooltip();
        int color = itemBase.color(stack);
        if (Handler.isBlackChaos(stack)) {
            color = 0xffff0000;
        }
        if (map!=null) {
            SkillBase elt = map.keySet().stream().toList().get(i);
            int number = map.get(elt);
            number++;

            if (number >= elt.levelMax(stack)){
                guiGraphics.drawString(font, Component.translatable("item.chest_item.skill.level").append(": ")
                                .append(Component.translatable("enchantment.level." + number)
                                        .append(Component.translatable("item.chest_item.skill.level.max"))),
                        x + 35, y + 10, color, false);
            }else {
                guiGraphics.drawString(font, Component.translatable("item.chest_item.skill.level").append(": ").append(Component.translatable("enchantment.level." + number)),
                        x + 35, y + 10, color, false);
            }
            String baneName = elt.baneName();
            String mixinName = baneName + SkillBase.skillBaseXP;
            int sx = 0;
            CompoundTag compoundTag = stack.get(DataReg.tag);
            if (compoundTag!=null) {
                sx = compoundTag.getIntOr(mixinName, 0);
            }
            guiGraphics.drawString(font, Component.translatable( "item.chest_item.skill.xp").append(": ").append(String.valueOf(sx)), x+35, y+20,color , false);

        }
        if (IdentifierMap!=null) {
            SkillBase elt = IdentifierMap.keySet().stream().toList().get(i);
            Identifier Identifier1 = IdentifierMap.get(elt);
            if (Handler.isBlackChaos(stack)) {
                if (guiGraphics instanceof IGuiGraphics iGuiGraphics) {
                    new RendererFarm(guiGraphics.pose(), iGuiGraphics.cI1_21_11$guiRenderState(), 0xffff8080)
                            .chest_item$blit(MRender.RenderPs.LightSlowness(false,0.1f,10), Identifier.fromNamespaceAndPath(Identifier1.getNamespace(),
                                            "textures/gui/sprites/"+Identifier1.getPath()+".png"),
                                    x, y, 0, 0, 32, 32, 32, 32);
                }
            }else {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier1, x, y, 32, 32);
            }

            if (!Handler.isBlackChaos(stack)) {
                if (!(elt instanceof BlackSkill)) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID, "frame"), x, y, 32, 32);
                } else {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID, "frame_black"), x, y, 32, 32);
                }
            }else {
                guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(Chestitem.MODID,
                        "frame_chaos"), x, y, 32, 32);
            }
        }
        if (stringMap!=null) {
            SkillBase elt = stringMap.keySet().stream().toList().get(i);
            Component component= stringMap.get(elt);
            guiGraphics.drawString(font, component, x+35, y, color, false);
        }
    }

    private int gridSizeX() {
       return 1;
    }

    private int gridSizeY() {
        Map<SkillBase, Integer> map = element.element(stack);
        if (map!=null) {
            return map.size();
        }else return 0;
    }
}
