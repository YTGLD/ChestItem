package com.ytgld.chest_item.event;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.evil_mother.soul.SoulBottle;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;

import java.awt.*;
import java.util.Set;

public class SoulBottleHandler {

    public static final String finalGive = "ChestItemFinalGive";

    @SubscribeEvent
    public void giveBottle(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof Player player) {
            Set<String> tags = player.getTags();
            if (tags.contains(finalGive)) {
                return;
            }
            ResourceKey<Level> eventTo = event.getTo();
            if (player.level() instanceof ServerLevel serverLevel) {
                Level level = serverLevel.getServer().getLevel(eventTo);
                if (level != null && level.dimension().isFor(Level.NETHER.registryKey())) {
                    if (!tags.contains(finalGive)) {
                        ChestInventory chestInventory= Handler.getItem(player);
                        if (chestInventory != null) {
                            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                                ItemStack stack= chestInventory.getItem(i);
                                if (stack.isEmpty()) {
                                    chestInventory.setItem(i,new ItemStack(InitItems.SoulBottle_.asItem()));
                                    player.addTag(finalGive);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void loot(Entity entity){
        if (entity instanceof Player player) {
            Item celestialSoulItem = InitItems.CelestialSoul_.asItem();
            Item magicSoulItem = InitItems.MagicSoul_.asItem();

            int countCelestial = RandomSource.create().nextInt(5) + 4;
            int countMagic = RandomSource.create().nextInt(4) + 3;

            SoulBottleHandler.addSoul(player, celestialSoulItem, countCelestial);
            SoulBottleHandler.addSoul(player, magicSoulItem, countMagic);
        }
    }
    @SubscribeEvent
    public void spiritSoul(LivingDeathEvent event) {

        LivingEntity livingEntity = event.getEntity();

        Item spiritSoulItem = InitItems.SpiritSoul_.asItem();
        Item magicSoulItem = InitItems.MagicSoul_.asItem();
        Item deathSoulItem = InitItems.DeathSoul_.asItem();
        Item bloodSoulItem = InitItems.BloodSoul_.asItem();
        Item celestialSoulItem = InitItems.CelestialSoul_.asItem();

        if (event.getSource().getEntity() instanceof OwnableEntity entity) {
            if (entity.getOwner() instanceof Player player) {
                int countCelestial = RandomSource.create().nextInt(2);
                SoulBottleHandler.addSoul(player, celestialSoulItem, countCelestial);
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {//灵魂，魔魂
            //杀死友善生物：0~1
            //灵魂，魔魂
            if (livingEntity instanceof Animal) {
                int countSpirit = RandomSource.create().nextInt(2);
                int countMagic = RandomSource.create().nextInt(2);
                SoulBottleHandler.addSoul(player, spiritSoulItem, countSpirit);
                SoulBottleHandler.addSoul(player, magicSoulItem, countMagic);
            }
            // 灵魂：1~3
            // 魔魂：0~1
            if (livingEntity instanceof Monster monster) {
                int countSpirit = RandomSource.create().nextInt(3) + 1;
                int countMagic = RandomSource.create().nextInt(2);

                SoulBottleHandler.addSoul(player, spiritSoulItem, countSpirit);
                SoulBottleHandler.addSoul(player, magicSoulItem, countMagic);

                // 亡魂 ：0~2
                if (monster.isInvertedHealAndHarm()) {
                    int countDie = RandomSource.create().nextInt(3);
                    SoulBottleHandler.addSoul(player, deathSoulItem, countDie);
                }
            }


            //末影人，凋零骷髅
            // 灵魂：1~4
            // 魔魂：1~3
            // 血魂：2~5
            if (livingEntity instanceof EnderMan
                    || livingEntity instanceof WitherSkeleton) {
                int countSpirit = RandomSource.create().nextInt(4) + 1;
                int countMagic = RandomSource.create().nextInt(3) + 1;
                int countBlood = RandomSource.create().nextInt(4) + 2;
                SoulBottleHandler.addSoul(player, spiritSoulItem, countSpirit);
                SoulBottleHandler.addSoul(player, magicSoulItem, countMagic);
                SoulBottleHandler.addSoul(player, bloodSoulItem, countBlood);
            }
        }
    }




    //抢夺等级可以增加产量
    public static void addSoul(Player player, Item item ,int number){
        int loot = (int) player.getAttributeValue(AttReg.looting);
        number += loot;
        SoulBottle.addSoul(player,new ItemStack(item,number));
    }
}
