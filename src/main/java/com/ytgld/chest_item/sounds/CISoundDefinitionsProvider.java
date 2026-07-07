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
                .subtitle("sound.chest_item.heart")
                .replace(true)
        );
        add(Sounds.Sword.value(), SoundDefinition.definition()
                .with(sound("chest_item:sword1",SoundDefinition.SoundType.SOUND)
                        .stream(true)
                        .preload(false)
                )
                .with(sound("chest_item:sword2",SoundDefinition.SoundType.SOUND)
                        .stream(true)
                        .preload(false)
                )
                .with(sound("chest_item:sword3",SoundDefinition.SoundType.SOUND)
                        .stream(true)
                        .preload(false)
                )
                .subtitle("sound.chest_item.sword")
                .replace(true)
        );

        add(Sounds.FlySword.value(), SoundDefinition.definition()
                .with(sound("chest_item:fly_sword",SoundDefinition.SoundType.SOUND)
                        .stream(true)
                        .preload(false)
                )
                .subtitle("sound.chest_item.fly_sword")
                .replace(true)
        );
        add(Sounds.Kill.value(), SoundDefinition.definition()
                .with(sound("chest_item:kill",SoundDefinition.SoundType.SOUND)
                        .stream(true)
                        .preload(false)
                )
                .subtitle("sound.chest_item.kill")
                .replace(true)
        );
    }
}
