package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.function.Consumer;

/**
 *罡风刀环
 * <p>
 *  伤害在80%到150%之间随机变化
 * <p>
 *  如果你有很多经验值，则消耗大量经验值换取最大加成的伤害
 *
 *
 */

public class WindKnife extends ItemBase {
    public WindKnife(Properties properties) {
        super(properties);
    }
    public static void event(LivingIncomingDamageEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.WindKnife_.get())) {
                            float damage = Mth.nextFloat(RandomSource.create(),0.8f,1.5f);
                            if (player.experienceLevel>10){
                                event.setAmount(event.getAmount()*1.5f);
                                player.level().playSound(null,player.getX(),player.getY(),player.getZ(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.AMBIENT,1,1);
                                player.giveExperiencePoints(-50);
                            }else {
                                event.setAmount(event.getAmount()*damage);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void text(ItemStack stack,Consumer<Component> tooltipAdder,TooltipFlag flag){
        tooltipAdder.accept(Component.translatable("item.chest_item.wind_knife.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.wind_knife.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.wind_knife.string.2").withStyle(ChatFormatting.GOLD));

    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,100,20,255);
    }
}
