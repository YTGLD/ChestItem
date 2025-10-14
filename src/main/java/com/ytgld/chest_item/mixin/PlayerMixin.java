package com.ytgld.chest_item.mixin;

import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.IPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
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
    private void readAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (compound.contains("ChestItems", 9)) {
            this.chest_item$chestInventory.get().fromTag(compound.getList("ChestItems", 10), player.registryAccess());
        }
    }
    @Inject(method = "addAdditionalSaveData", at = @At(value = "RETURN"))
    private void addAdditionalSaveData(CompoundTag compound, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        compound.put("ChestItems", this.chest_item$chestInventory.get().createTag(player.registryAccess()));

    }

    @Override
    public AtomicReference<ChestInventory> chest_item$chestInventory() {
        return chest_item$chestInventory;
    }
}
