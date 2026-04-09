package com.ytgld.chest_item.items.reinforced.items;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

/**
 * 激发衬件
 * <p>
 * 腐堕之心的恢复速度会提高%d%%
 */
public class Excite extends ReinforcedBaseItem {
    public Excite(Properties properties) {
        super(properties);
    }

    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Excite");
            intValue =  builder.translation("chest_item.config.Excite")
                    .defineInRange("number",0.2,0,Integer.MAX_VALUE);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Excite",
                            "激发衬件","腐堕之心恢复速度会提高的值")
            );
        }
    }
    public static float doINtHeal(float def, Player player){
        if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.Excite_.asItem())) {
            if (ReinforcedBaseItem.hasDecayHeart(player)) {
                return (float) (def * (1 + ConfigItem.intValue.getAsDouble()));
            }
        }
        return def;
    }
    @Override
    public void text(ItemStack stack, List<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.add(Component.translatable("item.chest_item.excite.string.0",ConfigItem.intValue.get() * 100f).withStyle(Style.EMPTY.withColor(color)));
    }


    @Override
    public int sanDown() {
        return -1;
    }
}
