package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.SweepAttackEvent;

import java.util.List;
import java.util.function.Consumer;

/**
 * 苍戮
 * <p>
 * 攻击有%d%%的概率造成%d次的连续斩击
 *
 */
public class TheKill extends EvilMother{
    public TheKill(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue;
        public static ModConfigSpec.IntValue intValue2;

        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("TheKill");
            intValue = builder.translation("chest_item.config.TheKill")
                    .defineInRange("number", 50, 0, 100);
            intValue2 = builder.translation("chest_item.config.TheKill2")
                    .defineInRange("number2", 4, 0, Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("TheKill",
                            "苍戮", "连续斩击的触发概率"),
                    new CIString("TheKill2",
                            "苍戮2", "连斩次数")
            );
        }
    }
    public static void attack(SweepAttackEvent event){
        if (!event.isSweeping()) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.TheKill_.asItem())) {
                if (!player.getCooldowns().isOnCooldown(InitItems.TheKill_.asItem().getDefaultInstance())){
                    if (Mth.nextInt(RandomSource.create(),0,100) <= ConfigItem.intValue.getAsInt()){
                        Entity entity = event.getTarget();
                        if (entity instanceof LivingEntity living) {
                            int s = (int)(float)living.getData(AttReg.slashing.get());
                            living.setData(AttReg.slashing.get(),(float)ConfigItem.intValue2.getAsInt());
                            player.getCooldowns().addCooldown(InitItems.TheKill_.asItem().getDefaultInstance(),ConfigItem.intValue2.getAsInt() * 20);
                        }
                    }
                }

            }
        }
    }
    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.text(stack, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.the_kill.string.0",ConfigItem.intValue.getAsInt(),ConfigItem.intValue2.getAsInt()).withStyle(Style.EMPTY.withColor(color)));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_kill.string.1").withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public int getSanity() {
        return -3;
    }
}
