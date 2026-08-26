package com.ytgld.chest_item.event.key;

import com.ytgld.chest_item.items.black.Test;
import com.ytgld.chest_item.items.black.celestial.Blood;
import com.ytgld.chest_item.items.other.end.TheEndIsComing;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.tip.an_element.elements.PlagueSpores;
import com.ytgld.chest_item.utils.RenderObjectManager;
import com.ytgld.chest_item.utils.dout.WrathHeavenRenderObject;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class UseCuriosHandler {
    public static void register(final PayloadRegistrar registrar) {
        registrar.playToServer(UseChestCurios.TYPE, UseChestCurios.CHEST_CURIOS_STREAM_CODEC,
                handlerUse::handleOpenCurios);

    }
    public static HandlerUse handlerUse = new HandlerUse();
    public static class HandlerUse {
        public void handleOpenCurios(final UseChestCurios data, final IPayloadContext ctx) {
            ctx.enqueueWork(() -> {
                Player player = ctx.player();
                TheEndIsComing.event(player);
                PlagueSpores.useSkill(player);
                Blood.onKeyIsDown(player);
                Test.onKeyIsDown(player);

                RenderObjectManager.add(new WrathHeavenRenderObject(player.getEyePosition().add(0,10,0),
                        Light.ARGB.color(255,140,240,255),10));
            });
        }
    }
}
