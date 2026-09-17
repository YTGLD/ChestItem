package com.ytgld.chest_item.event;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.Reactor;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.reactor.Calciner;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.reactor.Destruction;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class UseItemToReactorHandler {
    public static void event(PlayerInteractEvent.EntityInteract event){
        Player player = event.getEntity();
        ItemStack stack = player.getItemInHand(event.getHand());
        if (event.getTarget() instanceof Reactor reactor) {
            if (stack.is(Items.TNT)) {
                boolean not  = food(reactor, Items.TNT) >= Destruction.max;
                if (!not) {
                    addFoodOrNumber(reactor, stack, 1);
                }
                giveGift(reactor, EvilGifts.destruction.get(),
                        not);
            }
            if (stack.is(Items.DIAMOND)) {
                boolean not  = food(reactor, Items.DIAMOND) >= Calciner.max;

                if (!not) {
                    addFoodOrNumber(reactor, stack, 1);
                }
                giveGift(reactor, EvilGifts.calciner.get(),
                        not);
            }
        }
    }



    private static int food(Reactor reactor, Item item){
        CompoundTag compoundTag = reactor.getPersistentData();
        String mixinName = mixinName(item);
        return compoundTag.getIntOr(mixinName,0);
    }
    private static void addFoodOrNumber(Reactor reactor, ItemStack stack,int value){
        CompoundTag compoundTag = reactor.getPersistentData();
        Item item = stack.getItem();
        String mixinName = mixinName(item);
        compoundTag.putInt(mixinName,compoundTag.getIntOr(mixinName,0) + value);
        reactor.level().playSound(null,reactor.blockPosition(), SoundEvents.ARMADILLO_LAND, SoundSource.BLOCKS,1,1);
        stack.shrink(1);
    }
    private static void giveGift(Reactor reactor, EvilGiftBase evilGiftBase, boolean isTrue){
        if (isTrue && reactor.getOwner() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(item()) && stack.getItem() instanceof IEvilGift evilGift) {
                        evilGift.addGift(stack,evilGiftBase);
                    }
                }
            }
        }
    }
    private static String mixinName(Item item){
        String name = BuiltInRegistries.ITEM.getKey(item).getNamespace();
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();

        return name + "_" + path;
    }
    private static Item item(){
        return InitItems.Agreement_.asItem();
    }
}
