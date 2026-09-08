package com.ytgld.chest_item.items.evil_mother.evil_gift;

import com.google.common.collect.HashMultimap;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;

public abstract class EvilGiftBase {
    public abstract String id();
    public abstract Identifier image();
    public abstract AttHolderModify attHolderModify();


    static Identifier theMixinImage(String string){
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/evil_mother/" + string + ".png");
    }

    public record AttHolderModify(HashMap<Holder<Attribute> , AttributeModifier> multimap){}

}
