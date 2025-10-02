package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.EndSpiral;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class Space extends ItemBase {
    public Space(Properties properties) {
        super(properties);
    }
    public static void summon(Player player){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (!player.getCooldowns().isOnCooldown(stack)) {
                        if (stack.is(InitItems.Space_)) {
                            EndSpiral endSpiral = new EndSpiral(Entitys.End_Spiral.get(), player.level());
                            endSpiral.setOwner(player);
                            endSpiral.setPos(player.position());
                            player.level().addFreshEntity(endSpiral);
                            player.getCooldowns().addCooldown(stack,2000);
                            break;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.space.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.space.string.1").withStyle(ChatFormatting.GOLD));

    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,75,50,255);
    }
}


