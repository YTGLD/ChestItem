package com.ytgld.chest_item.items.meet;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

/**
 * 天罡重刃
 * <p>
 * <p>
 * 每点增生组织提供1.5%的所有伤害加成（上限30%）
 * <p>
 * 每点护甲提供1%所有伤害加成（上限25%）
 * <p>
 * 每点饥饿值提供0.5%所有伤害加成（上限10%）
 * <p>
 * 默认减少20%所有伤害
 */
public class HeavyBlade extends ItemBase implements Meat {
    public HeavyBlade(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        public static ModConfigSpec.DoubleValue intValue3 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("HeavyBlade");
            intValue =  builder.translation("chest_item.config.HeavyBlade")
                    .defineInRange("number",30f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.HeavyBlade2")
                    .defineInRange("number2",25f,0,Integer.MAX_VALUE);
            intValue3 =  builder.translation("chest_item.config.HeavyBlade3")
                    .defineInRange("number3",20f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("HeavyBlade",
                            "天罡重刃","最大增生组织提供伤害"),
                    new CIString("HeavyBlade2",
                            "天罡重刃2","最大护甲提供伤害"),
                    new CIString("HeavyBlade3",
                            "天罡重刃3","饥饿值提供提供伤害")
            );
        }
    }
    public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.HeavyBlade_)) {
                            event.setAmount(event.getAmount()*(1+damage(player)));
                            break;
                        }
                    }
                }
            }
        }
    }




    private static float damage(Player player){
        float s = -20;

        float a = player.getArmorValue();
        if (a > ConfigItem.intValue2.get().byteValue()){
            a = ConfigItem.intValue2.get().byteValue();
        }
        float f = player.getFoodData().getFoodLevel();
        if (f > ConfigItem.intValue3.get().floatValue()) {
            f = ConfigItem.intValue3.get().floatValue();
        }
        float h = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES.get());
        if (h > ConfigItem.intValue.get().floatValue()) {
            h = ConfigItem.intValue.get().floatValue();
        }
        return (s+a+f+h)/100f;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.heavy_blade.string.1",ConfigItem.intValue.get().floatValue()).withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.heavy_blade.string.2",ConfigItem.intValue2.get().floatValue()).withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.heavy_blade.string.3",ConfigItem.intValue3.get().floatValue()).withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_item.heavy_blade.string.4").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

    }
}
