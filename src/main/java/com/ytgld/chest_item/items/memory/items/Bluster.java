package com.ytgld.chest_item.items.memory.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

/**
 * 咆哮
 * <p>
 *咆哮，恐惧于那愤怒的咆哮
 * <p>
 * 受到伤害增加100%
 * <p>
 *受到伤害增加100%
 */

public class Bluster extends MemoryBase {
    public Bluster(Properties properties) {
        super(properties);
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"bluster_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.BlusterTooltip_.asItem();
    }

    @Override
    public void doText(ItemStack stack, List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("item.chest_item.bluster.string.1").withStyle(ChatFormatting.GRAY));
    }

    public static class  BlusterTooltip extends BaseTooltip {
        public BlusterTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,255,0,100);
        }
        @Override
        public void doText(ItemStack stack, List<Component> tooltipComponents) {
            tooltipComponents.add(Component.translatable("item.chest_item.bluster_tooltip.string.1").setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.add(Component.translatable("item.chest_item.bluster_tooltip.string.2").setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.bluster_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static void damage(LivingDamageEvent.Pre event){
            if (event.getSource().getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player,"chest_item:bluster_tooltip")){
                    event.setNewDamage(event.getNewDamage() * 2);
                }
            }
            if (event.getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player,"chest_item:bluster_tooltip")){
                    event.setNewDamage(event.getNewDamage() * 2);
                }
            }
        }
    }
}
