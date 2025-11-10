package com.ytgld.chest_item.tip.an_element.elements;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 可怕的药剂
 * <p>
 * 生命值低于50%时伤害和速度有所增加
 */
public class TerriblePotion extends SkillBase {
    public TerriblePotion(){

    }
    public static void pTerriblePotion(ItemStack stack, Player player){
        if (!player.level().isClientSide()) {
            if (SkillBase.isHasElement(stack, SkillList.pTerriblePotion)) {
                player.getAttributes().addTransientAttributeModifiers(modifySpeedAndDamageTerriblePotion(stack, SkillList.pTerriblePotion, player));
            } else {
                player.getAttributes().removeAttributeModifiers(modifySpeedAndDamageTerriblePotion(stack, SkillList.pTerriblePotion, player));
            }
        }
    }






    public static Multimap<Holder<Attribute>, AttributeModifier>
    modifySpeedAndDamageTerriblePotion(
            ItemStack stack,
            TerriblePotion terriblePotion,
            Player player
    ) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        if (SkillBase.isHasElement(stack, SkillList.pTerriblePotion)) {
            int lvl = SkillBase.getHasElementLevel(stack, SkillList.pTerriblePotion);
            lvl++;
            float modifySpeedAndDamage = 0;
            if (player.getHealth() <= player.getMaxHealth() * 0.5f){
                modifySpeedAndDamage = (lvl * 0.05f);

                if (player.tickCount % 20 == 0){
                    SkillBase.addXP(stack,terriblePotion,1,400,10);
                }
            }
            modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                    terriblePotion.baneName()),
                    modifySpeedAndDamage, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                    terriblePotion.baneName()),
                    modifySpeedAndDamage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }

        return modifiers;
    }
    @Override
    public String baneName() {
        return "terrible_potion";
    }
}

