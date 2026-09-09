package com.ytgld.chest_item.items.evil_mother.evil_gift;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.Dawn;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.RottenUtensils;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.SnapString;
import com.ytgld.chest_item.items.evil_mother.evil_gift.gifts.Synthesizer;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.*;

public final class EvilGifts {
    public static final ResourceKey<Registry<EvilGiftBase>> GiftRegisterBase = key("gift");
    public static final Registry<EvilGiftBase> GiftRegister = new RegistryBuilder<>(GiftRegisterBase).create();
    public static final DeferredRegister<EvilGiftBase> REGISTER = DeferredRegister.create(GiftRegister, Chestitem.MODID);


    public static DeferredHolder<EvilGiftBase, ?> rotten_utensils = REGISTER.register("rotten_utensils", RottenUtensils::new);
    public static DeferredHolder<EvilGiftBase, ?> dawn = REGISTER.register("dawn", Dawn::new);
    public static DeferredHolder<EvilGiftBase, ?> snap_string = REGISTER.register("snap_string", SnapString::new);
    public static DeferredHolder<EvilGiftBase, ?> synthesizer = REGISTER.register("synthesizer", Synthesizer::new);

    public static void event(NewRegistryEvent event){
        event.register(GiftRegister);
    }

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(Chestitem.MODID, name));
    }
}
