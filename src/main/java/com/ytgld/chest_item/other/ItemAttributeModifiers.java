package com.ytgld.chest_item.other;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntFunction;

public record ItemAttributeModifiers(List<Entry> modifiers) {
    public static final ItemAttributeModifiers EMPTY = new ItemAttributeModifiers(List.of());
    public static final Codec<ItemAttributeModifiers> CODEC = ItemAttributeModifiers.Entry.CODEC
            .listOf()
            .xmap(ItemAttributeModifiers::new, ItemAttributeModifiers::modifiers);

    public static class Builder {
        private final ImmutableList.Builder<ItemAttributeModifiers.Entry> entries = ImmutableList.builder();

        public Builder() {
        }

        public ItemAttributeModifiers.Builder add(Holder<Attribute> attribute, AttributeModifier modifier) {
            this.entries.add(new ItemAttributeModifiers.Entry(attribute, modifier));
            return this;
        }

        public ItemAttributeModifiers build() {
            return new ItemAttributeModifiers(this.entries.build());
        }
    }
    public void forEach(BiConsumer<Holder<Attribute>, AttributeModifier> action) {
        for (Entry itemattributemodifiers$entry : this.modifiers) {
            action.accept(itemattributemodifiers$entry.attribute, itemattributemodifiers$entry.modifier);
        }

    }
    public record Entry(Holder<Attribute> attribute, AttributeModifier modifier) {
        public static final Codec<ItemAttributeModifiers.Entry> CODEC = RecordCodecBuilder.create(
                p_419448_ -> p_419448_.group(
                                Attribute.CODEC.fieldOf("type").forGetter(ItemAttributeModifiers.Entry::attribute),
                                AttributeModifier.MAP_CODEC.forGetter(ItemAttributeModifiers.Entry::modifier)
                        )
                        .apply(p_419448_, ItemAttributeModifiers.Entry::new)
        );


    }

}
