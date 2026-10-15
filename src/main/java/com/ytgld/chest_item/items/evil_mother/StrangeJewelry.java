package com.ytgld.chest_item.items.evil_mother;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Consumer;

/**
 * 诡异珠宝
 * <p>
 * 免疫理智带来的负面和正面加成
 */
public class StrangeJewelry extends EvilMother{
    public StrangeJewelry(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "EvilMother";
        }
        public static ModConfigSpec.IntValue intValue;

        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("StrangeJewelry");
            intValue = builder.translation("chest_item.config.StrangeJewelry")
                    .defineInRange("number", 15, 0, 100);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("StrangeJewelry",
                            "诡异珠宝", "减少的理智和抗性")
            );
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        int value = ConfigItem.intValue.get();
        float dovValue = value / 100f;
        dovValue = -dovValue;

        modifiers.put(AttReg.resistance, new AttributeModifier(identifier(),
                dovValue, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        modifiers.put(AttReg.theSanity, new AttributeModifier(identifier(),
                dovValue, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.strange_jewelry.string.1")
                .withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public int getSanity() {
        return -4;
    }
}
