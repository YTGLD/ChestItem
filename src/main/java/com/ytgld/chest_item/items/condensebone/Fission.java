package com.ytgld.chest_item.items.condensebone;

import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.ItemBone;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import com.ytgld.chest_item.tip.an_element.elements.PlagueDivinePower;
import com.ytgld.chest_item.tip.an_element.elements.PlagueSpores;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.nbt.CompoundTag;
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
    public static final String plague_spores = "plague_spores";
    public static final String plague_divine_power = "plague_divine_power";



    @Nullable
    @Override
    public Map<SkillBase, ResourceLocation> name() {
        Map<SkillBase, ResourceLocation> map = new HashMap<>();
        map.put(pPlagueSpores, PlagueSpores.plagueSpores);
        map.put(pPlagueDivinePower, PlagueDivinePower.plagueDivinePower);
        return map;
    }

    @Override
    public Map<SkillBase, Component> tooltip() {
        Map<SkillBase, Component> map = new HashMap<>();
        map.put(this.pPlagueSpores,Component.translatable("item.chest_item.skill.plague_spores"));
        map.put(this.pPlagueDivinePower,Component.translatable("item.chest_item.skill.plague_divine_power"));
        return map;
    }

    @Nullable
    @Override
    public Map<SkillBase, Integer> element(ItemStack stack) {
        Map<SkillBase, Integer> map = new HashMap<>();
        @Nullable CompoundTag compoundTag = stack.get(DataReg.tag);

        {
            int s = 0;
            if (compoundTag != null) {
                s = compoundTag.getIntOr(plague_spores, 0);
            }
            map.put(this.pPlagueSpores, s);
        }
        {
            int s = 0;
            if (compoundTag != null) {
                s = compoundTag.getIntOr(plague_divine_power, 0);
            }
            map.put(this.pPlagueDivinePower, s);
        }


        return map;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new SkillTooltip(this,this,stack));
    }
}
