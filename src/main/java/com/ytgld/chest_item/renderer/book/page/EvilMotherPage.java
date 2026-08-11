package com.ytgld.chest_item.renderer.book.page;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.items.evil_mother.EvilMother;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import com.ytgld.chest_item.renderer.book.CIBookScreen;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.List;

@AddBookPage
public class EvilMotherPage implements RegisterBookPage {


    private final int aInt = 24;
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
        {
            if (InitItems.MotherRemains_.asItem() instanceof EvilMother evilMother) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MotherRemains_.asItem(), new Vec2(aInt,0),
                        Component.translatable("chest_item.book.mother_remains.main"),
                        List.of(
                                Component.translatable("chest_item.book.mother_remains.1"),
                                Component.translatable("chest_item.book.mother_remains.2")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));
            }
        }{
            if (InitItems.EvilBelt_.asItem() instanceof EvilMother evilMother) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.EvilBelt_.asItem(), new Vec2(aInt*2,0),
                        Component.translatable("chest_item.book.evil_belt.main"),
                        List.of(
                                Component.translatable("chest_item.book.evil_belt.1"),
                                Component.translatable("chest_item.book.evil_belt.2")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80,120,105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));
            }
        }{
            if (InitItems.TheKill_.asItem() instanceof EvilMother evilMother) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.TheKill_.asItem(), new Vec2(aInt*3, 0),
                        Component.translatable("chest_item.book.the_kill.main"),
                        List.of(
                                Component.translatable("chest_item.book.the_kill.1"),
                                Component.translatable("chest_item.book.the_kill.2"),
                                Component.translatable("chest_item.book.the_kill.3")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80, 120, 105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));
            }
        }{
            if (InitItems.AnnualPlate_.asItem() instanceof EvilMother evilMother) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.AnnualPlate_.asItem(), new Vec2(aInt*4, 0),
                        Component.translatable("chest_item.book.annual_plate.main"),
                        List.of(
                                Component.translatable("chest_item.book.annual_plate.1"),
                                Component.translatable("chest_item.book.annual_plate.2")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80, 120, 105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));
            }
        }{
            if (ReinforcedItems.SilentDevice_.asItem() instanceof EvilMother evilMother) {
                list.add(new CIBookScreen.CIBookGuiAdd(ReinforcedItems.SilentDevice_.asItem(), new Vec2(aInt*5, 0),
                        Component.translatable("chest_item.book.silent_device.main"),
                        List.of(
                                Component.translatable("chest_item.book.silent_device.1"),
                                Component.translatable("chest_item.book.silent_device.2")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80, 120, 105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        evilMother.theColor()));
            }
        }{
            if (ReinforcedItems.Excite_.asItem() instanceof ReinforcedBaseItem reinforcedBaseItem) {
                list.add(new CIBookScreen.CIBookGuiAdd(ReinforcedItems.Excite_.asItem(), new Vec2(aInt*6, aInt),
                        Component.translatable("chest_item.book.excite.main"),
                        List.of(
                                Component.translatable("chest_item.book.excite.1"),
                                Component.translatable("chest_item.book.excite.2")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80, 120, 105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        reinforcedBaseItem.theColor()));
            }
        }{
            if (ReinforcedItems.Strengthen_.asItem() instanceof ReinforcedBaseItem reinforcedBaseItem) {
                list.add(new CIBookScreen.CIBookGuiAdd(ReinforcedItems.Strengthen_.asItem(), new Vec2(aInt*6, -aInt),
                        Component.translatable("chest_item.book.strengthen.main"),
                        List.of(
                                Component.translatable("chest_item.book.strengthen.1"),
                                Component.translatable("chest_item.book.strengthen.2")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80, 120, 105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        reinforcedBaseItem.theColor()));
            }
        }{
            if (ReinforcedItems.Contingency_.asItem() instanceof ReinforcedBaseItem reinforcedBaseItem) {
                list.add(new CIBookScreen.CIBookGuiAdd(ReinforcedItems.Contingency_.asItem(), new Vec2(aInt*7, aInt),
                        Component.translatable("chest_item.book.contingency.main"),
                        List.of(
                                Component.translatable("chest_item.book.contingency.1"),
                                Component.translatable("chest_item.book.contingency.2")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80, 120, 105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        reinforcedBaseItem.theColor()));
            }
        }{
            if (ReinforcedItems.Dynamic_.asItem() instanceof ReinforcedBaseItem reinforcedBaseItem) {
                list.add(new CIBookScreen.CIBookGuiAdd(ReinforcedItems.Dynamic_.asItem(), new Vec2(aInt*7, -aInt),
                        Component.translatable("chest_item.book.dynamic.main"),
                        List.of(
                                Component.translatable("chest_item.book.dynamic.1"),
                                Component.translatable("chest_item.book.dynamic.2")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80, 120, 105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        reinforcedBaseItem.theColor()));
            }
        }{
            if (ReinforcedItems.Activity_.asItem() instanceof ReinforcedBaseItem reinforcedBaseItem) {
                list.add(new CIBookScreen.CIBookGuiAdd(ReinforcedItems.Activity_.asItem(), new Vec2(aInt*8, 0),
                        Component.translatable("chest_item.book.activity.main"),
                        List.of(
                                Component.translatable("chest_item.book.activity.1"),
                                Component.translatable("chest_item.book.activity.2")
                        ),
                        Light.ARGB.color(255, 160, 240, 210),
                        Light.ARGB.color(255, 80, 120, 105),
                        CIBookScreen.ThePage.EVILMOTHER,
                        reinforcedBaseItem.theColor()));
            }
        }
    }
}
