package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.IGUILightList;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OneEyedSpider extends ItemBlackShadow implements IGUILightList {
    public OneEyedSpider(Properties properties) {
        super(properties);
    }

    public static void hurtOfBlood(LivingEntityUseItemEvent.Finish event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.OneEyedSpider_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag != null) {
                                String string = BuiltInRegistries.ITEM.getKey(event.getItem().getItem()).toString();
                                if (!compoundTag.getBooleanOr(string, false)) {
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
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> attributeModifiers = super.doAttribute(stack,player);
        if (stack.is(InitItems.OneEyedSpider_)) {
            CompoundTag compoundTag = stack.get(DataReg.tag);
            float sa = 0;
            if (compoundTag != null) {
                int size = compoundTag.size();
                sa = (float) Math.sqrt(size);
            }
            attributeModifiers.put(AttReg.hyperplasia, new AttributeModifier(
                    Identifier.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                    sa * 2, AttributeModifier.Operation.ADD_VALUE));

            attributeModifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(
                    Identifier.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                    sa * 3.5 / 2, AttributeModifier.Operation.ADD_VALUE));

            attributeModifiers.put(Attributes.ARMOR, new AttributeModifier(
                    Identifier.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                    sa * 2.7 / 2, AttributeModifier.Operation.ADD_VALUE));

            attributeModifiers.put(AttReg.heal, new AttributeModifier(
                    Identifier.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                    (sa * 6.75) / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

            attributeModifiers.put(AttReg.hyperplasia_stronger, new AttributeModifier(
                    Identifier.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                    (sa * 4.75) / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

            attributeModifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(
                    Identifier.parse(InitItems.OneEyedSpider_.asItem().getDescriptionId()),
                    (sa * 8) / 100f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        }
        return attributeModifiers;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.one_eye_spider.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.accept(Component.literal(""));
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag!=null) {
            int size = compoundTag.size();
            float sa = (float) Math.sqrt(size);
            tooltipAdder.accept(Component.translatable("item.chest_item.one_eye_spider.string.2").append("：").append(String.valueOf((int)(sa*3.5/2))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.one_eye_spider.string.3").append("：").append(String.valueOf((int)(sa*2.7/2))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
            tooltipAdder.accept(Component.translatable("attribute.name.chest_item.hyperplasia").append("：").append(String.valueOf((int)(sa*2))).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));

            tooltipAdder.accept(Component.translatable("item.chest_item.one_eye_spider.string.4").append("：").append(String.valueOf((int)(sa*8))).append("%").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
            tooltipAdder.accept(Component.translatable("attribute.name.chest_item.heal").append("：").append(String.valueOf((int)(sa*6.75))).append("%").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
            tooltipAdder.accept(Component.translatable("attribute.name.chest_item.hyperplasia_stronger").append("：").append(String.valueOf((int)(sa*4.75))).append("%").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80FF5ACD))));
        }
    }
    @Override
    public GUILight guiLight(LivingEntity entity) {
        int lightNumber = 1;

        float s = 0;
        if (entity!=null) {
            s = (float) Math.sin(entity.tickCount / 10f);
            s *= 50;
        }


        Map<Integer,Integer> listGUIColor = new HashMap<>();
        listGUIColor.put(0, Light.ARGB.color((int) (100+s),250,80,250));

        Map<Integer, Vec2> listPosOffset = new HashMap<>();
        listPosOffset.put(0, new Vec2(0,3));

        Map<Integer, Identifier> listImg = new HashMap<>();
        for (int i = 0; i < lightNumber; i++) {
            Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png");
            listImg.put(i,identifier);
        }

        return new GUILight(listGUIColor,listPosOffset,listImg,true,lightNumber);
    }
}
