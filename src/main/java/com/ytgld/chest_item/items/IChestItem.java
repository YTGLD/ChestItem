package com.ytgld.chest_item.items;

import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public interface IChestItem {
    default void onChestTick(ItemStack stack, Player player) {}
    default void onChestAttack(ItemStack stack, Player player, LivingEntity target, ItemStackAttackEvent event) {}
    default void onChestHurt(ItemStack stack, Player player, LivingIncomingDamageEvent event) {}
    default void onChestHeal(ItemStack stack, Player player, LivingHealEvent event) {}
}
