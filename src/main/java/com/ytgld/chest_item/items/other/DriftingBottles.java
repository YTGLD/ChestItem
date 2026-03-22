package com.ytgld.chest_item.items.other;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DriftingBottles extends ItemBase {
    public DriftingBottles(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("DriftingBottles");
            intValue =  builder.translation("chest_item.config.DriftingBottles")
                    .defineInRange("number",1.0f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("DriftingBottles",
                            "漂流瓶","游泳速度")
            );
        }
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player, ItemStack stack) {
        return doAttribute(stack,player);
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float g = 0;
        if (player.isInWater()) {
            g = -0.95f;
        }

        modifiers.put(Attributes.GRAVITY, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DriftingBottles_.asItem().getDescriptionId()),
                g, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));


        modifiers.put(Attributes.WATER_MOVEMENT_EFFICIENCY, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DriftingBottles_.asItem().getDescriptionId()),
                0.5, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(NeoForgeMod.SWIM_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DriftingBottles_.asItem().getDescriptionId()),
                ConfigItem.intValue.get().floatValue(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));


        modifiers.put(Attributes.OXYGEN_BONUS, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DriftingBottles_.asItem().getDescriptionId()),
                3, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        if (flag.hasShiftDown()) {

            tooltipComponents.accept(Component.translatable("item.chest_item.drifting_bottles.string.2").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80DAA520))));
            tooltipComponents.accept(Component.literal(""));
            tooltipComponents.accept(Component.translatable("item.chest_item.drifting_bottles.string.3").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80DAA520))));
            tooltipComponents.accept(Component.translatable("item.chest_item.drifting_bottles.string.4").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80DAA520))));
            tooltipComponents.accept(Component.translatable("item.chest_item.drifting_bottles.string.5").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80DAA520))));
        }else {
            tooltipComponents.accept(Component.translatable("options.key.hold").append(Component.translatable("key.keyboard.left.shift")).withStyle(ChatFormatting.GOLD));
            tooltipComponents.accept(Component.literal(""));
            tooltipComponents.accept(Component.translatable("item.chest_item.drifting_bottles.string.1").withStyle(ChatFormatting.GOLD));
        }
    }


    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,50,100,50);
    }
}

