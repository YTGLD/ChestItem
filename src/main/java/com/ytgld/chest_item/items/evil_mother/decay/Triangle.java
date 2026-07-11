package com.ytgld.chest_item.items.evil_mother.decay;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 *
 * 古朽三角
 * <p>
 * 获得基于%d%%生命值的伤害
 * <p>
 * 获得基于%d%%伤害的护甲值
 * <p>
 * 获得基于%d%%护甲值的生命值
 */
public class Triangle extends DecayItem {
    public Triangle(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        public static ModConfigSpec.DoubleValue intValue3 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Triangle");
            intValue =  builder.translation("chest_item.config.Triangle")
                    .defineInRange("number",0.2f,0, Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Triangle2")
                    .defineInRange("number2",0.2f,0, Integer.MAX_VALUE);
            intValue3 =  builder.translation("chest_item.config.Triangle3")
                    .defineInRange("number3",0.2f,0, Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Triangle",
                            "古朽三角","生命值转换成伤害的倍率"),
                    new CIString("Triangle2",
                            "古朽三角2","伤害转换成护甲值的倍率"),
                    new CIString("Triangle3",
                            "古朽三角3","护甲值转换为生命值的倍率")
            );
        }
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        var attribute = super.doAttribute(stack, player);
        addHealth(attribute,player,Attributes.MAX_HEALTH,ConfigItem.intValue.get().floatValue() , Attributes.ATTACK_DAMAGE);
        addHealth(attribute,player,Attributes.ATTACK_DAMAGE,ConfigItem.intValue2.get().floatValue() , Attributes.ARMOR);
        addHealth(attribute,player,Attributes.ARMOR,ConfigItem.intValue3.get().floatValue() , Attributes.MAX_HEALTH);
        return attribute;
    }

    @Override
    public int getSanity() {
        return -3;
    }


    public void addHealth(Multimap<Holder<Attribute>, AttributeModifier> attribute ,
                          Player player,Holder<Attribute> attributeHolder,
                          float value,Holder<Attribute> toEnd){
        AttributeInstance instance = player.getAttributes().getInstance(attributeHolder);
        if (instance != null) {
            double all = 0;
            for (AttributeModifier modifier : instance.getModifiers()){
                all+=modifier.amount();
            }
            AttributeModifier attributeModifier = new AttributeModifier(
                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "calabash"),
                    all * value,
                    AttributeModifier.Operation.ADD_VALUE);
            attribute.put(toEnd,attributeModifier);

        }
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.triangle.string.1",(int)(ConfigItem.intValue.get().floatValue() * 100)).setStyle(Style.EMPTY.withColor(theColor())));
        tooltipComponents.accept(Component.translatable("item.chest_item.triangle.string.2",(int)(ConfigItem.intValue2.get().floatValue() * 100)).setStyle(Style.EMPTY.withColor(theColor())));
        tooltipComponents.accept(Component.translatable("item.chest_item.triangle.string.3",(int)(ConfigItem.intValue3.get().floatValue() * 100)).setStyle(Style.EMPTY.withColor(theColor())));
    }

    @Override
    public @Nullable Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player, ItemStack stack) {
        return HashMultimap.create();
    }
}
