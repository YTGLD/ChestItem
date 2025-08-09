package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Entitys {
    public static final DeferredRegister.Entities REGISTRY =
            DeferredRegister.createEntities(Chestitem.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<AbyssOrb>> Abyss_Orb = REGISTRY.register("abyss_orb", () ->
            EntityType.Builder.of(AbyssOrb::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "abyss_orb"))));
}
