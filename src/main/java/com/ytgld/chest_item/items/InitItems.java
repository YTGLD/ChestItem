package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.blood.BoneHead;
import com.ytgld.chest_item.items.blood.LifeCrystal;
import com.ytgld.chest_item.items.gold.*;
import com.ytgld.chest_item.items.meet.Heart;
import com.ytgld.chest_item.items.meet.MeatBall;
import com.ytgld.chest_item.items.meet.SelfIncreasingHeart;
import com.ytgld.chest_item.items.meet.Stomach;
import com.ytgld.chest_item.items.other.*;
import com.ytgld.chest_item.items.blood.GodBlood;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.concurrent.CompletableFuture;
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
    public static final DeferredItem<Item> Ring_ = register("ring",
            (resourceLocation)-> new Ring(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Life_Stone = register("life_stone",
            (resourceLocation)-> new LifeStone(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Armor_Stone = register("armor_stone",
            (resourceLocation)-> new ArmorStone(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Stronger_Stone = register("stronger_stone",
            (resourceLocation)-> new StrongerStone(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Separate_Rune = register("separate_rune",
            (resourceLocation)-> new SeparateRune(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Pain_Rune = register("pain_rune",
            (resourceLocation)-> new PainRune(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Undead_Rune = register("undead_rune",
            (resourceLocation)-> new UndeadRune(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Heart_ = register("heart",
            (resourceLocation)-> new Heart(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Stomach_ = register("stomach",
            (resourceLocation)-> new Stomach(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Meat_Ball = register("meatball",
            (resourceLocation)-> new MeatBall(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));
    public static final DeferredItem<Item> Self_Increasing_Heart = register("self_increasing_heart",
            (resourceLocation)-> new SelfIncreasingHeart(new Item.Properties().setId(ResourceKey.create(Registries.ITEM,resourceLocation))));

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
                    output.accept(InitItems.Ring_);
                    output.accept(InitItems.Life_Stone);
                    output.accept(InitItems.Armor_Stone);
                    output.accept(InitItems.Stronger_Stone);
                    output.accept(InitItems.Separate_Rune);
                    output.accept(InitItems.Pain_Rune);
                    output.accept(InitItems.Undead_Rune);
                    output.accept(InitItems.Heart_);
                    output.accept(InitItems.Stomach_);
                    output.accept(InitItems.Meat_Ball);
                    output.accept(InitItems.Self_Increasing_Heart);
                }).build());

    }

    public static class TagsProvider extends ItemTagsProvider {


        public static final TagKey<Item> chestItem = createTag("chest_item");

        public TagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider,  Chestitem.MODID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(chestItem)
                    .add(God_blood.asItem())
                    .add(Drug_Heal.asItem())
                    .add(Life_Crystal.asItem())
                    .add(Bone_Head.asItem())
                    .add(Conch_.asItem())
                    .add(Lead_.asItem())
                    .add(Stone_.asItem())
                    .add(Knife_.asItem())
                    .add(Ring_.asItem())
                    .add(Life_Stone.asItem())
                    .add(Armor_Stone.asItem())
                    .add(Stronger_Stone.asItem())
                    .add(Separate_Rune.asItem())
                    .add(Pain_Rune.asItem())
                    .add(Undead_Rune.asItem())
                    .add(InitItems.Heart_.asItem())
                    .add(InitItems.Meat_Ball.asItem())
                    .add(InitItems.Self_Increasing_Heart.asItem())
                    .add(InitItems.Stomach_.asItem());;
        }
        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, name));
        }
    }
}
