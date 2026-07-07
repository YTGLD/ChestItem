package com.ytgld.chest_item.items.memory.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

/**
 * 战争
 * <p>
 * 愤怒，那不可遏制的愤怒
 * <p>
 * 受到攻击会积攒怒气并转换为下一击的伤害
 * <p>
 * 受到的伤害增加50%
 */
public class War extends MemoryBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("War");
            intValue =  builder.translation("chest_item.config.War")
                    .defineInRange("number",1.5f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.War2")
                    .defineInRange("number2",0.7f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("War",
                            "信仰战争","受到伤害倍率"),
                    new CIString("War2",
                            "信仰战争2","攻击转化倍率")
            );
        }
    }
    public War(Properties properties) {
        super(properties);
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"war_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.WarTooltip_.asItem();
    }

    @Override
    public void doText(ItemStack stack, List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("item.chest_item.war.string.1").withStyle(ChatFormatting.GRAY));
    }

    public static class WarTooltip extends BaseTooltip {
        public WarTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,255,0,0);
        }
        @Override
        public void doText(ItemStack stack, List<Component> tooltipComponents) {
            tooltipComponents.add(Component.translatable("item.chest_item.war_tooltip.string.1" ,100 *  ConfigItem.intValue2.get().floatValue()).setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.add(Component.translatable("item.chest_item.war_tooltip.string.2" ,100 *  ConfigItem.intValue.get().floatValue() - 100).setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.war_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static final String damageAddBoost=  "warDamageAddBoost";
        public static void damagePre(LivingDamageEvent.Pre event) {
            if (event.getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player, "chest_item:war_tooltip")) {
                    event.setNewDamage(event.getNewDamage() * ConfigItem.intValue.get().floatValue());
                    if (event.getSource().getEntity() instanceof LivingEntity) {
                        CompoundTag compoundTag = player.getPersistentData();
                        compoundTag.putFloat(damageAddBoost, compoundTag.getFloat(damageAddBoost)+event.getNewDamage() * ConfigItem.intValue2.get().floatValue());
                    }
                }
            }
            if (event.getSource().getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player, "chest_item:war_tooltip")) {
                    CompoundTag compoundTag = player.getPersistentData();
                    float add = compoundTag.getFloat(damageAddBoost);
                    event.setNewDamage(event.getNewDamage() + add);
                    compoundTag.putFloat(damageAddBoost,0);
                }
            }
        }
    }
}


