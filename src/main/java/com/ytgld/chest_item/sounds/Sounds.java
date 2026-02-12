package com.ytgld.chest_item.sounds;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.entity.EndComing;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class Sounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT,Chestitem.MODID);
    public static final Holder<SoundEvent> LASER = REGISTRY.register(
            "laser_column",
            SoundEvent::createVariableRangeEvent
    );

}
