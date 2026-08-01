package com.ytgld.chest_item.items.reinforced.items;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import com.ytgld.chest_item.other.IMobEffectInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.List;

/**
 * 腐化蒸馏器
 * <p>
 * 对自身施加的正面药水效果时长增加%d%%
 * <p>
 * 对自身施加的负面药水效果时长降低%d%%
 */
public class Distillation extends ReinforcedBaseItem{
    public Distillation(Properties properties) {
        super(properties,false);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Reinforced";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Distillation");
            intValue =  builder.translation("chest_item.config.Distillation")
                    .defineInRange("number",0.35f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Distillation2")
                    .defineInRange("number2",0.25f,0,Integer.MAX_VALUE);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Distillation",
                            "腐化蒸馏器","正面药水的额外时长倍率"),
                    new CIString("Distillation2",
                            "腐化蒸馏器2","负面药水的降低时长倍率")
            );
        }
    }

    public static void event(MobEffectEvent.Added event){
        if (event.getEntity() instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.Distillation_.asItem())){
                MobEffectInstance effectInstance = event.getEffectInstance();
                if (effectInstance instanceof IMobEffectInstance iMobEffectInstance) {
                    if (effectInstance.getEffect().value().isBeneficial()) {
                        float add = 1 + ConfigItem.intValue.get().floatValue();
                        iMobEffectInstance.ci$setTime((int) (effectInstance.getDuration() * add));
                    }
                    if (!effectInstance.getEffect().value().isBeneficial()){
                        float down = 1 - ConfigItem.intValue2.get().floatValue();
                        iMobEffectInstance.ci$setTime((int) (effectInstance.getDuration() * down));
                    }
                }
            }
        }
    }
    @Override
    public void text(ItemStack stack, List<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.add(Component.translatable("item.chest_item.distillation.string.0",ConfigItem.intValue.get().floatValue() * 100).withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.add(Component.translatable("item.chest_item.distillation.string.1",ConfigItem.intValue2.get().floatValue() * 100).withStyle(Style.EMPTY.withColor(color)));
    }
    @Override
    public int sanDown() {
        return -1;
    }
}
