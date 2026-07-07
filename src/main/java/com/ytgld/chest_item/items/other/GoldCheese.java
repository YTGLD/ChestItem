package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEnchantItemEvent;

import java.util.List;


public class GoldCheese extends ItemBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("GoldCheese");
            intValue =  builder.translation("chest_item.config.GoldCheese")
                    .defineInRange("number",1,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.GoldCheese2")
                    .defineInRange("number2",3,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("GoldCheese",
                            "黄金奶酪","附魔等级加成"),
                    new CIString("GoldCheese2",
                            "黄金奶酪2","额外的经验掉落")
            );
        }
    }
    public GoldCheese(Properties properties) {
        super(properties);
    }
    public static void event(PlayerEnchantItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Gold_Cheese)) {
                            ItemStack eventStack = event.getEnchantedItem();
                            if (BuiltInRegistries.ITEM.getKey(eventStack.getItem()).getPath().contains("gold")) {

                                List<EnchantmentInstance> list = event.getEnchantments();
                                for (EnchantmentInstance enchantmentInstance : list){
                                    int level = enchantmentInstance.level;
                                    Holder<Enchantment> enchantment = enchantmentInstance.enchantment;
                                    ItemEnchantments.Mutable itemenchantments$mutable = new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(eventStack));
                                    itemenchantments$mutable.set(enchantment,level+ConfigItem.intValue.get().intValue());
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
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Gold_Cheese)) {
                            if (BuiltInRegistries.ITEM.getKey(player.getMainHandItem().getItem()).getPath().contains("gold")) {
                                event.setDroppedExperience(event.getDroppedExperience() * ConfigItem.intValue2.get().intValue());
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.gold_cheese.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_item.gold_cheese.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.gold_cheese.string.2").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.gold_cheese.string.3").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,255,50);
    }
}
