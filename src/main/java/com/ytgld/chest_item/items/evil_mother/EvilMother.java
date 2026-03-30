package com.ytgld.chest_item.items.evil_mother;

import com.mojang.blaze3d.platform.GlStateManager;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EvilMother extends ItemBase implements IBlackLight {
    public EvilMother(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorBlack().color())));
        return co;
    }
    @Override
    public DoBlack colorBlack() {
        return new DoBlack(50,80,120,105,
                new CIStateShardsHasBlack.CIFunc(
                        GlStateManager.SourceFactor.SRC_ALPHA,
                        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                        GlStateManager.SourceFactor.ONE,
                        GlStateManager.DestFactor.ZERO
                )
        );
    }
    @Override
    public ResourceLocation blackStar() {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                "textures/evil_mother/cube.png");
    }
    @Override
    public ResourceLocation blackFire() {
        return ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,
                "textures/evil_mother/cube.png");
    }
}
