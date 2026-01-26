package com.ytgld.chest_item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.IGUILight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.MRender;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class OwnerLead extends ItemBase implements IGUILight {
    public OwnerLead(Properties properties) {
        super(properties);
    }
    public static final String name = "compoundTagMyLoveCharm";
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        return attributeModifierMultimap();
    }

    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap() {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                0.05F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                2, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(NeoForgeMod.SWIM_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                0.15F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.BLOCK_BREAK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                0.08F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.OwnerLead_.asItem().getDescriptionId()),
                4, AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return attributeModifierMultimap();
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        CompoundTag compoundTag =stack.get(DataReg.tag);
        if (compoundTag!=null) {
            tooltipAdder.accept(Component.literal(compoundTag.getStringOr(name,"null")).append(Component.literal("的礼物！").withStyle(ChatFormatting.GOLD)));
        }
    }

    @Override
    public int guiColor(ItemStack stack) {
        return Light.ARGB.color(255,255,0,0);
    }

    @Override
    public Identifier img() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/heart.png");
    }

    @Override
    public RenderPipeline renderType() {
        return MRender.RenderPs.LightSlowness(true,0.1f,1000);
    }

    @Override
    public Vec2 posOffset() {
        return new Vec2(0,0);
    }
}
