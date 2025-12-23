package com.ytgld.chest_item.tip.an_element.elements;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 每点增生组织增加2点生命值*
 */
public class Hyperplasia extends SkillBase {
    public Hyperplasia(){

    }
    public static void pHyperplasia(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (!player.level().isClientSide()) {
                    if (SkillBase.isHasElement(stack, SkillList.pHyperplasia)) {
                        player.getAttributes().addTransientAttributeModifiers(modifySpeedAndDamageHyperplasia(stack, SkillList.pHyperplasia, player));
                        break;
                    } else {
                        player.getAttributes().removeAttributeModifiers(modifySpeedAndDamageHyperplasia(stack, SkillList.pHyperplasia, player));
                    }
                }
            }
        }
    }
    public static Multimap<Holder<Attribute>, AttributeModifier>
    modifySpeedAndDamageHyperplasia(
            ItemStack stack,
            Hyperplasia terriblePotion,
            Player player
    ) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float modify = SkillList.pHyperplasia.aneLvlForModify();
        int s = (int) (float) player.getData(AttReg.hyperplasiaATTACHMENT_TYPES.get());
        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                terriblePotion.baneName()),
                modify*s, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }
    @Override
    public String baneName() {
        return "hyperplasia";
    }

    @Override
    public boolean isPercentage() {
        return false;
    }

    @Override
    public float aneLvlForModify() {
        return 2.5F;
    }

    @Override
    public int levelMax() {
        return 1;
    }
}


