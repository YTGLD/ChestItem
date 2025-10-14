package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.List;

/**
 * 钢铁之心
 * <p>
 *
 *
 * <p>
 * 饥饿值不再可以恢复生命值
 *
 *
 */
public class SpeedHeart extends ItemBase {
    public SpeedHeart(Properties properties) {
        super(properties);
    }
    public static void eat(LivingEntityUseItemEvent.Finish event){
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.SpeedHeart_)) {
                            FoodProperties sf = event.getItem().get(DataComponents.FOOD);
                            if (sf!=null){
                                float food = sf.nutrition();
                                float sta = sf.saturation();
                                player.heal((food+sta)/3.3f);
                                break;
                            }



                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.speed_heart.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_item.speed_heart.string.2").withStyle(ChatFormatting.GOLD));
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,100,20,255);
    }

}
