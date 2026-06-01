package com.ytgld.chest_item.renderer.book;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public class BookPageAll {
    @AddBookPage
    public static class Self_Increasing_Heart implements RegisterBookPage {
        @Override
        public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
            list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Self_Increasing_Heart.asItem(),new Vec2(32,-64),
                    Component.translatable("chest_item.book.self_increasing_heart.main"),
                    List.of(
                            Component.translatable("chest_item.book.self_increasing_heart.1"),
                            Component.translatable("chest_item.book.self_increasing_heart.2"),
                            Component.translatable("chest_item.book.self_increasing_heart.3")
                    ),
                    Light.ARGB.color(255,255,255,255),
                    Light.ARGB.color(255,150,150,150),
                    CIBookScreen.ThePage.MEAT));
        }
    }
    @AddBookPage
    public static class Bone_Head implements RegisterBookPage {
        @Override
        public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
            list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Bone_Head.asItem(),new Vec2(0,32),
                    Component.translatable("chest_item.book.bone_head.main"),
                    List.of(
                            Component.translatable("chest_item.book.bone_head.1"),
                            Component.translatable("chest_item.book.bone_head.2"),
                            Component.translatable("chest_item.book.bone_head.3"),
                            Component.translatable("chest_item.book.bone_head.4")
                    ),
                    Light.ARGB.color(255,255,255,255),
                    Light.ARGB.color(255,150,150,150),
                    CIBookScreen.ThePage.BASE));
        }
    }
    @AddBookPage
    public static class Heart_ implements RegisterBookPage {
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
}
