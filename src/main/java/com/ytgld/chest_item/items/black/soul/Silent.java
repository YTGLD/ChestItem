package com.ytgld.chest_item.items.black.soul;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.items.black.give.BrassCoins;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.List;

public class Silent extends TheImprintOfTheSoul {
    public Silent(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Silent");
            intValue =  builder.translation("chest_item.config.Silent")
                    .defineInRange("number",0.75f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Silent2")
                    .defineInRange("number2",1.5f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Silent",
                            "静止沉默","抗性倍率"),
                    new CIString("Silent2",
                            "静止沉默2","治疗")
            );
        }
    }
    public static void hurtSilent_(LivingIncomingDamageEvent event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Silent_)) {
                            if (player.getDeltaMovement().length() <= 0.1){
                                event.setAmount(event.getAmount() * ConfigItem.intValue.get().floatValue());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void livingHealEventSilent_(LivingHealEvent event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Silent_)) {
                            if (player.getDeltaMovement().length() <= 0.1){
                                event.setAmount(event.getAmount() * ConfigItem.intValue2.get().floatValue());
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
        if (stack.get(DataReg.tag)==null){
            tooltipAdder.add(Component.translatable("chest_item.the_soul.give").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("chest_item.the_soul.give.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.literal(""));
            tooltipAdder.add(Component.translatable("item.chest_item.silent.string.3").withStyle(ChatFormatting.GOLD));

        }else {
            tooltipAdder.add(Component.translatable("item.chest_item.silent.string.1",100f * ConfigItem.intValue.get().floatValue() -100f).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.silent.string.2",100 - 100f * ConfigItem.intValue.get().floatValue()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        }
    }

    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        give(chestInventory,player);
    }
    public static void  give (ChestInventory chestInventory,Player player){
        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
            ItemStack stack = chestInventory.getItem(i);
            if (stack.is(InitItems.Silent_)) {
                return;
            }

        }
        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
            ItemStack stack = chestInventory.getItem(i);
            if (stack.is(InitItems.BrassCoins_)) {
                if (BrassCoins.isTrue(stack, 300)) {
                    ChestInventory inventory = Handler.getItem(player);
                    if (inventory != null) {
                        ItemStack itemStack10 = inventory.getItem(9);
                        ItemStack itemStack11 = inventory.getItem(10);
                        ItemStack itemStack12 = inventory.getItem(11);

                        ItemStack soul = new ItemStack(InitItems.Silent_.asItem());
                        if (soul.get(DataReg.tag) == null) {
                            soul.set(DataReg.tag, new CompoundTag());
                        }
                        if (itemStack10.isEmpty()) {
                            inventory.setItem(9, soul);
                            player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE, 1, 1);
                            stack.shrink(1);
                            return;
                        }
                        if (itemStack11.isEmpty()) {
                            inventory.setItem(10, soul);
                            player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE, 1, 1);
                            stack.shrink(1);
                            return;
                        }
                        if (itemStack12.isEmpty()) {
                            inventory.setItem(11, soul);
                            player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE, 1, 1);
                            stack.shrink(1);
                            return;
                        }
                    }
                }
            }
        }
    }



    @Override
    public ResourceLocation resourceLocation() {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/silent.png");
    }

    @Override
    public int soulColor() {
        return Light.ARGB.color(255,	187 ,255, 255);
    }
}
