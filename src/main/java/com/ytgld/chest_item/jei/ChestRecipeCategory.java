package com.ytgld.chest_item.jei;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.crafting.SoulRecipe;
import com.ytgld.chest_item.items.InitItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.common.Internal;
import mezz.jei.common.gui.textures.Textures;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class ChestRecipeCategory implements IRecipeCategory<SoulRecipe> {
    public static final RecipeType<SoulRecipe> TYPE =
            RecipeType.create(
                    Chestitem.MODID,
                    "crafting",
                    SoulRecipe.class
            );
    private final IDrawable theRecipeArrow;
    private final IDrawable theSlot;
    public ChestRecipeCategory(){
        Textures textures = Internal.getTextures();
        this.theRecipeArrow = textures.getRecipeArrow();
        this.theSlot = textures.getSlot();
    }
    @Override
    public RecipeType<SoulRecipe> getRecipeType() {
        return TYPE;
    }


    @Override
    public Component getTitle() {
        return Component.translatable("chest_item.jei.recipe");
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 100;
    }


    @Override
    public void draw(SoulRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        theRecipeArrow.draw(guiGraphics,40,60);
        int offsetItem = 0;
        for (Ingredient item : recipe.ingredients){
            c:for (Ingredient.Value value : item.getValues()) {
                for (ItemStack stack : value.getItems()){
                    if (stack.getItem() == InitItems.SoulBottle_.asItem()) {
                        continue c;
                    }
                }
                offsetItem += 16;
                theSlot.draw(guiGraphics,offsetItem - 16,40);
            }
        }

        int offset = 0;
        for (String string : recipe.getSoulCost().keySet()) {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(string));
            if (item == InitItems.SoulBottle_.asItem()) {
                continue;
            }
            offset += 16;
            theSlot.draw(guiGraphics,offset - 16,20);

        }
        theSlot.draw(guiGraphics,80, 40);
    }

    @Override
    public IDrawable getIcon() {
        return new ItemIDrawable();
    }

    @Override
    public void setRecipe(
            IRecipeLayoutBuilder builder,
            SoulRecipe page,
            IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 0, 0)
                .addItemLike(InitItems.SoulBottle_.asItem());

        int offset = 0;
        for (String string : page.getSoulCost().keySet()) {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(string));
            if (item == InitItems.SoulBottle_.asItem()) {
                continue;
            }
            offset += 16;
            builder.addSlot(RecipeIngredientRole.INPUT, offset - 16, 20)
                    .addItemStack(new ItemStack(item,page.getSoulCost().get(string)));
        }
        int offsetItem = 0;
        for (Ingredient item : page.ingredients){
            c:for (Ingredient.Value value : item.getValues()) {
                for (ItemStack stack : value.getItems()){
                    if (stack.getItem() == InitItems.SoulBottle_.asItem()) {
                        continue c;
                    }
                    offsetItem += 16;
                    builder.addSlot(RecipeIngredientRole.INPUT, offsetItem - 16, 40)
                            .addItemLike(stack.getItem());
                }
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 40)
                .addItemLike(page.result.value());
    }

    public static class ItemIDrawable implements IDrawable {

        @Override
        public int getWidth() {
            return 16;
        }

        @Override
        public int getHeight() {
            return 16;
        }

        @Override
        public void draw(GuiGraphics guiGraphicsExtractor, int i, int i1) {
            guiGraphicsExtractor.renderItem(InitItems.SoulBottle_.get().getDefaultInstance(),i,i1);
        }
    }
}