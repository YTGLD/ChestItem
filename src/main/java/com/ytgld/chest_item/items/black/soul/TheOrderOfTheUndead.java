package com.ytgld.chest_item.items.black.soul;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import oshi.driver.mac.net.NetStat;

import java.util.function.Consumer;

/**
 * 亡者号令
 * <p>
 * “种下奴印，你将不再是人类，而是我们亡灵一脉最好的伙伴......”
 * <p>
 * <p>
 * 免疫魔法伤害
 * <p>
 * 你所造成的非魔法伤害增加30%
 * <p>
 * 免疫所有的负面效果
 * <p>
 * <p>
 * “但是你要注意。你现在是一个彻头彻尾的亡灵，你应该知道你会怎么样......”
 * <p>
 * <p>
 *
 *
 *
 */
public class TheOrderOfTheUndead extends TheImprintOfTheSoul {
    public TheOrderOfTheUndead(Properties properties) {
        super(properties);
    }

    public static void notMagicDamage(LivingIncomingDamageEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.TheOrderOfTheUndead_)) {
                            if (!event.getSource().is(DamageTypes.MAGIC)) {
                                event.setAmount(event.getAmount() * 1.3f);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void  immMagic(Player player, DamageSource damageSource , CallbackInfoReturnable<Boolean> cir){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.TheOrderOfTheUndead_)) {
                        if (damageSource.is(DamageTypes.MAGIC)) {
                            cir.setReturnValue(true);
                            break;
                        }
                    }
                }
            }
        }
    }
    public static void  canHasEffect(Player player , MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.TheOrderOfTheUndead_)) {
                        if (!effectInstance.getEffect().value().isBeneficial()) {
                            cir.setReturnValue(false);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void LivingChangeTargetEvent(LivingChangeTargetEvent event) {
        if (event.getNewAboutToBeSetTarget() instanceof Player player) {
            if (event.getEntity().getType().is(EntityTypeTags.UNDEAD)) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    if (!player.level().isClientSide()) {
                        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                            ItemStack stack = chestInventory.getItem(i);
                            if (stack.is(InitItems.TheOrderOfTheUndead_)) {
                                if (player.getLastHurtMob()!=null) {
                                    if (player.getLastHurtMob().is(event.getEntity())) {
                                        return;
                                    }
                                }
                                event.setCanceled(true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void  healAndMagicDamage(Player player, CallbackInfoReturnable<Boolean> cir){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.TheOrderOfTheUndead_)) {
                        cir.setReturnValue(false);
                        break;
                    }
                }
            }
        }
    }
    public static void  hunger(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.TheOrderOfTheUndead_)) {
                        if (player.getRemainingFireTicks() > 0) {
                            player.setRemainingFireTicks(100);
                        }
                        if (player.tickCount % 400 == 1) {
                            player.getFoodData().eat(-1, -0.5f);
                        }
                        break;
                    }
                }
            }
        }
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        return attributeModifierMultimap(player);
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        float a = 20 - player.getFoodData().getFoodLevel();

        float speed = 0;
        if (a > 5) {
            speed = -0.2f;
        }

        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.TheOrderOfTheUndead_.asItem().getDescriptionId()),
                speed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }


    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return attributeModifierMultimap(player);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.the_order_of_the_undead.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_order_of_the_undead.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_order_of_the_undead.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_order_of_the_undead.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_order_of_the_undead.string.9").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_order_of_the_undead.string.5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_order_of_the_undead.string.6").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_order_of_the_undead.string.7").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_order_of_the_undead.string.8").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
    }

    @Override
    public Identifier Identifier() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/the_order_of_the_undead.png");
    }

    @Override
    public int soulColor() {
        return 0XFF6A5ACD;
    }
}
