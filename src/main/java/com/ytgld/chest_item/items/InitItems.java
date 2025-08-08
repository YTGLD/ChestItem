package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.blood.GodBlood;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InitItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chestitem.MODID);

    public static final DeferredItem<Item> God_blood = register("god_blood",
            (resourceLocation)-> new GodBlood(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));

    public static DeferredItem<Item>  register(String name, Function<ResourceLocation, ? extends Item> func) {
        DeferredItem<Item> item = ITEMS.register(name,func);
        return item;
    }

    public static class TabChestItem{
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Chestitem.MODID);
        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> tab = CREATIVE_MODE_TABS.register(Chestitem.MODID, () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.chest_item"))
                .icon(() -> God_blood.get().getDefaultInstance())
                .displayItems((parameters, output) -> {
                    output.accept(InitItems.God_blood);
                }).build());

    }
}
