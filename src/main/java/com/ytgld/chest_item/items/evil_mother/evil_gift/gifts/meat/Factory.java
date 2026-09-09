package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.meat;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGifts;
import com.ytgld.chest_item.items.evil_mother.evil_gift.IEvilGift;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.HashMap;

/**
 * 反应工厂
 * <p>
 * 当理智降低到正常水平以下
 * <p>
 * 并且持有者携带经验反应炉死亡时
 * <p>
 * 将令一些物品产生反应
 * <p>
 * 从而自发产生经验值
 */
public class Factory extends EvilGiftBase {
    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"factory");
    }

    @Override
    public Identifier image() {
        return EvilGiftBase.theMixinImage(id());
    }

    @Override
    public void tickGift(Player player, ItemStack stack) {
        super.tickGift(player, stack);

        int san = (int) EvilMother.getSanValue(player);
        //10
        int baseSan = (int) EvilMother.getSanValueBase(player);

        int outSan = san - baseSan;

        outSan *= 5;

        int time = 200 - Math.abs(outSan);

        if (time < 10) {
            time = 10;
        }
        if (player.tickCount % time == 1) {
            int xp = 1;
            player.giveExperiencePoints(xp);
        }
    }

    @Override
    public AttHolderModify attHolderModify() {
        AttHolderModify attHolderModify = new AttHolderModify(new HashMap<>());

        attHolderModify.multimap().put(Attributes.MAX_HEALTH,
                new AttributeModifier(this.id(),
                        -0.05f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return attHolderModify;
    }


    public static void event(LivingDeathEvent event){
        if (event.getEntity() instanceof Player player) {
            if (!(EvilMother.getSanValue(player) < 0)) {
                return;
            }
            if (!Handler.has(player, InitItems.NuclearReaction_.asItem())) {
                return;
            }
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.getItem() instanceof IEvilGift iEvilGift) {
                        if (iEvilGift.addGift(stack, EvilGifts.factory.get())) {
                            break;
                        }
                    }
                }
            }
        }
    }
}

