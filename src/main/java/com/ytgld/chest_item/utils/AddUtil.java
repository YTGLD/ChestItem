package com.ytgld.chest_item.utils;

import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.utils.dout.SwordRenderObject;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class AddUtil {
    private static void addSword(Level level, Vec3 vec3, Vector3f speed, Vec3 pitch, int color,int size){
        SwordRenderObject swordRenderObject = new SwordRenderObject(vec3, pitch,size);
        swordRenderObject.vector3f = speed;
        swordRenderObject.color = color;
        RenderObjectManager.add(swordRenderObject);
    }
    public static void addSword(Player player, LivingEntity living){
        int san = (int) (player.getAttributeBaseValue (AttReg.theSanity) - player.getAttributeValue(AttReg.theSanity));
        int color = Light.ARGB.color(255,150,220,250);
        if (san >= 10) {
            living.addEffect(new MobEffectInstance(Effects.EvilErosion,200,1));
            color = Light.ARGB.color(255,0,255,155);
        }
        AddUtil.addSword(player.level(),living.getEyePosition(),new Vector3f(0,0,0),new Vec3(
                Mth.nextInt(RandomSource.create(),-360,360),
                Mth.nextInt(RandomSource.create(),-360,360),
                Mth.nextInt(RandomSource.create(),-360,360)
        ), color,2);
    }

}
