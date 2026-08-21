package com.ytgld.chest_item.crafting;

import com.ytgld.chest_item.Chestitem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {


    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(
                    Registries.RECIPE_TYPE,
                    Chestitem.MODID
            );


    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(
                    Registries.RECIPE_SERIALIZER,
                    Chestitem.MODID
            );



    public static final DeferredHolder<
            RecipeType<?>,
            RecipeType<SoulRecipe>
            > SOUL_TYPE =
            TYPES.register(
                    "soul_crafting",
                    () -> new RecipeType<>(){}
            );



    public static final DeferredHolder<
            RecipeSerializer<?>,
            RecipeSerializer<SoulRecipe>
            > SOUL_SERIALIZER =
            SERIALIZERS.register(
                    "soul_crafting",
                    () -> new RecipeSerializer<>(
                            SoulRecipe.CODEC,
                            SoulRecipe.STREAM_CODEC
                    )
            );
}