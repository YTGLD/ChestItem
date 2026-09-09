package com.ytgld.chest_item.event.activated;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.GiveEvilGiftHandler;
import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

public class EventHandler {
    /**
     * 由于是event.getSource().getEntity()
     * <p>
     * 所以这个地方应该是玩家攻击事件
     */
    @SubscribeEvent
    public void CurioLivingIncomingDamageEvent(LivingIncomingDamageEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                NeoForge.EVENT_BUS.post(new ItemStackAttackEvent(event, player, chestInventory));
            }
        }
    }
    @SubscribeEvent
    public void EntityTickEvent(EntityTickEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                NeoForge.EVENT_BUS.post(new ItemStackTickEvent(player, chestInventory));
            }
        }
    }
    @SubscribeEvent
    public void ItemStackTickEvent(ItemStackTickEvent event){
        Player player = event.player;
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory != null) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.getItem() instanceof ItemBase itemBase) {
                    itemBase.tick(player,stack);;
                    GiveEvilGiftHandler.tick(stack,player);
                }
            }
        }
    }
}
