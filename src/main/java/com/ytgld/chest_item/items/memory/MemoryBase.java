package com.ytgld.chest_item.items.memory;

import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.black.ITheChaos;
import com.ytgld.chest_item.items.memory.tooltip.BigTooltip;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.*;
import java.util.function.Consumer;

public abstract class MemoryBase extends Item {
    public MemoryBase(Properties properties) {
        super(properties);
    }
    public abstract MemoryString memoryName();
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        if (!player.hasData(TheMemoryDataHandler.mStringSetData)){
            player.setData(TheMemoryDataHandler.mStringSetData,new HashSet<>());
        }
        return InteractionResult.CONSUME;
    }

    public void doText(ItemStack stack , Consumer<Component> tooltipComponents){

    };

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.accept(Component.translatable("item.chest_item.memory.string.1").withStyle(ChatFormatting.GRAY));
        tooltipAdder.accept(Component.translatable("item.chest_item.memory.string.2").withStyle(ChatFormatting.GRAY));
        tooltipAdder.accept(Component.literal(""));
        doText(stack,tooltipAdder);
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            if (!player.hasData(TheMemoryDataHandler.mStringSetData)) {
                player.setData(TheMemoryDataHandler.mStringSetData, new HashSet<>());
            }
            Set<String> strings = player.getData(TheMemoryDataHandler.mStringSetData);
            addMemory(player);
            if (strings.contains(nameSResourceLocation().toString())) {
                if (!level.isClientSide()) {
                    player.sendOverlayMessage(Component.translatable("chest_item.memory"));
                }
            }
        }
        stack.shrink(1);
        return super.finishUsingItem(stack, level, livingEntity);
    }
    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    private Identifier nameSResourceLocation (){
        return Identifier.fromNamespaceAndPath(memoryName().path,memoryName().name);
    }
    private int max(Player player){
        return (int) (player.getAttributeValue(MemoryAttreg.maxMemory));
    }

    private void addMemory(Player player){
        Set<String> strings = player.getData(TheMemoryDataHandler.mStringSetData);
        List<Integer> integers = new ArrayList<>();
        for (String ignored : strings){
            integers.add(1);
        }
        int  s = 0;
        for (Integer ignored : integers){
            s++;
        }
        if (s < max(player)){
            strings.add(nameSResourceLocation().toString());
        }
    }
    public static List<ItemStack> getMemoryItem(Player player) {
        Set<String> strings = player.getData(TheMemoryDataHandler.mStringSetData);
        List<ItemStack> list = new ArrayList<>();
        for (String string : strings) {
            String[] parts = string.split(":");
            Item item = BuiltInRegistries.ITEM.getValue(Identifier.fromNamespaceAndPath(parts[0], parts[1]));
            list.add(item.getDefaultInstance());
        }
        return list;
    }
    public abstract Item name();

    @Override
    public Component getName(ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        MutableComponent empty = Component.empty();
        if (name() instanceof BaseTooltip tooltip) {
            empty.setStyle(Style.EMPTY.withColor(tooltip.color()));
            empty.append(tooltip.getName(tooltip.getDefaultInstance()));
        }

        return co.append(empty);
    }

    public record MemoryString (String  path , String name){}
    public static abstract class BaseTooltip extends Item implements IBlackLight,ITheChaos {
        public BaseTooltip(Properties properties) {
            super(properties);
        }
        public abstract void doText(ItemStack stack , Consumer<Component> tooltipComponents);
        public abstract Component doTextOne();
        public int color(){
            return Light.ARGB.color(255,255,0,0);
        };
        @Override
        public Component getName(ItemStack stack) {
            Component component = super.getName(stack);
            MutableComponent co = component.copy();
            co.setStyle(Style.EMPTY.withColor(color()));
            MutableComponent soul = Component
                    .translatable("chest_item.memory.name");
            return soul.append(Component.literal("<"))
                    .append(co)
                    .append(Component.literal(">"));
        }
        @Override
        public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
            return Optional.of(new BigTooltip(this));
        }
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
            doText(stack,tooltipAdder);
        }
    }

    public static boolean hasMemory(Player player ,String string){
        Set<String> strings = player.getData(TheMemoryDataHandler.mStringSetData);
        return strings.contains(string);
    }
}














