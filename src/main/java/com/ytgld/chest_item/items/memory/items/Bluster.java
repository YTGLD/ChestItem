package com.ytgld.chest_item.items.memory.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
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
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Bluster");
            intValue =  builder.translation("chest_item.config.Bluster")
                    .defineInRange("number",2f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Bluster2")
                    .defineInRange("number2",2f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Bluster",
                            "信仰嫉灭","伤害倍率"),
                    new CIString("Bluster2",
                            "信仰嫉灭2","受伤倍率")
            );
        }
    }
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
            tooltipComponents.add(Component.translatable("item.chest_item.bluster_tooltip.string.1" ,ConfigItem.intValue2.get().floatValue() * 100f).setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.add(Component.translatable("item.chest_item.bluster_tooltip.string.2" ,ConfigItem.intValue.get().floatValue() * 100f).setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.bluster_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static void damage(LivingDamageEvent.Pre event){
            if (event.getSource().getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player,"chest_item:bluster_tooltip")){
                    event.setNewDamage(event.getNewDamage() * ConfigItem.intValue.get().floatValue());
                }
            }
            if (event.getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player,"chest_item:bluster_tooltip")){
                    event.setNewDamage(event.getNewDamage() * ConfigItem.intValue2.get().floatValue());
                }
            }
        }
    }
}
