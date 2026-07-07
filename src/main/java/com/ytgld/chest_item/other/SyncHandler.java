package com.ytgld.chest_item.other;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.Nullable;

public class SyncHandler  implements AttachmentSyncHandler<Float> {

    @Override
    public void write(RegistryFriendlyByteBuf buf, Float attachment, boolean initialSync) {
        buf.writeFloat(attachment);
    }

    @Override
    @Nullable
    public Float read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable Float previousValue) {
        return buf.readFloat();
    }

    @Override
    public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
        return holder == to;
    }
}

