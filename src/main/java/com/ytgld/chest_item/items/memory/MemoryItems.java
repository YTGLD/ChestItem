package com.ytgld.chest_item.items.memory;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.memory.items.Bluster;
import com.ytgld.chest_item.items.memory.items.Contradiction;
import com.ytgld.chest_item.items.memory.items.Extreme;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class MemoryItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chestitem.MODID);
    public static final DeferredItem<Item> Bluster_ = register("bluster",
            (resourceLocation)-> new Bluster(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BlusterTooltip_ = register("bluster_tooltip",
            (resourceLocation)-> new Bluster.BlusterTooltip(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> Contradiction_ = register("contradiction",
            (resourceLocation)-> new Contradiction(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ContradictionTooltip_ = register("contradiction_tooltip",
            (resourceLocation)-> new Contradiction.ContradictionTooltip(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> Extreme_ = register("extreme",
            (resourceLocation)-> new Extreme(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ExtremeTooltip_ = register("extreme_tooltip",
            (resourceLocation)-> new Extreme.ExtremeTooltip(new Item.Properties().stacksTo(1)));



    public static DeferredItem<Item> register(String name, Function<ResourceLocation, ? extends Item> func) {
        return ITEMS.register(name,func);
    }
}
