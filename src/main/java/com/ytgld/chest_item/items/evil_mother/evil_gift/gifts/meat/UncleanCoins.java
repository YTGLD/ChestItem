package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.meat;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
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
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.HashMap;

/**
 * 不洁硬币
 * <p>
 * 当理智降低到正常水平以下
 * <p>
 * 并且持有者吃下一个附魔金苹果时
 * <p>
 * 令一些物品吸收金苹果的能量
 * <p>
 * 使得物品增生出新的组织从而增加生命值
 *
 *
 */
public class UncleanCoins extends EvilGiftBase {
    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"unclean_coins");
    }

    @Override
    public Identifier image() {
        return EvilGiftBase.theMixinImage(id());
    }

    @Override
    public AttHolderModify attHolderModify() {
        AttHolderModify attHolderModify = new AttHolderModify(new HashMap<>());

        attHolderModify.multimap().put( AttReg.heal,
                new AttributeModifier(this.id(),
                        -0.05f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        attHolderModify.multimap().put(Attributes.MAX_HEALTH,
                new AttributeModifier(this.id(),
                        2, AttributeModifier.Operation.ADD_VALUE));

        return attHolderModify;
    }


    public static void event(LivingEntityUseItemEvent.Finish event){
        if (event.getEntity() instanceof Player player) {
            if (!(EvilMother.getSanValue(player) < 0)) {
                return;
            }
            if (event.getItem().is(Items.ENCHANTED_GOLDEN_APPLE)) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.getItem() instanceof IEvilGift iEvilGift) {
                            if (iEvilGift.addGift(stack, EvilGifts.unclean_coins.get())) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
