package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class OneEyedSpider extends ItemBlackShadow {
    public OneEyedSpider(Properties properties) {
        super(properties);
    }

    public static void hurtOfBlood(ItemStack food, LivingEntity entity) {
        if (entity instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.OneEyedSpider_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag != null) {
                                String string = BuiltInRegistries.ITEM.getKey(food.getItem()).toString();
                                if (!compoundTag.getBoolean(string)) {
                                    compoundTag.putBoolean(string, true);
                                    break;
                                }
                            } else {
                                stack.set(DataReg.tag, new CompoundTag());
                            }
                        }
                    }
                }
            }
        }
    }

    public static void tick(ItemStackTickEvent event) {
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.OneEyedSpider_)) {
                    player.getAttributes().addTransientAttributeModifiers(getAttributeModifiers(stack));
                    break;
                } else {
                    player.getAttributes().removeAttributeModifiers(getAttributeModifiers(stack));
                }
            }
        }
    }

    public static Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers = HashMultimap.create();
        CompoundTag compoundTag = stack.get(DataReg.tag);
        float sa = 0;
        if (compoundTag != null) {
            int size = compoundTag.size();
            sa = (float) Math.sqrt(size);
        }
        attributeModifiers.put(AttReg.hyperplasia, new AttributeModifier(
                ResourceLocation.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                sa * 2, AttributeModifier.Operation.ADD_VALUE));

        attributeModifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(
                ResourceLocation.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                sa * 3.5 / 2, AttributeModifier.Operation.ADD_VALUE));

        attributeModifiers.put(Attributes.ARMOR, new AttributeModifier(
                ResourceLocation.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                sa * 2.7 / 2, AttributeModifier.Operation.ADD_VALUE));

        attributeModifiers.put(AttReg.heal, new AttributeModifier(
                ResourceLocation.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                (sa * 6.75) / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        attributeModifiers.put(AttReg.hyperplasia_stronger, new AttributeModifier(
                ResourceLocation.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                (sa * 4.75) / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        attributeModifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(
                ResourceLocation.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                (sa * 8) / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return attributeModifiers;

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.one_eye_spider.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.add(Component.literal(""));
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            int size = compoundTag.size();
            float sa = (float) Math.sqrt(size);
            tooltipAdder.add(Component.translatable("item.chest_item.one_eye_spider.string.2").append("：").append(String.valueOf((int) (sa * 3.5 / 2))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
            tooltipAdder.add(Component.translatable("item.chest_item.one_eye_spider.string.3").append("：").append(String.valueOf((int) (sa * 2.7 / 2))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
            tooltipAdder.add(Component.translatable("attribute.name.chest_item.hyperplasia").append("：").append(String.valueOf((int) (sa * 2))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));

            tooltipAdder.add(Component.translatable("item.chest_item.one_eye_spider.string.4").append("：").append(String.valueOf((int) (sa * 8))).append("%").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
            tooltipAdder.add(Component.translatable("attribute.name.chest_item.heal").append("：").append(String.valueOf((int) (sa * 6.75))).append("%").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
            tooltipAdder.add(Component.translatable("attribute.name.chest_item.hyperplasia_stronger").append("：").append(String.valueOf((int) (sa * 4.75))).append("%").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
        }

    }
}
