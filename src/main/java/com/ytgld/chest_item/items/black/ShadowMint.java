package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShadowMint extends ItemBlackShadow {


    public ShadowMint(Properties properties) {
        super(properties);
    }


    public static void hurtAttacker (LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.ShadowMint_)) {

                            if (event.getSource().getEntity() instanceof LivingEntity living) {
                                float shadow_shield = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);

                                if (shadow_shield > 0) {
                                    living.hurt(living.damageSources().magic(),10);
                                    if (living.isDeadOrDying()){
                                        player.setData(AttReg.shadow_shield_ATTACHMENT_TYPES, shadow_shield + 4);
                                        player.heal(4);
                                    }
                                    player.setData(AttReg.shadow_shield_ATTACHMENT_TYPES, shadow_shield - 1);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.shadow_shield, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.ShadowMint_.asItem().getDescriptionId()+"aaaaaa"),
                10, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.shadow_shield, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.ShadowMint_.asItem().getDescriptionId()),
                0.4, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(AttReg.shadow_shield_speed, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.ShadowMint_.asItem().getDescriptionId()),
                0.7, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }


    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.chest_item.shadow_mint.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.add(Component.translatable("item.chest_item.shadow_mint.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }
}

