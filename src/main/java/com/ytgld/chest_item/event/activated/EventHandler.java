package com.ytgld.chest_item.event.activated;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.ClientAttReg;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.ChestSlot;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Set;

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
                }
            }
        }
    }
    @SubscribeEvent
    public void ItemStackTickEvent(PlayerEvent.PlayerRespawnEvent event){
        Player player = event.getEntity();
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory != null) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                onPlayerPut(player,stack);
            }
        }
    }
    private void onPlayerPut(Player player, ItemStack stack) {

        if (player == null) {
            return;
        }
        String s = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        Set<String> stringSet = player.getData(ClientAttReg.record.get());
        stringSet.add(s);
        player.setData(ClientAttReg.record,stringSet);
    }

}
