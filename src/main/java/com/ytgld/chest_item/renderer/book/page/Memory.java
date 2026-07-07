package com.ytgld.chest_item.renderer.book.page;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.renderer.book.CIBookScreen;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

import java.util.List;

@AddBookPage
public class Memory implements RegisterBookPage {
    @Override
    public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
        {
            if (MemoryItems.BlusterTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
            list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.BlusterTooltip_.asItem(), new Vec2(0, -196),
                        Component.translatable("item.chest_item.bluster_tooltip"),
                        List.of(
                                Component.translatable("chest_item.book.bluster_tooltip.1"),
                                Component.translatable("chest_item.book.bluster_tooltip.2"),
                                Component.literal(""),
                                Component.translatable("chest_item.book.memory")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color()));
            }
        }
        {
            if (MemoryItems.ContradictionTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
                list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.ContradictionTooltip_.asItem(), new Vec2(24, -196),
                        Component.translatable("item.chest_item.contradiction_tooltip"),
                        List.of(
                                Component.translatable("chest_item.book.contradiction_tooltip.1"),
                                Component.translatable("chest_item.book.contradiction_tooltip.2"),
                                Component.literal(""),
                                Component.translatable("chest_item.book.memory")
                        ),
                        Light.ARGB.color(255, 255, 255, 255),
                        Light.ARGB.color(255, 150, 150, 150),
                        CIBookScreen.ThePage.BLACK,
                        itemBase.color()));
            }
            {
                if (MemoryItems.ExtremeTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
                    list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.ExtremeTooltip_.asItem(), new Vec2(48, -196),
                            Component.translatable("item.chest_item.extreme_tooltip"),
                            List.of(
                                    Component.translatable("chest_item.book.extreme_tooltip.1"),
                                    Component.translatable("chest_item.book.extreme_tooltip.2"),
                                    Component.translatable("chest_item.book.extreme_tooltip.3"),
                                    Component.literal(""),
                                    Component.translatable("chest_item.book.memory")
                            ),
                            Light.ARGB.color(255, 255, 255, 255),
                            Light.ARGB.color(255, 150, 150, 150),
                            CIBookScreen.ThePage.BLACK,
                            itemBase.color()));
                }
            }
            {
                if (MemoryItems.ForeverCurtainTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
                    list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.ForeverCurtainTooltip_.asItem(), new Vec2(72, -196),
                            Component.translatable("item.chest_item.forever_curtain_tooltip"),
                            List.of(
                                    Component.translatable("chest_item.book.forever_curtain_tooltip.1"),
                                    Component.translatable("chest_item.book.forever_curtain_tooltip.2"),
                                    Component.literal(""),
                                    Component.translatable("chest_item.book.memory")
                            ),
                            Light.ARGB.color(255, 255, 255, 255),
                            Light.ARGB.color(255, 150, 150, 150),
                            CIBookScreen.ThePage.BLACK,
                            itemBase.color()));
                }
            }
            {
                if (MemoryItems.MartyrdomTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
                    list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.MartyrdomTooltip_.asItem(), new Vec2(-72, -196),
                            Component.translatable("item.chest_item.martyrdom_tooltip"),
                            List.of(
                                    Component.translatable("chest_item.book.martyrdom_tooltip.1"),
                                    Component.translatable("chest_item.book.martyrdom_tooltip.2"),
                                    Component.literal(""),
                                    Component.translatable("chest_item.book.memory")
                            ),
                            Light.ARGB.color(255, 255, 255, 255),
                            Light.ARGB.color(255, 150, 150, 150),
                            CIBookScreen.ThePage.BLACK,
                            itemBase.color()));
                }
            }
            {
                if (MemoryItems.PeaceTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
                    list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.PeaceTooltip_.asItem(), new Vec2(-48, -196),
                            Component.translatable("item.chest_item.peace_tooltip"),
                            List.of(
                                    Component.translatable("chest_item.book.peace_tooltip.1"),
                                    Component.translatable("chest_item.book.peace_tooltip.2"),
                                    Component.literal(""),
                                    Component.translatable("chest_item.book.memory")
                            ),
                            Light.ARGB.color(255, 255, 255, 255),
                            Light.ARGB.color(255, 150, 150, 150),
                            CIBookScreen.ThePage.BLACK,
                            itemBase.color()));
                }
            }
            {
                if (MemoryItems.WarTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
                    list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.WarTooltip_.asItem(), new Vec2(-24, -196),
                            Component.translatable("item.chest_item.war_tooltip"),
                            List.of(
                                    Component.translatable("chest_item.book.war_tooltip.1"),
                                    Component.translatable("chest_item.book.war_tooltip.2"),
                                    Component.literal(""),
                                    Component.translatable("chest_item.book.memory")
                            ),
                            Light.ARGB.color(255, 255, 255, 255),
                            Light.ARGB.color(255, 150, 150, 150),
                            CIBookScreen.ThePage.BLACK,
                            itemBase.color()));
                }
            }
            {
                if (MemoryItems.TheFoxTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
                    list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.TheFoxTooltip_.asItem(), new Vec2(0, -228),
                            Component.translatable("item.chest_item.the_fox_tooltip"),
                            List.of(
                                    Component.translatable("chest_item.book.the_fox_tooltip.1"),
                                    Component.translatable("chest_item.book.the_fox_tooltip.2"),
                                    Component.literal(""),
                                    Component.translatable("chest_item.book.memory")
                            ),
                            Light.ARGB.color(255, 255, 255, 255),
                            Light.ARGB.color(255, 150, 150, 150),
                            CIBookScreen.ThePage.BLACK,
                            itemBase.color()));
                }
            }
            {
                if (MemoryItems.ProtestTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
                    list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.ProtestTooltip_.asItem(), new Vec2(32, -228),
                            Component.translatable("item.chest_item.protest_tooltip"),
                            List.of(
                                    Component.translatable("chest_item.book.protest_tooltip.1"),
                                    Component.translatable("chest_item.book.protest_tooltip.2"),
                                    Component.literal(""),
                                    Component.translatable("chest_item.book.memory")
                            ),
                            Light.ARGB.color(255, 255, 255, 255),
                            Light.ARGB.color(255, 150, 150, 150),
                            CIBookScreen.ThePage.BLACK,
                            itemBase.color()));
                }
            }
            {
                if (MemoryItems.CraveTooltip_.asItem() instanceof MemoryBase.BaseTooltip itemBase) {
                    list.add(new CIBookScreen.CIBookGuiAdd(MemoryItems.CraveTooltip_.asItem(), new Vec2(-32, -228),
                            Component.translatable("item.chest_item.crave_tooltip"),
                            List.of(
                                    Component.translatable("chest_item.book.crave_tooltip.1"),
                                    Component.translatable("chest_item.book.crave_tooltip.2"),
                                    Component.literal(""),
                                    Component.translatable("chest_item.book.memory")
                            ),
                            Light.ARGB.color(255, 255, 255, 255),
                            Light.ARGB.color(255, 150, 150, 150),
                            CIBookScreen.ThePage.BLACK,
                            itemBase.color()));
                }
            }
        }
    }
}
