package com.ytgld.chest_item.items.memory.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 永幕
 * <p>
 * 将肉体跪服于赎罪之山下
 * <p>
 * 获得10点创伤，创伤可自行增长
 * <p>
 * 创伤被抹去时同时忘却痛楚
 */
public class ForeverCurtain extends MemoryBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("ForeverCurtain");
            intValue =  builder.translation("chest_item.config.ForeverCurtain")
                    .defineInRange("number",10f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("ForeverCurtain",
                            "信仰永幕","创伤数值")
            );
        }
    }
    public ForeverCurtain(Properties properties) {
        super(properties);
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"forever_curtain_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.ForeverCurtainTooltip_.asItem();
    }

    @Override
    public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
        tooltipComponents.accept(Component.translatable("item.chest_item.forever_curtain.string.1").withStyle(ChatFormatting.GRAY));
    }

    public static class  ForeverCurtainTooltip extends BaseTooltip {
        public ForeverCurtainTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,175,70,110);
        }
        @Override
        public void doText(ItemStack stack, @UnknownNullability Consumer<Component> tooltipComponents) {
            tooltipComponents.accept(Component.translatable("item.chest_item.forever_curtain_tooltip.string.1",ConfigItem.intValue.get().floatValue()).setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.accept(Component.translatable("item.chest_item.forever_curtain_tooltip.string.2").setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.forever_curtain_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static void tick(EntityTickEvent event){

            if (event.getEntity() instanceof Player player) {
                Supplier<AttachmentType<Float>> supplier = AttReg.painShield;
                if (MemoryBase.hasMemory(player, "chest_item:forever_curtain_tooltip")) {
                    if (!player.getAttributes().hasModifier(AttReg.painShield_number, resourceLocation)) {
                        player.getAttributes().addTransientAttributeModifiers(addHeartPain());
                    }
                    if (player.getData(supplier) <= 0) {
                        if (player.hasEffect(Effects.Pain)) {
                            player.removeEffect(Effects.Pain);
                        }
                    }
                    if (player.tickCount % 100 == 0) {
                        AttributeInstance maxShield = player.getAttribute(AttReg.painShield_number);
                        if (maxShield != null) {
                            if (player.getData(supplier) <= maxShield.getValue()) {
                                player.setData(supplier, player.getData(supplier) + 0.5f);
                            }
                        }
                    }
                } else {
                    if (player.getAttributes().hasModifier(AttReg.painShield_number, resourceLocation)) {
                        player.getAttributes().removeAttributeModifiers(addHeartPain());
                    }
                }
            }
        }
        private static final Identifier resourceLocation = Identifier.parse(Chestitem.MODID + "forever_curtain_tooltip");

        public static Multimap<Holder<Attribute>, AttributeModifier> addHeartPain() {
            Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

            modifiers.put(AttReg.painShield_number, new AttributeModifier(resourceLocation,
                    ConfigItem.intValue.get().floatValue(), AttributeModifier.Operation.ADD_VALUE));

            return modifiers;
        }

    }

}

