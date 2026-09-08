package com.ytgld.chest_item.items.evil_mother.evil_gift;

import com.google.common.collect.HashMultimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;

public class RottenUtensils extends EvilGiftBase{

    @Override
    public String id() {
        return "rotten_utensils";
    }

    @Override
    public Identifier image() {
        return EvilGiftBase.theMixinImage(id());
    }

    @Override
    public AttHolderModify attHolderModify() {
        AttHolderModify attHolderModify = new AttHolderModify(new HashMap<>());

        attHolderModify.multimap().put( AttReg.theSanity,
                new AttributeModifier(Identifier.fromNamespaceAndPath(Chestitem.MODID,this.id()),
                        -1, AttributeModifier.Operation.ADD_VALUE));


        return attHolderModify;
    }
}
