package com.ytgld.chest_item.items.meet;

import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.GlStateManager;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 实质之痛苦
 * <p>
 * 通过吸收其他护盾来获得伤痛
 * <p>
 * 伤痛会带来极大的加成和防御
 */
public class ActualSuffering extends ItemBase implements Meat, IBlackLight , ITextColor {
    public ActualSuffering(Properties properties) {
        super(properties);
    }


    @Override
    public void text(ItemStack stack, List<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.add(Component.translatable("item.chest_item.actual_suffering.string.0").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.actual_suffering.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        var attribute  =super.doAttribute(stack, player);
        attribute.put(AttReg.shadow_shield,
                new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()),
                        4, AttributeModifier.Operation.ADD_VALUE));
        attribute.put(AttReg.hyperplasia,
                new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()),
                        4, AttributeModifier.Operation.ADD_VALUE));
        attribute.put(AttReg.chaos_armor,
                new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()),
                        4, AttributeModifier.Operation.ADD_VALUE));


        attribute.put(AttReg.painShield_number,
                new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()),
                        10, AttributeModifier.Operation.ADD_VALUE));
        attribute.put(AttReg.painShield_res,
                new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()),
                        0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        attribute.put(AttReg.painShield_speed,
                new AttributeModifier(ResourceLocation.parse(this.getDescriptionId()),
                        0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return attribute;
    }

    @Override
    public DoBlack colorBlack() {
        return new DoBlack(50 ,100,0,0,
                new CIStateShardsHasBlack.CIFunc (
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO
                )
        );
    }

    @Override
    public int colorText() {
        return 16755200;
    }
}
