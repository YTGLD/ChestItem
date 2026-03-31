package com.ytgld.chest_item.items.evil_mother;

import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.memory.MemoryAttreg;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * 腐毒束具
 * <p>
 * 获得绝对的击退抗性
 * <p>
 * 并使你的肉身几乎无法推动
 */
public class EvilBelt extends EvilMother{
    public EvilBelt(Properties properties) {
        super(properties);
    }
    public static void  knock(LivingKnockBackEvent event){
        if (event .getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.EvilBelt_.asItem())) {
                event.setRatioX(0);
                event.setStrength(0);
                event.setRatioZ(0);

                event.setCanceled(true);
            }
        }
    }

    public static void  push(Entity entity , CallbackInfo cir){
        if (entity instanceof Player player && !player.level().isClientSide) {
            if (Handler.has(player, InitItems.EvilBelt_.asItem())) {
                cir.cancel();
            }
        }
    }
    public static void  canBeCollidedWith(Entity entity , CallbackInfoReturnable<Boolean> cir){
        if (entity instanceof Player player && !player.level().isClientSide) {
            if (Handler.has(player, InitItems.EvilBelt_.asItem())) {
                cir.setReturnValue(true);
            }
        }
    }
    @Override
    public void text(ItemStack stack, List<Component> tooltipAdder, TooltipFlag flag) {
        super.text(stack, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.evil_belt.string.0", TheKill.ConfigItem.intValue.getAsInt(), TheKill.ConfigItem.intValue2.getAsInt()).withStyle(Style.EMPTY.withColor(colorBlack().color())));
        tooltipAdder.add(Component.translatable("item.chest_item.evil_belt.string.1").withStyle(Style.EMPTY.withColor(colorBlack().color())));

    }

//    @Override
//    public Multimap<Holder<Attribute>, AttributeModifier> multimapAttribute(ItemStack stack, Player player) {
//        Multimap<Holder<Attribute> , AttributeModifier> attributeModifierMultimap = super.multimapAttribute(stack, player);
//        attributeModifierMultimap.put(MemoryAttreg.maxMemory, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
//                this.asItem().getDescriptionId()),
//                1, AttributeModifier.Operation.ADD_VALUE));
//        return attributeModifierMultimap;
//    }

    @Override
    public int getSanity() {
        return -3;
    }
}
