package com.ytgld.chest_item.items.reinforced;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.reinforced.items.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ReinforcedItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chestitem.MODID);
    public static final DeferredItem<Item> SilentDevice_ = register("silent_device",
            (Identifier)-> new SilentDevice(new Item.Properties().stacksTo(1)));


    public static final DeferredItem<Item> Strengthen_ = register("strengthen",
            (resourceLocation)-> new Strengthen(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> Accelerated_ = register("accelerated",
            (Identifier)-> new Accelerated(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> Excite_ = register("excite",
            (Identifier)-> new Excite(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> Activity_ = register("activity",
            (Identifier)-> new Activity(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> Dynamic_ = register("dynamic",
            (Identifier)-> new Dynamic(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> Contingency_ = register("contingency",
            (Identifier)-> new Contingency(new Item.Properties().stacksTo(64)));

    public static DeferredItem<Item> register(String name, Function<ResourceLocation, ? extends Item> func) {
        return ITEMS.register(name,func);
    }
}
