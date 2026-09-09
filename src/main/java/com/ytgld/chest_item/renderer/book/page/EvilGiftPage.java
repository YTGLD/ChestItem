package com.ytgld.chest_item.renderer.book.page;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.renderer.book.CIBookScreen;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.List;

@AddBookPage
public class EvilGiftPage implements RegisterBookPage {

    private final int aInt = 24;
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
        {
            if (InitItems.MotherRemains_.asItem() instanceof EvilMother evilMother) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MotherRemains_.asItem(), new Vec2(aInt * 3, aInt * 2),
                        Component.translatable("chest_item.book.rotten_utensils.main"),
                        List.of(
                                Component.translatable("chest_item.book.rotten_utensils.1"),
                                Component.translatable("chest_item.book.rotten_utensils.2"),
                                Component.translatable("chest_item.book.rotten_utensils.3"),
                                Component.translatable("chest_item.book.rotten_utensils.4")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MotherRemains_.asItem(), new Vec2(aInt * 4, aInt * 2),
                        Component.translatable("chest_item.book.dawn.main"),
                        List.of(
                                Component.translatable("chest_item.book.dawn.1"),
                                Component.translatable("chest_item.book.dawn.2"),
                                Component.translatable("chest_item.book.dawn.3"),
                                Component.translatable("chest_item.book.dawn.4")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));

                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MotherRemains_.asItem(), new Vec2(aInt * 3, aInt * 3),
                        Component.translatable("chest_item.book.synthesizer.main"),
                        List.of(
                                Component.translatable("chest_item.book.synthesizer.1"),
                                Component.translatable("chest_item.book.synthesizer.2"),
                                Component.translatable("chest_item.book.synthesizer.3"),
                                Component.translatable("chest_item.book.synthesizer.4")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));

                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MotherRemains_.asItem(), new Vec2(aInt * 4, aInt * 3),
                        Component.translatable("chest_item.book.snap_string.main"),
                        List.of(
                                Component.translatable("chest_item.book.snap_string.1"),
                                Component.translatable("chest_item.book.snap_string.2"),
                                Component.translatable("chest_item.book.snap_string.3"),
                                Component.translatable("chest_item.book.snap_string.4")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));


                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MotherRemains_.asItem(), new Vec2(aInt * 5, aInt * 4),
                        Component.translatable("chest_item.book.evil_stomach.main"),
                        List.of(
                                Component.translatable("chest_item.book.evil_stomach.1"),
                                Component.translatable("chest_item.book.evil_stomach.2"),
                                Component.translatable("chest_item.book.evil_stomach.3"),
                                Component.translatable("chest_item.book.evil_stomach.4")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));


                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MotherRemains_.asItem(), new Vec2(aInt * 5, aInt * 3),
                        Component.translatable("chest_item.book.unclean_coins.main"),
                        List.of(
                                Component.translatable("chest_item.book.unclean_coins.1"),
                                Component.translatable("chest_item.book.unclean_coins.2"),
                                Component.translatable("chest_item.book.unclean_coins.3"),
                                Component.translatable("chest_item.book.unclean_coins.4")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));



                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MotherRemains_.asItem(), new Vec2(aInt * 6, aInt * 3),
                        Component.translatable("chest_item.book.factory.main"),
                        List.of(
                                Component.translatable("chest_item.book.factory.1"),
                                Component.translatable("chest_item.book.factory.2"),
                                Component.translatable("chest_item.book.factory.3"),
                                Component.translatable("chest_item.book.factory.4")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));


                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MotherRemains_.asItem(), new Vec2(aInt * 6, aInt * 4),
                        Component.translatable("chest_item.book.fortress_cone.main"),
                        List.of(
                                Component.translatable("chest_item.book.fortress_cone.1"),
                                Component.translatable("chest_item.book.fortress_cone.2"),
                                Component.translatable("chest_item.book.fortress_cone.3"),
                                Component.translatable("chest_item.book.fortress_cone.4")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));
            }
        }
    }
}
