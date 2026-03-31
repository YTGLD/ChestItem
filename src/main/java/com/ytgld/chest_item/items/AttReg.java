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
    public static final Supplier<AttachmentType<Float>> hyperplasiaATTACHMENT_TYPES = ATTACHMENT_TYPES.register(
            "hyperplasia", () -> AttachmentType.builder(() -> 0f).sync(new SyncHandler())
                    .serialize(Codec.FLOAT.fieldOf("hyperplasia").codec()).build()
    );

    public static final Supplier<AttachmentType<Float>> black_shadowAttachmentType = ATTACHMENT_TYPES.register(
            "black_shadow", () -> AttachmentType.builder(() -> 0f).sync(new SyncHandler())
                    .serialize(Codec.FLOAT.fieldOf("black_shadow").codec()).build()
    );




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
    public static final DeferredHolder<Attribute,?> xp_drop = REGISTRY.register("xp_drop",()->{
        return new RangedAttribute("attribute.name.chest_item.xp_drop", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> resistance = REGISTRY.register("resistance",()->{
        return new RangedAttribute("attribute.name.chest_item.resistance", 1, -1024, 1024).setSyncable(true);
    });







    /**
     * 疮疤的增生——对不可能说“不”
     */
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
            "shadow_shield", () -> AttachmentType.builder(() -> 0f).sync(new SyncHandler()).serialize(Codec.FLOAT.
                    fieldOf("shadow_shield").codec()).build()

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



    public static final DeferredHolder<Attribute,?> chaos_armor = REGISTRY.register("chaos_armor",()->{
        return new RangedAttribute("attribute.name.chest_item.chaos_armor", 1, -1024, 1024).setSyncable(true);
    });
    /**
     * 混沌理论：
     * <p>
     *侵蚀装甲崩碎时造成的伤害，默认为100%
     */
    public static final DeferredHolder<Attribute,?> chaos_armor_damage = REGISTRY.register("chaos_armor_damage",()->{
        return new RangedAttribute("attribute.name.chest_item.chaos_armor_damage", 1, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?> chaos_armor_speed = REGISTRY.register("chaos_armor_speed",()->{
        return new RangedAttribute("attribute.name.chest_item.chaos_armor_speed", 1, -1024, 1024).setSyncable(true);
    });

    public static final DeferredHolder<Attribute,?> chaos_armor_min = REGISTRY.register("chaos_armor_min",()->{
        return new RangedAttribute("attribute.name.chest_item.chaos_armor_min", 1, -1024, 1024).setSyncable(true);
    });

    public static final Supplier<AttachmentType<Float>> chaosWinds = ATTACHMENT_TYPES.register(
            "chaos_wind", () -> AttachmentType.builder(() -> 0f).sync(new SyncHandler()).serialize(Codec.FLOAT.
                    fieldOf("chaos_wind").codec()).build()

    );
    /**
     * 疮疤的最终奥义——利用伤痛
     */
    public static final DeferredHolder<Attribute,?> painShield_number = REGISTRY.register("pain_shield_number",()->{
        return new RangedAttribute("attribute.name.chest_item.pain_shield_number", 0, -1024, 1024).setSyncable(true);
    });
    public static final DeferredHolder<Attribute,?>  painShield_speed = REGISTRY.register("pain_shield_speed",()->{
        return new RangedAttribute("attribute.name.chest_item.pain_shield_speed", 1, -1024, 1024).setSyncable(true);
    });

    public static final DeferredHolder<Attribute,?>  painShield_res = REGISTRY.register("pain_shield_res",()->{
        return new RangedAttribute("attribute.name.chest_item.pain_shield_res", 1, -1024, 1024).setSyncable(true);
    });
    public static final Supplier<AttachmentType<Float>> painShield = ATTACHMENT_TYPES.register(
            "pain_shield", () -> AttachmentType.builder(() -> 0f).sync(new SyncHandler()).serialize(Codec.FLOAT.
                    fieldOf("pain_shield").codec()).build()

    );
    /**
     * 邪母的嬗变
     */
    public static final DeferredHolder<Attribute,?> theSanity = REGISTRY.register("the_sanity",()->{
        return new RangedAttribute("attribute.name.chest_item.the_sanity", 10, -100, 100).setSyncable(true);
    });
    public static final Supplier<AttachmentType<Float>> slashing = ATTACHMENT_TYPES.register(
            "slashing", () -> AttachmentType.builder(() -> 0f).sync(new SyncHandler()).serialize(Codec.FLOAT.
                    fieldOf("slashing").codec()).build()

    );




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

        event.add(EntityType.PLAYER , AttReg.xp_drop,1);
        event.add(EntityType.PLAYER , AttReg.resistance,1);

        event.add(EntityType.PLAYER , AttReg.chaos_armor,1);
        event.add(EntityType.PLAYER , AttReg.chaos_armor_damage,1);
        event.add(EntityType.PLAYER , AttReg.chaos_armor_speed,1);
        event.add(EntityType.PLAYER , AttReg.chaos_armor_min,1);

        event.add(EntityType.PLAYER , AttReg.painShield_number,0);
        event.add(EntityType.PLAYER , AttReg.painShield_res,1);
        event.add(EntityType.PLAYER , AttReg.painShield_speed,1);

        event.add(EntityType.PLAYER , AttReg.theSanity,10);

    }
}
