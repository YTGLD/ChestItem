package com.ytgld.chest_item.items.tool;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.black.ITheChaos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.function.Consumer;

/**
 *沉沦战斧
 * <p>
 * 2倍暴击伤害，非暴击伤害较小
 * */
public class WallowAxe extends AxeItem implements IBlackLight , ITheChaos {
    public WallowAxe(Properties properties) {
        super(new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,5000,
                9,
                4,
                9,
                        ItemTags.AXES),10,-3.5f,properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD)));
        return co;
    }
    public static void damage(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity().getMainHandItem().is(InitItems.WallowAxe_.get())) {
                if (Handler.has(player, InitItems.DryBones_.asItem())) {
                    float vValue = (float) player.getAttributeValue(AttReg.shadow_shield);
                    float attachment = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);

                    float a = attachment / vValue;

                    if (a > 0.25f) {
                        event.setNewDamage(event.getNewDamage() * 1.5f);

                        float c = attachment - vValue * 0.25f;
                        if (c < 0) {
                            c = 0;
                        }
                        Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES,player, c);
                    }
                }
            }
        }
    }
    public static void cit(CriticalHitEvent event) {
        if (event.isCriticalHit()) {
            if (event.getEntity().getMainHandItem().is(InitItems.WallowAxe_.get())) {
                event.setDamageMultiplier(event.getDamageMultiplier() * 2);
            }
        } else {
            if (event.getEntity().getMainHandItem().is(InitItems.WallowAxe_.get())) {
                event.setDamageMultiplier(event.getDamageMultiplier() * 0.25f);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        builder.accept(Component.translatable("item.chest_item.wallow_axe.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        builder.accept(Component.literal(""));
        builder.accept(Component.translatable("item.chest_item.wallow_axe.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        builder.accept(Component.literal(""));
        builder.accept(Component.translatable("item.chest_item.wallow_axe.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        builder.accept(Component.translatable("item.chest_item.wallow_axe.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return TextColor.fromRgb(0X806A5ACD).getValue();
    }
}
