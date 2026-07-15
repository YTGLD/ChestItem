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
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.function.Consumer;

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
    @ConfigPlugin
     public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Memory";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Peace");
            intValue =  builder.translation("chest_item.config.Peace")
                    .defineInRange("number",0.7f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Peace",
                            "信仰和平","伤害倍率")
            );
        }
    }
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
    public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
        tooltipComponents.accept(Component.translatable("item.chest_item.peace.string.1").withStyle(ChatFormatting.GRAY));
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
        public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
            tooltipComponents.accept(Component.translatable("item.chest_item.peace_tooltip.string.1").setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.accept(Component.translatable("item.chest_item.peace_tooltip.string.2",100 - 100* ConfigItem.intValue.get().floatValue()).setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.peace_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static void damagePre(LivingDamageEvent.Pre event){
            if (event.getSource().getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player,"chest_item:peace_tooltip")){
                    event.setNewDamage(event.getNewDamage() * ConfigItem.intValue.get().floatValue());
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

