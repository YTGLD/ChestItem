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
        add(Sounds.LASER, SoundDefinition.definition()
                .with(sound("chest_item:laser_column",SoundDefinition.SoundType.SOUND)
                                .stream(true)
                                .preload(false)
                )
                // 设定副标题。
                .subtitle("sound.chest_item.sound_1")
                // 可以替换。
                .replace(true)
        );
    }
}
