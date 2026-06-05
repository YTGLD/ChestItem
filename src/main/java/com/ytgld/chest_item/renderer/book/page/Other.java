package com.ytgld.chest_item.renderer.book.page;

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
public class Other implements RegisterBookPage {
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
        {
            if (InitItems.Bone_Head.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Bone_Head.asItem(), new Vec2(0, 32),
                        Component.translatable("chest_item.book.bone_head.main"),
                        List.of(
                                Component.translatable("chest_item.book.bone_head.1"),
                                Component.translatable("chest_item.book.bone_head.2"),
                                Component.translatable("chest_item.book.bone_head.3"),
                                Component.translatable("chest_item.book.bone_head.4")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Bone_Head.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Life_Stone.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Life_Stone.asItem(), new Vec2(24, 64),
                        Component.translatable("chest_item.book.life_stone.main"),
                        List.of(
                                Component.translatable("chest_item.book.life_stone.1"),
                                Component.translatable("chest_item.book.life_stone.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Life_Stone.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Armor_Stone.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Armor_Stone.asItem(), new Vec2(0, 64),
                        Component.translatable("chest_item.book.armor_stone.main"),
                        List.of(
                                Component.translatable("chest_item.book.armor_stone.1"),
                                Component.translatable("chest_item.book.armor_stone.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Armor_Stone.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Stronger_Stone.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Stronger_Stone.asItem(), new Vec2(-24, 64),
                        Component.translatable("chest_item.book.stronger_stone.main"),
                        List.of(
                                Component.translatable("chest_item.book.stronger_stone.1"),
                                Component.translatable("chest_item.book.stronger_stone.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Stronger_Stone.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Undead_Rune.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Undead_Rune.asItem(), new Vec2(24, 96),
                        Component.translatable("chest_item.book.undead_rune.main"),
                        List.of(
                                Component.translatable("chest_item.book.undead_rune.1"),
                                Component.translatable("chest_item.book.undead_rune.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Undead_Rune.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Separate_Rune.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Separate_Rune.asItem(), new Vec2(0, 96),
                        Component.translatable("chest_item.book.separate_rune.main"),
                        List.of(
                                Component.translatable("chest_item.book.separate_rune.1"),
                                Component.translatable("chest_item.book.separate_rune.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Separate_Rune.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Pain_Rune.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Pain_Rune.asItem(), new Vec2(-24, 96),
                        Component.translatable("chest_item.book.pain_rune.main"),
                        List.of(
                                Component.translatable("chest_item.book.pain_rune.1"),
                                Component.translatable("chest_item.book.pain_rune.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Pain_Rune.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Ring_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Ring_.asItem(), new Vec2(-32, 132),
                        Component.translatable("chest_item.book.ring.main"),
                        List.of(
                                Component.translatable("chest_item.book.ring.1"),
                                Component.translatable("chest_item.book.ring.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Ring_.asItem().getDefaultInstance())));
            }
        }
        {
            if (InitItems.Knife_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Knife_.asItem(), new Vec2(32, 132),
                        Component.translatable("chest_item.book.knife.main"),
                        List.of(
                                Component.translatable("chest_item.book.knife.1"),
                                Component.translatable("chest_item.book.knife.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Knife_.asItem().getDefaultInstance())));
            }
        }
    }
}
