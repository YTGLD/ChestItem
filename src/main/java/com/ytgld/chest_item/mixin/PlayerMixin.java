package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.IPlayer;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicReference;

@Mixin(Player.class)
public class PlayerMixin implements IPlayer {
    @Unique
    protected final AtomicReference<ChestInventory> chest_item$chestInventory = new AtomicReference<>(new ChestInventory((Player) (Object) this));

    @Inject(method = "readAdditionalSaveData", at = @At(value = "RETURN"))
    private void readAdditionalSaveData(ValueInput p_422427_, CallbackInfo ci) {
        this.chest_item$chestInventory.get().fromSlots(p_422427_.listOrEmpty("ChestItems", ItemStackWithSlot.CODEC));

    }
    @Inject(method = "addAdditionalSaveData", at = @At(value = "RETURN"))
    private void addAdditionalSaveData(ValueOutput p_421801_, CallbackInfo ci) {
        this.chest_item$chestInventory.get().storeAsSlots(p_421801_.list("ChestItems", ItemStackWithSlot.CODEC));
    }

    @Override
    public AtomicReference<ChestInventory> chest_item$chestInventory() {
        return chest_item$chestInventory;
    }
}
