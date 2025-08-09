package com.ytgld.chest_item.event.use;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.blood.LifeCrystal;
import com.ytgld.chest_item.items.other.DrugHeal;
import com.ytgld.chest_item.items.blood.GodBlood;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class EventMain {
    @SubscribeEvent
    public void ItemStackAttackEvent(ItemStackAttackEvent event){
        GodBlood.attack(event);
    }
    @SubscribeEvent
    public void ItemStackTickEvent(ItemStackTickEvent event){
        GodBlood.tick(event);
        DrugHeal.tick(event);
        LifeCrystal.tick(event);
    }
    @SubscribeEvent
    public void tooltip(ItemTooltipEvent event){
        if (event.getItemStack().getItem() instanceof ItemBase) {
            event.getToolTip().add(1, Component.literal(""));
            event.getToolTip().add(1, Component.translatable("item.chest_item.chest").withStyle(ChatFormatting.GOLD));
        }
    }
    @SubscribeEvent
    public void ItemTooltipEventASD(LootTableLoadEvent event) {

        LootTable table = event.getTable();

        if (event.getName().toString().contains("chests/")) {

            if (event.getName().toString().contains("dungeon")) {
                table.addPool(LootPool.lootPool().name(Chestitem.MODID + "dungeon")
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(InitItems.Life_Crystal)
                                .when(LootItemRandomChanceCondition.randomChance(0.05f)))
                        .add(LootItem.lootTableItem(InitItems.God_blood)
                                .when(LootItemRandomChanceCondition.randomChance(0.05f)))
                        .add(LootItem.lootTableItem(InitItems.Drug_Heal)
                                .when(LootItemRandomChanceCondition.randomChance(0.05f)))
                        .build());
            }

        }
    }

}
