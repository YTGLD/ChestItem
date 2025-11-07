package com.ytgld.chest_item.tip.an_element;

import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.tip.an_element.elements.*;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;

public interface SkillList {
    PlagueSpores pPlagueSpores = new PlagueSpores();
    PlagueDivinePower pPlagueDivinePower = new PlagueDivinePower();



    @Nullable
    Map<SkillBase, ResourceLocation> name();
    @Nullable
    Map<SkillBase, Component> tooltip();
    @Nullable
    Map<SkillBase, Integer> element(ItemStack stack);


}
