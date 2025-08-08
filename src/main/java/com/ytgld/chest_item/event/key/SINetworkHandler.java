package com.ytgld.chest_item.event.key;

import com.ytgld.chest_item.Handler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class SINetworkHandler {
    public static void register(final PayloadRegistrar registrar) {
        registrar.playToServer(UseCurio.TYPE, UseCurio.USE_CURIO_STREAM_CODEC,
                handlerUse::handleOpenCurios);

    }
    public static HandlerUse handlerUse = new HandlerUse();
    public static class HandlerUse {
        public void handleOpenCurios(final UseCurio data, final IPayloadContext ctx) {
            ctx.enqueueWork(() -> {
                Player player = ctx.player();
                if (player instanceof ServerPlayer serverPlayer) {
                    Handler.openItemChest(serverPlayer);
                }
            });
        }
    }
}
