package com.ytgld.chest_item;

import com.ytgld.chest_item.renderer.book.CIBookScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class MysteriousMetal extends Item {
    public MysteriousMetal(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        builder.accept(Component.translatable("item.chest_item.mysterious_metal.string.1").withStyle(ChatFormatting.GOLD));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        Minecraft.getInstance().setScreen(new CIBookScreen(player));
        return super.use(level, player, hand);
    }
}
