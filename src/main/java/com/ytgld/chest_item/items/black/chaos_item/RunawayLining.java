package com.ytgld.chest_item.items.black.chaos_item;

import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Config;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.items.black.celestial.TheCelestial;
import com.ytgld.chest_item.other.AttributeDataType;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class RunawayLining extends ItemBlackShadow implements IBlackLight, ITheChaos{
    public RunawayLining(Properties properties) {
        super(properties);
    }
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF0000)));
        return co;
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF0000))));
            tooltipAdder.accept(Component.literal(""));
            if (!flag.hasShiftDown()) {
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.0").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorText()))));
                tooltipAdder.accept(Component.translatable("key.keyboard.left.shift").withStyle(ChatFormatting.GOLD));
            }else {
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.literal(""));
                tooltipAdder.accept(Component.translatable("item.chest_item.runaway_lining.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.bloody_belt").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.corruption_crystal").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.evil_thoughts_forge_dreams").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.death_omen_stone_monument").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("item.chest_item.dry_bones").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("chest_item.the_imprint_of_the_soul").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("chest_item.the_imprint_of_the_soul.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
                tooltipAdder.accept(Component.translatable("chest_item.celestial").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF5ACD))));
            }
        }else {
            tooltipAdder.accept((Component.translatable("item.chest_item.runaway_lining.string.0")).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorText()))));
        }
    }

    public static Identifier identifier(ItemStack stack) {
        return Identifier.parse("runaway_lining_string:" + stack.getItem().getDescriptionId());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        if (player.level().isClientSide()) {
            CompoundTag compoundTag = stack.get(DataReg.tag);
            if (compoundTag != null) {
                compoundTag.putInt(clientTime, compoundTag.getIntOr(clientTime,0)-1);
            }
        }
        return super.doAttribute(stack, player);
    }

    public static final float min = -0.15f;
    public static final float max =  0.35f;
    public static final String lock =  "LockSting";

    public static final String clientTime = "clientTime";
    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack other, Slot slot, ClickAction action, Player player, SlotAccess access) {
        if (other.is(Items.TOTEM_OF_UNDYING.asItem())) {
            die(player);
            player.level().playSound(null,player.blockPosition(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.AMBIENT,1,1);
            CompoundTag compoundTag = stack.get(DataReg.tag);
            if (compoundTag == null) {
                stack.set(DataReg.tag, new CompoundTag());
            }
            if (player.level().isClientSide()) {
                if (compoundTag != null) {
                    compoundTag.putInt(clientTime, 100);
                }
            }
            other.shrink(1);
            return true;
        }

        return false;
    }

    public static void die(Player player){
        if (Handler.has(player,InitItems.RunawayLining_.asItem())) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (!stack.is(InitItems.RunawayLining_) && !stack.isEmpty()){
                        AttributeDataType attributeDataType = stack.get(DataReg.attributeType);
                        AttributeDataType doIt = new AttributeDataType(List.of());

                        CompoundTag compoundTag = stack.get(DataReg.tag);

                        if (compoundTag == null) {
                            stack.set(DataReg.tag,new CompoundTag());
                        }

                        if (compoundTag != null){
                            if (compoundTag.getBooleanOr(lock,false)){
                                continue;
                            }
                        }
                        if (attributeDataType == null) {
                            AttributeDataType attribute = addAttributeType(player,stack,doIt);
                            stack.set(DataReg.attributeType,attribute);
                        }
                        if (compoundTag != null) {
                            compoundTag.putBoolean(lock,true);
                            compoundTag.putBoolean(IBlackLight.blackName,true);
                        }
                        break;
                    }
                }
            }
        }
    }

    public static float addNumber( RandomSource create,Player player){
        float add = Mth.nextFloat(create, (float) (double)Config.config.RunawayLiningMin.get(),(float) (double)Config.config.RunawayLiningMax.get());
        float sqrtLuck = (float) Math.sqrt(player.getLuck());
        if (sqrtLuck > 4) {
            sqrtLuck = 4;
        }
        sqrtLuck /= 20;
        add += sqrtLuck;
        return add;
    }
    public static AttributeDataType addAttributeType(
            Player player,ItemStack stack,
            AttributeDataType attributeDataType){

        Optional<Holder.Reference<Attribute>> optional =
                BuiltInRegistries.ATTRIBUTE.get(RandomSource.create().nextInt(BuiltInRegistries.ATTRIBUTE.size()));
        Optional<Holder.Reference<Attribute>> optional1 =
                BuiltInRegistries.ATTRIBUTE.get(RandomSource.create().nextInt(BuiltInRegistries.ATTRIBUTE.size()));
        Optional<Holder.Reference<Attribute>> optional2 =
                BuiltInRegistries.ATTRIBUTE.get(RandomSource.create().nextInt(BuiltInRegistries.ATTRIBUTE.size()));
        if (optional.isPresent() && optional1.isPresent() && optional2.isPresent()) {
            if (optional.get().getKey()!=null && optional1.get().getKey()!=null && optional2.get().getKey()!=null){
                return attributeDataType.builder().add(optional.get(),
                              new AttributeModifier(Identifier.parse(
                                Chestitem.MODID + "_" + "runaway_lining_string" + "_"+
                                stack.getItem().getDescriptionId()+"_" +"1"
                                ), addNumber(RandomSource.create(),player), AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                      .add(optional1.get(),
                              new AttributeModifier(Identifier.parse(
                              Chestitem.MODID + "_" + "runaway_lining_string" + "_"+
                                      stack.getItem().getDescriptionId()+"_" +"2"
                              ), addNumber(RandomSource.create(),player), AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                      .add(optional2.get(),
                              new AttributeModifier(Identifier.parse(
                              Chestitem.MODID + "_" + "runaway_lining_string" + "_"+
                                      stack.getItem().getDescriptionId()+"_" +"3"
                              ), addNumber(RandomSource.create(),player), AttributeModifier.Operation.ADD_MULTIPLIED_BASE)).build();
            }
        }
        return new AttributeDataType(List.of());
    }

    public static void addMap(Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap, Player player , ItemStack stack){
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (Handler.has(player,InitItems.RunawayLining_.asItem())) {
            if (stack.is(InitItems.BloodyBelt_)
                    || stack.is(InitItems.CorruptionCrystal_)
                    || stack.is(InitItems.EvilThoughtsForgeDreams_)
                    || stack.is(InitItems.DeathOmenStoneMonument_)
                    || stack.is(InitItems.DryBones_)
                    || stack.getItem() instanceof TheCelestial
                    || stack.getItem() instanceof TheImprintOfTheSoul
            ) {
                if (compoundTag != null) {
                    compoundTag.putBoolean(IBlackLight.blackName,true);
                }else {
                    stack.set(DataReg.tag,new CompoundTag());
                }
            }
        }
        if (compoundTag != null) {
            if (compoundTag.getBooleanOr(IBlackLight.blackName, false)) {
                if (stack.is(InitItems.BloodyBelt_)) {
                    attributeModifierMultimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(identifier(stack),
                            2, AttributeModifier.Operation.ADD_VALUE));
                    attributeModifierMultimap.put(Attributes.MAX_HEALTH, new AttributeModifier(identifier(stack),
                            4, AttributeModifier.Operation.ADD_VALUE));

                }
                if (stack.is(InitItems.CorruptionCrystal_)) {
                    attributeModifierMultimap.put(AttReg.looting, new AttributeModifier(identifier(stack),
                            1, AttributeModifier.Operation.ADD_VALUE));
                    attributeModifierMultimap.put(AttReg.fortune, new AttributeModifier(identifier(stack),
                            1, AttributeModifier.Operation.ADD_VALUE));

                    attributeModifierMultimap.put(AttReg.shadow_shield, new AttributeModifier(identifier(stack),
                            4, AttributeModifier.Operation.ADD_VALUE));
                    attributeModifierMultimap.put(AttReg.shadow_shield_stronger, new AttributeModifier(identifier(stack),
                            0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                }
                if (stack.is(InitItems.EvilThoughtsForgeDreams_)) {
                    attributeModifierMultimap.put(AttReg.shadow_shield_stronger, new AttributeModifier(identifier(stack),
                            0.2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(AttReg.shadow_shield, new AttributeModifier(identifier(stack),
                            0.3, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(AttReg.shadow_shield_speed, new AttributeModifier(identifier(stack),
                            0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                }
                if (stack.is(InitItems.DeathOmenStoneMonument_)) {
                    attributeModifierMultimap.put(Attributes.MAX_HEALTH, new AttributeModifier(identifier(stack),
                            0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                    attributeModifierMultimap.put(Attributes.ARMOR, new AttributeModifier(identifier(stack),
                            0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

                }
                if (stack.is(InitItems.DryBones_)) {
                    attributeModifierMultimap.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(identifier(stack),
                            0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(identifier(stack),
                            0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(identifier(stack),
                            0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(Attributes.ARMOR, new AttributeModifier(identifier(stack),
                            0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                }
                if (stack.getItem() instanceof TheCelestial) {
                    attributeModifierMultimap.put(AttReg.heal, new AttributeModifier(identifier(stack),
                            0.05F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                    attributeModifierMultimap.put(AttReg.more_speed, new AttributeModifier(identifier(stack),
                            0.02F, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

                }
                if (stack.getItem() instanceof TheImprintOfTheSoul) {
                    attributeModifierMultimap.put(AttReg.chaos_armor, new AttributeModifier(identifier(stack),
                            2, AttributeModifier.Operation.ADD_VALUE));
                    attributeModifierMultimap.put(AttReg.chaos_armor_min, new AttributeModifier(identifier(stack),
                            -0.2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

                }
            }
        }
    }
}
