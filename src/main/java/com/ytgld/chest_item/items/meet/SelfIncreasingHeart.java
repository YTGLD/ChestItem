package com.ytgld.chest_item.items.meet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SelfIncreasingHeart extends ItemBase {
    public SelfIncreasingHeart(Properties properties) {
        super(properties);
    }

    public static void tick(LivingEntityUseItemEvent.Finish event) {
        LivingEntity living = event.getEntity();
        if (living instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Self_Increasing_Heart)) {
                            if (event.getItem().getUseAnimation() == ItemUseAnimation.EAT) {
                                player.addEffect(new MobEffectInstance(Effects.IncreasingMeat_,1800,0));
                                @Nullable MobEffectInstance mobEffectInstance = player.getEffect(Effects.IncreasingMeat_);
                                if (mobEffectInstance != null) {
                                    if (mobEffectInstance.getAmplifier()<9) {
                                        player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), mobEffectInstance.getDuration()+1800, mobEffectInstance.getAmplifier() + 1,false,false));
                                    }else {
                                        player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),18000, 10,false,false));
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.Self_Increasing_Heart)) {
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

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.Self_Increasing_Heart.asItem().getDescriptionId()),
                -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.self_increasing_heart.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.self_increasing_heart.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.self_increasing_heart.string.2").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.self_increasing_heart.string.3").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,135,105);
    }
}



