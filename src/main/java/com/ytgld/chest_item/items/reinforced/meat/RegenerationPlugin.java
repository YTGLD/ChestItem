package com.ytgld.chest_item.items.reinforced.meat;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Consumer;

/**
 * 再生插件
 * <p>
 * 疤痕组织有%d%%的概率进行双倍治愈
 * <p>
 * 疤痕组织在治愈时有%d%%的概率同时治疗持有者
 */
public class RegenerationPlugin extends MeatBaseItem{
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("RegenerationPlugin");
            intValue =  builder.translation("chest_item.config.RegenerationPlugin")
                    .defineInRange("number",0.15,0,1);
            intValue2 =  builder.translation("chest_item.config.RegenerationPlugin2")
                    .defineInRange("number2",0.25,0,1);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("RegenerationPlugin",
                            "再生插件","双倍治愈的概率"),
                    new CIString("RegenerationPlugin2",
                            "再生插件2","对玩家进行治疗的概率")
            );
        }
    }
    public RegenerationPlugin(Properties properties) {
        super(properties);
    }

    public static void heal(LivingEntity living){
        if (living instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.RegenerationPlugin_.asItem())) {
                if (Mth.nextInt(RandomSource.create(),1,100) <= (ConfigItem.intValue2.get() * 100)){
                    player.heal(4);
                }
            }
        }
    }
    public static float doubleAdd(LivingEntity living,float value){
        if (living instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.RegenerationPlugin_.asItem())) {
                if (Mth.nextInt(RandomSource.create(),1,100) <= (ConfigItem.intValue.get() * 100)){
                    value = value * 2;
                }
            }
        }
        return value;
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.accept(Component.translatable("item.chest_item.regeneration_plugin.string.0",
                ConfigItem.intValue.get() * 100f).withStyle(Style.EMPTY.withColor(theColor())));

        tooltipComponents.accept(Component.translatable("item.chest_item.regeneration_plugin.string.1",
                ConfigItem.intValue2.get() * 100f).withStyle(Style.EMPTY.withColor(theColor())));
    }

}
