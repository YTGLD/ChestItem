package com.ytgld.chest_item.items.meet;

import com.ytgld.chest_item.Handler;
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
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.function.Consumer;

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
        if (a>25){
            a = 25;
        }
        float f = player.getFoodData().getFoodLevel();
        if (f > 20) {
            f = 20;
        }
        float h = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES.get());
        if (h > 30) {
            h = 30;
        }
        return (s+a+f+h)/100f;
    }
    @Override
    public void text(ItemStack stack,Consumer<Component> tooltipAdder,TooltipFlag flag){
        tooltipAdder.accept(Component.translatable("item.chest_item.heavy_blade.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.heavy_blade.string.2").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.heavy_blade.string.3").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.heavy_blade.string.4").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

    }
}
