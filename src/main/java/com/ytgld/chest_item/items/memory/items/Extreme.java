package com.ytgld.chest_item.items.memory.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

/**
 *极端
 * <p>
 * 永堕于沉沦之海
 * <p>
 * 增加100%经验掉落
 * <p>
 * 自身生命值将影响经验的整体掉落量
 */
public class Extreme extends MemoryBase {
    public Extreme(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Extreme");
            intValue =  builder.translation("chest_item.config.Extreme")
                    .defineInRange("number",3f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Extreme",
                            "信仰极端","经验倍率")
            );
        }
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"extreme_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.ExtremeTooltip_.asItem();
    }
    @Override
    public void doText(ItemStack stack, List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("item.chest_item.extreme.string.1").withStyle(ChatFormatting.GRAY));
    }
    public static class  ExtremeTooltip extends BaseTooltip {
        public ExtremeTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,50,255,50);
        }
        @Override
        public void doText(ItemStack stack, List<Component> tooltipComponents) {
            tooltipComponents.add(Component.translatable("item.chest_item.extreme_tooltip.string.1").setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.add(Component.translatable("item.chest_item.extreme_tooltip.string.2").setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.extreme_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }

        public static void expDrop(LivingExperienceDropEvent event){
            if (event.getAttackingPlayer() instanceof Player player) {
                if (MemoryBase.hasMemory(player, "chest_item:extreme_tooltip")) {
                    float v = ConfigItem.intValue.get().intValue();
                    float lv = player.getHealth() / player.getMaxHealth();
                    lv *= 100;
                    float now = (int) (lv);
                    if (now < 0) {
                        now = 0;
                    }
                    now /= 100f;
                    v *= now;
                    if (v < 0.1) {
                        v = 0.1f;
                    }
                    event.setDroppedExperience((int) (event.getDroppedExperience() * v));
                }
            }
        }
    }
}

