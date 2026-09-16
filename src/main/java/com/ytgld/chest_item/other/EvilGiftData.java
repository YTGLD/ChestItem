package com.ytgld.chest_item.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public record EvilGiftData(HashSet<String> hashSet) {
    public static final Codec<EvilGiftData> CODEC =
            RecordCodecBuilder.create((instance) ->
                    instance.group(Codec.STRING.listOf()
                            .xmap(HashSet::new, ArrayList::new)
                                    .fieldOf("evil_gift").forGetter((setSoulData) -> setSoulData.hashSet))
                            .apply(instance, EvilGiftData::new));
    public EvilGiftData add(String string) {
        this.hashSet.add(string);
        return this;
    }
}