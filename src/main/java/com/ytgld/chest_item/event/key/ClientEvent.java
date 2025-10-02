package com.ytgld.chest_item.event.key;

import com.ytgld.chest_item.event.Keys;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class ClientEvent {
    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post evt) {
        if (Keys.KEY_MAPPING_LAZY_R.consumeClick()) {
            ClientPacketDistributor.sendToServer(new OpenChest(ItemStack.EMPTY));
        }
        if (Keys.KEY_MAPPING_LAZY_C.consumeClick()) {
            ClientPacketDistributor.sendToServer(new UseChestCurios(ItemStack.EMPTY));
        }
    }

}
