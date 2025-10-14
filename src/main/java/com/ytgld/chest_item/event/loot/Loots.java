package com.ytgld.chest_item.event.loot;

import com.mojang.serialization.MapCodec;
import com.ytgld.chest_item.Chestitem;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class Loots {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT = DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS,Chestitem.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>,?> LOOT_chest = LOOT.register("loot",(resourceLocation)->{
        return ChestLoot.CODEC.get();
    });
}
