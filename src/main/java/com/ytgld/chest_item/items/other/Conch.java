package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.entity.AbyssOrb;
import com.ytgld.chest_item.entity.Entitys;
import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.function.Consumer;

public class Conch  extends ItemBase {
    public Conch(Properties properties) {
        super(properties);
    }
    public static void event(ItemStackAttackEvent event){
        LivingIncomingDamageEvent livingIncomingDamageEvent = event.event;
        Player player = event.player;
        LivingEntity target = livingIncomingDamageEvent.getEntity();
        ChestInventory chestInventory = event.chestInventory;
        if (!player.level().isClientSide) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.Conch_)) {
                    if (!player.getCooldowns().isOnCooldown(stack)) {
                        if (Mth.nextInt(RandomSource.create(), 1, 100) <= 25) {
                            AbyssOrb abyssOrb = new AbyssOrb(Entitys.Abyss_Orb.get(), player.level());
                            abyssOrb.setOwner(player);
                            abyssOrb.target = target;
                            abyssOrb.setPos(player.position());
                            abyssOrb.setDeltaMovement(Mth.nextFloat(RandomSource.create(), 0, 0.5F), Mth.nextFloat(RandomSource.create(), 0, 0.5F), Mth.nextFloat(RandomSource.create(), 0, 0.5F));

                            player.level().addFreshEntity(abyssOrb);
                            player.getCooldowns().addCooldown(stack,10);
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
        tooltipAdder.accept(Component.translatable("item.chest_item.conch.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.conch.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,122,122,255);
    }
}
