package com.ytgld.chest_item.crafting;

import com.ytgld.chest_item.items.InitItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Optional;

public class CraftingHandler {

    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            return;
        }

        Container container = event.getInventory();

        if (container instanceof CraftingContainer craftingContainer
                && player.level() instanceof ServerLevel serverLevel) {


            RecipeManager manager = serverLevel.getRecipeManager();

            Optional<RecipeHolder<SoulRecipe>> optional =
                    manager.getRecipeFor(
                            ModRecipes.SOUL_TYPE.get(),
                            craftingContainer.asCraftInput(),
                            player.level()
                    );


            if(optional.isEmpty()) {
                return;
            }


            SoulRecipe recipe = optional.get().value();


            for(int i = 0; i < craftingContainer.getContainerSize(); i++) {

                ItemStack stack =
                        craftingContainer.getItem(i);


                if(stack.is(InitItems.SoulBottle_.get())) {

                    SoulRecipe.consumeSoul(
                            stack,
                            recipe.getSoulCost()
                    );

                    break;
                }
            }
        }
    }
}
