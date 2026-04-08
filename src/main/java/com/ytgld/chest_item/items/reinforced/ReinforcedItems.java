package com.ytgld.chest_item.items.reinforced;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.reinforced.items.Strengthen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ReinforcedItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chestitem.MODID);
    public static final DeferredItem<Item> Strengthen_ = register("strengthen",
            (resourceLocation)-> new Strengthen(new Item.Properties().stacksTo(1)));

    public static DeferredItem<Item> register(String name, Function<ResourceLocation, ? extends Item> func) {
        return ITEMS.register(name,func);
    }
}
