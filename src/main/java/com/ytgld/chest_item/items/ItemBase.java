package com.ytgld.chest_item.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.items.black.chaos_item.RunawayLining;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ItemBase extends Item implements Terror{
    public ItemBase(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        int c = 0XFFCD853F;
        CompoundTag tag = stack.get(DataReg.tag);
        if (tag != null) {
            if (tag.getBooleanOr(IBlackLight.blackName, false)){
                c = 0xffff0000;
                co = Component.translatable("chest_item.attribute").append(component.copy());
            }
        }
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(c)));
        return co;
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player){
        Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap = HashMultimap.create();
        RunawayLining.addMap(attributeModifierMultimap, player, stack);
        return attributeModifierMultimap;
    }
    public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        text(stack, tooltipComponents, flag);
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255, 255, 0, 100);
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }
}
