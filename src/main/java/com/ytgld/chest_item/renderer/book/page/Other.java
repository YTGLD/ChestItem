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
        {
            if (InitItems.Fission_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Fission_.asItem(), new Vec2(0, 132),
                        Component.translatable("chest_item.book.fission.main"),
                        List.of(
                                Component.translatable("chest_item.book.fission.1"),
                                Component.translatable("chest_item.book.fission.2"),
                                Component.translatable("chest_item.book.fission.3"),
                                Component.translatable("chest_item.book.fission.4")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Fission_.asItem().getDefaultInstance())));
            }
        }
        {
            if (InitItems.Conch_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Conch_.asItem(), new Vec2(0, 164),
                        Component.translatable("chest_item.book.conch.main"),
                        List.of(
                                Component.translatable("chest_item.book.conch.1"),
                                Component.translatable("chest_item.book.conch.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Conch_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Lead_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Lead_.asItem(), new Vec2(24, 164),
                        Component.translatable("chest_item.book.lead.main"),
                        List.of(
                                Component.translatable("chest_item.book.lead.1"),
                                Component.translatable("chest_item.book.lead.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Lead_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.God_blood.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.God_blood.asItem(), new Vec2(48, 164),
                        Component.translatable("chest_item.book.god_blood.main"),
                        List.of(
                                Component.translatable("chest_item.book.god_blood.1"),
                                Component.translatable("chest_item.book.god_blood.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.God_blood.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Stone_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Stone_.asItem(), new Vec2(72, 164),
                        Component.translatable("chest_item.book.stone.main"),
                        List.of(
                                Component.translatable("chest_item.book.stone.1"),
                                Component.translatable("chest_item.book.stone.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Stone_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Drug_Heal.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Drug_Heal.asItem(), new Vec2(-72, 164),
                        Component.translatable("chest_item.book.drug_heal.main"),
                        List.of(
                                Component.translatable("chest_item.book.drug_heal.1"),
                                Component.translatable("chest_item.book.drug_heal.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Drug_Heal.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Gold_Cheese.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Gold_Cheese.asItem(), new Vec2(-48, 164),
                        Component.translatable("chest_item.book.gold_cheese.main"),
                        List.of(
                                Component.translatable("chest_item.book.gold_cheese.1"),
                                Component.translatable("chest_item.book.gold_cheese.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Gold_Cheese.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.Battery_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Battery_.asItem(), new Vec2(-24, 164),
                        Component.translatable("chest_item.book.battery.main"),
                        List.of(
                                Component.translatable("chest_item.book.battery.1"),
                                Component.translatable("chest_item.book.battery.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Battery_.asItem().getDefaultInstance())));
            }
        }
        {
            if (InitItems.EndEffect_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.EndEffect_.asItem(), new Vec2(-32, 196),
                        Component.translatable("chest_item.book.end_effect.main"),
                        List.of(
                                Component.translatable("chest_item.book.end_effect.1"),
                                Component.translatable("chest_item.book.end_effect.2"),
                                Component.translatable("chest_item.book.end_effect.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.EndEffect_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.TheEndIsComing_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.TheEndIsComing_.asItem(), new Vec2(32, 196),
                        Component.translatable("chest_item.book.the_end_is_coming.main"),
                        List.of(
                                Component.translatable("chest_item.book.the_end_is_coming.1"),
                                Component.translatable("chest_item.book.the_end_is_coming.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.TheEndIsComing_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.NuclearReaction_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.NuclearReaction_.asItem(), new Vec2(0, 228),
                        Component.translatable("chest_item.book.nuclear_reaction.main"),
                        List.of(
                                Component.translatable("chest_item.book.nuclear_reaction.1"),
                                Component.translatable("chest_item.book.nuclear_reaction.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.NuclearReaction_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.WindKnife_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.WindKnife_.asItem(), new Vec2(24, 228),
                        Component.translatable("chest_item.book.wind_knife.main"),
                        List.of(
                                Component.translatable("chest_item.book.wind_knife.1"),
                                Component.translatable("chest_item.book.wind_knife.2"),
                                Component.translatable("chest_item.book.wind_knife.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.WindKnife_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.EyeBook_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.EyeBook_.asItem(), new Vec2(-24, 228),
                        Component.translatable("chest_item.book.eye_book.main"),
                        List.of(
                                Component.translatable("chest_item.book.eye_book.1"),
                                Component.translatable("chest_item.book.eye_book.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.EyeBook_.asItem().getDefaultInstance())));
            }
        }
        {
            if (InitItems.Kaolinite_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.Kaolinite_.asItem(), new Vec2(0, 260),
                        Component.translatable("chest_item.book.kaolinite.main"),
                        List.of(
                                Component.translatable("chest_item.book.kaolinite.1"),
                                Component.translatable("chest_item.book.kaolinite.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.Kaolinite_.asItem().getDefaultInstance())));
            }
        }
        {
            if (InitItems.IronHeart_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.IronHeart_.asItem(), new Vec2(24, 292),
                        Component.translatable("chest_item.book.iron_heart.main"),
                        List.of(
                                Component.translatable("chest_item.book.iron_heart.1"),
                                Component.translatable("chest_item.book.iron_heart.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.IronHeart_.asItem().getDefaultInstance())));
            }
        }
        {
            if (InitItems.IronCube_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.IronCube_.asItem(), new Vec2(-24, 292),
                        Component.translatable("chest_item.book.iron_cube.main"),
                        List.of(
                                Component.translatable("chest_item.book.iron_cube.1"),
                                Component.translatable("chest_item.book.iron_cube.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BASE,
                        itemBase.color(InitItems.IronCube_.asItem().getDefaultInstance())));
            }
        }
    }
}
