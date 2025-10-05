package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Chestitem.MODID)
public class AttReg {
    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Chestitem.MODID);
    public static final DeferredHolder<Attribute,?> heal = REGISTRY.register("heal",()->{
        return new RangedAttribute("attribute.name.chest_item.heal", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> instability = REGISTRY.register("instability",()->{
        return new RangedAttribute("attribute.name.chest_item.instability", 0, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> instability_low = REGISTRY.register("instability_low",()->{
        return new RangedAttribute("attribute.name.chest_item.instability_low", 0, -1024, 1024).setSyncable(true);
    });
    @SubscribeEvent
    public static void EntityAttributeCreationEvent(EntityAttributeModificationEvent event){
        event.add(EntityType.PLAYER , AttReg.heal,1);
        event.add(EntityType.PLAYER , AttReg.instability,1);
        event.add(EntityType.PLAYER , AttReg.instability_low,1);

    }
}
