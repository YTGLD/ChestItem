package com.ytgld.chest_item.items.gold;

import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class EyeBook  extends ItemBase implements IGold {
    public EyeBook(Properties properties) {
        super(properties);
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
            ItemStack stack = chestInventory.getItem(i);
            if (stack.is(InitItems.EyeBook_)) {
                if (player.tickCount % 80 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0, false, false), player);
                    break;
                }
            }
        }
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.eye_book.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.eye_book.string.1").withStyle(ChatFormatting.GOLD));
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,255,20);
    }
}
