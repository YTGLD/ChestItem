package com.ytgld.chest_item.sounds;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class CISoundDefinitionsProvider extends SoundDefinitionsProvider {
    public CISoundDefinitionsProvider(PackOutput output) {
        super(output, Chestitem.MODID);
    }

    @Override
    public void registerSounds() {
        this.add(Sounds.LASER, SoundDefinition.definition().with(sound("chest_item:laser_column", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.sound_1").replace(true));

        this.add(Sounds.Heart, SoundDefinition.definition().with(sound("chest_item:heart", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.heart").replace(true));

        this.add(Sounds.Sword, SoundDefinition.definition().with(sound("chest_item:sword1", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .with(sound("chest_item:sword2", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .with(sound("chest_item:sword3", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.sword").replace(true));

        this.add(Sounds.FlySword, SoundDefinition.definition().with(sound("chest_item:fly_sword", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.fly_sword").replace(true));

        this.add(Sounds.Kill, SoundDefinition.definition().with(sound("chest_item:kill", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.kill").replace(true));

        this.add(Sounds.bass, SoundDefinition.definition().with(sound("chest_item:bass", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.bass").replace(true));

        this.add(Sounds.tombstone, SoundDefinition.definition().with(sound("chest_item:tombstone", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.tombstone").replace(false));

        this.add(Sounds.shadow_shield_heal_max, SoundDefinition.definition().with(sound("chest_item:shadow_shield_heal_max", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.shadow_shield_heal_max").replace(true));

        this.add(Sounds.shadow_shield_heal, SoundDefinition.definition().with(sound("chest_item:shadow_shield_heal_1", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .with(sound("chest_item:shadow_shield_heal_2", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.shadow_shield_heal").replace(true));


        this.add(Sounds.evil_shield_full, SoundDefinition.definition().with(sound("chest_item:evil_shield_full", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.evil_shield_full").replace(true));

        this.add(Sounds.evil_shield_heal, SoundDefinition.definition().with(sound("chest_item:evil_shield_heal", SoundDefinition.SoundType.SOUND).stream(true).preload(false))
                .subtitle("sound.chest_item.evil_shield_heal").replace(true));
    }
}
