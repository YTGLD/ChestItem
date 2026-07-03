package com.ytgld.chest_item.items.condensebone;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MassEnergyConverter extends ItemBone {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("MassEnergyConverter");
            intValue =  builder.translation("chest_item.config.MassEnergyConverter")
                    .defineInRange("number",0.3f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.MassEnergyConverter2")
                    .defineInRange("number2",0.5f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("MassEnergyConverter",
                            "质能转化器","损失 1% 生命值带来的抗性"),
                    new CIString("MassEnergyConverter2",
                            "质能转化器2","损失 1% 生命值带来的伤害")
            );
        }
    }
    public MassEnergyConverter(Properties properties) {
        super(properties);
    }

    public static void LivingIncomingDamageEvent(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.MassEnergyConverter_)) {
                            float lv = player.getHealth() / player.getMaxHealth();
                            lv *= 100;
                            int now = (int) (100 -(lv));
                            float apply = 1 / 100f * now;
                            apply *= ConfigItem.intValue.get().floatValue();

                            float s  = 1 -apply;
                            if (s > 1) {
                                s = 1;
                            }

                            event.setAmount(event.getAmount()*s);
                            break;
                        }
                    }
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.MassEnergyConverter_)) {
                            float lv = player.getHealth() / player.getMaxHealth();
                            lv *= 100;
                            int now = (int) (100 -(lv));
                            float apply = 1 / 100f * now;
                            apply *= ConfigItem.intValue2.get().floatValue();

                            float s  = 1 + apply;


                            event.setAmount(event.getAmount()*s);
                            break;
                        }
                    }
                }
            }
        }
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.mass_energy_converter.string.1" ,ConfigItem.intValue.get().floatValue() ).withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.mass_energy_converter.string.2" ,ConfigItem.intValue2.get().floatValue() ).withStyle(ChatFormatting.GOLD));
    }
}
