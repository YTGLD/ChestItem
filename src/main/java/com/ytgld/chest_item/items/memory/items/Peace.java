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
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

/**
 * 和平
 * <p>
 * 一花一世界，一岁一枯荣
 * <p>
 * 所有都生物不会主动攻击你
 * <p>
 * 伤害降低30%
 */
public class Peace extends MemoryBase {
    public Peace(Properties properties) {
        super(properties);
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"peace_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.PeaceTooltip_.asItem();
    }

    @Override
    public void doText(ItemStack stack, List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("item.chest_item.peace.string.1").withStyle(ChatFormatting.GRAY));
    }

    public static class PeaceTooltip extends BaseTooltip {
        public PeaceTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,10,225,225);
        }
        @Override
        public void doText(ItemStack stack, List<Component> tooltipComponents) {
            tooltipComponents.add(Component.translatable("item.chest_item.peace_tooltip.string.1").setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.add(Component.translatable("item.chest_item.peace_tooltip.string.2").setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.peace_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static void damagePre(LivingDamageEvent.Pre event){
            if (event.getSource().getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player,"chest_item:peace_tooltip")){
                    event.setNewDamage(event.getNewDamage() * 0.7f);
                }
            }
        }
        public static void LivingChangeTargetEvent(LivingChangeTargetEvent event) {
            if (event.getNewAboutToBeSetTarget() instanceof Player player) {
                if (MemoryBase.hasMemory(player, "chest_item:peace_tooltip")) {
                    if (player.getLastHurtMob()!=null) {
                        if (player.getLastHurtMob().is(event.getEntity())) {
                            return;
                        }
                    }
                    event.setCanceled(true);
                }
            }
        }
    }
}

