package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.blood.BoneHead;
import com.ytgld.chest_item.items.blood.LifeCrystal;
import com.ytgld.chest_item.items.other.*;
import com.ytgld.chest_item.items.blood.GodBlood;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class InitItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chestitem.MODID);

    public static final DeferredItem<Item> God_blood = register("god_blood",
            (resourceLocation)-> new GodBlood(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Drug_Heal = register("drug_heal",
            (resourceLocation)-> new DrugHeal(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Life_Crystal = register("life_crystal",
            (resourceLocation)-> new LifeCrystal(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Bone_Head = register("bone_head",
            (resourceLocation)-> new BoneHead(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Conch_ = register("conch",
            (resourceLocation)-> new Conch(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Lead_ = register("lead",
            (resourceLocation)-> new Lead(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Stone_ = register("stone",
            (resourceLocation)-> new Stone(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Knife_ = register("knife",
            (resourceLocation)-> new Knife(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));

    public static DeferredItem<Item>  register(String name, Function<ResourceLocation, ? extends Item> func) {
        return ITEMS.register(name,func);
    }

    public static class TabChestItem{
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Chestitem.MODID);
        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> tab = CREATIVE_MODE_TABS.register(Chestitem.MODID, () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.chest_item"))
                .icon(Items.CHEST::getDefaultInstance)
                .displayItems((parameters, output) -> {
                    output.accept(InitItems.God_blood);
                    output.accept(InitItems.Drug_Heal);
                    output.accept(InitItems.Life_Crystal);
                    output.accept(InitItems.Bone_Head);
                    output.accept(InitItems.Conch_);
                    output.accept(InitItems.Lead_);
                    output.accept(InitItems.Stone_);
                    output.accept(InitItems.Knife_);
                }).build());

    }
}
