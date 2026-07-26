package com.ytgld.chest_item.items.reinforced;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.reinforced.items.*;
import com.ytgld.chest_item.items.reinforced.meat.ComplexComponents;
import com.ytgld.chest_item.items.reinforced.meat.RegenerationPlugin;
import com.ytgld.chest_item.items.reinforced.meat.StabilizingDevice;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class ReinforcedItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chestitem.MODID);
    public static final DeferredItem<Item> SilentDevice_ = register("silent_device",
            (Identifier)-> new SilentDevice(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> RegenerationPlugin_ = register("regeneration_plugin",
            (Identifier)-> new RegenerationPlugin(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> StabilizingDevice_ = register("stabilizing_device",
            (Identifier)-> new StabilizingDevice(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> ComplexComponents_ = register("complex_components",
            (Identifier)-> new ComplexComponents(new Item.Properties().stacksTo(64)));






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
    public static final DeferredItem<Item> Distillation_ = register("distillation",
            (Identifier)-> new Distillation(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> Diffusion_ = register("diffusion",
            (Identifier)-> new Diffusion(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> MysteryLiner_ = register("mystery_liner",
            (Identifier)-> new MysteryLiner(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> Fusion_ = register("fusion",
            (Identifier)-> new Fusion(new Item.Properties().stacksTo(64)));

    public static DeferredItem<Item> register(String name, Function<ResourceLocation, ? extends Item> func) {
        return ITEMS.register(name,func);
    }
}
