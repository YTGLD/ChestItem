package com.ytgld.chest_item.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.OwnerLead;
import com.ytgld.chest_item.items.black.*;
import com.ytgld.chest_item.items.black.celestial.*;
import com.ytgld.chest_item.items.black.chaos_item.ChaosFortress;
import com.ytgld.chest_item.items.black.chaos_item.RunawayLining;
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
import com.ytgld.chest_item.items.evil_mother.AnnualPlate;
import com.ytgld.chest_item.items.evil_mother.EvilBelt;
import com.ytgld.chest_item.items.evil_mother.MotherRemains;
import com.ytgld.chest_item.items.evil_mother.TheKill;
import com.ytgld.chest_item.items.gold.*;
import com.ytgld.chest_item.items.iron.IronCube;
import com.ytgld.chest_item.items.iron.IronHeart;
import com.ytgld.chest_item.items.meet.*;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.items.other.*;
import com.ytgld.chest_item.items.reinforced.ReinforcedItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class InitItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Chestitem.MODID);

    public static final DeferredItem<@NotNull Item> God_blood = register("god_blood",
            (Identifier)-> new GodBlood(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Drug_Heal = register("drug_heal",
            (Identifier)-> new DrugHeal(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Life_Crystal = register("life_crystal",
            (Identifier)-> new LifeCrystal(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Bone_Head = register("bone_head",
            (Identifier)-> new BoneHead(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Conch_ = register("conch",
            (Identifier)-> new Conch(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Lead_ = register("lead",
            (Identifier)-> new Lead(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Stone_ = register("stone",
            (Identifier)-> new Stone(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Knife_ = register("knife",
            (Identifier)-> new Knife(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Ring_ = register("ring",
            (Identifier)-> new Ring(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Life_Stone = register("life_stone",
            (Identifier)-> new LifeStone(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Armor_Stone = register("armor_stone",
            (Identifier)-> new ArmorStone(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Stronger_Stone = register("stronger_stone",
            (Identifier)-> new StrongerStone(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Separate_Rune = register("separate_rune",
            (Identifier)-> new SeparateRune(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Pain_Rune = register("pain_rune",
            (Identifier)-> new PainRune(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Undead_Rune = register("undead_rune",
            (Identifier)-> new UndeadRune(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Heart_ = register("heart",
            (Identifier)-> new Heart(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Stomach_ = register("stomach",
            (Identifier)-> new Stomach(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Meat_Ball = register("meatball",
            (Identifier)-> new MeatBall(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Self_Increasing_Heart = register("self_increasing_heart",
            (Identifier)-> new SelfIncreasingHeart(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> God_Apple = register("god_apple",
            (Identifier)-> new GodApple(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Gold_Cheese = register("gold_cheese",
            (Identifier)-> new GoldCheese(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Space_ = register("space",
            (Identifier)-> new Space(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Vex_Ring = register("vex_ring",
            (Identifier)-> new VexRing(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> TheEndIsComing_ = register("the_end_is_coming",
            (Identifier)-> new TheEndIsComing(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> ImitationBiomass_ = register("imitation_biomass",
            (Identifier)-> new ImitationBiomass(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Battery_ = register("battery",
            (Identifier)-> new Battery(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> SpeedHeart_ = register("speed_heart",
            (Identifier)-> new SpeedHeart(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> MAGIC_IRON = register("magic_iron",
            (Identifier)-> new ItemBase(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> WindKnife_ = register("wind_knife",
            (Identifier)-> new WindKnife(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> NuclearReaction_ = register("nuclear_reaction",
            (Identifier)-> new NuclearReaction(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Kaolinite_ = register("kaolinite",
            (Identifier)-> new Kaolinite(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> GiantHeart_ = register("giant_heart",
            (Identifier)-> new GiantHeart(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> EndEffect_ = register("end_effect",
            (Identifier)-> new EndEffect(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> EyeBook_ = register("eye_book",
            (Identifier)-> new EyeBook(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Abnormal = register("abnormal",
            (Identifier)-> new ItemBase(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Complete = register("complete",
            (Identifier)-> new ItemBase(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> IronHeart_ = register("iron_heart",
            (Identifier)-> new IronHeart(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> IronCube_ = register("iron_cube",
            (Identifier)-> new IronCube(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> ScarHeart_ = register("scar_heart",
            (Identifier)-> new ScarHeart(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> LifeCoin_ = register("life_coin",
            (Identifier)-> new LifeCoin(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> HeavyBlade_ = register("heavy_blade",
            (Identifier)-> new HeavyBlade(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> EvilThoughtsForgeDreams_ = register("evil_thoughts_forge_dreams",
            (Identifier)-> new EvilThoughtsForgeDreams(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> DryBones_ = register("dry_bones",
            (Identifier)-> new DryBones(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> ShadowMint_ = register("shadow_mint",
            (Identifier)-> new ShadowMint(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> TheOrderOfTheUndead_ = register("the_order_of_the_undead",
            (Identifier)-> new TheOrderOfTheUndead(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Mutation_ = register("mutation",
            (Identifier)-> new Mutation(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> MadnessTheory_ = register("madness_theory",
            (Identifier)-> new MadnessTheory(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Glutton_ = register("glutton",
            (Identifier)-> new Glutton(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Speed_ = register("speed",
            (Identifier)-> new Speed(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> DevilCoins_ = register("devil_coins",
            (Identifier)-> new DevilCoins(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> ChaosSeven_ = register("chaos_seven",
            (Identifier)-> new ChaosSeven(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Silent_ = register("silent",
            (Identifier)-> new Silent(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> BrassCoins_ = register("brass_coins",
            (Identifier)-> new BrassCoins(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> CorruptionCrystal_ = register("corruption_crystal",
            (Identifier)-> new CorruptionCrystal(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> CondenseBoneCube_ = register("condense_bone_cube",
            (Identifier)-> new CondenseBoneCube(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> ShieldEngine_ = register("shield_engine",
            (Identifier)-> new ShieldEngine(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> AlienationDiodes_ = register("alienation_diodes",
            (Identifier)-> new AlienationDiodes(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> QualitativeComponents_ = register("qualitative_components",
            (Identifier)-> new QualitativeComponents(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> MassEnergyConverter_ = register("mass_energy_converter",
            (Identifier)-> new MassEnergyConverter(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Fission_ = register("fission",
            (Identifier)-> new Fission(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> FleshAndBloodGears_ = register("flesh_and_blood_gears",
            (Identifier)-> new FleshAndBloodGears(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> LoneWolf_ = register("lone_wolf",
            (Identifier)-> new LoneWolf(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> HardwoodTotemPole_ = register("hardwood_totem_pole",
            (Identifier)-> new HardwoodTotemPole(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> DeathOmenStoneMonument_ = register("death_omen_stone_monument",
            (Identifier)-> new DeathOmenStoneMonument(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Pod_ = register("pod",
            (Identifier)-> new Pod(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Blood_ = register("blood",
            (Identifier)-> new Blood(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Chaos_ = register("chaos",
            (Identifier)-> new Chaos(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> NineDome_ = register("nine_dome",
            (Identifier)-> new NineDome(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Sword_ = register("sword",
            (Identifier)-> new Sword(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Samsara_ = register("samsara",
            (Identifier)-> new Samsara(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> ChaosConstructor_ = register("chaos_constructor",
            (Identifier)-> new ChaosConstructor(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> DriftingBottles_ = register("drifting_bottles",
            (Identifier)-> new DriftingBottles(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> DefeatTheArmy_ = register("defeat_the_army",
            (Identifier)-> new DefeatTheArmy(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> ErosionTokens_ = register("erosion_tokens",
            (Identifier)-> new ErosionTokens(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Test_ = register("test",
            (Identifier)-> new Test(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> OneEyedSpider_ = register("one_eye_spider",
            (Identifier)-> new OneEyedSpider(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> TheBell_ = register("the_bell",
            (Identifier)-> new TheBell(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> BloodyBelt_ = register("bloody_belt",
            (Identifier)-> new BloodyBelt(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Complementary_ = register("complementary",
            (Identifier)-> new Complementary(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> BloodyDeath_ = register("bloody_death",
            (Identifier)-> new BloodyDeath(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> NormalConstructs_ = register("normal_constructs",
            (Identifier)-> new NormalConstructs(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> FissionEmblem_ = register("fission_emblem",
            (Identifier)-> new FissionEmblem(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> Warmaker_ = register("warmaker",
            (Identifier)-> new Warmaker(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> LeadOfEnlightenment_ = register("lead_of_enlightenment",
            (Identifier)-> new LeadOfEnlightenment(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> ChaosFortress_ = register("chaos_fortress",
            (Identifier)-> new ChaosFortress(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> OwnerLead_ = register("owner_lead",
            (Identifier)-> new OwnerLead(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> RunawayLining_ = register("runaway_lining",
            (Identifier)-> new RunawayLining(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> ActualSuffering_ = register("actual_suffering",
            (Identifier)-> new ActualSuffering(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));


    public static final DeferredItem<@NotNull Item> MotherRemains_ = register("mother_remains",
            (Identifier)-> new MotherRemains(new Item.Properties().stacksTo(64).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> TheKill_ = register("the_kill",
            (Identifier)-> new TheKill(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> EvilBelt_ = register("evil_belt",
            (Identifier)-> new EvilBelt(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));
    public static final DeferredItem<@NotNull Item> AnnualPlate_ = register("annual_plate",
            (Identifier)-> new AnnualPlate(new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM,Identifier))));

    public static DeferredItem<@NotNull Item> register(String name, Function<Identifier, ? extends Item> func) {
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
                    output.accept(MemoryItems.TheFox_);

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
                    output.accept(ReinforcedItems.RegenerationPlugin_);
                    output.accept(ReinforcedItems.ComplexComponents_);
                    output.accept(ReinforcedItems.StabilizingDevice_);




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


                    output.accept(InitItems.MotherRemains_);
                    output.accept(InitItems.TheKill_);
                    output.accept(InitItems.EvilBelt_);
                    output.accept(InitItems.AnnualPlate_);
                    output.accept(ReinforcedItems.SilentDevice_);
                    output.accept(ReinforcedItems.Strengthen_);
                    output.accept(ReinforcedItems.Accelerated_);
                    output.accept(ReinforcedItems.Excite_);
                    output.accept(ReinforcedItems.Activity_);
                    output.accept(ReinforcedItems.Dynamic_);
                    output.accept(ReinforcedItems.Contingency_);


                }).build());

    }

    public static class TagsProvider extends ItemTagsProvider {


        public static final TagKey<@NotNull Item> chestItem = createTag("chest_item");
        public static final TagKey<@NotNull Item> chestItem_iron = createTag("chest_item_iron");
        public static final TagKey<@NotNull Item> celestial = createTag("celestial");
        public static final TagKey<@NotNull Item> chestItemMeat = createTag("chest_item_meat");
        public static final TagKey<@NotNull Item> chestItemBone = createTag("chest_item_bone");
        public static final TagKey<Item> evilMother = createTag("evil_mother");

        public TagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider,  Chestitem.MODID);
        }

        @Override
        protected void addTags(HolderLookup.@NotNull Provider provider) {
            tag(evilMother).add(
                    MotherRemains_.asItem(),
                    TheKill_.asItem(),
                    AnnualPlate_.asItem(),
                    ReinforcedItems.SilentDevice_.asItem(),
                    ReinforcedItems.Accelerated_.asItem(),
                    ReinforcedItems.Excite_.asItem(),
                    ReinforcedItems.Strengthen_.asItem(),
                    ReinforcedItems.Activity_.asItem(),
                    ReinforcedItems.Dynamic_.asItem(),
                    ReinforcedItems.Contingency_.asItem(),
                    EvilBelt_.asItem()

            );

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
                    .add(ActualSuffering_.asItem())
                    .add(ReinforcedItems.RegenerationPlugin_.asItem())
                    .add(ReinforcedItems.ComplexComponents_.asItem())
                    .add(ReinforcedItems.StabilizingDevice_.asItem())
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
        private static TagKey<@NotNull Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Chestitem.MODID, name));
        }
    }
}
