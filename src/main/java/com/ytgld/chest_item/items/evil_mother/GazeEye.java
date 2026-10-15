package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.effect.Effects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.function.Consumer;

public class GazeEye extends EvilMother{
    public GazeEye(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(Player player, ItemStack stack) {
        super.tick(player, stack);
        if (!player.level().isClientSide() && player.tickCount % 20 == 1) {
            player.addEffect(new MobEffectInstance(Effects.gaze,200,0,false,false));
        }
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.gaze_eye.string.1").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.gaze_eye.string.2").withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public int getSanity() {
        return -3;
    }
}
