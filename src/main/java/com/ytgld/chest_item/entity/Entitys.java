package com.ytgld.chest_item.entity;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ambient.Bat;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Chestitem.MODID)
public class Entitys {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Chestitem .MODID);
    public static final DeferredHolder<EntityType<?>, EntityType<EndComing>> EndComing_ = REGISTRY.register("end_coming",
            ()-> EntityType.Builder.of(EndComing::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(10).build("end_coming"));


    public static final DeferredHolder<EntityType<?>, EntityType<AttackEndComing>> AttackEndComing_ = REGISTRY.register("attack_end_coming", () ->
            EntityType.Builder.of(AttackEndComing::new, MobCategory.MISC).sized(0.1f, 0.1f).clientTrackingRange(50).build("attack_end_coming"));




    @SubscribeEvent
    public static void EntityAttributeCreationEvent(EntityAttributeCreationEvent event){
        event.put(Entitys.EndComing_.get(), Bat.createAttributes().build());
    }
}
