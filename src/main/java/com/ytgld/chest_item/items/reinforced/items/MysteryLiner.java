package com.ytgld.chest_item.items.reinforced.items;

import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.player.PlayerEnchantItemEvent;

import java.util.List;

/**
 * 神秘衬件
 * <p>
 * 增加%d附魔物品的附魔等级
 */
public class MysteryLiner extends ReinforcedBaseItem {
    public MysteryLiner(Properties properties) {
        super(properties, false);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Reinforced";
        }
        public static ModConfigSpec.IntValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("MysteryLiner");
            intValue =  builder.translation("chest_item.config.MysteryLiner")
                    .defineInRange("number",1,0,Integer.MAX_VALUE);
            builder.pop();
        }
        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("MysteryLiner",
                            "神秘衬件","附魔等级加成")
            );
        }
    }
    public static void event(PlayerEnchantItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (ReinforcedBaseItem.hasReinforcedItem(player, ReinforcedItems.MysteryLiner_.asItem())){
                ItemStack eventStack = event.getEnchantedItem();
                List<EnchantmentInstance> list = event.getEnchantments();
                for (EnchantmentInstance enchantmentInstance : list){
                    int level = enchantmentInstance.level();
                    Holder<Enchantment> enchantment = enchantmentInstance.enchantment();

                    ItemEnchantments.Mutable itemenchantments$mutable =
                            new ItemEnchantments.Mutable(EnchantmentHelper.getEnchantmentsForCrafting(eventStack));

                    itemenchantments$mutable.set(enchantment,level + ConfigItem.intValue.getAsInt());

                    EnchantmentHelper.setEnchantments(eventStack,itemenchantments$mutable.toImmutable());
                }
            }
        }
    }
    @Override
    public void text(ItemStack stack, List<Component> tooltipComponents) {
        super.text(stack, tooltipComponents);
        tooltipComponents.add(Component.translatable("item.chest_item.mystery_liner.string.0", ConfigItem.intValue.get()).withStyle(Style.EMPTY.withColor(color)));
    }
    @Override
    public int sanDown() {
        return -2;
    }
}
