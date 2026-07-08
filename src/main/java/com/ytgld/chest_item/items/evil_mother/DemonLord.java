package com.ytgld.chest_item.items.evil_mother;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.black.ChaosConstructor;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class DemonLord extends EvilMother implements SkillList {

    public DemonLord(Properties properties) {
        super(properties);
    }

    @Override
    public int getSanity() {
        return -5;
    }

    @Override
    public @Nullable Map<SkillBase, Identifier> name() {
        Map<SkillBase, Identifier> map = new HashMap<>();
        map.put(pDecisively, SkillList.pDecisively.baneImage());
        map.put(pRotten, SkillList.pRotten.baneImage());
        return map;
    }

    @Override
    public @Nullable Map<SkillBase, Component> tooltip() {
        Map<SkillBase, Component> map = new HashMap<>();
        map.put(SkillList.pDecisively,Component.translatable("item.chest_item.skill."+pDecisively.baneName()));
        map.put(SkillList.pRotten,Component.translatable("item.chest_item.skill."+pRotten.baneName()));
        return map;
    }

    @Nullable
    @Override
    public Map<SkillBase, Integer> element(ItemStack stack) {
        Map<SkillBase, Integer> map = new HashMap<>();
        SkillBase.getElementMap(stack,map,pDecisively);
        SkillBase.getElementMap(stack,map,pRotten);
        return map;
    }
    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new SkillTooltip(this,this,stack));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.oppression, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                this.getDescriptionId()),
                60, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.demon_lord.string.1").withStyle(Style.EMPTY.withColor(color)));
    }



}
