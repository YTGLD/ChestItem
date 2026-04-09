package com.ytgld.chest_item.items.reinforced;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.evil_mother.IEvil;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class ReinforcedBaseItem extends Item implements IEvil {
    public ReinforcedBaseItem(Properties properties) {
        super(properties);
        ReinforcedLoot.lListItems.add(this);
    }

    public abstract int sanDown();
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        if (addReinforcedItem(player)) {
            player.level().playSound(null, player.blockPosition(), SoundEvents.ARMOR_EQUIP_NETHERITE.value(), SoundSource.AMBIENT, 1, 1);
            itemstack.shrink(1);
        }else if (!player.level().isClientSide){
            player.displayClientMessage(Component.translatable("event.chest_item.reinforced.can_not"),false);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    private boolean addReinforcedItem(Player player){
        Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
        List<Integer> integers = new ArrayList<>();
        for (String ignored : strings){
            integers.add(1);
        }
        if (strings.contains(nameSResourceLocation().toString())){
            return false;
        }
        int  s = 0;
        for (Integer ignored : integers){
            s++;
        }
        if (s < max(player)){
            strings.add(nameSResourceLocation().toString());
            return true;
        }
        return false;
    }
    private int max(Player player){
        return (int) (player.getAttributeValue(ReinforcedAttreg.maxReinforced));
    }

    private ResourceLocation nameSResourceLocation(){
        return BuiltInRegistries.ITEM.getKey(this.name());
    }

    public static List<Item> getItems(Player player) {
        Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
        List<Item> list = new ArrayList<>();
        for (String string : strings) {
            String[] parts = string.split(":");
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]));
            list.add(item);
        }
        return list;
    }

    public Item name(){
        return this;
    };
    public Multimap<Holder<Attribute>, AttributeModifier> attributeUse(Player player){
        return HashMultimap.create();
    }
    public final Multimap<Holder<Attribute>, AttributeModifier> doAttribute(Player player){
        Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap = attributeUse(player);
        attributeModifierMultimap.put(AttReg.theSanity, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                this.asItem().getDescriptionId()),
                sanDown(), AttributeModifier.Operation.ADD_VALUE));
        return attributeModifierMultimap;
    }
    @Override
    public @NotNull Component getName(ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(color));
        return co;
    }
    public static boolean hasReinforcedItem(Player player ,Item item){
        Set<String> strings = player.getData(ReinforcedDataHandler.reinforced);
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        return strings.contains(resourceLocation.toString());
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        text(stack,tooltipComponents);
    }
    public void text(ItemStack stack,List<Component> tooltipComponents){

    }

    public static boolean hasDecayHeart(Player player){
        return player.getAttributeValue(AttReg.theSanity) <= 0;
    }
    public static double getPainHeartValue(Player player){
        return player.getData(AttReg.painShield);
    }

    public static boolean decayHeartIsZero(Player player){
        if (ReinforcedBaseItem.hasDecayHeart(player)) {
            return ReinforcedBaseItem.getPainHeartValue(player) <= 0;
        }
        return false;
    }
    public static boolean canLoot(Player player){
        return player.getAttributeValue(AttReg.theSanity) < 10;
    }
    public static int getSanValue(Player player){
        int sna = (int) player.getAttributeValue(AttReg.theSanity);
        int base = (int) player.getAttributeBaseValue(AttReg.theSanity);
        if (sna != base) {
            int cha = sna - base;
            if (cha < 0) {
                cha = -cha;
                return cha;
            }
        }
        return 0;
    }
}
