package com.ytgld.chest_item.event.use;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.reinforced.ReinforcedAttreg;
import com.ytgld.chest_item.items.reinforced.ReinforcedDataHandler;
import com.ytgld.chest_item.items.reinforced.items.Activity;
import com.ytgld.chest_item.items.reinforced.items.Contingency;
import com.ytgld.chest_item.items.reinforced.items.Dynamic;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Set;

public class ReinforcedEvent {
    @SubscribeEvent
    public  void LivingIncomingDamageEvent(LivingIncomingDamageEvent event){
        Activity.doHeal(event);
        Dynamic.doHeal(event);
        Contingency.doHeal(event);

    }
    @SubscribeEvent
    public  void dropMustDropItem(EntityTickEvent.Post event){
        if (event.getEntity() instanceof Player player) {
            int max = (int) player.getAttributeValue(ReinforcedAttreg.maxReinforced);
            Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
            int nowReinforce = strings.size();
            if (nowReinforce > max && !strings.isEmpty()) {
                for (String itemName : strings) {
                    if (strings.remove(itemName)) {
                        String[] parts = itemName.split(":");
                        Item item = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(parts[0], parts[1]));
                        ItemEntity entity = new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), item.getDefaultInstance());
                        player.level().addFreshEntity(entity);
                        Handler.upDATA(player);
                        break;
                    }
                }
            }
        }
    }
}
