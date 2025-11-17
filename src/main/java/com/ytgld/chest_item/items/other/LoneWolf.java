package com.ytgld.chest_item.items.other;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ILight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 孤狼
 * <p>
 * 附近没有生物时速度提高15%
 */
public class LoneWolf extends ItemBase {
    public LoneWolf(Properties properties) {
        super(properties);
    }
    public static void ItemStackTickEvent(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (player!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.LoneWolf_)) {
                        player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap(player));
                        break;
                    } else {
                        player.getAttributes().removeAttributeModifiers(attributeModifierMultimap(player));
                    }
                }
            }
        }
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float apply = 0;
        if (isNOt(player)){
            apply = 0.15f;
        }

        modifiers.put(AttReg.more_speed, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.LoneWolf_.asItem().getDescriptionId()),
                apply, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }

    public static boolean isNOt(LivingEntity me){
        Vec3 playerPos = me.position();
        int range = 4;
        List<Integer> integers = new ArrayList<>();
        List<LivingEntity> entities = me.level().getEntitiesOfClass(LivingEntity.class, new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range, playerPos.x + range, playerPos.y + range, playerPos.z + range));
        for (LivingEntity living : entities) {
            if (!living.is(me)) {
                integers.add(1);
            }
        }
        return integers.isEmpty();
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return attributeModifierMultimap(player);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.lone_wolf.string.1").withStyle(ChatFormatting.GOLD));
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255, 255, 100, 50);
    }

}
