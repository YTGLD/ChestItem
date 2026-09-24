package com.ytgld.chest_item.renderer.book.page.in2;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.black.celestial.CommonCelestial;
import com.ytgld.chest_item.renderer.book.CIBookScreen;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;

import java.util.List;

@AddBookPage
public class ChaosPage implements RegisterBookPage {
    private final int aInt = 24;
    private final int color = Light.ARGB.color(255,160, 80, 255);
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list){
        addCelestialText(list);
        addChaosText(list);
    }
    public static int theCColor(Item item){
        if (item instanceof CommonCelestial celestial) {
            return celestial.color(item.getDefaultInstance());
        }
        return 0;
    }
    private void addChaosText(List<CIBookScreen.CIBookGuiAdd> list) {
        addCelestialText(list);
        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Warmaker_.asItem(), new Vec2(0, 0),
                Component.translatable("chest_item.book.warmaker.main"),
                List.of(
                        Component.translatable("chest_item.book.warmaker.1"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.warmaker.2"),
                        Component.translatable("chest_item.book.warmaker.3"),
                        Component.translatable("chest_item.book.warmaker.4"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.warmaker.5"),
                        Component.translatable("chest_item.book.warmaker.6")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                color,List.of(),true,true));

        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.ChaosFortress_.asItem(), new Vec2(aInt, 0),
                Component.translatable("chest_item.book.chaos_fortress.main"),
                List.of(
                        Component.translatable("chest_item.book.chaos_fortress.1"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.chaos_fortress.2"),
                        Component.translatable("chest_item.book.chaos_fortress.3"),
                        Component.translatable("chest_item.book.chaos_fortress.4"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.chaos_fortress.5"),
                        Component.translatable("chest_item.book.chaos_fortress.6"),
                        Component.translatable("chest_item.book.chaos_fortress.7"),
                        Component.translatable("chest_item.book.chaos_fortress.8")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                color,List.of(),true,true));

        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.EmperorCup_.asItem(), new Vec2(aInt * 2, 0),
                Component.translatable("chest_item.book.emperor_cup.main"),
                List.of(
                        Component.translatable("chest_item.book.emperor_cup.text")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                color,List.of(),true,true));

        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.GreatToken_.asItem(), new Vec2(aInt * 3, 0),
                Component.translatable("chest_item.book.great_token.main"),
                List.of(
                        Component.translatable("chest_item.book.great_token.text")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                color,List.of(),true,true));

        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.ThreeRealms_.asItem(), new Vec2(aInt * 4, 0),
                Component.translatable("chest_item.book.three_realms.main"),
                List.of(
                        Component.translatable("chest_item.book.three_realms.text")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                color,List.of(),true,true));


        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.LeadOfEnlightenment_.asItem(), new Vec2(aInt * 3, -aInt * 2),
                Component.translatable("chest_item.book.lead_of_enlightenment.main"),
                List.of(
                        Component.translatable("chest_item.book.lead_of_enlightenment.1"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.lead_of_enlightenment.2"),
                        Component.translatable("chest_item.book.lead_of_enlightenment.3"),
                        Component.translatable("chest_item.book.lead_of_enlightenment.4"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.lead_of_enlightenment.5"),
                        Component.translatable("chest_item.book.lead_of_enlightenment.6"),
                        Component.translatable("chest_item.book.lead_of_enlightenment.7"),
                        Component.translatable("chest_item.book.lead_of_enlightenment.8")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                color,List.of(),true,true));



    }


    private void addCelestialText(List<CIBookScreen.CIBookGuiAdd> list){
        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Blood_.asItem(), new Vec2(0 - aInt, aInt),
                Component.translatable("chest_item.book.blood.main"),
                List.of(
                        Component.translatable("chest_item.book.blood.1"),
                        Component.translatable("chest_item.book.blood.2"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.blood.3"),
                        Component.translatable("chest_item.book.blood.4"),
                        Component.translatable("chest_item.book.blood.5"),
                        Component.translatable("chest_item.book.blood.6"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.blood.7"),
                        Component.translatable("chest_item.book.blood.8")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                theCColor(InitItems.Blood_.asItem()),List.of(),true,true));


        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Chaos_.asItem(), new Vec2(-aInt - aInt, aInt * 2),
                Component.translatable("chest_item.book.chaos.main"),
                List.of(
                        Component.translatable("chest_item.book.chaos.1"),
                        Component.translatable("chest_item.book.chaos.2"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.chaos.3"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.chaos.4"),
                        Component.translatable("chest_item.book.chaos.5")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                theCColor(InitItems.Chaos_.asItem()),List.of(),true,true));


        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.NineDome_.asItem(), new Vec2(-aInt * 2 - aInt, aInt * 3),
                Component.translatable("chest_item.book.nine_dome.main"),
                List.of(
                        Component.translatable("chest_item.book.nine_dome.1"),
                        Component.translatable("chest_item.book.nine_dome.2"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.nine_dome.3"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.nine_dome.4"),
                        Component.translatable("chest_item.book.nine_dome.5")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                theCColor(InitItems.NineDome_.asItem()),List.of(),true,true));


        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Sword_.asItem(), new Vec2(aInt * 2 - aInt, aInt * 4),
                Component.translatable("chest_item.book.sword.main"),
                List.of(
                        Component.translatable("chest_item.book.sword.1"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.sword.2"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.sword.3"),
                        Component.translatable("chest_item.book.sword.4")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                theCColor(InitItems.Sword_.asItem()),List.of(),true,true));



        list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Samsara_.asItem(), new Vec2(aInt - aInt, aInt * 5),
                Component.translatable("chest_item.book.samsara.main"),
                List.of(
                        Component.translatable("chest_item.book.samsara.1"),
                        Component.translatable("chest_item.book.samsara.2"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.samsara.3"),
                        Component.translatable("chest_item.book.samsara.4"),
                        Component.literal(""),
                        Component.translatable("chest_item.book.samsara.5"),
                        Component.translatable("chest_item.book.samsara.6")
                ),
                color,
                color,
                CIBookScreen.ThePage.BLACK,
                theCColor(InitItems.Samsara_.asItem()),List.of(),true,true));
    }
}
