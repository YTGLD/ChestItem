package com.ytgld.chest_item.event.use;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.OwnerLead;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.items.black.BloodyBelt;
import com.ytgld.chest_item.items.black.ChaosConstructor;
import com.ytgld.chest_item.items.black.OneEyedSpider;
import com.ytgld.chest_item.items.black.ShadowMint;
import com.ytgld.chest_item.items.black.celestial.Blood;
import com.ytgld.chest_item.items.black.celestial.Chaos;
import com.ytgld.chest_item.items.black.celestial.Samsara;
import com.ytgld.chest_item.items.black.celestial.TheCelestial;
import com.ytgld.chest_item.items.black.chaos_item.ChaosFortress;
import com.ytgld.chest_item.items.black.chaos_item.RunawayLining;
import com.ytgld.chest_item.items.black.chaos_item.Warmaker;
import com.ytgld.chest_item.items.black.give.BrassCoins;
import com.ytgld.chest_item.items.black.give.LeadOfEnlightenment;
import com.ytgld.chest_item.items.black.soul.*;
import com.ytgld.chest_item.items.black.soul.chaos.ChaosSeven;
import com.ytgld.chest_item.items.black.soul.treaty.Complementary;
import com.ytgld.chest_item.items.blood.BoneHead;
import com.ytgld.chest_item.items.blood.GodBlood;
import com.ytgld.chest_item.items.condensebone.AlienationDiodes;
import com.ytgld.chest_item.items.condensebone.MassEnergyConverter;
import com.ytgld.chest_item.items.condensebone.ShieldEngine;
import com.ytgld.chest_item.items.gold.*;
import com.ytgld.chest_item.items.meet.*;
import com.ytgld.chest_item.items.other.*;
import com.ytgld.chest_item.other.AttributeDataType;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.ShieldRenderHandler;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.*;

public class EventMain {
    public Set<UUID> setUUID = new HashSet<>();
    @SubscribeEvent
    public void setUUIDPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        setUUID.add(UUID.fromString("3cc7f94f-5dfc-46a2-9fa3-6be4f46d0cba"));
        setUUID.add(UUID.fromString("081c4a0e-d3d6-4a5b-9dec-3c03c92cdc8e"));
        setUUID.add(UUID.fromString("00000000-0000-3005-998f-5030997cf9c8"));
        setUUID.add(UUID.fromString("70f68910-6833-401b-988b-30ceeb675b60"));
        setUUID.add(UUID.fromString("5939a1ab-6e04-4511-af2e-2817cdda3089"));
        setUUID.add(UUID.fromString("66b8e3c0-c0f9-47ec-b016-aaa61d112c76"));

        Player player = event.getEntity();
        if (setUUID.contains(player.getUUID())) {
            if (!player.entityTags().contains(OwnerLead.name)) {
                ItemStack stack = InitItems.OwnerLead_.get().getDefaultInstance();
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putString(OwnerLead.name, player.getDisplayName().getString());
                stack.set(DataReg.tag,compoundTag);
                player.addItem(stack);
                player.addTag(OwnerLead.name);
            }
        }
    }
    @SubscribeEvent
    public void LivingDropsEvent(LivingDropsEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            if (event.getEntity() instanceof LivingEntity living) {
                float att = (float) player.getAttributeValue(AttReg.malicious_plunder);
                Collection<ItemEntity> drop = event.getDrops();
                List<ItemEntity> add = new ArrayList<>(drop);
                int number = (int) att;
                number-=1;
                if (number > 0) {
                    for (int i = 0; i < number; i++) {
                        int mth = new Random().nextInt(add.size());
                        drop.add(add.get(mth));
                    }
                }
            }
        }
    }


    public static int time = 0;

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
                if (attributes != null && !attributes.isEmpty()) {
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
                            MutableComponent co = component.copy();
                            if (stack.getItem() instanceof ITextColor iTextColor) {
                                co.setStyle(Style.EMPTY.withColor(iTextColor.colorText()));
                            }
                            evt.addTooltipLines(co);
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void BlackRl(AddAttributeTooltipsEvent evt){
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
            AttributeDataType attributeDataType = stack.get(DataReg.attributeType);
            if (attributeDataType != null) {
                Multimap<Holder<Attribute>, AttributeModifier> attributes = HashMultimap.create();
                for (AttributeDataType.Entry modifiers : attributeDataType.modifiers()){
                    attributes.put(modifiers.attribute(),modifiers.modifier());
                }
                if (!attributes.isEmpty()) {
                    attributes.values().removeIf(modifier -> skipped.isSkipped(modifier.id()));
                    evt.addTooltipLines(Component.empty());
                    attributesTooltip.add(Component.translatable("event.chest_item.equip.black")
                            .withStyle(Style.EMPTY.withColor(Light.ARGB.color(255, 255, 0, 0))));
                    AttributeUtil.applyTextFor(
                            stack,
                            attributesTooltip::add,
                            attributes,
                            AttributeTooltipContext.of(player, context, context.tooltipDisplay(), context.flag()));

                    for (Component component : attributesTooltip) {
                        MutableComponent co = component.copy();
                        co.setStyle(Style.EMPTY.withColor(Light.ARGB.color(255, 255, 0, 50)));
                        evt.addTooltipLines(co);
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
        BloodyBelt.die(event);
        BrassCoins.die(event);
        Warmaker.die(event);
        LeadOfEnlightenment.die(event);
        ChaosFortress.killArmor(event);
    }
    @SubscribeEvent
    public void CriticalHitEvent(CriticalHitEvent event){
        Lead.event(event);
        AlienationDiodes.CriticalHitEvent(event);
    }
    @SubscribeEvent
    public  void dieTotem(LivingUseTotemEvent event) {
        ChaosFortress.dieTotem(event);
        RunawayLining.dieTotem(event);
    }
    @SubscribeEvent
    public void LivingDamageEvent(LivingDamageEvent.Pre event){
        ShieldRenderHandler.thepainShield(event);
        Complementary.damage(event);
        ChaosShield(event);
        hyperplasiaShield(event);
        GodApple.event(event);
        ShadowMint.hurtAttacker(event);
        MadnessTheory.attackEXP(event);
        HardwoodTotemPole.tick(event);
        Blood.tick(event);
        ChaosConstructor.hurtOfBlood(event);
        FissionEmblem.hurt(event);
        FissionEmblem.attack(event);
        FissionEmblem.scAttack(event);
        Warmaker.hurt(event);
        ChaosFortress.hurtRes(event);
        LeadOfEnlightenment.die(event);

        //降低受到的魔法和虚空伤害，并将受到的对应伤害转换成临时的攻击伤害
        if (event.getEntity() instanceof Player player) {
            float malicious = (float) player.getAttributeValue(AttReg.malicious_transformation);
            float res = malicious - 1;
            if (res > 0) {
                if (res > 1) {
                    res = 1;
                }
                if (event.getSource().is(DamageTypes.MAGIC) || event.getSource().is(DamageTypes.DRY_OUT)){
                    event.setNewDamage(event.getNewDamage() * (1 - res));

                    float damage = event.getNewDamage();
                    float end = player.getData(AttReg.maliciousTransformationDamage) + damage;
                    float att = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    player.setData(AttReg.maliciousTransformationDamage, Math.min(end, att));
                }
            }
        }
        if (event.getSource().getEntity() instanceof Player player){
            float data = player.getData(AttReg.maliciousTransformationDamage);
            event.setNewDamage(event.getNewDamage() + data);
            player.setData(AttReg.maliciousTransformationDamage,0f);
        }
    }
    public void hyperplasiaShield (LivingDamageEvent.Pre event) {
            if (event.getEntity() instanceof Player living) {
            AttributeInstance hyperplasia_stronger = living.getAttribute(AttReg.hyperplasia_stronger);
            if (hyperplasia_stronger != null) {
                float value = (float) hyperplasia_stronger.getValue();
                float data = living.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                if (data > 0) {
                    float damage = event.getNewDamage();
                    int newData = (int) (data - 1 - ((int) (damage * 0.5f)));
                    living.setData(AttReg.hyperplasiaATTACHMENT_TYPES,(float)newData);
                    float modify = (float) Math.sqrt(value);
                    if (modify < 0.3f) {
                        modify = 0.3f;
                    }
                    float newDamage = damage * (0.3f / modify);
                    event.setNewDamage(newDamage);
                } else {
                    living.setData(AttReg.hyperplasiaATTACHMENT_TYPES, 0f);
                }
            }
        }
    }
    public void ChaosShield (LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player living) {
            float data = living.getData(AttReg.chaosWinds);
            float damageChaos = (float) living.getAttributeValue(AttReg.chaos_armor_damage);
            float damageChaosBase = (float) living.getAttributeValue(AttReg.chaos_armor);

            if (data > 0) {
                float damage = event.getNewDamage();
                float newData = data - damage;
                if (newData > 0) {
                    living.setData(AttReg.chaosWinds, (newData));
                    event.setNewDamage(0);
                } else {
                    float minModify = (float) living.getAttributeValue(AttReg.chaos_armor_min);
                    if (damage > damageChaosBase * 0.4f * minModify) {
                        if (event.getSource().getEntity() instanceof LivingEntity entity) {
                            float doDamage = event.getNewDamage() * damageChaos;
                            if (Handler.has(living,InitItems.ErosionTokens_.asItem())){
                                Vec3 playerPos = living.position();
                                int range = 8;
                                List<LivingEntity> livingEntities = living.level().getEntitiesOfClass(LivingEntity.class,
                                        new AABB(playerPos.x - range, playerPos.y - range,
                                                playerPos.z - range, playerPos.x + range,
                                                playerPos.y + range, playerPos.z + range));
                                for (LivingEntity e : livingEntities){
                                    if (!e.is(living)){
                                        if (!(e instanceof Player)){
                                            e.hurt(entity.damageSources().magic(), doDamage);
                                        }
                                    }
                                }

                            }else {
                                entity.hurt(entity.damageSources().magic(), doDamage);
                            }
                        }
                    }
                    living.setData(AttReg.chaosWinds, 0f);
                    event.setNewDamage(damage - data);
                }
            } else {
                living.setData(AttReg.chaosWinds, 0f);
            }
        }
    }

    public void ShadowShield (LivingIncomingDamageEvent event){

        if (event.getEntity() instanceof Player living) {
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
                    float damage = event.getAmount() ;
                    float newData = data - damage / 1.2f / value;
                    if (newData > 0) {
                        living.setData(AttReg.shadow_shield_ATTACHMENT_TYPES, (newData));
                        event.setAmount(0);
                    } else {
                        living.setData(AttReg.shadow_shield_ATTACHMENT_TYPES, 0f);
                        event.setAmount(damage - data);
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
        Samsara.event(event);
        Chaos.event(event);
        ChaosFortress.hurtBy2(event);

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
        ShadowShield(event);
    }
    @SubscribeEvent
    public void LivingChangeTargetEvent(LivingChangeTargetEvent event){
        TheOrderOfTheUndead.LivingChangeTargetEvent(event);
    }
    @SubscribeEvent
    public void LivingChangeTargetEvent(EntityTickEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (!player.level().isClientSide()) {
                if (player.tickCount % 300 == 0) {
                    int s = (int) (float) player.getData(AttReg.attachmentTypeBLOOD_Model);
                    if (s > 0) {
                        player.setData(AttReg.attachmentTypeBLOOD_Model, s - 1f);
                    }else {
                        player.setData(AttReg.attachmentTypeBLOOD_Model, 0f);
                    }
                }
            }
        }

    }
    @SubscribeEvent
    public void ItemStackTickEvent(ItemStackTickEvent event){
        {
            ChestInventory chestInventory = event.chestInventory;
            Player player = event.player;
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.get(DataReg.tag) == null) {
                        stack.set(DataReg.tag, new CompoundTag());
                    }
                }
            }
        }
        DrugHeal.tick(event);
        Ring.tick(event);
        Stomach.tick(event);
        GodApple.event(event);
        Kaolinite.event(event);
        EyeBook.tick(event);
        TheOrderOfTheUndead.hunger(event);
        Mutation.attrib(event);
        MadnessTheory.expOrb(event);
        Glutton.attrib(event);
        Speed.tick(event);
        ChaosSeven.tick(event);
        Silent.tick(event);
        Blood.tick(event);
        ChaosConstructor.tick(event);
        TheBell.ItemStackTickEvent(event);
        BloodyBelt.tick(event);
        FissionEmblem.tick(event);
        Warmaker.tick(event);
        LivingEntity living = event.player;
        ShieldRenderHandler.tickShield(living);

        if (ShieldRenderHandler.canHeal(living))  {
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

                if (living.tickCount % (time * 7) == 1) {
                    if (data < sNumber) {
                        living.setData(AttReg.hyperplasiaATTACHMENT_TYPES, data + 1);
                    }
                }
                if (data < 0) {
                    living.setData(AttReg.hyperplasiaATTACHMENT_TYPES, 0f);
                }
            }
        }
        if (ShieldRenderHandler.canHeal(living))  {
            AttributeInstance attributeInstance = living.getAttribute(AttReg.chaos_armor);
            if (attributeInstance != null ) {
                float timeModify = (float) living.getAttributeValue(AttReg.chaos_armor_speed);

                int doTime = (int) (100 * timeModify);

                if (doTime < 10) {
                    doTime = 10;
                }
                float value = (float) attributeInstance.getValue();
                float sNumber = value - 1;
                float data = living.getData(AttReg.chaosWinds);

                if (living.tickCount % doTime == 1) {
                    if (data < sNumber) {
                        living.setData(AttReg.chaosWinds, data + 1);
                    }
                }
                if (data < 0) {
                    living.setData(AttReg.chaosWinds, 0f);
                }
            }
        }
        if (ShieldRenderHandler.canHeal(living))   {
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
        OneEyedSpider.hurtOfBlood(event);
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


            if (event.getItemStack().getItem() instanceof ItemBlackShadow) {
                event.getToolTip().add(1, Component.literal(""));
                event.getToolTip().add(1, Component.translatable("item.chest_item.chest", Keys.KEY_MAPPING_LAZY_R.getKey().getDisplayName()).withStyle(Style.EMPTY
                        .withColor(Light.ARGB.color(255, 255, 0, 100))));
                if (event.getItemStack().getItem() instanceof SkillItem) {
                    event.getToolTip().add(1, Component.translatable("item.chest_item.skill", Keys.KEY_MAPPING_LAZY_C.getKey().getDisplayName()).withStyle(Style.EMPTY
                            .withColor(Light.ARGB.color(255, 255, 0, 100))));
                }
                if (event.getItemStack().getItem() instanceof TheImprintOfTheSoul soul) {
                    if (!soul.canRemove(event.getItemStack())) {
                        if (event.getEntity() != null && !event.getEntity().isCreative()) {
                            event.getToolTip().add(1, Component.translatable("chest_item.the_imprint_of_the_soul.can_not_remove").withStyle(Style.EMPTY
                                    .withColor(Light.ARGB.color(255, 255, 20, 80))));
                        } else {
                            event.getToolTip().add(1, Component.translatable("chest_item.the_imprint_of_the_soul.can_not_remove_and").withStyle(Style.EMPTY
                                    .withColor(Light.ARGB.color(255, 255, 150, 0))));
                        }
                    }
                }
                if (event.getItemStack().getItem() instanceof TheCelestial celestial) {
                    if (!celestial.canRemove(event.getItemStack())) {
                        if (event.getEntity() != null && !event.getEntity().isCreative()) {
                            event.getToolTip().add(1, Component.translatable("chest_item.celestial.can_not_remove").withStyle(Style.EMPTY
                                    .withColor(Light.ARGB.color(255, 255, 20, 80))));
                        } else {
                            event.getToolTip().add(1, Component.translatable("chest_item.celestial.can_not_remove_creative").withStyle(Style.EMPTY
                                    .withColor(Light.ARGB.color(255, 255, 150, 0))));
                        }
                    }
                }
            }
            if (!(event.getItemStack().getItem() instanceof ItemBlackShadow)
                    && !(event.getItemStack().getItem() instanceof TheCelestial)) {
                event.getToolTip().add(1, Component.literal(""));
                event.getToolTip().add(1, Component.translatable("item.chest_item.chest", Keys.KEY_MAPPING_LAZY_R.getKey().getDisplayName()).withStyle(ChatFormatting.GOLD));

                if (event.getItemStack().getItem() instanceof SkillItem) {
                    event.getToolTip().add(1, Component.translatable("item.chest_item.skill", Keys.KEY_MAPPING_LAZY_C.getKey().getDisplayName()).withStyle(ChatFormatting.GOLD));
                }
            }
        }
    }
    @SubscribeEvent
    public void ItemTooltipEventASD(LootTableLoadEvent event) {

        LootTable table = event.getTable();

        if (event.getName().toString().contains("chests/")){
            if (event.getName().toString().contains("underwater")
                    || event.getName().toString().contains("shipwreck")){
                table.addPool(LootPool.lootPool().name(Chestitem.MODID + "underwater")
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(InitItems.DriftingBottles_)
                                .when(LootItemRandomChanceCondition.randomChance(0.02f)))
                        .build());
            }
            if (event.getName().toString().contains("city")
                    || event.getName().toString().contains("end")){

                table.addPool(LootPool.lootPool().name(Chestitem.MODID + "city_or_end")
                        .setRolls(ConstantValue.exactly(1))

                        .add(LootItem.lootTableItem(InitItems.TheBell_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.BloodyBelt_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))

                        .build());
            }
        }
        if (event.getName().toString().contains("chests/")){
            if (event.getName().toString().contains("trial_chambers")){
                table.addPool(LootPool.lootPool().name(Chestitem.MODID + "trial_chambers")

                        .setRolls(ConstantValue.exactly(1))

                        .add(LootItem.lootTableItem(InitItems.Complementary_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.FissionEmblem_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
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


                        .add(LootItem.lootTableItem(InitItems.Blood_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Chaos_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.NineDome_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Sword_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))
                        .add(LootItem.lootTableItem(InitItems.Samsara_)
                                .when(LootItemRandomChanceCondition.randomChance(0.01f)))



                        .build());
            }

        }
    }
}
