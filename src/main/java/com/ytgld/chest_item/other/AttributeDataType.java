package com.ytgld.chest_item.other;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.ArrayList;
import java.util.List;

public record AttributeDataType(List<Entry> modifiers)  {
    public static final Codec<AttributeDataType> CODEC = Entry.CODEC
            .listOf()
            .xmap(AttributeDataType::new, AttributeDataType::modifiers);


    public Builder builder(){
        return new Builder();
    }


    public static class Builder {
        private final List<Entry> entries = new ArrayList<>();
        Builder() {
        }
        public Builder add(Holder<Attribute> attribute, AttributeModifier modifier) {
            this.entries.add(new Entry(attribute, modifier));
            return this;
        }
        public AttributeDataType build() {
            return new AttributeDataType(this.entries);
        }
    }





    public record Entry(Holder<Attribute> attribute,
                        AttributeModifier modifier) {

        public static final Codec<Entry> CODEC =
                RecordCodecBuilder.create((entryInstance) ->
                        entryInstance.
                                group(Attribute.CODEC.fieldOf("attribute").forGetter(Entry::attribute),
                                        AttributeModifier.CODEC.fieldOf("modifier").forGetter(Entry::modifier)).apply(entryInstance,Entry::new));

    }
}
