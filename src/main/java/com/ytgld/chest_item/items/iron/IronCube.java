package com.ytgld.chest_item.items.iron;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ILight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

/**
 *  坚韧立方体
 * <p>
 * 伤害变得极度稳定，且输出能力有所增加
 * <p>
 * 生命值降低不会影响你的意志，反而会增加自身的稳定性
 */
public class IronCube extends ItemBase implements ILight {
    public IronCube(Properties properties) {
        super(properties);
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.ARMOR, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.IronCube_.asItem().getDescriptionId()),
                -0.45, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        float lv = player.getHealth() / player.getMaxHealth();
        lv *= 100;
        int now = (int) (100 -(lv));
        float apply = 1 / 100f * now;


        apply+=1;


        modifiers.put(AttReg.instability, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.IronCube_.asItem().getDescriptionId()),
                -0.1f*apply, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(AttReg.instability_low, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.IronCube_.asItem().getDescriptionId()),
                -0.15f*apply, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }


    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack,player);
    }

    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.iron_cube.string.1").withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.iron_cube.string.2").withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.iron_cube.string.3").withStyle(ChatFormatting.GOLD));
    }

    @Override
    public boolean isWhirlpool() {
        return true;
    }
}
