package com.ytgld.chest_item.items.memory;

import com.mojang.serialization.Codec;
import com.ytgld.chest_item.Chestitem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class TheMemoryDataHandler {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Chestitem.MODID);
    public static final Supplier<AttachmentType<Set<String>>> mStringSetData = ATTACHMENT_TYPES.register(
            "string_set_data",
            () -> AttachmentType.<Set<String>>builder(() -> new HashSet<>()) // 这里加上 <Set<String>>
                    .sync(new StringSetSync())
                    .serialize(StringSetCodec.CODEC.fieldOf("string_set_data").codec())
                    .build()
    );
    public static final Supplier<AttachmentType<Set<String>>> notActivated = ATTACHMENT_TYPES.register(
            "not_activated",
            () -> AttachmentType.<Set<String>>builder(() -> new HashSet<>())
                    .sync(new StringSetSync())
                    .serialize(StringSetCodec.CODEC.fieldOf("not_activated").codec())
                    .build()
    );
    public static final Supplier<AttachmentType<IntAndStringSyncHandler.ISClass>> counter = ATTACHMENT_TYPES.register(
            "counter", () -> AttachmentType.builder(()->new IntAndStringSyncHandler.ISClass(new HashMap<>()))
                    .sync(new IntAndStringSyncHandler()).serialize(IntAndStringSyncHandler.CODEC.
                            fieldOf("counter").codec()).build()
    );
    public static class StringSetSync implements AttachmentSyncHandler<Set<String>> {

        @Override
        public void write(RegistryFriendlyByteBuf buf, Set<String> attachment, boolean initialSync) {
            buf.writeVarInt(attachment.size());
            for (String s : attachment) {
                buf.writeUtf(s);
            }
        }

        @Override
        @Nullable
        public Set<String> read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable Set<String> previousValue) {
            int size = buf.readVarInt();
            Set<String> set = previousValue != null ? previousValue : new HashSet<>();
            set.clear();
            for (int i = 0; i < size; i++) {
                set.add(buf.readUtf(32767)); // 32767 是字符串最大长度限制
            }
            return set;
        }

        @Override
        public boolean sendToPlayer(IAttachmentHolder holder, ServerPlayer to) {
            return holder == to;
        }
    }
    public static class StringSetCodec {
        public static final Codec<Set<String>> CODEC = Codec.STRING.listOf()
                .xmap(HashSet::new, ArrayList::new);
    }
}
