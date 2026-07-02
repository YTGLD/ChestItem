package com.ytgld.chest_item.items.reinforced.items;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * 活性衬件
 * <p>
 * 腐堕之心被击碎时获得的%d%%级生命恢复（%d%%秒）
 */
public class Activity extends EvilMotherReinforced {
    public Activity(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Activity");
            intValue =  builder.translation("chest_item.config.Activity")
                    .defineInRange("number",2,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Activity2")
                    .defineInRange("number2",600,0,Integer.MAX_VALUE);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Activity",
                            "活性衬件","腐堕之心被击碎时获得的生命恢复等级"),
                    new CIString("Activity2",
                            "活性衬件2","腐堕之心被击碎时获得的生命恢复时长")
            );
        }
    }
    public static void doHeal(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.Activity_.asItem())) {
                if (ReinforcedBaseItem.decayHeartIsZero(player)) {
                    if (!player.getCooldowns().isOnCooldown(ReinforcedItems.Activity_.asItem().getDefaultInstance())){
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,ConfigItem.intValue2.getAsInt(),ConfigItem.intValue.getAsInt()));
                        player.getCooldowns().addCooldown(ReinforcedItems.Activity_.asItem().getDefaultInstance(),ConfigItem.intValue2.getAsInt() * 2);
                    }
                }
            }
        }
    }
    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.accept(Component.translatable("item.chest_item.activity.string.0",ConfigItem.intValue.getAsInt(),ConfigItem.intValue2.getAsInt() / 20).withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public int sanDown() {
        return -1;
    }
}
