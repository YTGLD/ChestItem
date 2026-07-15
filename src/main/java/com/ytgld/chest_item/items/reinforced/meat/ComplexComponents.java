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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

/**
 *
 * 繁复组件
 * <p>
 * 疤痕组织吸收全部伤害
 * <p>
 * 疤痕组织被抹去时会施加额外%d%%休眠时长
 */
public class ComplexComponents extends MeatBaseItem{
    public ComplexComponents(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Reinforced";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("ComplexComponents");
            intValue =  builder.translation("chest_item.config.ComplexComponents")
                    .defineInRange("number",2f,0,Integer.MAX_VALUE);

            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("ComplexComponents",
                            "繁复组件","额外休眠时长")
            );
        }
    }
    public static boolean absorptionDamage(LivingEntity living){
        if (living instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.ComplexComponents_.asItem())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> attributeUse(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap = HashMultimap.create();
        attributeModifierMultimap.put(AttReg.hyperplasiaCooldown, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                this.asItem().getDescriptionId()),
                ConfigItem.intValue.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return attributeModifierMultimap;
    }

    @Override
    public void text(ItemStack stack, List<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.add(Component.translatable("item.chest_item.complex_components.string.0").withStyle(Style.EMPTY.withColor(theColor())));
        tooltipComponents.add(Component.translatable("item.chest_item.complex_components.string.1",
                ConfigItem.intValue.get() * 100f).withStyle(Style.EMPTY.withColor(theColor())));
    }
}
