package com.ytgld.chest_item.items.meet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.function.Consumer;

/**
 * 减少25%最大生命值
 * <P>
 * 减少40%生命恢复
 * <P>
 * <P>
 * 你最高受到5点伤害
 * <P>
 * 超过5点的伤害将转换成等数值的流血
 * <P>
 */
public class GodApple extends ItemBase {


    public static final int TIME = 10 * 20;

    public static final String bloodTime = "bloodTime";
    public static final String bloodDamage = "bloodDamage";

    public GodApple(Properties properties) {
        super(properties);
    }

    public static void event(LivingDamageEvent.Pre event){
        if (!event.getSource().is(DamageTypes.GENERIC_KILL)) {
            if (event.getEntity() instanceof Player player) {
                if (!player.level().isClientSide) {
                    ChestInventory chestInventory = Handler.getItem(player);
                    if (chestInventory != null) {
                        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                            ItemStack stack = chestInventory.getItem(i);
                            if (stack.is(InitItems.God_Apple.get())) {
                                CompoundTag compoundTag = stack.get(DataReg.tag);
                                if (compoundTag != null) {
                                    float s = event.getNewDamage() - 5;
                                    if (s > 5) {
                                        compoundTag.putFloat(bloodDamage, compoundTag.getFloatOr(bloodDamage, 0) + s);
                                        compoundTag.putInt(bloodTime, TIME);
                                        event.setNewDamage(5);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void event(ItemStackTickEvent event) {
        Player player = event.player;
        ChestInventory chestInventory = event.chestInventory;
        if (!player.level().isClientSide&&chestInventory!=null){
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.God_Apple.get())) {
                    CompoundTag compoundTag = stack.get(DataReg.tag);
                    if (compoundTag != null) {
                        if (compoundTag.getIntOr(bloodTime, 0) <= 0) {
                            compoundTag.putFloat(bloodDamage, 0);
                        }
                        if (compoundTag.getIntOr(bloodDamage, 0) <= 0) {
                            compoundTag.putFloat(bloodTime, 0);
                        }
                        if (player.isDeadOrDying()) {
                            compoundTag.putFloat(bloodDamage, 0);
                            compoundTag.putFloat(bloodTime, 0);
                        }

                        if (!player.level().isClientSide
                                && player.tickCount % 20 == 1) {
                            if (compoundTag.getIntOr(bloodTime, 0) > 0 && compoundTag.getFloatOr(bloodDamage, 0) > 0) {
                                compoundTag.putInt(bloodTime, compoundTag.getIntOr(bloodTime, 0) - TIME / 10);
                                float dmg = compoundTag.getFloatOr(bloodDamage, 0) / 10f;
                                compoundTag.putFloat(bloodDamage, compoundTag.getFloatOr(bloodDamage, 0) - dmg);
                                player.hurt(player.damageSources().genericKill(), dmg);
                            }
                        }
                        break;
                    }else {
                        stack.set(DataReg.tag,new CompoundTag());
                    }
                }
            }
        }
    }

    public static void event2(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.God_Apple)) {
                    player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap());
                    break;
                } else {
                    player.getAttributes().removeAttributeModifiers(attributeModifierMultimap());
                }
            }
        }
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap() {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.heal, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                -0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.god_apple.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.god_apple.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.god_apple.string.2").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.god_apple.string.3").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.god_apple.string.4").withStyle(ChatFormatting.GOLD));

    }


    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,135,105);
    }
}

