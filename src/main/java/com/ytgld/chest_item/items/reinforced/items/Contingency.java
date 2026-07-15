package com.ytgld.chest_item.items.reinforced.items;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

/**
 * 应急衬件
 * <p>
 * 腐堕之心击碎时瞬间恢复%d的护盾，但是有%d的冷却时间
 *
 */
public class Contingency extends EvilMotherReinforced {
    public Contingency(Properties properties) {
        super(properties);
    }

    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Reinforced";
        }
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Contingency");
            intValue =  builder.translation("chest_item.config.Contingency")
                    .defineInRange("number",8,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Contingency2")
                    .defineInRange("number2",900,0,Integer.MAX_VALUE);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Contingency",
                            "应急衬件","腐堕之心被击碎时获得的生命恢复等级"),
                    new CIString("Contingency2",
                            "应急衬件2","腐堕之心被击碎时获得的生命恢复时长")
            );
        }
    }
    public static void doHeal(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.Contingency_.asItem())) {
                if (ReinforcedBaseItem.decayHeartIsZero(player)) {
                    if (!player.getCooldowns().isOnCooldown(ReinforcedItems.Contingency_.asItem())){
                        Handler.addHeartShield(player,ConfigItem.intValue.getAsInt());
                        player.getCooldowns().addCooldown(ReinforcedItems.Contingency_.asItem(), Contingency.ConfigItem.intValue2.getAsInt());
                    }
                }
            }
        }
    }
    @Override
    public void text(ItemStack stack, List<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.add(Component.translatable("item.chest_item.contingency.string.0", ConfigItem.intValue.getAsInt(), ConfigItem.intValue2.getAsInt() / 20).withStyle(Style.EMPTY.withColor(color)));
    }
    @Override
    public int sanDown() {
        return -2;
    }
}
