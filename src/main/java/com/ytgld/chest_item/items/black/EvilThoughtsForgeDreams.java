package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * 恶念锻梦者
 * <p>
 * 将50%的增生组织转换成幽影护盾
 * <p>
 * +30%幽影护盾扩充速度
 * <p>
 * +40%幽影护盾稳固度
 */
public class EvilThoughtsForgeDreams extends ItemBlackShadow {


    public EvilThoughtsForgeDreams(Properties properties) {
        super(properties);
    }
    
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float bs = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
        modifiers.put(AttReg.shadow_shield, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.EvilThoughtsForgeDreams_.asItem().getDescriptionId()),
                bs/2, AttributeModifier.Operation.ADD_VALUE));

        modifiers.put(AttReg.shadow_shield_speed, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.EvilThoughtsForgeDreams_.asItem().getDescriptionId()),
                0.3, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(AttReg.shadow_shield_stronger, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.EvilThoughtsForgeDreams_.asItem().getDescriptionId()),
                0.4, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }


    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack,player);
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.evil_thoughts_forge_dreams.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }


}
