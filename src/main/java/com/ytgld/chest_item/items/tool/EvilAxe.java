package com.ytgld.chest_item.items.tool;

import com.ytgld.chest_item.items.evil_mother.IEvil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

public class EvilAxe extends AxeItem implements IEvil {
    public EvilAxe(Properties properties) {
        super(new ToolMaterial(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,8000,
                11,
                5,
                30,
                ItemTags.AXES),11,-3.55f,properties);
    }

    @Override
    public Component getName(ItemStack itemStack) {
        Component component = super.getName(itemStack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(color)));
        return co;
    }
}
