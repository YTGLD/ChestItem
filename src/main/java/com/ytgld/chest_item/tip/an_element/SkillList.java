package com.ytgld.chest_item.tip.an_element;

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
    TerriblePotion pTerriblePotion = new TerriblePotion();
    Therapeutic pTherapeutic = new Therapeutic();
    Decisively pDecisively = new Decisively();
    Hyperplasia pHyperplasia = new Hyperplasia();
    DoomsdayJudgment pDoomsdayJudgment = new DoomsdayJudgment();
    Rotten pRotten = new Rotten();



    @Nullable
    Map<SkillBase, ResourceLocation> name();
    @Nullable
    Map<SkillBase, Component> tooltip();
    @Nullable
    Map<SkillBase, Integer> element(ItemStack stack);


}
