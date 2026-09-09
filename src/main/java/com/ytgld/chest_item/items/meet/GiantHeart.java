package com.ytgld.chest_item.items.meet;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

/**
 * 增加20%抗性
 * <p>
 *  增加100%食物变为生命值的速度
 */
public class GiantHeart extends ItemBase implements Meat {
    public GiantHeart(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
     public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Meat";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("GiantHeart");
            intValue =  builder.translation("chest_item.config.GiantHeart")
                    .defineInRange("number",0.8f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("GiantHeart",
                            "巨人之心","伤害倍率")
            );
        }
    }
    public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.GiantHeart_)) {
                            event.setAmount(event.getAmount()*ConfigItem.intValue.get().floatValue());
                            break;
                        }
                    }
                }
            }
        }
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.giant_heart.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.giant_heart.string.1",100-ConfigItem.intValue.get().floatValue() * 100).withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.giant_heart.string.2").withStyle(ChatFormatting.GOLD));
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,135,105);
    }
}
