package com.ytgld.chest_item.items.reinforced.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

/**
 * 融合引擎
 * <p>
 * 宠物或召唤物获得%d%%主人的护甲值和生命值加成
 * <p>
 * 宠物或召唤物会每隔%d秒恢复少量生命值
 */
public class Fusion extends ReinforcedBaseItem {
    public Fusion(Properties properties) {
        super(properties,false);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Reinforced";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Fusion");
            intValue =  builder.translation("chest_item.config.Fusion")
                    .defineInRange("number",0.2f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Fusion2")
                    .defineInRange("number2",20,1,Integer.MAX_VALUE);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Fusion",
                            "融合引擎","获得的额外护甲和生命值加成"),
                    new CIString("Fusion2",
                            "融合引擎2","每隔多少tick恢复2点生命值")
            );
        }
    }
    public static void event(EntityTickEvent.Post event){
        if (event.getEntity() instanceof LivingEntity living && living instanceof OwnableEntity ownableEntity) {
            if (ownableEntity.getOwner() instanceof Player player) {
                if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.Fusion_.asItem())){
                    living.getAttributes().addTransientAttributeModifiers(attributeUseOwnableEntity(player));

                    int time = ConfigItem.intValue2.get();
                    if (living.tickCount % time == 1) {
                        living.heal(2);
                    }
                }
            }
        }
    }

    public static Multimap<Holder<Attribute>, AttributeModifier> attributeUseOwnableEntity(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier>  attributeModifierMultimap = HashMultimap.create();
        float value = ConfigItem.intValue.get().floatValue();
        float armor = player.getArmorValue() * value;
        float health = player.getMaxHealth() * value;

        attributeModifierMultimap.put(Attributes.ARMOR,new AttributeModifier(Identifier.fromNamespaceAndPath(Chestitem.MODID,
                ReinforcedItems.Fusion_.asItem().getDescriptionId())
                ,armor, AttributeModifier.Operation.ADD_VALUE));

        attributeModifierMultimap.put(Attributes.MAX_HEALTH,new AttributeModifier(Identifier.fromNamespaceAndPath(Chestitem.MODID,
                ReinforcedItems.Fusion_.asItem().getDescriptionId())
                ,health, AttributeModifier.Operation.ADD_VALUE));

        return attributeModifierMultimap;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.chest_item.fusion.string.0",(int)(ConfigItem.intValue.get().floatValue() * 100f)).withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.add(Component.translatable("item.chest_item.fusion.string.1", ConfigItem.intValue2.get().floatValue() / 20f).withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public int sanDown() {
        return -2;
    }
}
