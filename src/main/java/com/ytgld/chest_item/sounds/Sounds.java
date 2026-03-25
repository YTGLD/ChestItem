package com.ytgld.chest_item.sounds;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Sounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT,Chestitem.MODID);
    public static final Holder<SoundEvent> LASER = REGISTRY.register(
            "laser_column",
            SoundEvent::createVariableRangeEvent
    );
    public static final Holder<SoundEvent> Heart = REGISTRY.register(
            "heart",
            SoundEvent::createVariableRangeEvent
    );
}
