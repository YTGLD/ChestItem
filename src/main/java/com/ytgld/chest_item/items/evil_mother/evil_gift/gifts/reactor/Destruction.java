package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.reactor;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.entity.Reactor;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * 湮灭装置
 * <p>
 * 给反应炉“喂食”16块TNT
 * <p>
 * 会使反应炉激活毁灭组件
 * <p>
 * 在反应炉被意外摧毁时
 * <p>
 * 产生巨大的爆炸
 *
 */
public class Destruction extends EvilGiftBase {
    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"destruction");
    }

    @Override
    public Identifier image() {
        return EvilGiftBase.theMixinImage(id());
    }
    public static final int max = 16;

    public static void event(LivingDeathEvent event){
        if (event.getEntity() instanceof Reactor reactor
                && reactor.getOwner() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (IEvilGift.isHasEvilGift(stack, EvilGifts.destruction.get())) {
                        reactor.level().explode(player,reactor.getX(),reactor.getY(),reactor.getZ(),6.5f,false, Level.ExplosionInteraction.NONE);
                    }
                }
            }
        }
    }
}
