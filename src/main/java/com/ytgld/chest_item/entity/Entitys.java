package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Chestitem.MODID)
public class Entitys {
    public static final DeferredRegister.Entities REGISTRY =
            DeferredRegister.createEntities(Chestitem.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<EndComing>> EndComing_ = REGISTRY.register("end_coming", () ->
            EntityType.Builder.of(EndComing::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "end_coming"))));
    public static final DeferredHolder<EntityType<?>, EntityType<AttackEndComing>> AttackEndComing_ = REGISTRY.register("attack_end_coming", () ->
            EntityType.Builder.of(AttackEndComing::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "attack_end_coming"))));
    public static final DeferredHolder<EntityType<?>, EntityType<UnstableSpheres>> UnstableSpheres_ = REGISTRY.register("unstable_spheres", () ->
            EntityType.Builder.of(UnstableSpheres::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "unstable_spheres"))));
    public static final DeferredHolder<EntityType<?>, EntityType<LaserColumn>> LaserColumn_ = REGISTRY.register("laser_column", () ->
            EntityType.Builder.of(LaserColumn::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(200).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "laser_column"))));

    public static final DeferredHolder<EntityType<?>, EntityType<TheHyperplasia>> TheHyperplasia_ = REGISTRY.register("hyperplasia", () ->
            EntityType.Builder.of(TheHyperplasia::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(Chestitem.MODID, "hyperplasia"))));

    @SubscribeEvent
    public static void EntityAttributeCreationEvent(EntityAttributeCreationEvent event){
        event.put(Entitys.EndComing_.get(), Bat.createAttributes().build());
        event.put(Entitys.LaserColumn_.get(), Zombie.createAttributes().build());
    }
}
