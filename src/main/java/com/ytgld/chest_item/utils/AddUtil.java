package com.ytgld.chest_item.utils;

import com.ytgld.chest_item.utils.dout.SwordRenderObject;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class AddUtil {
    public static void addSword(Level level, Vec3 vec3, Vector3f speed, Vec3 pitch, int color,int size){
        SwordRenderObject swordRenderObject = new SwordRenderObject(vec3, pitch,size);
        swordRenderObject.vector3f = speed;
        swordRenderObject.color = color;
        RenderObjectManager.add(swordRenderObject);
    }
}
