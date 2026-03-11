package com.ytgld.chest_item.sounds;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class CISoundDefinitionsProvider extends SoundDefinitionsProvider {
    public CISoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Chestitem.MODID,helper);
    }

    @Override
    public void registerSounds() {
        add(Sounds.Heart.value(), SoundDefinition.definition()
                .with(sound("chest_item:heart",SoundDefinition.SoundType.SOUND)
                                .stream(true)
                                .preload(false)
                )
                .subtitle("sound.chest_item.sound_1")
                .replace(true)
        );
    }
}
