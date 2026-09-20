package com.ytgld.chest_item.renderer.book.page.in2;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.book.CIBookScreen;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.List;

@AddBookPage
public class ChaosPage implements RegisterBookPage {
    private final int aInt = 24;
    private final int color = 0x00000000;
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list){
        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Warmaker_.asItem(), new Vec2(0, 0),
                Component.translatable("chest_item.book.warmaker.main"),
                List.of(
                        Component.translatable("chest_item.book.warmaker.1"),
                        Component.translatable("chest_item.book.warmaker.2"),
                        Component.translatable("chest_item.book.warmaker.3"),
                        Component.translatable("chest_item.book.warmaker.4"),
                        Component.translatable("chest_item.book.warmaker.5")
                ),
                Light.ARGB.color(255, 100, 40, 255),
                Light.ARGB.color(255, 100, 40, 255),
                CIBookScreen.ThePage.BLACK,
                color,List.of(
                        Component.translatable("chest_item.book.warmaker.other")
        ),true));
    }
}
