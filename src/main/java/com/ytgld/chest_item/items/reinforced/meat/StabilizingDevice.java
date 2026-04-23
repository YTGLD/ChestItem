package com.ytgld.chest_item.items.reinforced.meat;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Consumer;

/**
 *
 * 稳固装置
 * <p>
 * 疤痕组织%d%%秒内最多受到1次攻击
 * <p>
 *  疤痕组织治愈效果降低%d%%
 */
public class StabilizingDevice extends MeatBaseItem{
    public StabilizingDevice(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("StabilizingDevice");
            intValue =  builder.translation("chest_item.config.StabilizingDevice")
                    .defineInRange("number",1,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.StabilizingDevice2")
                    .defineInRange("number2",0.67,0,1);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("StabilizingDevice",
                            "稳固装置","疤痕组织提高的坚韧度"),
                    new CIString("StabilizingDevice2",
                            "稳固装置2","疤痕组织的降低的恢复效果")
            );
        }
    }

    public static float cutHeal(LivingEntity living, float value){
        if (living instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.StabilizingDevice_.asItem())) {
                value = (float) (value * (1 - ConfigItem.intValue2.getAsDouble()));
            }
        }
        return value;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> attributeUse(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap = HashMultimap.create();
        attributeModifierMultimap.put(AttReg.hyperplasia_stronger, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                this.asItem().getDescriptionId()),
                1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return attributeModifierMultimap;
    }
    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.accept(Component.translatable("item.chest_item.stabilizing_device.string.0",
                ConfigItem.intValue.get() * 100f).withStyle(Style.EMPTY.withColor(theColor())));
        tooltipComponents.accept(Component.translatable("item.chest_item.stabilizing_device.string.1",
                ConfigItem.intValue2.get() * 100f).withStyle(Style.EMPTY.withColor(theColor())));
    }

}
