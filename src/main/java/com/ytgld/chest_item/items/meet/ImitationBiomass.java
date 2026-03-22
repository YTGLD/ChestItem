package com.ytgld.chest_item.items.meet;

import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.items.gold.ArmorStone;
import com.ytgld.chest_item.items.gold.LifeStone;
import com.ytgld.chest_item.items.gold.StrongerStone;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class ImitationBiomass extends ItemBase implements Meat {

    public ImitationBiomass(Properties properties) {
        super(properties);
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.imitation_biomass.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.armor_stone.string.1", 100f- ArmorStone.ConfigItem.intValue.get().floatValue()* 100F - 100f).withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.stronger_stone.string.1", StrongerStone.ConfigItem.intValue.get().floatValue() * 100f - 100f).withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.life_stone.string.1", LifeStone.ConfigItem.intValue.get().floatValue() * 100f -100f).withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.heart.string.1").withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.imitation_biomass.string.1").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));

    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,0,255);
    }
}
