package com.ytgld.chest_item.items.black.soul;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.items.black.TheSoul;
import com.ytgld.chest_item.other.*;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Speed  extends TheImprintOfTheSoul {
    public Speed(Properties properties) {
        super(properties);
    }
    
    @Override
    public Identifier Identifier() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/speed.png");
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        give(chestInventory,player);
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        return attributeModifierMultimap();
    }
    public static void give(ChestInventory chestInventory,Player player){
        Set<Item> set = new HashSet<>();

        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
            ItemStack stack = chestInventory.getItem(i);
            if (stack.is(InitItems.Speed_)) {
                return;
            }
            if (stack.getItem() instanceof Meat) {
                set.add(stack.getItem());
            }
        }
        if (set.size() >= 9) {
            ChestInventory inventory = Handler.getItem(player);
            if (inventory != null) {
                ItemStack itemStack10 = inventory.getItem(9);
                ItemStack itemStack11 = inventory.getItem(10);
                ItemStack itemStack12 = inventory.getItem(11);

                ItemStack soul = new ItemStack(InitItems.Speed_.asItem());
                if (soul.get(DataReg.tag) == null) {
                    soul.set(DataReg.tag,new CompoundTag());
                }
                if (itemStack10.isEmpty()) {
                    inventory.setItem(9,soul);
                    player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE,1,1);

                    return;
                }
                if (itemStack11.isEmpty()){
                    inventory.setItem(10,soul);
                    player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE,1,1);
                    return;
                }
                if (itemStack12.isEmpty()) {
                    inventory.setItem(11,soul);
                    player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE,1,1);
                    return;
                }

            }
        }
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        if (stack.get(DataReg.tag)==null){
            return HashMultimap.create();
        }
        return attributeModifierMultimap();
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap()
 {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

            modifiers.put(Attributes.JUMP_STRENGTH, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Speed_.asItem().getDescriptionId()),
                0.3f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.more_speed, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Speed_.asItem().getDescriptionId()),
                0.3f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Speed_.asItem().getDescriptionId()),
                0.3f, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        if (stack.get(DataReg.tag)==null){
            tooltipAdder.accept(Component.translatable("chest_item.the_soul.give").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("chest_item.the_soul.give.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.literal(""));
            tooltipAdder.accept(Component.translatable("item.chest_item.speed.string.2").withStyle(ChatFormatting.GOLD));

        }else {
            tooltipAdder.accept(Component.translatable("item.chest_item.speed.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        }
    }
    @Override
    public int soulColor() {
        return Light.ARGB.color(255,255,100,100);
    }
}
