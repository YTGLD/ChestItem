package com.ytgld.chest_item.items.evil_mother;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.HandlerClient;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

/**
 * 腐烂物质
 * <p>
 * 舍弃所有的护盾来换取纯粹的抗性与伤害
 */
public class RottingSubstance extends EvilMother{
    public RottingSubstance(Properties properties) {
        super(properties);
    }

    public static boolean canHeal(Player living) {
        if (Handler.has(living,InitItems.RottingSubstance_.asItem())){
            return false;
        }
        return true;
    }

    @Override
    public void tick(Player player, ItemStack stack) {
        super.tick(player, stack);
        player.setData(AttReg.hyperplasiaATTACHMENT_TYPES,0f);
        player.setData(AttReg.painShield,0f);
        player.setData(AttReg.shadow_shield_ATTACHMENT_TYPES,0f);
        player.setData(AttReg.chaosWinds,0f);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> multimap = HashMultimap.create();
        multimap.put(AttReg.painShield_number,
                new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + this.getDescriptionId()),
                        1, AttributeModifier.Operation.ADD_VALUE));
        multimap.put(AttReg.chaos_armor,
                new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + this.getDescriptionId()),
                        1, AttributeModifier.Operation.ADD_VALUE));
        multimap.put(AttReg.hyperplasia,
                new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + this.getDescriptionId()),
                        1, AttributeModifier.Operation.ADD_VALUE));
        multimap.put(AttReg.shadow_shield,
                new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + this.getDescriptionId()),
                        1, AttributeModifier.Operation.ADD_VALUE));
        return multimap;
    }

    public static void damage(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.RottingSubstance_.asItem())) {
                int value = (int) allShieldValue(player);
                float base = (float) Math.sqrt(value) / 6f;
                float doIt = 1 + base;
                event.setNewDamage(event.getNewDamage() * doIt);
            }
        }
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.RottingSubstance_.asItem())) {
                int value = (int) allShieldValue(player);
                float base = (float) Math.sqrt(value) / 4f;
                if (base < 1) {
                    base = 1;
                }
                float doIt = 1 / base;
                event.setNewDamage(event.getNewDamage() * doIt);
            }
        }
    }


    public static float allShieldValue(Player player){
        float h = (float) player.getAttributeValue(AttReg.hyperplasia);
        float c = (float) player.getAttributeValue(AttReg.chaos_armor);
        float s = (float) player.getAttributeValue(AttReg.shadow_shield);
        float p = (float) player.getAttributeValue(AttReg.painShield_number);
        return h+s+c+p;
    }

    @Override
    public void text(ItemStack stack, List<Component> tooltipAdder, TooltipFlag flag) {
        super.text(stack, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.rotting_substance.string.0").withStyle(Style.EMPTY.withColor(color)));

    }

    @Override
    public int getSanity() {
        return -3;
    }
}
