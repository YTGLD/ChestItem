package com.ytgld.chest_item.items.reinforced;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.reinforced.items.*;
import com.ytgld.chest_item.items.reinforced.meat.ComplexComponents;
import com.ytgld.chest_item.items.reinforced.meat.RegenerationPlugin;
import com.ytgld.chest_item.items.reinforced.meat.StabilizingDevice;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class ReinforcedItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chestitem.MODID);


    public static final DeferredItem<Item> RegenerationPlugin_ = register("regeneration_plugin",
            (Identifier)-> new RegenerationPlugin(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<Item> StabilizingDevice_ = register("stabilizing_device",
            (Identifier)-> new StabilizingDevice(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<Item> ComplexComponents_ = register("complex_components",
            (Identifier)-> new ComplexComponents(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));





    public static final DeferredItem<Item> SilentDevice_ = register("silent_device",
            (Identifier)-> new SilentDevice(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<Item> Strengthen_ = register("strengthen",
            (Identifier)-> new Strengthen(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<Item> Accelerated_ = register("accelerated",
            (Identifier)-> new Accelerated(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<Item> Excite_ = register("excite",
            (Identifier)-> new Excite(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<Item> Activity_ = register("activity",
            (Identifier)-> new Activity(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<Item> Dynamic_ = register("dynamic",
            (Identifier)-> new Dynamic(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<Item> Contingency_ = register("contingency",
            (Identifier)-> new Contingency(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static DeferredItem<@NotNull Item> register(String name, Function<Identifier, ? extends Item> func) {
        return ITEMS.register(name,func);
    }
}
