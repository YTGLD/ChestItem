package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

import java.util.function.Consumer;

public class NuclearReaction extends ItemBase {
    public NuclearReaction(Properties properties) {
        super(properties);
    }
    public static void event(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.NuclearReaction_)) {
                            event.setDroppedExperience((int) (event.getDroppedExperience() * 1.25f));
                            break;
                        }
                    }
                }
            }
        }
    }
    public static void applyExp(Player player, ExperienceOrb orb) {
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory != null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.NuclearReaction_)) {
                        if (Mth.nextInt(RandomSource.create(),1,100) <= 25) {
                            player.giveExperiencePoints(orb.getValue()*2);
                            player.takeXpDelay = 0;
                            break;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void text(ItemStack stack,Consumer<Component> tooltipAdder,TooltipFlag flag){
        tooltipAdder.accept(Component.translatable("item.chest_item.nuclear_reaction.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.nuclear_reaction.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.nuclear_reaction.string.2").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.nuclear_reaction.string.3").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,100,20,255);
    }
}
