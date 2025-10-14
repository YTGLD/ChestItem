package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Shadow private int tickTimer;

    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void tick(Player player, CallbackInfo ci) {
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory != null) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack =chestInventory.getItem(i);
                if (stack.is(InitItems.Heart_)||stack.is(InitItems.ImitationBiomass_)){
                    if (!player.level().isClientSide()&&player.tickCount%2 == 1) {
                        tickTimer++;
                        break;
                    }
                }
            }
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack =chestInventory.getItem(i);
                if (stack.is(InitItems.Stomach_)){
                    if (!player.level().isClientSide()) {
                        tickTimer++;
                        break;
                    }
                }
            }
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack =chestInventory.getItem(i);
                if (stack.is(InitItems.GiantHeart_)){
                    if (!player.level().isClientSide()) {
                        tickTimer++;
                        break;
                    }
                }
            }
        }

    }
}
