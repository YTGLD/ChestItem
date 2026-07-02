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
public class Meat implements RegisterBookPage {
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
        {
            if (InitItems.Self_Increasing_Heart.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Self_Increasing_Heart.asItem(), new Vec2(32, -64),
                        Component.translatable("chest_item.book.self_increasing_heart.main"),
                        List.of(
                                Component.translatable("chest_item.book.self_increasing_heart.1"),
                                Component.translatable("chest_item.book.self_increasing_heart.2"),
                                Component.translatable("chest_item.book.self_increasing_heart.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.Self_Increasing_Heart.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Heart_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Heart_.asItem(), new Vec2(0, -32),
                        Component.translatable("chest_item.book.heart.main"),
                        List.of(
                                Component.translatable("chest_item.book.heart.1"),
                                Component.translatable("chest_item.book.heart.2"),
                                Component.translatable("chest_item.book.heart.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.Heart_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Stomach_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Stomach_.asItem(), new Vec2(64, -64),
                        Component.translatable("chest_item.book.stomach.main"),
                        List.of(
                                Component.translatable("chest_item.book.stomach.1"),
                                Component.translatable("chest_item.book.stomach.2"),
                                Component.translatable("chest_item.book.stomach.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.Stomach_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Meat_Ball.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Meat_Ball.asItem(), new Vec2(-64, -64),
                        Component.translatable("chest_item.book.meatball.main"),
                        List.of(
                                Component.translatable("chest_item.book.meatball.1"),
                                Component.translatable("chest_item.book.meatball.2"),
                                Component.translatable("chest_item.book.meatball.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.Meat_Ball.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Heart_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.FleshAndBloodGears_.asItem(), new Vec2(-32, -64),
                        Component.translatable("chest_item.book.flesh_and_blood_gears.main"),
                        List.of(
                                Component.translatable("chest_item.book.flesh_and_blood_gears.1"),
                                Component.translatable("chest_item.book.flesh_and_blood_gears.2"),
                                Component.translatable("chest_item.book.flesh_and_blood_gears.3"),
                                Component.translatable("chest_item.book.flesh_and_blood_gears.4")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.FleshAndBloodGears_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.ImitationBiomass_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.ImitationBiomass_.asItem(), new Vec2(0, -64),
                        Component.translatable("chest_item.book.imitation_biomass.main"),
                        List.of(
                                Component.translatable("chest_item.book.imitation_biomass.1"),
                                Component.translatable("chest_item.book.imitation_biomass.2"),
                                Component.translatable("chest_item.book.imitation_biomass.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.ImitationBiomass_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.ScarHeart_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.ScarHeart_.asItem(), new Vec2(0, -132),
                        Component.translatable("chest_item.book.scar_heart.main"),
                        List.of(
                                Component.translatable("chest_item.book.scar_heart.1"),
                                Component.translatable("chest_item.book.scar_heart.2"),
                                Component.translatable("chest_item.book.scar_heart.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.ScarHeart_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.LifeCoin_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.LifeCoin_.asItem(), new Vec2(32, -132),
                        Component.translatable("chest_item.book.life_coin.main"),
                        List.of(
                                Component.translatable("chest_item.book.life_coin.1"),
                                Component.translatable("chest_item.book.life_coin.2"),
                                Component.translatable("chest_item.book.life_coin.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.LifeCoin_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.HeavyBlade_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.HeavyBlade_.asItem(), new Vec2(-32, -132),
                        Component.translatable("chest_item.book.heavy_blade.main"),
                        List.of(
                                Component.translatable("chest_item.book.heavy_blade.1"),
                                Component.translatable("chest_item.book.heavy_blade.2"),
                                Component.translatable("chest_item.book.heavy_blade.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.HeavyBlade_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.GiantHeart_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.GiantHeart_.asItem(), new Vec2(0, -168),
                        Component.translatable("chest_item.book.giant_heart.main"),
                        List.of(
                                Component.translatable("chest_item.book.giant_heart.1"),
                                Component.translatable("chest_item.book.giant_heart.2"),
                                Component.translatable("chest_item.book.giant_heart.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.GiantHeart_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.God_Apple.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.God_Apple.asItem(), new Vec2(0, -96),
                        Component.translatable("chest_item.book.god_apple.main"),
                        List.of(
                                Component.translatable("chest_item.book.god_apple.1"),
                                Component.translatable("chest_item.book.god_apple.2"),
                                Component.translatable("chest_item.book.god_apple.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.MEAT,
                        itemBase.color(InitItems.God_Apple.asItem().getDefaultInstance())));
            }
        }
    }
}
