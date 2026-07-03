package com.ytgld.chest_item.items.condensebone;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.List;

public class AlienationDiodes extends ItemBone {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("AlienationDiodes");
            intValue =  builder.translation("chest_item.config.AlienationDiodes")
                    .defineInRange("number",1.1f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.AlienationDiodes2")
                    .defineInRange("number2",1f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("AlienationDiodes",
                            "异化二极管","暴击伤害"),
                    new CIString("AlienationDiodes2",
                            "异化二极管2","暴击伤害造成的治疗")
            );
        }
    }
    public AlienationDiodes(Properties properties) {
        super(properties);
    }
    public static void CriticalHitEvent(CriticalHitEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player,InitItems.AlienationDiodes_.asItem())) {
                if (event.isCriticalHit()) {
                    event.setDamageMultiplier(event.getDamageMultiplier() * ConfigItem.intValue.get().floatValue());
                    player.heal(ConfigItem.intValue2.get().floatValue());
                }
            }
        }
    }

    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.alienation_diodes.string.1",ConfigItem.intValue.get().floatValue() * 100 - 100).withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.alienation_diodes.string.2").withStyle(ChatFormatting.GOLD));
    }

}
