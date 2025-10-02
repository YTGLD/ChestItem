package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEnchantItemEvent;

import java.util.List;
import java.util.function.Consumer;


public class GoldCheese extends ItemBase {

    public GoldCheese(Properties properties) {
        super(properties);
    }
    public static void event(PlayerEnchantItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Gold_Cheese)) {
                            ItemStack eventStack = event.getEnchantedItem();
                            if (BuiltInRegistries.ITEM.getKey(eventStack.getItem()).getPath().contains("gold")) {

                                List<EnchantmentInstance> list = event.getEnchantments();
                                for (EnchantmentInstance enchantmentInstance : list){
                                    int level = enchantmentInstance.level();
                                    Holder<Enchantment> enchantment = enchantmentInstance.enchantment();
                                    ItemEnchantments.Mutable itemenchantments$mutable = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(eventStack));
                                    itemenchantments$mutable.set(enchantment,level+1);
                                    EnchantmentHelper.setEnchantments(eventStack,itemenchantments$mutable.toImmutable());
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void event(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Gold_Cheese)) {
                            if (BuiltInRegistries.ITEM.getKey(player.getMainHandItem().getItem()).getPath().contains("gold")) {
                                event.setDroppedExperience(event.getDroppedExperience() * 3);
                                if (Mth.nextInt(RandomSource.create(),0,100) <= 20) {
                                    event.getEntity().level().addFreshEntity(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(Items.GOLD_INGOT)));
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.gold_cheese.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.gold_cheese.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.gold_cheese.string.2").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.gold_cheese.string.3").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,255,50);
    }
}
