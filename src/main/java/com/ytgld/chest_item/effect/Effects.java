package com.ytgld.chest_item.effect;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Effects {
    public static final DeferredRegister<MobEffect> EFFECT_DEFERRED_REGISTER = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT,Chestitem.MODID);
    public static final DeferredHolder<MobEffect,MobEffect> IncreasingMeat_ = EFFECT_DEFERRED_REGISTER.register("increasing_meat",
            IncreasingMeat::new);
}
