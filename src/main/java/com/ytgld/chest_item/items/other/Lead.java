package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.function.Consumer;

public class Lead  extends ItemBase {
    public Lead(Properties properties) {
        super(properties);
    }
    public static final String lead = "leadNumber";

    public static void event(CriticalHitEvent event){
        Player player = event.getEntity();
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.Lead_)) {
                        CompoundTag compoundTag = stack.get(DataReg.tag);
                        if (compoundTag!=null){
                            if (compoundTag.getInt(lead).isPresent()) {

                                compoundTag.putInt(lead, compoundTag.getInt(lead).get() + 1);
                                if (compoundTag.getInt(lead).get()>=3){
                                    event.setDisableSweep(false);
                                    event.setCriticalHit(true);
                                    compoundTag.putInt(lead, 0);
                                }
                            }else {
                                compoundTag.putInt(lead,0);
                            }


                        }else {
                            stack.set(DataReg.tag, new CompoundTag());
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.lead.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.literal(""));
        tooltipAdder.accept(Component.translatable("item.chest_item.lead.string.1").withStyle(ChatFormatting.GOLD));

        }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,0,0,255);
    }
}

