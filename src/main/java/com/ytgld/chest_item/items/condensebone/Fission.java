package com.ytgld.chest_item.items.condensebone;

import com.ytgld.chest_item.items.ItemBone;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Fission extends ItemBone implements SkillList {
    public Fission(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public Map<SkillBase, ResourceLocation> name() {
        Map<SkillBase, ResourceLocation> map = new HashMap<>();
        map.put(pPlagueSpores, SkillList.pPlagueSpores.baneImage());
        map.put(pPlagueDivinePower, SkillList.pPlagueDivinePower.baneImage());
        return map;
    }

    @Override
    public Map<SkillBase, Component> tooltip() {
        Map<SkillBase, Component> map = new HashMap<>();
        map.put(SkillList.pPlagueSpores,Component.translatable("item.chest_item.skill.plague_spores"));
        map.put(SkillList.pPlagueDivinePower,Component.translatable("item.chest_item.skill.plague_divine_power"));
        return map;
    }

    @Nullable
    @Override
    public Map<SkillBase, Integer> element(ItemStack stack) {
        Map<SkillBase, Integer> map = new HashMap<>();

        SkillBase.getElementMap(stack,map,pPlagueSpores);
        SkillBase.getElementMap(stack,map,pPlagueDivinePower);

        return map;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new SkillTooltip(this,this,stack));
    }
}
