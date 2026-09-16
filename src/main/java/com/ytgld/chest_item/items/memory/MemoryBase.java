package com.ytgld.chest_item.items.memory;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.IBigTooltip;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.black.ITheChaos;
import com.ytgld.chest_item.items.memory.tooltip.BigTooltip;
import com.ytgld.chest_item.items.memory.tooltip.ImageTooltip;
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
import org.jspecify.annotations.NonNull;

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
            Handler.setDataValue(TheMemoryDataHandler.mStringSetData,player,new HashSet<>());
        }
        return InteractionResult.CONSUME;
    }

    public void doText(ItemStack stack , Consumer<Component> tooltipComponents){

    };
    public void doTextGive(ItemStack stack , Consumer<Component> tooltipComponents){

    };
    @Override
    public @NonNull Optional<TooltipComponent> getTooltipImage(ItemStack itemStack) {
        if (name() instanceof BaseTooltip tooltip){
            return Optional.of(new ImageTooltip(tooltip));
        }
        return super.getTooltipImage(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        if (!isHasActivated()) {
            tooltipAdder.accept(Component.translatable("item.chest_item.memory.string.1").withStyle(ChatFormatting.GRAY));
            tooltipAdder.accept(Component.translatable("item.chest_item.memory.string.2").withStyle(ChatFormatting.GRAY));
            tooltipAdder.accept(Component.literal(""));
            doText(stack,tooltipAdder);
        }else {
            tooltipAdder.accept(Component.translatable("item.chest_item.memory.string.3").withStyle(ChatFormatting.GRAY));
            tooltipAdder.accept(Component.translatable("item.chest_item.memory.string.4").withStyle(ChatFormatting.GRAY));
            tooltipAdder.accept(Component.literal(""));
            tooltipAdder.accept(Component.translatable("item.chest_item.memory.string.7").withStyle(ChatFormatting.GRAY));
            doTextGive(stack,tooltipAdder);
            tooltipAdder.accept(Component.literal(""));
            if (flag.hasShiftDown()) {
                tooltipAdder.accept(Component.translatable("item.chest_item.memory.string.6").withStyle(ChatFormatting.DARK_GRAY));
                doText(stack,tooltipAdder);
            }else {
                tooltipAdder.accept(Component.translatable("item.chest_item.memory.string.5").withStyle(ChatFormatting.GRAY));
            }
        }
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            if (!player.hasData(TheMemoryDataHandler.mStringSetData)) {
                Handler.setDataValue(TheMemoryDataHandler.mStringSetData,player, new HashSet<>());
            }
            Set<String> strings = player.getData(TheMemoryDataHandler.mStringSetData);
            if (!isHasActivated()) {
                addMemory(player);
            }else {
                doEnabled(player);
            }
            if (strings.contains(nameSResourceLocation().toString())) {
                if (!level.isClientSide()) {
                    player.sendOverlayMessage(Component.translatable("chest_item.memory"));
                }
            }
        }
        stack.shrink(1);
        return super.finishUsingItem(stack, level, livingEntity);
    }

    /**
     *
     * @param player 需要的玩家
     * @param string 计数器名称
     * @param value 计数器每次增加的值
     */
    public static void addCounter(Player player ,String  string, int value){
        IntAndStringSyncHandler.ISClass isClass = player.getData(TheMemoryDataHandler.counter);
        isClass.map().put(string,isClass.map().getOrDefault(string,0) + value);

    }

    /**
     *
     * @param player 需要的玩家
     * @param string 计数器名称
     * @return 获取这个 string 的计数器的值
     */
    public static int getCounter(Player player,String string){
        IntAndStringSyncHandler.ISClass isClass = player.getData(TheMemoryDataHandler.counter);
        return isClass.map().getOrDefault(string,0);
    }

    public static void  clearCounter(Player player,String string){
        IntAndStringSyncHandler.ISClass isClass = player.getData(TheMemoryDataHandler.counter);
        isClass.map().remove(string);
    }
    /**
     * 用于判断瓶子是不是要激活或者完成任务才可以解锁信仰
     * @return 默认为false，所以多数物品不需要这个
     */
    public boolean isHasActivated(){
        return false;
    }

    /**
     *
     * @param player 检查玩家
     * @param string 需要判断的那个未激活信仰
     * @return 返回一个值：若存在那个未激活信仰则为false
     */
    public static boolean isHasEnabled(Player player,String string){
        Set<String> strings = player.getData(TheMemoryDataHandler.notActivated);
        if (strings.contains(string)) {
            return true;
        }
        return false;
    }
    private void doEnabled(Player player){
        Set<String> strings = player.getData(TheMemoryDataHandler.notActivated);
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
    public static void clearEnabledMemory(Player player ,String name){
        Set<String> strings = player.getData(TheMemoryDataHandler.notActivated);
        strings.remove(name);
    }
    public static void addMemoryIt(Player player ,String name){
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
            strings.add(name);
        }
    }
    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    private Identifier nameSResourceLocation (){
        return Identifier.fromNamespaceAndPath(memoryName().path,memoryName().name);
    }
    private static int max(Player player){
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
    public static abstract class BaseTooltip extends Item implements IBlackLight,ITheChaos , IBigTooltip {
        public BaseTooltip(Properties properties) {
            super(properties);
        }
        public abstract void doText(ItemStack stack , Consumer<Component> tooltipComponents);
        public int color(){
            return Light.ARGB.color(255,255,0,0);
        };

        @Override
        public Item item() {
            return this;
        }

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