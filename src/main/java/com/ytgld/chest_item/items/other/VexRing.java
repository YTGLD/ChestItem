package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.BlackVex;
import com.ytgld.chest_item.entity.Entitys;
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
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.function.Consumer;

public class VexRing extends ItemBase {

    public VexRing(Properties properties) {
        super(properties);
    }

    public static void tick(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.level().isClientSide) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Vex_Ring.get())) {
                            if (!player.getCooldowns().isOnCooldown(stack)){

                                BlackVex blackVex = new BlackVex(Entitys.Black_Vex.get(), player.level());
                                blackVex.setOwner(player);

                                blackVex.setPos(event.getEntity().position());
                                blackVex.setDeltaMovement(Mth.nextFloat(RandomSource.create(), 0, 0.1f), Mth.nextFloat(RandomSource.create(), 0, 0.1f), Mth.nextFloat(RandomSource.create(), 0, 0.1f));

                                player.level().addFreshEntity(blackVex);

                                player.getCooldowns().addCooldown(stack,100);
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.vex_ring.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.vex_ring.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.vex_ring.string.1").withStyle(ChatFormatting.GOLD));
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,75,125,255);
    }
}

