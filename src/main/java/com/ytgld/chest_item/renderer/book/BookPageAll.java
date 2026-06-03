package com.ytgld.chest_item.renderer.book;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.book.tool.AddBookPage;
import com.ytgld.chest_item.renderer.book.tool.RegisterBookPage;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public class BookPageAll {
    public static class BlackShadowSoul{
        @AddBookPage
        public static class Glutton_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        @AddBookPage
        public static class MadnessTheory_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        @AddBookPage
        public static class Mutation_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class Silent_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        @AddBookPage
        public static class Speed_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class TheOrderOfTheUndead_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        @AddBookPage
        public static class ChaosSeven_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
    public static class BlackShadow{
        @AddBookPage
        public static class EvilThoughtsForgeDreams_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
                if (InitItems.EvilThoughtsForgeDreams_.asItem() instanceof ItemBase itemBase) {
                    list.add(new CIBookScreen.CIBookGuiAdd(InitItems.EvilThoughtsForgeDreams_.asItem(), new Vec2(-32,0),
                            Component.translatable("chest_item.book.evil_thoughts_forge_dreams.main"),
                            List.of(
                                    Component.translatable("chest_item.book.evil_thoughts_forge_dreams.1"),
                                    Component.translatable("chest_item.book.evil_thoughts_forge_dreams.2"),
                                    Component.translatable("chest_item.book.evil_thoughts_forge_dreams.3")
                            ),
                            Light.ARGB.color(255, 255, 255, 255),
                            Light.ARGB.color(255, 150, 150, 150),
                            CIBookScreen.ThePage.BLACK,
                            itemBase.color(InitItems.EvilThoughtsForgeDreams_.asItem().getDefaultInstance())));
                }
            }
        }
        @AddBookPage
        public static class DryBones_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class ShadowMint_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        public static class Tool{

            @AddBookPage
            public static class WallowAxe_ implements RegisterBookPage {
                @Override
                public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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

    }
    public static class Meat{
        @AddBookPage
        public static class Self_Increasing_Heart implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class Heart_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class Stomach_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        @AddBookPage
        public static class Meat_Ball implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class FleshAndBloodGears_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class ImitationBiomass_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class God_Apple implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
        @AddBookPage
        public static class ScarHeart_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        @AddBookPage
        public static class LifeCoin_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        @AddBookPage
        public static class HeavyBlade_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        @AddBookPage
        public static class GiantHeart_ implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

    }
    public static class Other{

        @AddBookPage
        public static class Bone_Head implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class Life_Stone implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class Armor_Stone implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class Stronger_Stone implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }

        @AddBookPage
        public static class Undead_Rune implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class Separate_Rune implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
        @AddBookPage
        public static class Pain_Rune implements RegisterBookPage {
            @Override
            public void addPage(List<CIBookScreen.CIBookGuiAdd> list) {
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
            }
        }
    }
}
