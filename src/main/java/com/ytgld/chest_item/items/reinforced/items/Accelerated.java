package com.ytgld.chest_item.items.reinforced.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Consumer;

/**
 * 加速衬件
 * <p>
 * 腐堕之心接近被击碎时获得额外%d%%的强度
 * <p>
 * 腐堕之心被击碎后冷却时间降低%d%%
 */
public class Accelerated  extends EvilMotherReinforced {
    public Accelerated(Properties properties) {
        super(properties);
    }

    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Accelerated");
            intValue =  builder.translation("chest_item.config.Accelerated")
                    .defineInRange("number",0.3,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Accelerated2")
                    .defineInRange("number2",0.3,0,Integer.MAX_VALUE);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Accelerated",
                            "加速衬件","腐堕之心在低于50%时给予的额外强度"),
                    new CIString("Accelerated2",
                            "加速衬件2","腐堕之心被击碎时产生的额外反应时间倍数")
            );
        }
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.accept(Component.translatable("item.chest_item.accelerated.string.0",ConfigItem.intValue.get() * 100f).withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.accelerated.string.1",ConfigItem.intValue2.get() * 100f).withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> attributeUse(Player player) {
        Identifier resourceLocation = Identifier.parse(this.getDescriptionId());
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float res = 0;
        float time = 0;

        float sa = 0.5f;
        if (ReinforcedBaseItem.hasDecayHeart(player)){
            if (ReinforcedBaseItem.getPainHeartValue(player) <= player.getAttributeValue(AttReg.painShield_number) * sa) {
                res = ConfigItem.intValue.get().floatValue();
            }
            time = ConfigItem.intValue2.get().floatValue();
        }

        modifiers.put(AttReg.painShield_res, new AttributeModifier(resourceLocation,
                res, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(AttReg.shield_cooldown, new AttributeModifier(resourceLocation,
                -time, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }

    @Override
    public int sanDown() {
        return -2;
    }
}
