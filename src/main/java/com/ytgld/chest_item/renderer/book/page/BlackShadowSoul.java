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
public class BlackShadowSoul implements RegisterBookPage {
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
        {
            if (InitItems.Glutton_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Glutton_.asItem(), new Vec2(-112,0),
                        Component.translatable("chest_item.book.glutton.main"),
                        List.of(
                                Component.translatable("chest_item.book.glutton.1"),
                                Component.translatable("chest_item.book.glutton.2"),
                                Component.translatable("chest_item.book.glutton.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.Glutton_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.MadnessTheory_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MadnessTheory_.asItem(), new Vec2(-152,56),
                        Component.translatable("chest_item.book.madness_theory.main"),
                        List.of(
                                Component.translatable("chest_item.book.madness_theory.1"),
                                Component.translatable("chest_item.book.madness_theory.2"),
                                Component.translatable("chest_item.book.madness_theory.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.MadnessTheory_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Mutation_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Mutation_.asItem(), new Vec2(-152,-56),
                        Component.translatable("chest_item.book.mutation.main"),
                        List.of(
                                Component.translatable("chest_item.book.mutation.1"),
                                Component.translatable("chest_item.book.mutation.2"),
                                Component.translatable("chest_item.book.mutation.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.Mutation_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Silent_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Silent_.asItem(), new Vec2(-200,56),
                        Component.translatable("chest_item.book.silent.main"),
                        List.of(
                                Component.translatable("chest_item.book.silent.1"),
                                Component.translatable("chest_item.book.silent.2"),
                                Component.translatable("chest_item.book.silent.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.Silent_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Speed_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Speed_.asItem(), new Vec2(-200,-56),
                        Component.translatable("chest_item.book.speed.main"),
                        List.of(
                                Component.translatable("chest_item.book.speed.1"),
                                Component.translatable("chest_item.book.speed.2"),
                                Component.translatable("chest_item.book.speed.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.Speed_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.TheOrderOfTheUndead_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.TheOrderOfTheUndead_.asItem(), new Vec2(-240,0),
                        Component.translatable("chest_item.book.the_order_of_the_undead.main"),
                        List.of(
                                Component.translatable("chest_item.book.the_order_of_the_undead.1"),
                                Component.translatable("chest_item.book.the_order_of_the_undead.2"),
                                Component.translatable("chest_item.book.the_order_of_the_undead.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.TheOrderOfTheUndead_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.ChaosSeven_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.ChaosSeven_.asItem(), new Vec2(-176, 0),
                        Component.translatable("chest_item.book.chaos_seven.main"),
                        List.of(
                                Component.translatable("chest_item.chaos_seven.soul.1"),
                                Component.translatable("chest_item.chaos_seven.soul.2"),
                                Component.translatable("chest_item.chaos_seven.soul.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.ChaosSeven_.asItem().getDefaultInstance())));
            }
        }
    }
}
