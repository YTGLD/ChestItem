package com.ytgld.chest_item.items.meet;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.Meat;
import com.ytgld.chest_item.renderer.book.CIBookScreen;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public class Heart  extends ItemBase implements Meat {
    @AddBookPage
    public static class AddPageClass implements RegisterBookPage {
        @Override
        public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
            list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Heart_.asItem(),new Vec2(0,-32),
                    Component.translatable("chest_item.book.heart.main"),
                    List.of(
                            Component.translatable("chest_item.book.heart.1"),
                            Component.translatable("chest_item.book.heart.2"),
                            Component.translatable("chest_item.book.heart.3")
                    ),
                    Light.ARGB.color(255,255,255,255),
                    Light.ARGB.color(255,150,150,150),
                    CIBookScreen.ThePage.MEAT));
        }
    }
    public Heart(Properties properties) {
        super(properties);
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.heart.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.heart.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,135,105);
    }
}
