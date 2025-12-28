package com.ytgld.chest_item.items.meet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.event.use.EventMain;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ScarHeart  extends ItemBase implements Meat {
    public ScarHeart(Properties properties) {
        super(properties);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        return attributeModifierMultimap();
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.scar_heart.string.1").withStyle(ChatFormatting.GOLD));

    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap()
 {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.hyperplasia, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.ScarHeart_.asItem().getDescriptionId()),
                12, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return attributeModifierMultimap();
    }
}
