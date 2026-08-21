package com.ytgld.chest_item.crafting;

import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class ModRecipeCache {

    public static final List<SoulRecipe> SOUL_RECIPES =
            new ArrayList<>();

    public static void event(
            RecipesReceivedEvent event
    ) {
        ModRecipeCache.SOUL_RECIPES.clear();
        for(RecipeHolder<?> holder :
                event.getRecipeMap().values()) {

            if(holder.value() instanceof SoulRecipe recipe) {

                ModRecipeCache.SOUL_RECIPES.add(recipe);

            }
        }
    }
}