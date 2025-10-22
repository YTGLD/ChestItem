package com.ytgld.chest_item.items;

import com.mojang.serialization.Codec;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.other.SyncHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Chestitem.MODID)
public class AttReg {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Chestitem.MODID);






    public static final DeferredRegister<Attribute> REGISTRY = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, Chestitem.MODID);
    public static final DeferredHolder<Attribute,?> heal = REGISTRY.register("heal",()->{
        return new RangedAttribute("attribute.name.chest_item.heal", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> instability = REGISTRY.register("instability",()->{
        return new RangedAttribute("attribute.name.chest_item.instability", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> instability_low = REGISTRY.register("instability_low",()->{
        return new RangedAttribute("attribute.name.chest_item.instability_low", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> more_speed = REGISTRY.register("more_speed",()->{
        return new RangedAttribute("attribute.name.chest_item.more_speed", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> looting = REGISTRY.register("looting",()->{
        return new RangedAttribute("attribute.name.chest_item.looting", 0, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> fortune = REGISTRY.register("fortune",()->{
        return new RangedAttribute("attribute.name.chest_item.fortune", 0, -1024, 1024).setSyncable(true);
    });


    /**
     * 疮疤的增生——对不可能说“不”
     */
    public static final Supplier<AttachmentType<Float>> hyperplasiaATTACHMENT_TYPES = ATTACHMENT_TYPES.register(
            "hyperplasia", () -> AttachmentType.builder(() -> 0f).sync(new SyncHandler()).serialize(Codec.FLOAT.fieldOf("hyperplasia")).build()
    );
    public static final DeferredHolder<Attribute,?> hyperplasia = REGISTRY.register("hyperplasia",()->{
        return new RangedAttribute("attribute.name.chest_item.hyperplasia", 1, -1024, 1024).setSyncable(true);
    });

    public static final DeferredHolder<Attribute,?> hyperplasia_speed = REGISTRY.register("hyperplasia_speed",()->{
        return new RangedAttribute("attribute.name.chest_item.hyperplasia_speed", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> hyperplasia_stronger = REGISTRY.register("hyperplasia_stronger",()->{
        return new RangedAttribute("attribute.name.chest_item.hyperplasia_stronger", 1, -1024, 1024).setSyncable(true);
    });

    /**
     * 失败的理论——探索未知
     */
    public static final Supplier<AttachmentType<Float>> shadow_shield_ATTACHMENT_TYPES = ATTACHMENT_TYPES.register(
            "shadow_shield", () -> AttachmentType.builder(() -> 0f).sync(new SyncHandler())
                    .serialize(Codec.FLOAT.fieldOf("shadow_shield")).build()
    );
    public static final DeferredHolder<Attribute,?> shadow_shield = REGISTRY.register("shadow_shield",()->{
        return new RangedAttribute("attribute.name.chest_item.shadow_shield", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> shadow_shield_speed = REGISTRY.register("shadow_shield_speed",()->{
        return new RangedAttribute("attribute.name.chest_item.shadow_shield_speed", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> shadow_shield_stronger = REGISTRY.register("shadow_shield_stronger",()->{
        return new RangedAttribute("attribute.name.chest_item.shadow_shield_stronger", 1, -1024, 1024).setSyncable(true);
    });
    @SubscribeEvent
    public static void EntityAttributeCreationEvent(EntityAttributeModificationEvent event){
        event.add(EntityType.PLAYER , AttReg.heal,1);
        event.add(EntityType.PLAYER , AttReg.instability,1);
        event.add(EntityType.PLAYER , AttReg.instability_low,1);
        event.add(EntityType.PLAYER , AttReg.hyperplasia,1);
        event.add(EntityType.PLAYER , AttReg.hyperplasia_speed,1);
        event.add(EntityType.PLAYER , AttReg.hyperplasia_stronger,1);
        event.add(EntityType.PLAYER , AttReg.shadow_shield,1);
        event.add(EntityType.PLAYER , AttReg.shadow_shield_speed,1);
        event.add(EntityType.PLAYER , AttReg.shadow_shield_stronger,1);
        event.add(EntityType.PLAYER , AttReg.more_speed,1);
        event.add(EntityType.PLAYER , AttReg.looting,0);
        event.add(EntityType.PLAYER , AttReg.fortune,0);

    }
}
