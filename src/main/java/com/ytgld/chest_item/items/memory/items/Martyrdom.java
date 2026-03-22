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
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.function.Consumer;

/**
 * 殉爆
 * <p>
 * 为吾献身即便是尔等无上的荣耀
 * <p>
 * 增加爆炸效果，并自动收集爆炸掉落物
 * <p>
 * 受到的爆炸伤害极为致命
 */

public class Martyrdom extends MemoryBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Martyrdom");
            intValue =  builder.translation("chest_item.config.Martyrdom")
                    .defineInRange("number",3f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Martyrdom2")
                    .defineInRange("number2",1.85f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Martyrdom",
                            "信仰殉爆","额外受到爆炸伤害"),
                    new CIString("Martyrdom2",
                            "信仰殉爆2","爆炸增加倍率")
            );
        }
    }

    public Martyrdom(Properties properties) {
        super(properties);
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"martyrdom_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.MartyrdomTooltip_.asItem();
    }

    @Override
    public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
        tooltipComponents.accept(Component.translatable("item.chest_item.martyrdom.string.1").withStyle(ChatFormatting.GRAY));
    }

    public static class  MartyrdomTooltip extends BaseTooltip {
        public MartyrdomTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,255,0,100);
        }
        @Override
        public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
            tooltipComponents.accept(Component.translatable("item.chest_item.martyrdom_tooltip.string.1").setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.accept(Component.translatable("item.chest_item.martyrdom_tooltip.string.2").setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.martyrdom_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static float boom(Entity source ,float radius){
            if (source instanceof TraceableEntity traceableEntity&& traceableEntity.getOwner() instanceof Player player){
                if (MemoryBase.hasMemory(player,"chest_item:martyrdom_tooltip")){
                    return radius * ConfigItem.intValue2.get().floatValue();
                }
            }
            return radius;
        }
        public static void boom(Entity entity,Entity item){
            if (entity instanceof Player player){
                if (MemoryBase.hasMemory(player, "chest_item:martyrdom_tooltip")) {
                     if (item instanceof ItemEntity itemEntity){
                         itemEntity.invulnerableTime = 20;
                         itemEntity.setNoPickUpDelay();
                         itemEntity.setPos(player.position());
                     }
                }
            }
        }
        public static void boom(LivingDamageEvent.Pre event){
            if (event.getEntity() instanceof Player player) {
                if (event.getSource().is(DamageTypes.EXPLOSION)||event.getSource().is(DamageTypes.PLAYER_EXPLOSION)) {
                    if (MemoryBase.hasMemory(player, "chest_item:martyrdom_tooltip")) {
                        event.setNewDamage(event.getNewDamage() * ConfigItem.intValue.get().floatValue());
                    }
                }
            }
        }
    }
}

