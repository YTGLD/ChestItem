package com.ytgld.chest_item.event.key;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public record OpenChest(ItemStack carried) implements CustomPacketPayload {

    public static final Type<OpenChest> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "open"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenChest> USE_CURIO_STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.OPTIONAL_STREAM_CODEC,
                    OpenChest::carried,
                    OpenChest::new);

    @Nonnull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
