package com.ytgld.chest_item.items.iron;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 *  坚韧立方体
 * <p>
 * 伤害变得极度稳定，且输出能力有所增加
 * <p>
 * 生命值降低不会影响你的意志，反而会增加自身的稳定性
 */
public class IronCube extends ItemBase {
    public IronCube(Properties properties) {
        super(properties);
    }
    public static void ItemStackTickEvent(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (player!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.IronCube_)) {
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

        modifiers.put(Attributes.ARMOR, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.IronCube_.asItem().getDescriptionId()),
                -0.45, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        float lv = player.getHealth() / player.getMaxHealth();
        lv *= 100;
        int now = (int) (100 -(lv));
        float apply = 1 / 100f * now;


        apply+=1;


        modifiers.put(AttReg.instability, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.IronCube_.asItem().getDescriptionId()),
                -0.1f*apply, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(AttReg.instability_low, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.IronCube_.asItem().getDescriptionId()),
                -0.15f*apply, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
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
        tooltipAdder.accept(Component.translatable("item.chest_item.iron_cube.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.iron_cube.string.2").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.iron_cube.string.3").withStyle(ChatFormatting.GOLD));
    }
}
