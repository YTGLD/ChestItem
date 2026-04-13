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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Consumer;

public class Strengthen extends ReinforcedBaseItem {
    public Strengthen(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        public static ModConfigSpec.DoubleValue intValue3 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Strengthen");
            intValue =  builder.translation("chest_item.config.Strengthen")
                    .defineInRange("number",0.2F,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Strengthen2")
                    .defineInRange("number2",0.15f,0,Integer.MAX_VALUE);
            intValue3 =  builder.translation("chest_item.config.Strengthen2")
                    .defineInRange("number3",0.1F,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Strengthen",
                            "强化衬件","给予的护甲增益"),
                    new CIString("Strengthen2",
                            "强化衬件2","给予的速度增益"),
                    new CIString("Strengthen3",
                            "强化衬件3","给予的抗性增益")
            );
        }
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.accept(Component.translatable("item.chest_item.strengthen.string.0").withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> attributeUse(Player player) {
        Identifier resourceLocation = Identifier.parse(this.getDescriptionId());
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float armor = 0;
        float speed = 0;
        float res = 0;
        if (ReinforcedBaseItem.decayHeartIsZero(player)){
            armor = (float) ConfigItem.intValue.getAsDouble();
            speed = (float) ConfigItem.intValue2.getAsDouble();
            res = (float) ConfigItem.intValue3.getAsDouble();
        }

        modifiers.put(Attributes.ARMOR, new AttributeModifier(resourceLocation,
                armor, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.resistance, new AttributeModifier(resourceLocation,
                res, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation,
                speed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }
    @Override
    public int sanDown() {
        return -1;
    }
}
