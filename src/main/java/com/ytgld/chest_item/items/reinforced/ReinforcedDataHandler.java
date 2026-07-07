package com.ytgld.chest_item.items.reinforced;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.memory.TheMemoryDataHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class ReinforcedDataHandler {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Chestitem.MODID);
    public static final Supplier<AttachmentType<Set<String>>> reinforced = ATTACHMENT_TYPES.register(
            "reinforced",
            () -> AttachmentType.<Set<String>>builder(() -> new HashSet<>())
                    .sync(new TheMemoryDataHandler.StringSetSync())
                    .serialize(TheMemoryDataHandler.StringSetCodec.CODEC.fieldOf("reinforced").codec())
                    .build()
    );

}
