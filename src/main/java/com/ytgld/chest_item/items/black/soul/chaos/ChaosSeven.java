package com.ytgld.chest_item.items.black.soul.chaos;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;


/**
 * 传闻在混沌大战时期，有一尊混沌仙帝以一己之力击退万族，并为人族创建了各种的术法神通
 * <p>
 * ...在混沌仙帝陨落之后，有七个人得到了它意志的认可，它们分别是：
 * <p>
 * 窥眸
 * <p>
 * 死兆
 * <p>
 * 颠倒
 * <p>
 * 救赎
 * <p>
 * 愚者
 * <p>
 * 洞悉
 * <p>
 * 起始
 * <p>
 * 最终这七个人组建了一个辉煌无比的宗门——“月之石“并从混沌时代流传至今...
 */
public class ChaosSeven extends TheChaos{
    public ChaosSeven(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isChaos() {
        return true;
    }

    public static final String uDead = "ChaosSeven";

    public static void exp(LivingExperienceDropEvent event) {
        if (event.getAttackingPlayer() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.ChaosSeven_)) {
                            event.setDroppedExperience((int) (event.getDroppedExperience()*1.5f));
                            break;
                        }
                    }
                }
            }
        }
    }
    public static void canBeAffected(MobEffectInstance effectInstance, CallbackInfoReturnable<Boolean> cir,Player player){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.ChaosSeven_)) {
                        if (effectInstance.is(MobEffects.BLINDNESS)||effectInstance.is(MobEffects.DARKNESS)) {
                            cir.setReturnValue(false);
                            break;
                        }
                    }
                }
            }
        }
    }
    public static void tick(ItemStackTickEvent event) {
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.ChaosSeven_)) {
                    if (player.tickCount >= 20) {
                    } else {
                        player.addEffect(new MobEffectInstance(Effects.invulnerable, 200, 0, false, false, false));
                    }
                    CompoundTag compoundTag = stack.get(DataReg.tag);
                    if (compoundTag == null) {
                        stack.set(DataReg.tag, new CompoundTag());
                    }
                    float lv = player.getHealth() / player.getMaxHealth();


                    lv *= 100;
                    int now = (int) (100 - (lv));
                    if (compoundTag != null) {
                        compoundTag.putInt(uDead, now);
                        if (compoundTag.getIntOr(uDead, 0) <= 0) {
                            compoundTag.putInt(uDead, 0);
                        }
                    }
                    break;
                }
            }
        }
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        return attributeModifierMultimap(stack);
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse("aaa"+Chestitem.MODID +
                InitItems.ChaosSeven_.asItem().getDescriptionId()),
                5, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ARMOR, new AttributeModifier(Identifier.parse("aaa"+Chestitem.MODID +
                InitItems.ChaosSeven_.asItem().getDescriptionId()),
                5, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.parse("aaa"+Chestitem.MODID +
                InitItems.ChaosSeven_.asItem().getDescriptionId()),
                5, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.heal, new AttributeModifier(Identifier.parse("aaa"+Chestitem.MODID +
                InitItems.ChaosSeven_.asItem().getDescriptionId()),
                0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.looting, new AttributeModifier(Identifier.parse("aaa"+Chestitem.MODID +
                InitItems.ChaosSeven_.asItem().getDescriptionId()),
                1, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.fortune, new AttributeModifier(Identifier.parse("aaa"+Chestitem.MODID +
                InitItems.ChaosSeven_.asItem().getDescriptionId()),
                1, AttributeModifier.Operation.ADD_VALUE));





        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            int lvl = compoundTag.getIntOr(uDead,0);
            float heal = 0.85f / 333f * lvl;
            float speed = 0.8f / 333f * lvl;
            float damage = 0.75f / 333f * lvl;
            float attSpeed = 0.5f / 333f * lvl;
            float armor = 0.35f / 333f * lvl;


            modifiers.put(AttReg.heal, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    InitItems.ChaosSeven_.asItem().getDescriptionId()),
                    heal, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    InitItems.ChaosSeven_.asItem().getDescriptionId()),
                    speed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    InitItems.ChaosSeven_.asItem().getDescriptionId()),
                    damage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    InitItems.ChaosSeven_.asItem().getDescriptionId()),
                    attSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            modifiers.put(Attributes.ARMOR, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    InitItems.ChaosSeven_.asItem().getDescriptionId()),
                    armor, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));


        }
        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        if (stack.get(DataReg.tag)==null){
            tooltipAdder.accept(Component.translatable("chest_item.the_soul.give").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("chest_item.the_soul.give.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.literal(""));
            if (flag.hasShiftDown()) {
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.1").withStyle(ChatFormatting.GRAY));
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.2").withStyle(ChatFormatting.GRAY));
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.3").withStyle(ChatFormatting.GRAY));
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.4").withStyle(ChatFormatting.GRAY));
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.5").withStyle(ChatFormatting.GRAY));
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.6").withStyle(ChatFormatting.GRAY));
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.7").withStyle(ChatFormatting.GRAY));
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.8").withStyle(ChatFormatting.GRAY));
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.9").withStyle(ChatFormatting.GRAY));
                tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.10").withStyle(ChatFormatting.GRAY));
            }else  {
                tooltipAdder.accept(Component.translatable("options.key.hold").append(Component.translatable("key.keyboard.left.shift")).withStyle(ChatFormatting.GOLD));
            }
            tooltipAdder.accept(Component.literal(""));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.11").withStyle(ChatFormatting.GOLD));
        }else {
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.12").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.13").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.14").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.15").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.16").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.17").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_seven.string.18").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        }
    }

    public static void die(LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof Player player){
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (event.getEntity() instanceof EnderDragon) {
                    give(chestInventory, player);
                }
            }
        }
    }
    public static void give(ChestInventory chestInventory,Player player){
        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
            ItemStack stack = chestInventory.getItem(i);
            if (stack.is(InitItems.ChaosSeven_)) {
                return;
            }
        }
        List<Integer> list = new ArrayList<>();
        Collection<MobEffectInstance> collection = player.getActiveEffects();
        for (MobEffectInstance mobEffectInstance : collection){
            if (mobEffectInstance.getEffect().value().isBeneficial()||!mobEffectInstance.getEffect().value().isBeneficial()){
                list.add(1);
            }
        }
        int o = 0;
        for (int ignored : list){
            o++;
        }

        if (o>=7) {
            ChestInventory inventory = Handler.getItem(player);
            if (inventory != null) {
                ItemStack itemStack10 = inventory.getItem(9);
                ItemStack itemStack11 = inventory.getItem(10);
                ItemStack itemStack12 = inventory.getItem(11);

                ItemStack soul = new ItemStack(InitItems.ChaosSeven_.asItem());
                if (soul.get(DataReg.tag) == null) {
                    soul.set(DataReg.tag,new CompoundTag());
                }

                if (itemStack10.isEmpty()) {
                    player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE,1,1);
                    inventory.setItem(9,soul);
                    return;
                }
                if (itemStack11.isEmpty()){
                    player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE,1,1);
                    inventory.setItem(10,soul);
                    return;
                }
                if (itemStack12.isEmpty()) {
                    player.playSound(SoundEvents.ELDER_GUARDIAN_CURSE,1,1);
                    inventory.setItem(11,soul);
                    return;
                }

            }
        }
    }

    @Override
    public boolean canRemove(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        if (stack.get(DataReg.tag)==null){
            return HashMultimap.create();
        }
        return attributeModifierMultimap(stack);
    }
    @Override
    public Identifier Identifier() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/chaos_seven.png");

    }

    @Override
    public int soulColor() {
        return Light.ARGB.color(255,255,75,255);
    }
}
