package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
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
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

import java.util.List;

public class NuclearReaction extends ItemBase {
    public NuclearReaction(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
     public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Other";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("NuclearReaction");
            intValue =  builder.translation("chest_item.config.NuclearReaction")
                    .defineInRange("number",1.25f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.NuclearReaction2")
                    .defineInRange("number2",25,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("NuclearReaction",
                            "经验反应炉","经验掉落倍率"),
                    new CIString("NuclearReaction2",
                            "经验反应炉2","经验3倍的概率")
            );
        }
    }
    public static void event(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.NuclearReaction_)) {
                            event.setDroppedExperience((int) (event.getDroppedExperience() * ConfigItem.intValue.get().floatValue()));
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
                        if (Mth.nextInt(RandomSource.create(),1,100) <= ConfigItem.intValue2.get().intValue()) {
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
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.nuclear_reaction.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.nuclear_reaction.string.1",ConfigItem.intValue.get().floatValue() * 100 - 100).withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.nuclear_reaction.string.2", ConfigItem.intValue2.get().floatValue()).withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.nuclear_reaction.string.3").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,100,20,255);
    }
}
