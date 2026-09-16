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
public class BlackShadow implements RegisterBookPage {
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
        {
            if (InitItems.DryBones_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.DryBones_.asItem(), new Vec2(-64,24),
                        Component.translatable("chest_item.book.dry_bones.main"),
                        List.of(
                                Component.translatable("chest_item.book.dry_bones.1"),
                                Component.translatable("chest_item.book.dry_bones.2"),
                                Component.translatable("chest_item.book.dry_bones.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.DryBones_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.ShadowMint_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.ShadowMint_.asItem(), new Vec2(-64,-24),
                        Component.translatable("chest_item.book.shadow_mint.main"),
                        List.of(
                                Component.translatable("chest_item.book.shadow_mint.1"),
                                Component.translatable("chest_item.book.shadow_mint.2"),
                                Component.translatable("chest_item.book.shadow_mint.3")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.ShadowMint_.asItem().getDefaultInstance())));
            }
        }{
            if (InitItems.DefeatTheArmy_.asItem() instanceof ItemBase itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(InitItems.DefeatTheArmy_.asItem(), new Vec2(-64,-40),
                        Component.translatable("chest_item.book.defeat_the_army.main"),
                        List.of(
                                Component.translatable("chest_item.book.defeat_the_army.1"),
                                Component.translatable("chest_item.book.defeat_the_army.2")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color(InitItems.DefeatTheArmy_.asItem().getDefaultInstance())));
            }
        }{
            list.add(new CIBookScreen.CIBookGuiAdd(InitItems.WallowAxe_.asItem(), new Vec2(-64,60),
                    Component.translatable("chest_item.book.wallow_axe.main"),
                    List.of(
                            Component.translatable("chest_item.book.wallow_axe.1"),
                            Component.translatable("chest_item.book.wallow_axe.2"),
                            Component.translatable("chest_item.book.wallow_axe.3")
                    ),
                    Light.ARGB.color(255, 255, 255, 255),
                    Light.ARGB.color(255, 150, 150, 150),
                    CIBookScreen.ThePage.BLACK,
                    Light.ARGB.color(255,255,0,255)));
        }
    }
}
