package com.ytgld.chest_item.items.black.soul;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * 饕餮诅咒
 * <p>
 * <p>
 * “你这种卑微的物种就应该做我们的奴隶”
 * <p>
 * 极大提高进食速度
 * <p>
 * 接近饱腹时获得额外生命值和伤害吸收
 * <p>
 * 食用食物会瞬间恢复生命值
 * <p>
 * <p>
 * “这枚奴印是你唯一的出路”
 * <p>
 * 饥饿值和饱和度不再可以恢复生命值
 * <p>
 * 时刻感到异常的饥饿
 * <p>
 * 饱腹时速度减少约10%
 */
public class Glutton extends TheImprintOfTheSoul {

    public Glutton(Properties properties) {
        super(properties);
    }
    public static void eatStart(LivingEntityUseItemEvent.Start event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Glutton_)) {
                            if (event.getItem().getUseAnimation() == ItemUseAnimation.EAT){
                                event.setDuration(event.getDuration()/2);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void eatFinish(LivingEntityUseItemEvent.Finish event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Glutton_)) {
                            if (event.getItem().getUseAnimation() == ItemUseAnimation.EAT){
                                FoodProperties sf = event.getItem().get(DataComponents.FOOD);
                                if (sf!=null) {
                                    float food = sf.nutrition();
                                    float sta = sf.saturation();
                                    player.heal(food + sta);
                                    player.getCooldowns().addCooldown(stack,200);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);
        float hunger = 0;
        float speed = 0;
        if (player.getFoodData().getFoodLevel() > 12) {
            hunger = 10;
            if (player.getFoodData().getFoodLevel() >= 20) {
                speed = -0.25f;
            }
        }

        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Glutton_.asItem().getDescriptionId()),
                speed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));


        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Glutton_.asItem().getDescriptionId()),
                hunger, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.MAX_ABSORPTION, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Glutton_.asItem().getDescriptionId()),
                hunger, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }
    public static void  attrib(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (player!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.Glutton_)) {
                        if (player.tickCount % 200 == 1) {
                            if (!player.getCooldowns().isOnCooldown(stack)) {
                                player.getFoodData().eat(-1, 0.5f);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.glutton.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.translatable("item.chest_item.glutton.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.accept(Component.translatable("item.chest_item.glutton.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.accept(Component.translatable("item.chest_item.glutton.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.glutton.string.5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.translatable("item.chest_item.glutton.string.6").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
        tooltipAdder.accept(Component.translatable("item.chest_item.glutton.string.7").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
        tooltipAdder.accept(Component.translatable("item.chest_item.glutton.string.8").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
    }
    @Override
    public Identifier Identifier() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/glutton.png");
    }

    @Override
    public int soulColor() {
        return Light.ARGB.color(255,200 ,200 ,60);
    }
}
