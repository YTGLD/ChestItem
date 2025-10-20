package com.ytgld.chest_item.other;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ChestMenuTypes {
    public static final DeferredRegister<MenuType<?>> register = DeferredRegister.create(BuiltInRegistries.MENU, Chestitem.MODID);
    public static final DeferredHolder<MenuType<?>, MenuType<ChestItemMenu>> GENERIC_12 = register.register("chest_menu",
            ()-> new MenuType<>((i,inventory)->{
                return new ChestItemMenu(i,inventory,new SimpleContainer(12 ),1);
            },FeatureFlags.DEFAULT_FLAGS));

}
