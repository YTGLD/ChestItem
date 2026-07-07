package com.ytgld.chest_item.items.reinforced.items;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

/**
 * 动态衬件
 * <p>
 * 腐堕之心被击碎时发生爆炸，此效果具有%d秒的冷却
 */
public class Dynamic extends EvilMotherReinforced {
    public Dynamic(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Dynamic");
            intValue =  builder.translation("chest_item.config.Dynamic")
                    .defineInRange("number",2.5f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Dynamic2")
                    .defineInRange("number2",750,0,Integer.MAX_VALUE);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Dynamic",
                            "动态衬件","腐堕之心被击碎时发生的爆炸强度"),
                    new CIString("Dynamic2",
                            "动态衬件2","冷却时间")
            );
        }
    }
    public static void doHeal(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.Dynamic_.asItem())) {
                if (ReinforcedBaseItem.decayHeartIsZero(player)) {
                    if (!player.getCooldowns().isOnCooldown(ReinforcedItems.Dynamic_.asItem())){
                        player.level().explode(player,player.getX(),player.getY(),player.getZ(),(float)ConfigItem.intValue.getAsDouble(),false, Level.ExplosionInteraction.NONE);
                        player.getCooldowns().addCooldown(ReinforcedItems.Dynamic_.asItem(), ConfigItem.intValue2.getAsInt());
                    }
                }
            }
        }
    }
    @Override
    public void text(ItemStack stack, List<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.add(Component.translatable("item.chest_item.dynamic.string.0",ConfigItem.intValue2.getAsInt() / 20).withStyle(Style.EMPTY.withColor(color)));
    }
    @Override
    public int sanDown() {
        return -2;
    }
}
