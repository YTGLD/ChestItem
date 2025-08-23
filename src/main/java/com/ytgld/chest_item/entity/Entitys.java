package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Chestitem.MODID)
public class Entitys {
    public static final DeferredRegister.Entities REGISTRY =
            DeferredRegister.createEntities(Chestitem.MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<AbyssOrb>> Abyss_Orb = REGISTRY.register("abyss_orb", () ->
            EntityType.Builder.of(AbyssOrb::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "abyss_orb"))));
    public static final DeferredHolder<EntityType<?>, EntityType<EndSpiral>> End_Spiral = REGISTRY.register("end_spiral", () ->
            EntityType.Builder.of(EndSpiral::new, MobCategory.MISC).sized(3, 0.1f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "end_spiral"))));
    public static final DeferredHolder<EntityType<?>, EntityType<BlackVex>> Black_Vex = REGISTRY.register("black_vex", () ->
            EntityType.Builder.of(BlackVex::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "black_vex"))));

    public static final DeferredHolder<EntityType<?>, EntityType<EndComing>> EndComing_ = REGISTRY.register("end_coming", () ->
            EntityType.Builder.of(EndComing::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "end_coming"))));
    public static final DeferredHolder<EntityType<?>, EntityType<AttackEndComing>> AttackEndComing_ = REGISTRY.register("attack_end_coming", () ->
            EntityType.Builder.of(AttackEndComing::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50).build(ResourceKey.create(Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "attack_end_coming"))));




    @SubscribeEvent
    public static void EntityAttributeCreationEvent(EntityAttributeCreationEvent event){
        event.put(Entitys.Black_Vex.get(),BlackVex.createAttributes().build());
        event.put(Entitys.EndComing_.get(),BlackVex.createAttributes().build());
    }
}
