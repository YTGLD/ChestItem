package com.ytgld.chest_item.items.meet;

import com.mojang.datafixers.kinds.IdF;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.List;

public class MeatBall  extends ItemBase implements Meat {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("MeatBall");
            intValue =  builder.translation("chest_item.config.MeatBall")
                    .defineInRange("number",1f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.MeatBall2")
                    .defineInRange("number2",0.3f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("MeatBall",
                            "肉球","饥饿值"),
                    new CIString("MeatBall2",
                            "肉球2","饱和度")
            );
        }
    }
    public MeatBall(Properties properties) {
        super(properties);
    }

    public static void tick(LivingEntityUseItemEvent.Finish event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            if (Handler.has(player, InitItems.Meat_Ball.asItem())) {
                if (event.getItem().get(DataComponents.FOOD) != null) {
                    if (event.getItem().getUseAnimation() == ItemUseAnimation.EAT) {
                        player.getFoodData().eat(ConfigItem.intValue.get().intValue(), ConfigItem.intValue2.get().floatValue());
                    }
                }
            }
        }
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.meatball.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.meatball.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,135,105);
    }
}


