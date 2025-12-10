package com.ytgld.chest_item.items.black;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.apache.logging.log4j.core.appender.rolling.action.IfNot;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.function.Consumer;

public class Pod extends ItemBlackShadow {
    public Pod(Properties properties) {
        super(properties);
    }
    public static void pPod(MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir,Player player){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.Pod_)) {
                        if (effectInstance.is(MobEffects.POISON)
                                || effectInstance.is(MobEffects.DARKNESS)
                                || effectInstance.is(MobEffects.WITHER)) {
                            cir.setReturnValue(false);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.pod.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));

    }

}
