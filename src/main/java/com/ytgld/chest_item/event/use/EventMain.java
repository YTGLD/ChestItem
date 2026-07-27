package com.ytgld.chest_item.event.use;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.OwnerLead;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.event.OppressionHandler;
import com.ytgld.chest_item.event.Keys;
import com.ytgld.chest_item.event.ShiedHandler;
import com.ytgld.chest_item.event.SwordHandler;
import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.items.black.ChaosConstructor;
import com.ytgld.chest_item.items.black.ShadowMint;
import com.ytgld.chest_item.items.black.celestial.Blood;
import com.ytgld.chest_item.items.black.celestial.Chaos;
import com.ytgld.chest_item.items.black.celestial.CommonCelestial;
import com.ytgld.chest_item.items.black.celestial.Samsara;
import com.ytgld.chest_item.items.black.chaos_item.ChaosFortress;
import com.ytgld.chest_item.items.black.chaos_item.Warmaker;
import com.ytgld.chest_item.items.black.give.BrassCoins;
import com.ytgld.chest_item.items.black.give.LeadOfEnlightenment;
import com.ytgld.chest_item.items.black.soul.*;
import com.ytgld.chest_item.items.black.soul.chaos.ChaosSeven;
import com.ytgld.chest_item.items.black.soul.treaty.Complementary;
import com.ytgld.chest_item.items.other.blood.BoneHead;
import com.ytgld.chest_item.items.other.blood.GodBlood;
import com.ytgld.chest_item.items.condensebone.AlienationDiodes;
import com.ytgld.chest_item.items.condensebone.MassEnergyConverter;
import com.ytgld.chest_item.items.condensebone.ShieldEngine;
import com.ytgld.chest_item.items.evil_mother.*;
import com.ytgld.chest_item.items.evil_mother.decay.Triangle;
import com.ytgld.chest_item.items.gold.*;
import com.ytgld.chest_item.items.meet.*;
import com.ytgld.chest_item.items.memory.items.Contradiction;
import com.ytgld.chest_item.items.other.*;
import com.ytgld.chest_item.items.other.sword.Adjudication;
import com.ytgld.chest_item.items.reinforced.ReinforcedBaseItem;
import com.ytgld.chest_item.items.reinforced.meat.ComplexComponents;
import com.ytgld.chest_item.items.tool.WallowAxe;
import com.ytgld.chest_item.other.AttributeDataType;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.ShieldRenderHandler;
import com.ytgld.chest_item.renderer.light.Light;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import com.ytgld.chest_item.renderer.particle.other.SwordEnergyOption;
import com.ytgld.chest_item.sounds.Sounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.util.AttributeTooltipContext;
import net.neoforged.neoforge.common.util.AttributeUtil;
import net.neoforged.neoforge.event.AddAttributeTooltipsEvent;
import net.neoforged.neoforge.event.GatherSkippedAttributeTooltipsEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import javax.annotation.Nullable;
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
        setUUID.add(UUID.fromString("aa31ebcd-4054-4a14-aa37-e57d89bc15ff"));

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

    public static int time = 0;
    @SubscribeEvent
    public void ItemTooltipEvent(ClientTickEvent.Pre event){
        time++;
    }
    @SubscribeEvent
    public void attack(SweepAttackEvent event){
        TheKill.attack(event);
    }
    @SubscribeEvent
    public void EntityTickEvent(EntityTickEvent.Post event){
       SwordHandler.tickSwordIntent(event);
       SwordHandler.tickAttackHurt(event);
        EvilMother.attrib(event);
    }
    @SubscribeEvent
    public void  knock(LivingKnockBackEvent event){
        EvilBelt.knock(event);
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
        AttributeDataType attributeDataType = stack.get(DataReg.attributeType);
        if (player!=null) {
            if (stack.getItem() instanceof ReinforcedBaseItem reinforcedBaseItem) {
                Multimap<Holder<Attribute>, AttributeModifier> attributes = reinforcedBaseItem.doAttribute(player);
                if (!attributes.isEmpty()) {
                    attributes.values().removeIf(modifier -> skipped.isSkipped(modifier.id()));
                    evt.addTooltipLines(Component.empty());

                    attributesTooltip.add(Component.translatable("event.chest_item.reinforced").withStyle(Style.EMPTY.withColor(Light.ARGB.color(50,80,120,105))));

                    AttributeUtil.applyTextFor(
                            stack,
                            attributesTooltip::add,
                            attributes,
                            AttributeTooltipContext.of(player, context, context.tooltipDisplay(),context.flag()));


                    for (Component component : attributesTooltip) {
                        MutableComponent co = component.copy();
                        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(reinforcedBaseItem.theColor())));
                        evt.addTooltipLines(co);
                    }
                }
            }
            if (stack.getItem() instanceof Terror terror) {
                Multimap<Holder<Attribute>, AttributeModifier> attributes = terror.muAttribute(player,stack);
                if (attributes != null) {
                    if (attributeDataType != null){
                        for (AttributeDataType.Entry entry :attributeDataType.modifiers()){
                            attributes.put(entry.attribute(),entry.modifier());
                        }
                    }
                    if (!attributes.isEmpty()) {
                        attributes.values().removeIf(modifier -> skipped.isSkipped(modifier.id()));
                        evt.addTooltipLines(Component.empty());
                        if (!(stack.getItem() instanceof ItemBlackShadow)) {
                            if (stack.getItem() instanceof EvilMother evilMother) {
                                attributesTooltip.add(Component.translatable("event.chest_item.equip").withStyle(Style.EMPTY.withColor(evilMother.theColor())));
                            } else {
                                attributesTooltip.add(Component.translatable("event.chest_item.equip").withStyle(ChatFormatting.GOLD));
                            }
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
                            } else if (stack.getItem() instanceof EvilMother evilMother) {
                                MutableComponent co = component.copy();
                                co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(evilMother.theColor())));
                                evt.addTooltipLines(co);
                            } else {
                                MutableComponent co = component.copy();
                                if (stack.getItem() instanceof ITextColor color) {
                                    co.setStyle(Style.EMPTY.withColor(color.colorText()));
                                }
                                evt.addTooltipLines(co);
                            }
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
        WarGodCommand.healOFf(event);
    }
    @SubscribeEvent
    public void LivingDeathEvent(PlayerEvent.PlayerRespawnEvent event){
        DefyLife.PlayerRespawnEvent(event);
    }
    @SubscribeEvent
    public void LivingDeathEvent(LivingDeathEvent event){
        Mutation.die(event);
        ChaosSeven.die(event);
        BrassCoins.die(event);
        Warmaker.die(event);
        LeadOfEnlightenment.die(event);
        ChaosFortress.killArmor(event);
    }
    @SubscribeEvent
    public void CriticalHitEvent(CriticalHitEvent event){
        Lead.event(event);
        AlienationDiodes.CriticalHitEvent(event);
        AnnualPlate.dieAnnualPlate(event);
        WallowAxe.cit(event);
    }
    @SubscribeEvent
    public  void dieTotem(LivingUseTotemEvent event) {
        ChaosFortress.dieTotem(event);
    }

    @SubscribeEvent
    public void LivingDamageEvent(LivingDamageEvent.Pre event){
        thepainShield(event);
        ShiedHandler.ShadowShield(event);
        Adjudication.addSword(event);

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
        WallowAxe.damage(event);
        WarGodCommand.notDie(event);
        Triangle.hurtSize(event);


        if (event.getEntity() instanceof Player player) {
            AttributeInstance resistance = player.getAttribute(AttReg.resistance);
            if (resistance != null) {
                float value = (float) resistance.getValue();
                float base = (float) resistance.getBaseValue();
                if (value != base) {
                    event.setNewDamage(event.getNewDamage() * ((1 - value) + 1));
                }
            }
        }
    }
    public static void thepainShield (LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide()) {
            AttributeInstance stronger = player.getAttribute(AttReg.painShield_number);
            AttributeInstance number = player.getAttribute(AttReg.painShield_number);
            float base = 0.15f;
            float minDamage = 0.3f;
            if (stronger != null && number != null) {
                float value = (float) stronger.getValue();
                float theNumber = (float) number.getValue();
                if (theNumber <= 0) {
                    if (player.getData(AttReg.painShield) > 0) {
                        player.setData(AttReg.painShield, 0f);
                    }
                }
                float data = player.getData(AttReg.painShield);
                if (data > 0) {
                    float damage = event.getNewDamage();
                    float newData = data - 0.5f - (damage * 0.2f);
                    Handler.setDataValue(AttReg.painShield,player,(float)newData);
                    float modify = (float) Math.sqrt(value) * 1.25f;
                    if (modify < minDamage) {
                        modify = minDamage;
                    }
                    float newDamage = damage * (base / modify);
                    float x = 3;
                    x -= (float) Math.sqrt(value);
                    if (x < 1) {
                        x = 1;
                    }
                    if (data > 4+x) {
                        if (event.getSource().getEntity() instanceof LivingEntity living1) {
                            living1.hurt(living1.damageSources().playerAttack(player),
                                    (float) (newDamage
                                            + player.getAttributeValue(Attributes.MAX_HEALTH) * 0.15f
                                            + player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.75f));
                        }
                        int timeMeet = (int) (1200 * Math.sqrt(Math.sqrt(theNumber)));
                        int maxMeet = (int) (7 + theNumber / 5);

                        player.addEffect(new MobEffectInstance(Effects.Pain,timeMeet,0));
                        @Nullable MobEffectInstance mobEffectInstance = player.getEffect(Effects.Pain);
                        if (mobEffectInstance != null) {
                            if (mobEffectInstance.getAmplifier()<maxMeet) {
                                player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(), mobEffectInstance.getDuration()+timeMeet,
                                        mobEffectInstance.getAmplifier() + 1,false,false));
                            }else {
                                player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),timeMeet*maxMeet, maxMeet,false,false));
                            }
                        }

                        event.setNewDamage(0);
                    }else {
                        event.setNewDamage(newDamage);
                    }
                    player.level().playSound(null, player.blockPosition(), Sounds.Heart.value(), SoundSource.PLAYERS, 1.5f, 1);
                } else {
                    Handler.setDataValue(AttReg.painShield,player, 0f);
                    int time = 200;
                    addCooldown(player, (int) player.getAttributeValue(AttReg.shield_cooldown));
                }
            }
        }
    }

    public static void addCooldown(LivingEntity living,int time){
        if (living instanceof Player player) {
            if (!player.level().isClientSide()) {
                Handler.setDataValue(AttReg.theHeartCooldown,player, time);
            }
        }
    }
    public void hyperplasiaShield (LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player living) {
            float shadow_shield = living.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
            if (shadow_shield > 0) {
                return;
            }
            if (!living.level().isClientSide()) {
                AttributeInstance hyperplasia_stronger = living.getAttribute(AttReg.hyperplasia_stronger);
                if (hyperplasia_stronger != null) {
                    float value = (float) hyperplasia_stronger.getValue();
                    float data = living.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                    if (data > 0) {
                        float damage = event.getNewDamage();
                        int newData = (int) (data - 1 - ((int) (damage * 0.5f)));
                        Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES, living, (float) newData);
                        float modify = (float) Math.sqrt(value);
                        if (modify < 0.3f) {
                            modify = 0.3f;
                        }
                        float newDamage = damage * (0.3f / modify);
                        if (!ComplexComponents.absorptionDamage(living)) {
                            event.setNewDamage(newDamage);
                        } else {
                            event.setNewDamage(0);
                        }
                    } else {
                        Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES, living, 0f);
                        AttributeInstance time = living.getAttribute(AttReg.hyperplasiaCooldown);
                        if (time != null) {
                            int cooldown = (int) time.getValue();
                            Handler.setDataValue(AttReg.hyperplasiaCooldownAttachmentType, living, cooldown);
                        }
                    }
                }
            }
        }
    }
    public static void upDataHyperplasiaCooldown(Player player){
        if (player.tickCount % 20 == 1) {
            int c = player.getData(AttReg.hyperplasiaCooldownAttachmentType);
            int newV = c - 1;
            if (newV < 0) {
                newV = 0;
            }
            Handler.setDataValue(AttReg.hyperplasiaCooldownAttachmentType, player, newV);
        }
    }
    public void ChaosShield (LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Player living) {
            float shadow_shield = living.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
            if (shadow_shield > 0) {
                return;
            }
            float data = living.getData(AttReg.chaosWinds);
            float damageChaos = (float) living.getAttributeValue(AttReg.chaos_armor_damage);
            float damageChaosBase = (float) living.getAttributeValue(AttReg.chaos_armor);

            if (data > 0) {
                float damage = event.getNewDamage();
                float newData = data - damage;
                if (newData > 0) {
                    Handler.setDataValue(AttReg.chaosWinds,living, (newData));
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
                    Handler.setDataValue(AttReg.chaosWinds,living, 0f);
                    event.setNewDamage(damage - data);
                }
            } else {
                Handler.setDataValue(AttReg.chaosWinds,living, 0f);
            }
        }
    }
    @SubscribeEvent
    public void attackEXP(LivingExperienceDropEvent event){
        MadnessTheory.attackEXP(event);
        ChaosSeven.exp(event);
        GoldCheese.event(event);
        NuclearReaction.event(event);

        if (event.getAttackingPlayer() instanceof Player player) {
            AttributeInstance xp = player.getAttribute(AttReg.xp_drop);
            if (xp != null) {
                float value = (float) xp.getValue();
                float base = (float) xp.getBaseValue();
                if (value != base) {
                    event.setDroppedExperience((int) (event.getDroppedExperience() * value));
                }
            }
        }

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
            //1.3
            AttributeInstance instability = living.getAttribute(AttReg.instability);
            if (instability != null) {
                //1.3
                float value = (float) instability.getValue();

                float v1 = value - 1;
                //0.3
                if (v1>0) {
                    //-0.15
                    //0.36
                    float apply = Mth.nextFloat(RandomSource.create(), -v1 * 0.5f,v1*1.2f);
                    //0.75 (min)
                    //1.8 (max)
                    apply *= 5f;
                    float damage = event.getAmount() + apply;
                    event.setAmount(damage);
                }
            }

            AttributeInstance instability_low = living.getAttribute(AttReg.instability_low);
            if (instability_low != null) {
                //1.3
                float value = (float) instability_low.getValue();

                float v1 = value - 1;
                if (v1>0) {
                    float apply = Mth.nextFloat(RandomSource.create(), -v1,v1*1.2f);
                    apply *= 6f;
                    float damage = event.getAmount() + apply;
                    event.setAmount(damage);
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
        FissionEmblem.tick(event);
        Warmaker.tick(event);
        Player living = event.player;
        ShieldRenderHandler.tickShield(living);
        upDataHyperplasiaCooldown(living);

        if (ShieldRenderHandler.canHeal(living)){
            int cooldown = living.getData(AttReg.hyperplasiaCooldownAttachmentType);
            if (cooldown <= 0) {
                AttributeInstance hyperplasia = living.getAttribute(AttReg.hyperplasia);
                AttributeInstance hyperplasia_speed = living.getAttribute(AttReg.hyperplasia_speed);
                if (hyperplasia != null && hyperplasia_speed != null) {
                    float time = (float) (15 * hyperplasia_speed.getValue());
                    if (time < 1) {
                        time = 1;
                    }
                    float data = living.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
                    if (living.tickCount % (time * 7) == 1) {
                        Handler.addHyperplasiaData(living, 1);
                    }
                    if (data < 0) {
                        Handler.setDataValue(AttReg.hyperplasiaATTACHMENT_TYPES, living, 0f);
                    }
                }
            }
        }
        if (ShieldRenderHandler.canHeal(living)){
            AttributeInstance attributeInstance = living.getAttribute(AttReg.chaos_armor);
            if (attributeInstance != null ) {
                float timeModify = (float) living.getAttributeValue(AttReg.chaos_armor_speed);

                int doTime = (int) (100 * timeModify);

                if (doTime < 10) {
                    doTime = 10;
                }
                float sNumber = (float) attributeInstance.getValue();
                float data = living.getData(AttReg.chaosWinds);

                if (living.tickCount % doTime == 1) {
                    if (data < sNumber) {
                        Handler.setDataValue(AttReg.chaosWinds,living, data + 1);
                    }
                }
                if (data < 0) {
                    Handler.setDataValue(AttReg.chaosWinds,living, 0f);
                }
            }
        }
        upDataShadowCooldown(living);
        if (ShieldRenderHandler.canHeal(living) && Contradiction.ContradictionTooltip.canHeal(living)) {
            if (living.getData(AttReg.shadow_shield_cooldown_dataAttachmentType) <= 0) {
                AttributeInstance shadow_shield = living.getAttribute(AttReg.shadow_shield);
                AttributeInstance shadow_shield_speed = living.getAttribute(AttReg.shadow_shield_speed);

                if (shadow_shield != null && shadow_shield_speed != null) {
                    float time = 300;
                    time /= (float) shadow_shield_speed.getValue();
                    if (time < 20) {
                        time = 20f;
                    }
                    float data = living.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
                    if (living.tickCount % (int) time == 1) {
                        Handler.addShadowBlackShieldData(living,1);
                    }
                    if (data < 0) {
                        Handler.setDataValue(AttReg.shadow_shield_ATTACHMENT_TYPES, living, 0f);
                    }
                }
            }
        }
    }
    public static void upDataShadowCooldown(Player player){
        if (player.tickCount % 20 == 1) {
            int c = player.getData(AttReg.shadow_shield_cooldown_dataAttachmentType);
            int newV = c - 1;
            if (newV < 0) {
                newV = 0;
            }
            Handler.setDataValue(AttReg.shadow_shield_cooldown_dataAttachmentType, player, newV);
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
    public void LeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        OppressionHandler.lightHurtEmp(event);
    }
    @SubscribeEvent
    public void EntityTickEvent(EntityTickEvent.Pre event) {
        OppressionHandler.tickCanNotLooking(event);
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
        if (event.getItemStack().getItem() instanceof ReinforcedBaseItem item) {
            event.getToolTip().add(1, Component.literal(""));
            event.getToolTip().add(1, Component.translatable("item.chest_item.reinforced.equipped").withStyle(Style.EMPTY
                    .withColor(item.theColor())));
        }
        if (event.getItemStack().getItem() instanceof ItemBase) {
            if (event.getItemStack().getItem() instanceof EvilMother evilMother) {
                event.getToolTip().add(1, Component.literal(""));
                event.getToolTip().add(1, Component.translatable("item.chest_item.chest",Keys.KEY_MAPPING_LAZY_R.getKey().getDisplayName()).withStyle(Style.EMPTY
                        .withColor(evilMother.theColor())));

            }
            if (event.getItemStack().getItem() instanceof ItemBlackShadow) {
                event.getToolTip().add(1, Component.literal(""));
                event.getToolTip().add(1, Component.translatable("item.chest_item.chest",Keys.KEY_MAPPING_LAZY_R.getKey().getDisplayName()).withStyle(Style.EMPTY
                        .withColor(Light.ARGB.color(255, 255, 0, 100))));
                if (event.getItemStack().getItem() instanceof CommonCelestial celestial) {
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
                    && !(event.getItemStack().getItem() instanceof CommonCelestial)
                    && !(event.getItemStack().getItem() instanceof EvilMother)) {
                event.getToolTip().add(1, Component.literal(""));
                event.getToolTip().add(1, Component.translatable("item.chest_item.chest", Keys.KEY_MAPPING_LAZY_R.getKey().getDisplayName()).withStyle(ChatFormatting.GOLD));
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
