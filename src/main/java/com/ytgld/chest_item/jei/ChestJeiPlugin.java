package com.ytgld.chest_item.jei;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.crafting.ModRecipeCache;
import com.ytgld.chest_item.crafting.SoulRecipe;
import com.ytgld.chest_item.items.InitItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

@JeiPlugin
public class ChestJeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, "soul");
    }

    @Override
    public void registerRecipeCatalysts(
            IRecipeCatalystRegistration registration
    ) {
        registration.addRecipeCatalyst(
                InitItems.SoulBottle_.get(),
                ChestRecipeCategory.TYPE
                );
    }

    @Override
    public void registerCategories(
            IRecipeCategoryRegistration registry
    ) {
        registry.addRecipeCategories(
                new ChestRecipeCategory()
        );
    }
    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null) {
            return;
        }

        List<SoulRecipe> recipes = minecraft.level
                .getRecipeManager()
                .getRecipes()
                .stream()
                .map(RecipeHolder::value)
                .filter(SoulRecipe.class::isInstance)
                .map(SoulRecipe.class::cast)
                .toList();

        registration.addRecipes(
                ChestRecipeCategory.TYPE,
                recipes
        );
    }
}
