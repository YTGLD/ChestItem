package com.ytgld.chest_item.utils;

import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.utils.network.SwordRenderPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class AddUtil {

    public static void addSword(
            @Nullable Player player,
            LivingEntity living
    ) {
        int color = Light.ARGB.color(255, 150, 220, 250);

        if (player != null) {
            int san = (int) (player.getAttributeBaseValue(AttReg.theSanity) - player.getAttributeValue(AttReg.theSanity));

            if (san >= 10) {
                living.addEffect(new MobEffectInstance(Effects.EvilErosion, 200, 1));
                color = Light.ARGB.color(255, 0, 255, 150);
            }
        }

        RandomSource random = living.getRandom();

        float rotationX =  Mth.nextInt(random, -360, 360);
        float rotationY = Mth.nextInt(random, -360, 360);
        float rotationZ = Mth.nextInt(random, -360, 360);

        SwordRenderPacket packet = new SwordRenderPacket(
                living.getX(),
                living.getEyeY(),
                living.getZ(),

                rotationX,
                rotationY,
                rotationZ,

                color,
                2
        );

        sendToNearbyPlayers(living, packet, 64.0);
    }
    private static void sendToNearbyPlayers(
            LivingEntity entity,
            SwordRenderPacket packet,
            double range
    ) {
        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.getServer()
                .getPlayerList()
                .broadcast(
                        null,
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        range,
                        serverLevel.dimension(),
                        packet
                );
    }

}
