package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * 审判之剑
 * <p>
 * 攻击触发多段连斩并进入%d秒冷却
 * <p>
 * 飞散的残余剑气将重新聚集能量
 */
public class Adjudication extends ItemBase {
    public Adjudication(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Other";
        }
        public static ModConfigSpec.IntValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Adjudication");
            intValue =  builder.translation("chest_item.config.Adjudication")
                    .defineInRange("number",6,1,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Adjudication",
                            "审判之剑","每个箱子的经验值")
            );
        }
    }

    public static final int timeBase = 30;

    public static void addSword(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.Adjudication_.asItem())) {
                int number = ConfigItem.intValue.getAsInt();
                if (!player.getCooldowns().isOnCooldown(InitItems.Adjudication_.asItem())) {

                    event.getEntity().setData(AttReg.swordIntent.get(),
                            event.getEntity().getData(AttReg.swordIntent.get()) + number);

                    player.getCooldowns().addCooldown(InitItems.Adjudication_.asItem(),number * timeBase);
                }
            }
        }
    }

    @Override
    public void text(ItemStack stack, List<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.add(Component.translatable("item.chest_item.adjudication.string.0", ConfigItem.intValue.get() * (timeBase / 20)).withStyle(ChatFormatting.GOLD));
        tooltipComponents.add(Component.translatable("item.chest_item.adjudication.string.1").withStyle(ChatFormatting.GOLD));
    }
}
