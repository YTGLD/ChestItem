package com.ytgld.chest_item.items.meet;

import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FleshAndBloodGears extends ItemBase implements Meat , SkillList {


    public FleshAndBloodGears(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public Map<SkillBase, Identifier> name() {
        Map<SkillBase, Identifier> map = new HashMap<>();
        map.put(pTerriblePotion, SkillList.pTerriblePotion.baneImage());
        map.put(pTherapeutic, SkillList.pTherapeutic.baneImage());
        return map;
    }

    @Override
    public Map<SkillBase, Component> tooltip() {
        Map<SkillBase, Component> map = new HashMap<>();
        map.put(SkillList.pTerriblePotion,Component.translatable("item.chest_item.skill."+pTerriblePotion.baneName()));
        map.put(SkillList.pTherapeutic,Component.translatable("item.chest_item.skill."+pTherapeutic.baneName()));
        return map;
    }

    @Nullable
    @Override
    public Map<SkillBase, Integer> element(ItemStack stack) {
        Map<SkillBase, Integer> map = new HashMap<>();

        SkillBase.getElementMap(stack,map,pTerriblePotion);
        SkillBase.getElementMap(stack,map,pTherapeutic);

        return map;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new SkillTooltip(this,this,stack));
    }
}
