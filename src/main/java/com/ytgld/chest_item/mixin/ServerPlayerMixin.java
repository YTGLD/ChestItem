package com.ytgld.chest_item.mixin;

import com.mojang.authlib.GameProfile;
import com.ytgld.chest_item.other.IPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player{
    public ServerPlayerMixin(Level level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "restoreFrom", at = @At(value = "RETURN"))
    private void restoreFrom(ServerPlayer that, boolean keepEverything, CallbackInfo ci) {
        if ((ServerPlayer) (Object)this instanceof IPlayer player){
            if (that instanceof IPlayer iPlayer){
                player.chest_item$chestInventory().set(iPlayer.chest_item$chestInventory().get());
            }
        }
    }
}
