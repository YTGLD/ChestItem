package com.ytgld.chest_item.utils;

import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.joml.Vector3f;

public class UtilEvent {
    @SubscribeEvent
    public void event(LevelTickEvent.Pre event){
        RenderObjectManager.event(event);
    }
    @SubscribeEvent
    public void event(LivingDamageEvent.Post event){
        if (event.getSource().getEntity() instanceof Player player) {
            AddUtil.addSword(player.level(),event.getEntity().getEyePosition(),new Vector3f(0,0,0),new Vec3(
                    Mth.nextInt(RandomSource.create(),-360,360),
                    Mth.nextInt(RandomSource.create(),-360,360),
                    Mth.nextInt(RandomSource.create(),-360,360)
            ), Light.ARGB.color(255,180,240,255),2);
        }
    }

}
