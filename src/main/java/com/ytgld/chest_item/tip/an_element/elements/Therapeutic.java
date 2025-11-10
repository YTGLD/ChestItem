package com.ytgld.chest_item.tip.an_element.elements;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 生命值高于50%时增加治疗效果
 */
public class Therapeutic  extends SkillBase {
    public Therapeutic(){

    }
    public static void pPlagueSpores(ItemStack stack, Player player){
        if (!player.level().isClientSide()) {
            if (SkillBase.isHasElement(stack, SkillList.pTherapeutic)) {
                player.getAttributes().addTransientAttributeModifiers(modifyHealTherapeutic(stack, SkillList.pTherapeutic, player));
            } else {
                player.getAttributes().removeAttributeModifiers(modifyHealTherapeutic(stack, SkillList.pTherapeutic, player));
            }
        }
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> modifyHealTherapeutic(
            ItemStack stack,
            Therapeutic therapeutic,
            Player player
    ){
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        if (SkillBase.isHasElement(stack, SkillList.pTherapeutic)){
            int lvl = SkillBase.getHasElementLevel(stack, SkillList.pTherapeutic);
            lvl++;
            float modifyHeal = 0;
            if (player.getHealth() > player.getMaxHealth() * 0.5f){
                modifyHeal = (lvl * 0.08f);

                if (player.tickCount % 20 == 0){
                    SkillBase.addXP(stack,therapeutic,1,600,10);
                }
            }
            modifiers.put(AttReg.heal, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                    therapeutic.baneName()),
                    modifyHeal, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
        return modifiers;
    }
    @Override
    public String baneName() {
        return "therapeutic";
    }
}


