package com.ytgld.chest_item.event.key;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public record UseCurio(ItemStack carried) implements CustomPacketPayload {

    public static final Type<UseCurio> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "use_chest"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UseCurio> USE_CURIO_STREAM_CODEC =
            StreamCodec.composite(
                    ItemStack.OPTIONAL_STREAM_CODEC,
                    UseCurio::carried,
                    UseCurio::new);

    @Nonnull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
