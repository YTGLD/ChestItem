package com.ytgld.chest_item.items.reinforced.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.IDoAttribute;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class Strengthen extends ReinforcedBaseItem {
    public Strengthen(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> attributeUse(Player player) {
        ResourceLocation resourceLocation = ResourceLocation.parse(this.getDescriptionId());
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float armor = 0;
        float res = 0;
        float speed = 0;
        if (ReinforcedBaseItem.decayHeartIsZero(player)){
            armor = 0.2f;
            res = 0.1f;
            speed = 0.1f;
        }

        modifiers.put(Attributes.ARMOR, new AttributeModifier(resourceLocation,
                armor, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(AttReg.resistance, new AttributeModifier(resourceLocation,
                res, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation,
                speed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }
}
