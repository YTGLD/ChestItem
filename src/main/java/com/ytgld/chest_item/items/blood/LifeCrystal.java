package com.ytgld.chest_item.items.blood;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.tip.an_element.SkillList;
import com.ytgld.chest_item.tip.an_element.SkillTooltip;
import com.ytgld.chest_item.tip.an_element.extend.SkillBase;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LifeCrystal extends ItemBase implements SkillList {
    public LifeCrystal(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        return attributeModifierMultimap();
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap()
 {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

            modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Life_Crystal.asItem().getDescriptionId()),
                10, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(AttReg.hyperplasia, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Life_Crystal.asItem().getDescriptionId()),
                4, AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return attributeModifierMultimap();
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,50,50);
    }

    @Nullable
    @Override
    public Map<SkillBase, Identifier> name() {
        Map<SkillBase, Identifier> map = new HashMap<>();
        map.put(pTherapeutic, SkillList.pTherapeutic.baneImage());
        return map;
    }

    @Override
    public Map<SkillBase, Component> tooltip() {
        Map<SkillBase, Component> map = new HashMap<>();
        map.put(SkillList.pTherapeutic,Component.translatable("item.chest_item.skill."+pTherapeutic.baneName()));
        return map;
    }

    @Nullable
    @Override
    public Map<SkillBase, Integer> element(ItemStack stack) {
        Map<SkillBase, Integer> map = new HashMap<>();
        SkillBase.getElementMap(stack,map,pTherapeutic);

        return map;
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return Optional.of(new SkillTooltip(this,this,stack));
    }
}
