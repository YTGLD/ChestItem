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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Consumer;

/**
 * 壁垒构造体
 * <p>
 * 根据理智值来动态调整幽影护盾稳固度
 * <p>
 * 从0开始计算
 * <p>
 *  每增加1点理智就减少%d%%幽影护盾稳固度
 * <p>
 * 每减少1点理智就增加%d%%幽影护盾稳固度
 */
public class BarrierConstruct extends EvilMother{
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "EvilMother";
        }
        public static ModConfigSpec.IntValue intValue;

        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("BarrierConstruct");
            intValue = builder.translation("chest_item.config.BarrierConstruct")
                    .defineInRange("number", 1, 0, 100);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("BarrierConstruct",
                            "壁垒构造体", "属性")
            );
        }
    }
    public BarrierConstruct(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        int san = (int) player.getAttributeValue(AttReg.theSanity);
        int config = ConfigItem.intValue.getAsInt();
        float value = -san * config;
        modifiers.put(AttReg.shadow_shield_stronger, new AttributeModifier(identifier(),
                value, AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }
    @Override
    public int getSanity() {
        return 0;
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        int config = ConfigItem.intValue.getAsInt();
        tooltipComponents.accept(Component.translatable("item.chest_item.barrier_construct.string.1").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.barrier_construct.string.2").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.barrier_construct.string.3",config).withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.barrier_construct.string.4",config).withStyle(Style.EMPTY.withColor(color)));
    }
}
