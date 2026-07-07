package com.ytgld.chest_item.items.memory;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.memory.items.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
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

    public static final DeferredItem<Item> ForeverCurtain_ = register("forever_curtain",
            (resourceLocation)-> new ForeverCurtain(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ForeverCurtainTooltip_ = register("forever_curtain_tooltip",
            (resourceLocation)-> new ForeverCurtain.ForeverCurtainTooltip(new Item.Properties().stacksTo(1)));



    public static final DeferredItem<Item> Martyrdom_ = register("martyrdom",
            (resourceLocation)-> new Martyrdom(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MartyrdomTooltip_ = register("martyrdom_tooltip",
            (resourceLocation)-> new Martyrdom.MartyrdomTooltip(new Item.Properties().stacksTo(1)));


    public static final DeferredItem<Item> Peace_ = register("peace",
            (resourceLocation)-> new Peace(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> PeaceTooltip_ = register("peace_tooltip",
            (resourceLocation)-> new Peace.PeaceTooltip(new Item.Properties().stacksTo(1)));



    public static final DeferredItem<Item> War_ = register("war",
            (resourceLocation)-> new War(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> WarTooltip_ = register("war_tooltip",
            (resourceLocation)-> new War.WarTooltip(new Item.Properties().stacksTo(1)));



    public static final DeferredItem<Item> TheFox_ = register("the_fox",
            (resourceLocation)-> new TheFox(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> TheFoxTooltip_ = register("the_fox_tooltip",
            (resourceLocation)-> new TheFox.TheFoxTooltip(new Item.Properties().stacksTo(1)));



    public static final DeferredItem<Item> Protest_ = register("protest",
            (resourceLocation)-> new Protest(new Item.Properties().stacksTo(1)));;
    public static final DeferredItem<Item> ProtestTooltip_ = register("protest_tooltip",
            (resourceLocation)-> new Protest.ProtestTooltip(new Item.Properties().stacksTo(1)));;

    public static final DeferredItem<Item> Crave_ = register("crave",
            (resourceLocation)-> new Crave(new Item.Properties().stacksTo(1)));;
    public static final DeferredItem<Item>CraveTooltip_ = register("crave_tooltip",
            (resourceLocation)-> new Crave.CraveTooltip(new Item.Properties().stacksTo(1)));;

    public static DeferredItem<Item> register(String name, Function<ResourceLocation, ? extends Item> func) {
        return ITEMS.register(name,func);
    }
}
