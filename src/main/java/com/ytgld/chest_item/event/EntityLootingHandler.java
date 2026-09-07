package com.ytgld.chest_item.event;

import com.ytgld.chest_item.items.AttReg;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.event.enchanting.EnchantedEntityLootEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class EntityLootingHandler {
    public static void event(EnchantedEntityLootEvent event) {
        if (event.getDamageSource().getEntity() instanceof Player player) {
            if (event.getEnchantment().is(Enchantments.LOOTING)) {
                AttributeInstance attribute = player.getAttribute(AttReg.looting);
                if (attribute != null) {
                    int level = (int) attribute.getValue();
                    event.setEnchantmentLevel(event.getEnchantmentLevel() + level);
                }
            }
        }
    }
}
