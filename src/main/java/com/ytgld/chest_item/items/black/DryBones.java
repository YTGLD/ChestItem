package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class DryBones extends ItemBlackShadow {


    public DryBones(Properties properties) {
        super(properties);
    }
    public static void ItemStackTickEvent(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (player!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.DryBones_)) {
                        player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap(player));
                        break;
                    } else {
                        player.getAttributes().removeAttributeModifiers(attributeModifierMultimap(player));
                    }
                }
            }
        }
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float hyperplasia = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
        float shadow_shield = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
        float a =0;
        float s =0;
        float d =0;
        float f =0;

        AttributeInstance attributeInstance_hyperplasia = player.getAttribute(AttReg.hyperplasia);
        AttributeInstance attributeInstance_shadow_shield = player.getAttribute(AttReg.shadow_shield);
        if (attributeInstance_hyperplasia != null && attributeInstance_shadow_shield != null) {
            float hV = (float) attributeInstance_hyperplasia.getValue() - 1;
            float vS = (float)attributeInstance_shadow_shield.getValue() - 1;

            if (hyperplasia >= hV){
                if (hyperplasia!=0 && hV != 0) {
                    a = 0.2f;
                    s = 0.2f;
                }
            }

            if (shadow_shield >= vS){
                if (shadow_shield!=0 && vS != 0) {
                    d = 0.2f;
                    f = 0.2f;
                }
            }
        }

        modifiers.put(Attributes.ARMOR, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DryBones_.asItem().getDescriptionId()),
                a, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DryBones_.asItem().getDescriptionId()),
                s, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DryBones_.asItem().getDescriptionId()),
                d, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.DryBones_.asItem().getDescriptionId()),
                f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }


    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player) {
        return attributeModifierMultimap(player);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.dry_bones.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.accept(Component.translatable("item.chest_item.dry_bones.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }
}
