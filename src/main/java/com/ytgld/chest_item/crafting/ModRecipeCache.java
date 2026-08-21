package com.ytgld.chest_item.crafting;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;

import java.util.ArrayList;
import java.util.List;

public class ModRecipeCache {

    public static final List<SoulRecipe> SOUL_RECIPES =
            new ArrayList<>();

    public static void event(
            RecipesUpdatedEvent event
    ) {
        ModRecipeCache.SOUL_RECIPES.clear();
        for(RecipeHolder<?> holder :
                event.getRecipeManager().getRecipes()) {

            if(holder.value() instanceof SoulRecipe recipe) {

                ModRecipeCache.SOUL_RECIPES.add(recipe);

            }
        }
    }
}