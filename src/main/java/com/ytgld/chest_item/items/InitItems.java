package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.OwnerLead;
import com.ytgld.chest_item.items.black.*;
import com.ytgld.chest_item.items.black.celestial.*;
import com.ytgld.chest_item.items.black.chaos_item.ChaosFortress;
import com.ytgld.chest_item.items.black.chaos_item.Warmaker;
import com.ytgld.chest_item.items.black.give.BrassCoins;
import com.ytgld.chest_item.items.black.give.DevilCoins;
import com.ytgld.chest_item.items.black.give.LeadOfEnlightenment;
import com.ytgld.chest_item.items.black.soul.*;
import com.ytgld.chest_item.items.black.soul.chaos.ChaosSeven;
import com.ytgld.chest_item.items.black.soul.treaty.Complementary;
import com.ytgld.chest_item.items.blood.BoneHead;
import com.ytgld.chest_item.items.blood.GodBlood;
import com.ytgld.chest_item.items.blood.LifeCrystal;
import com.ytgld.chest_item.items.condensebone.*;
import com.ytgld.chest_item.items.end.EndEffect;
import com.ytgld.chest_item.items.end.TheEndIsComing;
import com.ytgld.chest_item.items.gold.*;
import com.ytgld.chest_item.items.iron.IronCube;
import com.ytgld.chest_item.items.iron.IronHeart;
import com.ytgld.chest_item.items.meet.*;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.items.other.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class    InitItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chestitem.MODID);

    public static final DeferredItem<Item> God_blood = register("god_blood",
            (resourceLocation)-> new GodBlood(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Drug_Heal = register("drug_heal",
            (resourceLocation)-> new DrugHeal(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Life_Crystal = register("life_crystal",
            (resourceLocation)-> new LifeCrystal(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Bone_Head = register("bone_head",
            (resourceLocation)-> new BoneHead(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Conch_ = register("conch",
            (resourceLocation)-> new Conch(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Lead_ = register("lead",
            (resourceLocation)-> new Lead(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Stone_ = register("stone",
            (resourceLocation)-> new Stone(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Knife_ = register("knife",
            (resourceLocation)-> new Knife(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Ring_ = register("ring",
            (resourceLocation)-> new Ring(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Life_Stone = register("life_stone",
            (resourceLocation)-> new LifeStone(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Armor_Stone = register("armor_stone",
            (resourceLocation)-> new ArmorStone(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Stronger_Stone = register("stronger_stone",
            (resourceLocation)-> new StrongerStone(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Separate_Rune = register("separate_rune",
            (resourceLocation)-> new SeparateRune(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Pain_Rune = register("pain_rune",
            (resourceLocation)-> new PainRune(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Undead_Rune = register("undead_rune",
            (resourceLocation)-> new UndeadRune(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Heart_ = register("heart",
            (resourceLocation)-> new Heart(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Stomach_ = register("stomach",
            (resourceLocation)-> new Stomach(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Meat_Ball = register("meatball",
            (resourceLocation)-> new MeatBall(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Self_Increasing_Heart = register("self_increasing_heart",
            (resourceLocation)-> new SelfIncreasingHeart(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> God_Apple = register("god_apple",
            (resourceLocation)-> new GodApple(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Gold_Cheese = register("gold_cheese",
            (resourceLocation)-> new GoldCheese(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Space_ = register("space",
            (resourceLocation)-> new Space(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Vex_Ring = register("vex_ring",
            (resourceLocation)-> new VexRing(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> TheEndIsComing_ = register("the_end_is_coming",
            (resourceLocation)-> new TheEndIsComing(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ImitationBiomass_ = register("imitation_biomass",
            (resourceLocation)-> new ImitationBiomass(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Battery_ = register("battery",
            (resourceLocation)-> new Battery(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SpeedHeart_ = register("speed_heart",
            (resourceLocation)-> new SpeedHeart(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MAGIC_IRON = register("magic_iron",
            (resourceLocation)-> new ItemBase(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> WindKnife_ = register("wind_knife",
            (resourceLocation)-> new WindKnife(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> NuclearReaction_ = register("nuclear_reaction",
            (resourceLocation)-> new NuclearReaction(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Kaolinite_ = register("kaolinite",
            (resourceLocation)-> new Kaolinite(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GiantHeart_ = register("giant_heart",
            (resourceLocation)-> new GiantHeart(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> EndEffect_ = register("end_effect",
            (resourceLocation)-> new EndEffect(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> EyeBook_ = register("eye_book",
            (resourceLocation)-> new EyeBook(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Abnormal = register("abnormal",
            (resourceLocation)-> new ItemBase(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> Complete = register("complete",
            (resourceLocation)-> new ItemBase(new Item.Properties().stacksTo(64)));
    public static final DeferredItem<Item> IronHeart_ = register("iron_heart",
            (resourceLocation)-> new IronHeart(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> IronCube_ = register("iron_cube",
            (resourceLocation)-> new IronCube(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ScarHeart_ = register("scar_heart",
            (resourceLocation)-> new ScarHeart(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> LifeCoin_ = register("life_coin",
            (resourceLocation)-> new LifeCoin(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> HeavyBlade_ = register("heavy_blade",
            (resourceLocation)-> new HeavyBlade(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> EvilThoughtsForgeDreams_ = register("evil_thoughts_forge_dreams",
            (resourceLocation)-> new EvilThoughtsForgeDreams(new Item.Properties().stacksTo(1)));


    public static final DeferredItem<Item> DryBones_ = register("dry_bones",
            (resourceLocation)-> new DryBones(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ShadowMint_ = register("shadow_mint",
            (resourceLocation)-> new ShadowMint(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> TheOrderOfTheUndead_ = register("the_order_of_the_undead",
            (resourceLocation)-> new TheOrderOfTheUndead(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Mutation_ = register("mutation",
            (resourceLocation)-> new Mutation(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MadnessTheory_ = register("madness_theory",
            (resourceLocation)-> new MadnessTheory(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Glutton_ = register("glutton",
            (resourceLocation)-> new Glutton(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> DevilCoins_ = register("devil_coins",
            (resourceLocation)-> new DevilCoins(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Speed_ = register("speed",
            (resourceLocation)-> new Speed(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ChaosSeven_ = register("chaos_seven",
            (resourceLocation)-> new ChaosSeven(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> Silent_ = register("silent",
            (resourceLocation)-> new Silent(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BrassCoins_ = register("brass_coins",
            (resourceLocation)-> new BrassCoins(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CorruptionCrystal_ = register("corruption_crystal",
            (resourceLocation)-> new CorruptionCrystal(new Item.Properties().stacksTo(1)));





    public static final DeferredItem<Item> CondenseBoneCube_ = register("condense_bone_cube",
            (resourceLocation)-> new CondenseBoneCube(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> ShieldEngine_ = register("shield_engine",
            (resourceLocation)-> new ShieldEngine(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> AlienationDiodes_ = register("alienation_diodes",
            (resourceLocation)-> new AlienationDiodes(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> QualitativeComponents_ = register("qualitative_components",
            (resourceLocation)-> new QualitativeComponents(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MassEnergyConverter_ = register("mass_energy_converter",
            (resourceLocation)-> new MassEnergyConverter(new Item.Properties().stacksTo(1)));










    public static final DeferredItem<Item> Fission_ = register("fission",
            (resourceLocation)-> new Fission(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> FleshAndBloodGears_ = register("flesh_and_blood_gears",
            (resourceLocation)-> new FleshAndBloodGears(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> HardwoodTotemPole_ = register("hardwood_totem_pole",
            (resourceLocation)-> new HardwoodTotemPole(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> DeathOmenStoneMonument_ = register("death_omen_stone_monument",
            (resourceLocation)-> new DeathOmenStoneMonument(new Item.Properties().stacksTo(1)));


    public static final DeferredItem<@NotNull Item> Blood_ = register("blood",
            (Identifier)-> new Blood(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> Chaos_ = register("chaos",
            (Identifier)-> new Chaos(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> NineDome_ = register("nine_dome",
            (Identifier)-> new NineDome(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> Sword_ = register("sword",
            (Identifier)-> new Sword(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> Samsara_ = register("samsara",
            (Identifier)-> new Samsara(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> ChaosConstructor_ = register("chaos_constructor",
            (Identifier)-> new ChaosConstructor(new Item.Properties().stacksTo(1)));


    public static final DeferredItem<@NotNull Item> DriftingBottles_ = register("drifting_bottles",
            (Identifier)-> new DriftingBottles(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> DefeatTheArmy_ = register("defeat_the_army",
            (Identifier)-> new DefeatTheArmy(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> ErosionTokens_ = register("erosion_tokens",
            (Identifier)-> new ErosionTokens(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> OneEyedSpider_ = register("one_eye_spider",
            (Identifier)-> new OneEyedSpider(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> TheBell_ = register("the_bell",
            (Identifier)-> new TheBell(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<@NotNull Item> Complementary_ = register("complementary",
            (Identifier)-> new Complementary(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> FissionEmblem_ = register("fission_emblem",
            (Identifier)-> new FissionEmblem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<@NotNull Item> Warmaker_ = register("warmaker",
            (Identifier)-> new Warmaker(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<@NotNull Item> LeadOfEnlightenment_ = register("lead_of_enlightenment",
            (Identifier)-> new LeadOfEnlightenment(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<@NotNull Item> ChaosFortress_ = register("chaos_fortress",
            (Identifier)-> new ChaosFortress(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<@NotNull Item> OwnerLead_ = register("owner_lead",
            (Identifier)-> new OwnerLead(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<@NotNull Item> ActualSuffering_ = register("actual_suffering",
            (Identifier)-> new ActualSuffering(new Item.Properties().stacksTo(1)));


    public static final DeferredItem<Item> Pod_ = register("pod",
            (resourceLocation)-> new Pod(new Item.Properties().stacksTo(1)));

    public static DeferredItem<Item> register(String name, Function<ResourceLocation, ? extends Item> func) {
        return ITEMS.register(name,func);
    }

    public static class TabChestItem{
        public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Chestitem.MODID);
        public static final DeferredHolder<CreativeModeTab, CreativeModeTab> tab = CREATIVE_MODE_TABS.register(Chestitem.MODID, () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.chest_item"))
                .icon(Items.CHEST::getDefaultInstance)
                .displayItems((parameters, output) -> {
                    output.accept(MemoryItems.Bluster_);
                    output.accept(MemoryItems.Contradiction_);
                    output.accept(MemoryItems.Extreme_);
                    output.accept(MemoryItems.ForeverCurtain_);
                    output.accept(MemoryItems.Martyrdom_);
                    output.accept(MemoryItems.Peace_);
                    output.accept(MemoryItems.War_);

                    output.accept(InitItems.Fission_);
                    output.accept(InitItems.FleshAndBloodGears_);
                    output.accept(InitItems.Blood_);
                    output.accept(InitItems.Chaos_);
                    output.accept(InitItems.NineDome_);
                    output.accept(InitItems.Sword_);
                    output.accept(InitItems.Samsara_);
                    output.accept(InitItems.ChaosSeven_);
                    output.accept(InitItems.FissionEmblem_);



                    output.accept(InitItems.TheBell_);
                    output.accept(InitItems.DriftingBottles_);
                    output.accept(InitItems.God_blood);
                    output.accept(InitItems.Drug_Heal);
                    output.accept(InitItems.Life_Crystal);
                    output.accept(InitItems.Bone_Head);
                    output.accept(InitItems.Conch_);
                    output.accept(InitItems.Lead_);
                    output.accept(InitItems.Stone_);
                    output.accept(InitItems.Knife_);
                    output.accept(InitItems.Ring_);
                    output.accept(InitItems.Life_Stone);
                    output.accept(InitItems.Armor_Stone);
                    output.accept(InitItems.Stronger_Stone);
                    output.accept(InitItems.Separate_Rune);
                    output.accept(InitItems.Pain_Rune);
                    output.accept(InitItems.Undead_Rune);
                    output.accept(InitItems.Gold_Cheese);
                    output.accept(InitItems.Space_);
                    output.accept(InitItems.TheEndIsComing_);
                    output.accept(InitItems.Battery_);
                    output.accept(InitItems.MAGIC_IRON);
                    output.accept(InitItems.SpeedHeart_);
                    output.accept(InitItems.WindKnife_);
                    output.accept(InitItems.Kaolinite_);
                    output.accept(InitItems.NuclearReaction_);
                    output.accept(InitItems.EndEffect_);
                    output.accept(InitItems.EyeBook_);
                    output.accept(InitItems.Abnormal);
                    output.accept(InitItems.Complete);
                    output.accept(InitItems.IronHeart_);
                    output.accept(InitItems.IronCube_);
                    output.accept(InitItems.CondenseBoneCube_);
                    output.accept(InitItems.ShieldEngine_);
                    output.accept(InitItems.AlienationDiodes_);
                    output.accept(InitItems.QualitativeComponents_);
                    output.accept(InitItems.MassEnergyConverter_);
                    output.accept(InitItems.HardwoodTotemPole_);





                    output.accept(InitItems.GiantHeart_);
                    output.accept(InitItems.ImitationBiomass_);
                    output.accept(InitItems.Heart_);
                    output.accept(InitItems.Stomach_);
                    output.accept(InitItems.Meat_Ball);
                    output.accept(InitItems.Self_Increasing_Heart);
                    output.accept(InitItems.God_Apple);
                    output.accept(InitItems.ScarHeart_);
                    output.accept(InitItems.LifeCoin_);
                    output.accept(InitItems.HeavyBlade_);




                    output.accept(InitItems.DeathOmenStoneMonument_);
                    output.accept(InitItems.EvilThoughtsForgeDreams_);
                    output.accept(InitItems.DryBones_);
                    output.accept(InitItems.ShadowMint_);
                    output.accept(InitItems.CorruptionCrystal_);
                    output.accept(InitItems.Pod_);
                    output.accept(InitItems.ChaosConstructor_);
                    output.accept(InitItems.ErosionTokens_);
                    output.accept(InitItems.OneEyedSpider_);
                    output.accept(InitItems.LeadOfEnlightenment_);
                    output.accept(InitItems.DefeatTheArmy_);
                    output.accept(InitItems.Warmaker_);
                    output.accept(InitItems.ChaosFortress_);
                    output.accept(InitItems.ActualSuffering_);



                    output.accept(InitItems.DevilCoins_);
                    output.accept(InitItems.BrassCoins_);

                    output.accept(InitItems.TheOrderOfTheUndead_);
                    output.accept(InitItems.Mutation_);
                    output.accept(InitItems.MadnessTheory_);
                    output.accept(InitItems.Glutton_);
                    output.accept(InitItems.Speed_);
                    output.accept(InitItems.Silent_);
                    output.accept(InitItems.Complementary_);


                }).build());

    }

    public static class TagsProvider extends ItemTagsProvider {


        public static final TagKey<Item> chestItem = createTag("chest_item");
        public static final TagKey<Item> chestItem_iron = createTag("chest_item_iron");
        public static final TagKey<Item> chestItemMeat = createTag("chest_item_meat");
        public static final TagKey<Item> chestItemBone = createTag("chest_item_bone");
        public static final TagKey<@NotNull Item> celestial = createTag("celestial");

        public TagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagProvider, ExistingFileHelper existingFileHelper) {
            super(output, lookupProvider, blockTagProvider, Chestitem.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {

            tag(celestial).add(
                    Blood_.asItem(),
                    Chaos_.asItem(),
                    Sword_.asItem(),
                    Samsara_.asItem(),



                    NineDome_.asItem()
            );

            tag(chestItemBone).add(
                    DryBones_.asItem(),
                    ShieldEngine_.asItem(),
                    AlienationDiodes_.asItem(),
                    QualitativeComponents_.asItem(),
                    MassEnergyConverter_.asItem(),
                    Bone_Head.asItem()
            );




            tag(chestItemMeat).add(
                            GiantHeart_.asItem())
                    .add(God_Apple.asItem())
                    .add(Heart_.asItem())
                    .add(HeavyBlade_.asItem())
                    .add(ImitationBiomass_.asItem())
                    .add(LifeCoin_.asItem())
                    .add(Meat_Ball.asItem())
                    .add(ScarHeart_.asItem())
                    .add(Self_Increasing_Heart.asItem())
                    .add(Stomach_.asItem());



            tag(chestItem_iron).add(
                            SpeedHeart_.asItem())
                    .add(NuclearReaction_.asItem())
                    .add(Complete.asItem())
                    .add(IronHeart_.asItem())
                    .add(InitItems.Battery_.asItem())
                    .add(IronCube_.asItem())
                    .add(DevilCoins_.asItem())
                    .add(Knife_.asItem())
                    .add(Lead_.asItem())
                    .add(DriftingBottles_.asItem())
                    .add(WindKnife_.asItem());

            tag(chestItem)
                    .add(God_blood.asItem())
                    .add(Drug_Heal.asItem())
                    .add(Life_Crystal.asItem())
                    .add(Conch_.asItem())
                    .add(Stone_.asItem())
                    .add(Ring_.asItem())
                    .add(Life_Stone.asItem())
                    .add(Armor_Stone.asItem())
                    .add(Stronger_Stone.asItem())
                    .add(Separate_Rune.asItem())
                    .add(Pain_Rune.asItem())
                    .add(Undead_Rune.asItem())
                    .add(InitItems.TheEndIsComing_.asItem())
                    .add(InitItems.Gold_Cheese.asItem())
                    .add(InitItems.Space_.asItem())
                    .add(InitItems.Kaolinite_.asItem())
                    .add(InitItems.EndEffect_.asItem())
                    .add(InitItems.EyeBook_.asItem())
                    .add(InitItems.BrassCoins_.asItem());
        }
        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Chestitem.MODID, name));
        }

    }
}
