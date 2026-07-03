package com.ytgld.chest_item.items.reinforced;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Chestitem.MODID)
public class ReinforcedAttreg {
    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Chestitem.MODID);
    public static final DeferredHolder<Attribute,?> maxReinforced = REGISTRY.register("max_reinforced",()->{
        return new RangedAttribute("attribute.name.chest_item.max_reinforced", 0, 0, 10).setSyncable(true);
    });
    @SubscribeEvent
    public static void EntityAttributeCreationEvent(EntityAttributeModificationEvent event){
        event.add(EntityTypes.PLAYER , maxReinforced,3);
    }
}
