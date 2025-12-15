package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class DefeatTheArmy extends ItemBlackShadow  {
    public DefeatTheArmy(Properties properties) {
        super(properties);
    }

    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.DefeatTheArmy_)) {
                    player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap(player));
                    break;
                } else {
                    player.getAttributes().removeAttributeModifiers(attributeModifierMultimap(player));
                }
            }
        }
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        float lv = player.getHealth() / player.getMaxHealth();
        lv *= 100;
        float now = (int) (100 - (lv));
        if (now < 0) {
            now = 0;
        }
        now /= 100f;

        float speed = 1.0f * now;
        float damage = 0.5f * now;
        float attSpeed = 0.8f * now;

        float chaosArmorSpeed = 0.8f * now;
        chaosArmorSpeed = -chaosArmorSpeed;


        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                speed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                damage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                attSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(AttReg.chaos_armor_speed, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                chaosArmorSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(AttReg.chaos_armor, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                8, AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.defeat_the_army.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return attributeModifierMultimap(player);
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,185,50,158);
    }

}
