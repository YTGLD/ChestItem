package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.HashMap;

public class Dawn extends EvilGiftBase {

    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"dawn");
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

        attHolderModify.multimap().put(Attributes.LUCK,
                new AttributeModifier(this.id(),
                        3, AttributeModifier.Operation.ADD_VALUE));


        return attHolderModify;
    }
}

