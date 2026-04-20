package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

/**
 * 腐毒束具
 * <p>
 * 获得绝对的击退抗性
 * <p>
 * 并使你的肉身几乎无法推动
 */
public class EvilBelt extends EvilMother{
    public EvilBelt(Properties properties) {
        super(properties);
    }
    public static void  knock(LivingKnockBackEvent event){
        if (event .getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.EvilBelt_.asItem())) {
                event.setRatioX(0);
                event.setStrength(0);
                event.setRatioZ(0);

                event.setCanceled(true);
            }
        }
    }

    public static void  push(Entity entity , CallbackInfo cir){
        if (entity instanceof Player player && !player.level().isClientSide()) {
            if (Handler.has(player, InitItems.EvilBelt_.asItem())) {
                cir.cancel();
            }
        }
    }
    public static void  canBeCollidedWith(Entity entity , CallbackInfoReturnable<Boolean> cir){
        if (entity instanceof Player player && !player.level().isClientSide()) {
            if (Handler.has(player, InitItems.EvilBelt_.asItem())) {
                cir.setReturnValue(true);
            }
        }
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.text(stack, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.evil_belt.string.0", TheKill.ConfigItem.intValue.getAsInt(), TheKill.ConfigItem.intValue2.getAsInt()).withStyle(Style.EMPTY.withColor(color)));
        tooltipAdder.accept(Component.translatable("item.chest_item.evil_belt.string.1").withStyle(Style.EMPTY.withColor(color)));

    }

    @Override
    public int getSanity() {
        return -4;
    }
}
