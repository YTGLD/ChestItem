package com.ytgld.chest_item.items.evil_mother.evil_gift.gifts;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.evil_mother.evil_gift.EvilGiftBase;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;

public class SnapString extends EvilGiftBase {

    @Override
    public Identifier id() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"snap_string");
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
                        3, AttributeModifier.Operation.ADD_VALUE));


        return attHolderModify;
    }
}
