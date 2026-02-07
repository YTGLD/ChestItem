package com.ytgld.chest_item.items.condensebone;

import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.IGUILightList;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ItemBone extends ItemBase implements IGUILightList {
    public ItemBone(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        CompoundTag tag = stack.get(DataReg.tag);
        int c =  0XFF4DFF80;
        if (tag != null) {
            if (tag.getBooleanOr(IBlackLight.blackName, false)){
                c = 0xffff0000;
                co = Component.translatable("chest_item.attribute").append(component.copy());
            }
        }
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(c)));
        return co;
    }


    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255, 50, 255, 50);
    }

    @Override
    public GUILight guiLight(LivingEntity entity) {
        return null;
    }
}
