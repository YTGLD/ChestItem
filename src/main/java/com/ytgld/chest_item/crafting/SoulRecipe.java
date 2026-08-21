package com.ytgld.chest_item.crafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SoulRecipe implements CraftingRecipe{
    public final List<Ingredient> ingredients;
    public final Holder<Item> result;
    public final int resultCount;

    private final Map<String, Integer> soulCost;

    private final String group;
    private final boolean showNotification;
    public static final StreamCodec<
            RegistryFriendlyByteBuf,
            SoulRecipe
            > STREAM_CODEC = StreamCodec.composite(

            Ingredient.CONTENTS_STREAM_CODEC
                    .apply(ByteBufCodecs.list()),
            r -> r.ingredients,


            ByteBufCodecs.holderRegistry(
                    Registries.ITEM
            ),
            r -> r.result,


            ByteBufCodecs.INT,
            r -> r.resultCount,


            ByteBufCodecs.map(
                    HashMap::new,
                    ByteBufCodecs.STRING_UTF8,
                    ByteBufCodecs.INT
            ),
            SoulRecipe::getSoulCost,


            ByteBufCodecs.STRING_UTF8,
            SoulRecipe::getGroup,


            ByteBufCodecs.BOOL,
            SoulRecipe::showNotification,


            SoulRecipe::new
    );
    public static final MapCodec<SoulRecipe> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(

                            Ingredient.CODEC
                                    .listOf()
                                    .fieldOf("ingredients")
                                    .forGetter(r -> r.ingredients),


                            BuiltInRegistries.ITEM
                                    .holderByNameCodec()
                                    .fieldOf("result")
                                    .forGetter(r -> r.result),


                            Codec.INT
                                    .optionalFieldOf("count", 1)
                                    .forGetter(r -> r.resultCount),


                            Codec.unboundedMap(
                                            Codec.STRING,
                                            Codec.INT
                                    )
                                    .optionalFieldOf(
                                            "soul_cost",
                                            Map.of()
                                    )
                                    .forGetter(
                                            SoulRecipe::getSoulCost
                                    ),


                            Codec.STRING
                                    .optionalFieldOf(
                                            "group",
                                            ""
                                    )
                                    .forGetter(
                                            SoulRecipe::getGroup
                                    ),


                            Codec.BOOL
                                    .optionalFieldOf(
                                            "show_notification",
                                            true
                                    )
                                    .forGetter(
                                            SoulRecipe::showNotification
                                    )

                    ).apply(instance, SoulRecipe::new)
            );
    public SoulRecipe(
            List<Ingredient> ingredients,
            Holder<Item> result,
            int resultCount,
            Map<String,Integer> soulCost,
            String group,
            boolean showNotification
    )
    {
        this.ingredients = ingredients;
        this.result = result;
        this.resultCount = resultCount;
        this.soulCost = soulCost;
        this.group = group;
        this.showNotification = showNotification;
    }

    public Map<String,Integer> getSoulCost() {
        return soulCost;
    }


    @Override
    public boolean matches(
            CraftingInput input,
            Level level
    ) {
        List<Ingredient> remaining =
                new ArrayList<>(ingredients);


        for(int i = 0; i < input.size(); i++) {

            ItemStack stack = input.getItem(i);

            if(stack.isEmpty()) {
                continue;
            }


            boolean matched = false;


            for(Ingredient ingredient : remaining) {

                if(ingredient.test(stack)) {

                    remaining.remove(ingredient);
                    matched = true;
                    break;
                }
            }


            if(!matched) {
                return false;
            }
        }


        if(!remaining.isEmpty()) {
            return false;
        }


        return checkSoulBottle(input);
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {
        return new ItemStack(
                result,
                resultCount
        );
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return null;
    }

    private boolean checkSoulBottle(CraftingInput input) {
        for(int i=0;i<input.size();i++) {
            ItemStack stack = input.getItem(i);
            if(stack.is(InitItems.SoulBottle_.get())) {
                SetSoulData data = stack.get(DataReg.soulMap);
                if(data == null) return false;
                Map<String,Integer> souls = data.soulMap();
                for(var e:soulCost.entrySet()) {
                    if(souls.getOrDefault(e.getKey(), 0) < e.getValue()) {
                        return false;
                    }
                }
                return true;
            }
        }


        return soulCost.isEmpty();
    }


    public static void consumeSoul(ItemStack stack, Map<String, Integer> cost) {

        SetSoulData data =
                stack.get(DataReg.soulMap);

        if(data == null) {
            return;
        }


        HashMap<String,Integer> souls =
                new HashMap<>(data.soulMap());


        for(var entry : cost.entrySet()) {

            String type = entry.getKey();

            int remain =
                    souls.getOrDefault(type,0)
                            - entry.getValue();


            if(remain <= 0) {
                souls.remove(type);
            }
            else {
                souls.put(type, remain);
            }
        }


        stack.set(
                DataReg.soulMap,
                new SetSoulData(souls)
        );
    }
    @Override
    public boolean showNotification() {
        return showNotification;
    }

    @Override
    public RecipeSerializer<? extends CraftingRecipe> getSerializer() {
        return ModRecipes.SOUL_SERIALIZER.get();
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }


    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {

        NonNullList<ItemStack> remaining =
                NonNullList.withSize(
                        input.size(),
                        ItemStack.EMPTY
                );


        for (int i = 0; i < input.size(); i++) {

            ItemStack stack = input.getItem(i);

            if (stack.is(InitItems.SoulBottle_.get())) {

                ItemStack copy = stack.copy();

                consumeSoul(
                        copy,
                        soulCost
                );

                remaining.set(i, copy);
            }
        }

        return remaining;
    }

    @Override
    public RecipeType<CraftingRecipe> getType() {
        return RecipeType.CRAFTING;
    }

}