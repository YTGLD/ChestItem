package com.ytgld.chest_item.items.evil_mother.evil_gift;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;

public abstract class EvilGiftBase {
    public abstract Identifier id();
    public abstract Identifier image();
    public AttHolderModify attHolderModify(){
        return new AttHolderModify(new HashMap<>());
    }

    public void tickGift(Player player, ItemStack stack){

    }

    protected static Identifier theMixinImage(Identifier identifier){
        return Identifier.fromNamespaceAndPath(identifier.getNamespace(),"textures/evil_mother/" + identifier.getPath() + ".png");
    }

    public record AttHolderModify(HashMap<Holder<Attribute> , AttributeModifier> multimap){}

}
