package com.ytgld.chest_item.items;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class TheImprintOfTheSoul extends ItemBlackShadow{
    public TheImprintOfTheSoul(Properties properties) {
        super(properties);
    }
    public boolean canRemove(ItemStack stack){
        return false;
    }
    public abstract ResourceLocation resourceLocation();

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        MutableComponent soul = Component
                .translatable("chest_item.the_imprint_of_the_soul");
        return soul.append(Component.literal("["))
                .append(co)
                .append(Component.literal("]"));
    }




    public abstract int soulColor ();



}
