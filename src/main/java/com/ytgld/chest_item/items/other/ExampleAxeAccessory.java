package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.items.IChestItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ExampleAxeAccessory extends AxeItem implements IChestItem {
    public ExampleAxeAccessory(Properties properties) {
        super(Tiers.IRON, properties);
    }

    @Override
    public void onChestTick(ItemStack stack, Player player) {
        if (player.tickCount % 60 == 0) {
            player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 100, 0, false, false), player);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.example_axe_accessory.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
    }
}
