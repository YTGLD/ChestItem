package com.ytgld.chest_item.renderer.book.page;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.condensebone.ItemBone;
import com.ytgld.chest_item.renderer.book.CIBookScreen;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;

import java.util.List;

@AddBookPage
public class Bone implements RegisterBookPage {
    private final int aInt = 24;
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
        {
            if (InitItems.ShieldEngine_.asItem() instanceof ItemBone itemBone) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.ShieldEngine_.asItem(), new Vec2(aInt * 9, aInt),
                        Component.translatable("chest_item.book.shield_engine.main"),
                        List.of(
                                Component.translatable("chest_item.book.shield_engine.1"),
                                Component.translatable("chest_item.book.shield_engine.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBone.color(itemBone.getDefaultInstance())));
            }
        }{
            if (InitItems.MassEnergyConverter_.asItem() instanceof ItemBone itemBone) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.MassEnergyConverter_.asItem(), new Vec2(aInt * 9, -aInt),
                        Component.translatable("chest_item.book.mass_energy_converter.main"),
                        List.of(
                                Component.translatable("chest_item.book.mass_energy_converter.1"),
                                Component.translatable("chest_item.book.mass_energy_converter.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBone.color(itemBone.getDefaultInstance())));
            }
        }{
            if (InitItems.QualitativeComponents_.asItem() instanceof ItemBone itemBone) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.QualitativeComponents_.asItem(), new Vec2(aInt * 10, -aInt),
                        Component.translatable("chest_item.book.qualitative_components.main"),
                        List.of(
                                Component.translatable("chest_item.book.qualitative_components.1"),
                                Component.translatable("chest_item.book.qualitative_components.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBone.color(itemBone.getDefaultInstance())));
            }
        }{
            if (InitItems.AlienationDiodes_.asItem() instanceof ItemBone itemBone) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.AlienationDiodes_.asItem(), new Vec2(aInt * 10, aInt),
                        Component.translatable("chest_item.book.alienation_diodes.main"),
                        List.of(
                                Component.translatable("chest_item.book.alienation_diodes.1"),
                                Component.translatable("chest_item.book.alienation_diodes.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBone.color(itemBone.getDefaultInstance())));
            }
        }
    }
}
