package com.ytgld.chest_item.event.use;

import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.ConfigC;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.items.black.CorruptionCrystal;
import com.ytgld.chest_item.items.black.DryBones;
import com.ytgld.chest_item.items.black.EvilThoughtsForgeDreams;
import com.ytgld.chest_item.items.black.ShadowMint;
import com.ytgld.chest_item.items.black.give.BrassCoins;
import com.ytgld.chest_item.items.black.soul.*;
import com.ytgld.chest_item.items.black.soul.chaos.ChaosSeven;
import com.ytgld.chest_item.items.blood.BoneHead;
import com.ytgld.chest_item.items.blood.GodBlood;
import com.ytgld.chest_item.items.blood.LifeCrystal;
import com.ytgld.chest_item.items.condensebone.AlienationDiodes;
import com.ytgld.chest_item.items.condensebone.MassEnergyConverter;
import com.ytgld.chest_item.items.condensebone.QualitativeComponents;
import com.ytgld.chest_item.items.condensebone.ShieldEngine;
import com.ytgld.chest_item.items.gold.*;
import com.ytgld.chest_item.items.iron.IronCube;
import com.ytgld.chest_item.items.iron.IronHeart;
import com.ytgld.chest_item.items.meet.*;
import com.ytgld.chest_item.items.other.*;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEnchantItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.List;

public class EventMain {
    public static int time = 0;
    @SubscribeEvent
    public void ItemTooltipEvent(LevelTickEvent.Pre event){
        time++;
    }

    @SubscribeEvent
    public void AddAttributeTooltipsEvent(AddAttributeTooltipsEvent evt){
        AttributeTooltipContext context = evt.getContext();
        ItemStack stack = evt.getStack();
        GatherSkippedAttributeTooltipsEvent skipped =
                NeoForge.EVENT_BUS.post(new GatherSkippedAttributeTooltipsEvent(stack, context));

        if (skipped.isSkippingAll()) {
            return;
        }
        List<Component> attributesTooltip = new ArrayList<>();
        Player player = context.player();
        if (player!=null) {
            if (stack.getItem() instanceof Terror terror) {
                Multimap<Holder<Attribute>, AttributeModifier> attributes = terror.muAttribute(player,stack);
                if (attributes != null) {
                    attributes.values().removeIf(modifier -> skipped.isSkipped(modifier.id()));
                    evt.addTooltipLines(Component.empty());
                    if (!(stack.getItem() instanceof ItemBlackShadow)) {
                        attributesTooltip.add(Component.translatable("event.chest_item.equip").withStyle(ChatFormatting.GOLD));
                    }

                    AttributeUtil.applyTextFor(
                            stack,
                            attributesTooltip::add,
                            attributes,
                            AttributeTooltipContext.of(player, context, context.tooltipDisplay(), context.flag()));
                    Component blackShadow = Component.translatable("event.chest_item.equip")
                            .withStyle(Style.EMPTY.withColor(Light.ARGB.color(255, 255, 0, 100)));

                    if (stack.getItem() instanceof ItemBlackShadow) {
                        evt.addTooltipLines(blackShadow);
                    }
                    for (Component component : attributesTooltip) {
                        if (stack.getItem() instanceof ItemBlackShadow) {
                            MutableComponent co = component.copy();
                            co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80EE82EE)));
                            evt.addTooltipLines(co);
                        } else {
                            evt.addTooltipLines(component);
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void ItemStackAttackEvent(ItemStackAttackEvent event){
        GodBlood.attack(event);
        Conch.event(event);
    }
    @SubscribeEvent
    public void LivingHealEvent(LivingHealEvent event){
        if (event.getEntity() instanceof LivingEntity living){
            AttributeInstance heal = living.getAttribute(AttReg.heal);
            if (heal!=null){
                float attack = (float) heal.getValue();
                event.setAmount(event.getAmount()*(attack));
            }
        }
        LifeStone.tick(event);
        Silent.livingHealEventSilent_(event);
    }
    @SubscribeEvent
    public void exp(LivingExperienceDropEvent event) {
        ChaosSeven.exp(event);
    }
    @SubscribeEvent
    public void LivingDeathEvent(LivingDeathEvent event){
        Mutation.die(event);
        ChaosSeven.die(event);
        BrassCoins.die(event);
    }
    @SubscribeEvent
    public void CriticalHitEvent(CriticalHitEvent event){
        Lead.event(event);
        AlienationDiodes.CriticalHitEvent(event);
    }
    @SubscribeEvent
    public void LivingDamageEvent(LivingDamageEvent.Pre event){
        GodApple.event(event);
        ShadowShield(event);
        ShadowMint.hurtAttacker(event);
        MadnessTheory.attackEXP(event);
    }
    public void ShadowShield (LivingDamageEvent.Pre event){

        if (event.getEntity() instanceof LivingEntity living) {

            if (living instanceof Player player) {
                ChestInventory chestInventory = Handler.getItem(player);
                if (chestInventory != null) {
                    if (!player.level().isClientSide()) {
                        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                            ItemStack stack = chestInventory.getItem(i);
                            if (stack.is(InitItems.ShadowMint_)) {
                                return;
                            }
                        }
                    }
                }
            }



            AttributeInstance shadow_shield_stronger = living.getAttribute(AttReg.shadow_shield_stronger);
            if (shadow_shield_stronger != null) {
                float value = (float) shadow_shield_stronger.getValue();
                float data = living.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
                if (data > 0) {
                    float damage = event.getNewDamage() ;
                    float newData = data - damage / 4.5f / value;
                    if (newData > 0) {
                        living.setData(AttReg.shadow_shield_ATTACHMENT_TYPES, (newData));
                        event.setNewDamage(0);
                    } else {
                        living.setData(AttReg.shadow_shield_ATTACHMENT_TYPES, 0f);
                        event.setNewDamage(damage - data);
                    }
                }else {
                    living.setData(AttReg.shadow_shield_ATTACHMENT_TYPES, 0f);
                }
            }
        }
    }

    @SubscribeEvent
    public void attackEXP(LivingExperienceDropEvent event){
        MadnessTheory.attackEXP(event);
    }
    @SubscribeEvent
    public void pick(PlayerXpEvent.PickupXp event){
        MadnessTheory.attackEXP(event);
    }
    @SubscribeEvent
    public void LivingIncomingDamageEvent(LivingIncomingDamageEvent event){
        Knife.event(event);
        ArmorStone.tick(event);
        StrongerStone.tick(event);
        WindKnife.event(event);
        GiantHeart.LivingIncomingDamageEvent(event);
        HeavyBlade.LivingIncomingDamageEvent(event);
        TheOrderOfTheUndead.notMagicDamage(event);
        Mutation.notMagicDamage(event);
        Silent.hurtSilent_(event);
        ShieldEngine.LivingIncomingDamageEvent(event);
        MassEnergyConverter.LivingIncomingDamageEvent(event);




        if (event.getEntity() instanceof LivingEntity living) {
            AttributeInstance hyperplasia_stronger = living.getAttribute(AttReg.hyperplasia_stronger);
            if (hyperplasia_stronger != null) {
                float value = (float) hyperplasia_stronger.getValue();
                float data = living.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                if (data > 0) {
                    float damage = event.getAmount() ;
                    float newData = data - damage / 2.25f / value;
                    if (newData > 0) {
                        living.setData(AttReg.hyperplasiaATTACHMENT_TYPES, (newData));
                        event.setAmount(0);
                    } else {
                        living.setData(AttReg.hyperplasiaATTACHMENT_TYPES, 0f);
                        event.setAmount(damage - data);
                    }
                }else {
                    living.setData(AttReg.hyperplasiaATTACHMENT_TYPES, 0f);
                }
            }
        }

        if (event.getSource().getEntity() instanceof LivingEntity living){
            AttributeInstance instability = living.getAttribute(AttReg.instability);
            if (instability != null) {
                float value = (float) instability.getValue();
                float v1 = value - 1;
                if (v1>0) {
                    float apply = Mth.nextFloat(RandomSource.create(), -v1,v1*1.15f);
                    event.setAmount(event.getAmount()*(1+apply));
                }else if (v1 != 0){
                    if (v1 < 0) {
                        v1 = -v1;
                    }
                    event.setAmount(event.getAmount()*(1+v1));
                }
            }

            AttributeInstance instability_low = living.getAttribute(AttReg.instability_low);
            if (instability_low != null) {
                float value = (float) instability_low.getValue();
                float v1 = value - 1;
                if (v1>0) {
                    float apply = Mth.nextFloat(RandomSource.create(), -v1*1.15f,v1);
                    event.setAmount(event.getAmount()*(1+apply));
                }else if (v1 != 0){
                    if (v1 < 0) {
                        v1 = -v1;
                    }
                    event.setAmount(event.getAmount()*(1+v1));
                }
            }
        }
    }
    @SubscribeEvent
    public void LivingChangeTargetEvent(LivingChangeTargetEvent event){
        TheOrderOfTheUndead.LivingChangeTargetEvent(event);
    }
    @SubscribeEvent
    public void ItemStackTickEvent(ItemStackTickEvent event){
        GodBlood.tick(event);
        DrugHeal.tick(event);
        LifeCrystal.tick(event);
        Stone.tick(event);
        Ring.tick(event);
        SeparateRune.tick(event);
        PainRune.tick(event);
        UndeadRune.tick(event);
        Stomach.tick(event);
        SelfIncreasingHeart.tick(event);
        GodApple.event(event);
        Kaolinite.event(event);
        GodApple.event2(event);
        EyeBook.tick(event);
        IronHeart.tick(event);
        IronCube.ItemStackTickEvent(event);
        ScarHeart.tick(event);
        LifeCoin.tick(event);
        EvilThoughtsForgeDreams.ItemStackTickEvent(event);
        DryBones.ItemStackTickEvent(event);
        ShadowMint.ItemStackTickEvent(event);
        TheOrderOfTheUndead.hunger(event);
        TheOrderOfTheUndead.attrib(event);
        Mutation.attrib(event);
        MadnessTheory.expOrb(event);
        Glutton.attrib(event);
        Speed.tick(event);
        ChaosSeven.tick(event);
        Silent.tick(event);
        CorruptionCrystal.tick(event);
        QualitativeComponents.tick(event);





        LivingEntity living = event.player;
        {
            AttributeInstance hyperplasia = living.getAttribute(AttReg.hyperplasia);
            AttributeInstance hyperplasia_speed = living.getAttribute(AttReg.hyperplasia_speed);

            if (hyperplasia != null && hyperplasia_speed != null) {
                float time = (float) (15 * hyperplasia_speed.getValue());
                if (time < 1) {
                    time = 1;
                }

                float value = (float) hyperplasia.getValue();
                float sNumber = value - 1;
                float data = living.getData(AttReg.hyperplasiaATTACHMENT_TYPES);

                if (living.tickCount % (time * 20) == 1) {
                    if (data < sNumber) {
                        living.setData(AttReg.hyperplasiaATTACHMENT_TYPES, data + 1);
                        if (ConfigC.config.hyperplasiaMusic.get()) {
                            living.level().playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.AMBIENT, 0.6f, 0.6f);
                        }
                    }
                }
                if (data < 0) {
                    living.setData(AttReg.hyperplasiaATTACHMENT_TYPES, 0f);
                }
            }
        }
        {
            AttributeInstance shadow_shield = living.getAttribute(AttReg.shadow_shield);
            AttributeInstance shadow_shield_speed = living.getAttribute(AttReg.shadow_shield_speed);

            if (shadow_shield != null && shadow_shield_speed != null) {
                float time = 300;
                time /= (float) shadow_shield_speed.getValue();
                if (time < 20) {
                    time = 20f;
                }
                float value = (float) shadow_shield.getValue();
                float sNumber = value - 1;
                float data = living.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);

                if (living.tickCount % (int)time == 1) {
                    if (data < sNumber) {
                        living.setData(AttReg.shadow_shield_ATTACHMENT_TYPES, data + 1);
                        if (ConfigC.config.hyperplasiaMusic.get()) {
                            living.level().playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.AMBIENT, 0.8f, 0.8f);
                        }
                    }
                }
                if (data < 0) {
                    living.setData(AttReg.shadow_shield_ATTACHMENT_TYPES, 0f);
                }
            }
        }
    }

    @SubscribeEvent
    public void tick(LivingEntityUseItemEvent.Finish event) {
        Stomach.tick(event);
        MeatBall.tick(event);
        SelfIncreasingHeart.tick(event);
        SpeedHeart.eat(event);
        Glutton.eatFinish(event);
    }
    @SubscribeEvent
    public void LivingExperienceDropEvent(LivingExperienceDropEvent event) {
        GoldCheese.event(event);
        NuclearReaction.event(event);
    }
    @SubscribeEvent
    public void PlayerEnchantItemEvent(PlayerEnchantItemEvent event) {
        GoldCheese.event(event);
    }
    @SubscribeEvent
    public void attack(LivingEntityUseItemEvent.Start event){
        BoneHead.event(event);
        Glutton.eatStart(event);
    }
    @SubscribeEvent
    public void tooltip(ItemTooltipEvent event){
        if (event.getItemStack().getItem() instanceof ItemBase) {
            if (event.getItemStack().getItem() instanceof ItemBlackShadow){
                event.getToolTip().add(1, Component.literal(""));
                event.getToolTip().add(1, Component.translatable("item.chest_item.chest").withStyle(Style.EMPTY
                        .withColor(Light.ARGB.color(255, 255, 0, 100))));
                if (event.getItemStack().getItem() instanceof TheImprintOfTheSoul soul) {
                    if (!soul.canRemove(event.getItemStack())) {
                        if (event.getEntity() !=null && !event.getEntity().isCreative()) {
                            event.getToolTip().add(1, Component.translatable("chest_item.the_imprint_of_the_soul.can_not_remove").withStyle(Style.EMPTY
                                    .withColor(Light.ARGB.color(255, 255, 20, 80))));
                        }else {
                            event.getToolTip().add(1, Component.translatable("chest_item.the_imprint_of_the_soul.can_not_remove_and").withStyle(Style.EMPTY
                                    .withColor(Light.ARGB.color(255, 255, 150, 0))));
                        }
                    }
                }
            }else {
                event.getToolTip().add(1, Component.literal(""));
                event.getToolTip().add(1, Component.translatable("item.chest_item.chest").withStyle(ChatFormatting.GOLD));
            }
        }
    }
    @SubscribeEvent
    public void ItemTooltipEventASD(LootTableLoadEvent event) {

        LootTable table = event.getTable();
        if (event.getName().toString().contains("chests/")){
            if (event.getName().toString().contains("trial_chambers")){
                table.addPool(LootPool.lootPool().name(Chestitem.MODID + "trial_chambers")

                        .setRolls(ConstantValue.exactly(1))

                        .add(LootItem.lootTableItem(InitItems.Life_Crystal)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.God_blood)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Drug_Heal)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Bone_Head)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Conch_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Lead_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Stone_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Knife_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Ring_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))


                        .add(LootItem.lootTableItem(InitItems.Life_Stone)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Armor_Stone)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Stronger_Stone)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))

                        .add(LootItem.lootTableItem(InitItems.Separate_Rune)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Undead_Rune)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Pain_Rune)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))


                        .add(LootItem.lootTableItem(InitItems.Heart_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Meat_Ball)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Self_Increasing_Heart)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Stomach_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))

                        .add(LootItem.lootTableItem(InitItems.Battery_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))

                        .add(LootItem.lootTableItem(InitItems.MAGIC_IRON)
                                .when(LootItemRandomChanceCondition.randomChance(0.09f)))
                        .add(LootItem.lootTableItem(InitItems.SpeedHeart_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Kaolinite_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.EyeBook_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Fission_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.FleshAndBloodGears_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))



                        .build());

            }
        }
        if (event.getName().toString().contains("pots/")){
            if (event.getName().toString().contains("trial_chambers")){
                table.addPool(LootPool.lootPool().name(Chestitem.MODID + "pots")
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(InitItems.MAGIC_IRON).when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Heart_).when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .build());

            }
        }
        if (event.getName().toString().contains("chests/")) {
            if (event.getName().toString().contains("dungeon")
                    ||event.getName().toString().contains("mineshaft")
                    ||event.getName().toString().contains("ocean")
                    ||event.getName().toString().contains("bastion")
                    ||event.getName().toString().contains("treasure")
                    ||event.getName().toString().contains("ancient")) {
                table.addPool(LootPool.lootPool().name(Chestitem.MODID + "dungeon")

                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(InitItems.Life_Crystal)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.God_blood)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Drug_Heal)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Bone_Head)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Conch_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Lead_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Stone_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Knife_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Ring_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))


                        .add(LootItem.lootTableItem(InitItems.Life_Stone)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Armor_Stone)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Stronger_Stone)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))

                        .add(LootItem.lootTableItem(InitItems.Separate_Rune)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Undead_Rune)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Pain_Rune)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))


                        .add(LootItem.lootTableItem(InitItems.Heart_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Meat_Ball)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Self_Increasing_Heart)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Stomach_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))

                        .add(LootItem.lootTableItem(InitItems.Battery_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))

                        .add(LootItem.lootTableItem(InitItems.MAGIC_IRON)
                                .when(LootItemRandomChanceCondition.randomChance(0.09f)))
                        .add(LootItem.lootTableItem(InitItems.SpeedHeart_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Kaolinite_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.EyeBook_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Fission_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.FleshAndBloodGears_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))




                        .build());
            }

        }
    }

}
