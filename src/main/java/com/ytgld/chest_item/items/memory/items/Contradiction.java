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
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.function.Consumer;

/**
 * 矛盾
 * <p>
 * 将残破的幽影带回混沌之巅
 * <p>
 * 获得基于最大幽影护盾容量的伤害减免
 * <p>
 * 幽影护盾无法自然恢复
 */
public class Contradiction extends MemoryBase {
    public Contradiction(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Contradiction");
            intValue =  builder.translation("chest_item.config.Contradiction")
                    .defineInRange("number",0.1f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Contradiction2")
                    .defineInRange("number2",10f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Contradiction",
                            "信仰矛盾","每个盾给予的抗性"),
                    new CIString("Contradiction2",
                            "信仰矛盾2","最大抗性上限")
            );
        }
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"contradiction_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.ContradictionTooltip_.asItem();
    }
    @Override
    public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
        tooltipComponents.accept(Component.translatable("item.chest_item.contradiction.string.1").withStyle(ChatFormatting.GRAY));
    }
    public static class  ContradictionTooltip extends BaseTooltip {
        public ContradictionTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,105,90,205);
        }
        @Override
        public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
            tooltipComponents.accept(Component.translatable("item.chest_item.contradiction_tooltip.string.1").setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.accept(Component.translatable("item.chest_item.contradiction_tooltip.string.2").setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.contradiction_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static boolean canHeal (LivingEntity living) {
            if (living instanceof Player player) {
                return !MemoryBase.hasMemory(player, "chest_item:contradiction_tooltip");
            }
            return true;
        }
        public static void damageRes(LivingIncomingDamageEvent event){
            if (event.getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player, "chest_item:contradiction_tooltip")) {
                    float value = (float) player.getAttributeValue(AttReg.shadow_shield);
                    value *= ConfigItem.intValue.get().floatValue();
                    if (value > ConfigItem.intValue2.get().floatValue()) {
                        value = ConfigItem.intValue2.get().floatValue();
                    }
                    float end = event.getAmount() - value;
                    if (end <= 0){
                        event.setAmount(0);
                        event.setCanceled(true);
                    }else {
                        event.setAmount(end);
                    }
                }
            }
        }
    }
}
