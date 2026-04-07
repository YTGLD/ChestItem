package com.ytgld.chest_item.items.reinforced;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReinforcedEvent {
    @SubscribeEvent
    public  void dropMustDropItem(EntityTickEvent.Post event){
        if (event.getEntity() instanceof Player player) {
            if (!player.level().isClientSide) {
                int max = (int) player.getAttributeValue(ReinforcedAttreg.maxReinforced);
                Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
                List<String> list = new ArrayList<>(strings);
                int nowReinforce = list.size();
                if (nowReinforce > max && !list.isEmpty()) {
                    String itemName = list.getFirst();
                    String[] parts = itemName.split(":");
                    Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]));
                    ItemEntity entity = new ItemEntity(player.level(),player.getX(),player.getY(),player.getZ(),item.getDefaultInstance());
                    player.level().addFreshEntity(entity);
                    list.removeFirst();
                }
            }
        }
    }
}
