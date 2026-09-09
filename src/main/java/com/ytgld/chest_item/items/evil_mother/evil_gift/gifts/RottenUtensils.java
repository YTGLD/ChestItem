package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts;

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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;

import java.util.HashMap;

public class RottenUtensils extends EvilGiftBase {

    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"rotten_utensils");
    }

    @Override
    public Identifier image() {
        return EvilGiftBase.theMixinImage(id());
    }

    @Override
    public AttHolderModify attHolderModify() {
        AttHolderModify attHolderModify = new AttHolderModify(new HashMap<>());

        attHolderModify.multimap().put( AttReg.theSanity,
                new AttributeModifier(this.id(),
                        -2, AttributeModifier.Operation.ADD_VALUE));


        return attHolderModify;
    }

    public static void event(LivingUseTotemEvent event){
        if (event.getEntity() instanceof Player player) {
            if (!(EvilMother.getSanValue(player) < 0)) {
                return;
            }


            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.getItem() instanceof IEvilGift iEvilGift) {
                        iEvilGift.addGift(stack, EvilGifts.rotten_utensils.get());
                    }
                }
            }
        }
    }
}
