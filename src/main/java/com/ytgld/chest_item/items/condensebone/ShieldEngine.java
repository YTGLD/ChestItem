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
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class ShieldEngine extends ItemBone {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("ShieldEngine");
            intValue =  builder.translation("chest_item.config.ShieldEngine")
                    .defineInRange("number",3f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.ShieldEngine2")
                    .defineInRange("number2",1f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("ShieldEngine",
                            "盾御引擎","损失生命值"),
                    new CIString("ShieldEngine2",
                            "盾御引擎2","抗性")
            );
        }
    }
    public ShieldEngine(Properties properties) {
        super(properties);
    }

    public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player,InitItems.ShieldEngine_.asItem())) {
                float lv = player.getHealth() / player.getMaxHealth();
                lv *= 100;
                int now = (int) (100 -(lv));
                float apply = 1 / 100f * now;
                apply /= ConfigItem.intValue.get().floatValue();

                float s  = 1 -apply * ConfigItem.intValue2.get().floatValue();
                if (s > 1) {
                    s = 1;
                }

                event.setAmount(event.getAmount()*s);
            }
        }
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.shield_engine.string.1",ConfigItem.intValue.get().floatValue(),ConfigItem.intValue2.get().floatValue()).withStyle(ChatFormatting.GOLD));
    }

}
