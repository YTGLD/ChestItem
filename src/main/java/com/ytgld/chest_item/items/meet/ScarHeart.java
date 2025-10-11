package com.ytgld.chest_item.items.meet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ScarHeart  extends ItemBase implements Meat {
    public ScarHeart(Properties properties) {
        super(properties);
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.ScarHeart_)) {
                    player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap());
                    break;
                } else {
                    player.getAttributes().removeAttributeModifiers(attributeModifierMultimap());
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.scar_heart.string.1").withStyle(ChatFormatting.GOLD));
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap() {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.hyperplasia, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.ScarHeart_.asItem().getDescriptionId()),
                7, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.hyperplasia_speed, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.ScarHeart_.asItem().getDescriptionId()),
                -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(AttReg.hyperplasia_stronger, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.ScarHeart_.asItem().getDescriptionId()),
                0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player) {
        return attributeModifierMultimap();
    }
}
