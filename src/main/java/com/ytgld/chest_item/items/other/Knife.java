package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class Knife extends ItemBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Other";
        }
        public static ModConfigSpec.DoubleValue doubleValue ;
        public static ModConfigSpec.DoubleValue doubleValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Knife");
            doubleValue =  builder.translation("chest_item.config.Knife")
                    .defineInRange("number_max",1.2f,0,Integer.MAX_VALUE);
            doubleValue2 =  builder.translation("chest_item.config.Knife2")
                    .defineInRange("number_min",0.9f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of( new CIString("Knife",
                            "掩埋刀锋","最大伤害"),
                    new CIString("Knife2",
                            "掩埋刀锋2","最小伤害"));
        }

    }
    public Knife(Properties properties) {
        super(properties);
    }

    public static void event(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Knife_)) {
                            event.setAmount(event.getAmount()*Mth.nextFloat(RandomSource.create(), (float) ConfigItem.doubleValue2.getAsDouble(), (float) ConfigItem.doubleValue.getAsDouble()));
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.knife.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_item.knife.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,255,0);
    }
}

