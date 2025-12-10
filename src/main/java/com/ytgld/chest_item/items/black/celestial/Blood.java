package com.ytgld.chest_item.items.black.celestial;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class Blood extends TheCelestial{
    public Blood(Properties properties) {
        super(properties);
    }

    public static final String time = "time";
    public static final int maxTime = 600;

    public static void onKeyIsDown(Player player){
        ChestInventory chestInventory = Handler.getItem(player);
        if (chestInventory!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.Blood_)) {
                        CompoundTag compoundTag = stack.get(DataReg.tag);
                        if (compoundTag == null) {
                            stack.set(DataReg.tag,new CompoundTag());
                        }
                        if (compoundTag!=null) {
                            if (!player.getCooldowns().isOnCooldown(stack)) {
                                compoundTag.putInt(time, maxTime);
                                player.setHealth(1);
                                player.addEffect(new MobEffectInstance(Effects.Rage,maxTime,0));
                                player.level().playSound(null, player.blockPosition(), SoundEvents.RAVAGER_ROAR, SoundSource.AMBIENT, 1, 1);
                                player.level().playSound(null, player.blockPosition(), SoundEvents.TRIAL_SPAWNER_OMINOUS_ACTIVATE, SoundSource.AMBIENT, 1, 1);

                                @Nullable MobEffectInstance mobEffectInstance = player.getEffect(Effects.Rage);
                                if (mobEffectInstance != null) {
                                    if (mobEffectInstance.getAmplifier()<3) {
                                        player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),
                                                mobEffectInstance.getDuration()+maxTime,
                                                mobEffectInstance.getAmplifier() + 1,
                                                false,false));
                                    }else {
                                        player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),maxTime*5, 4,false,false));
                                    }
                                }
                                player.getCooldowns().addCooldown(stack, 300);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void tick(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Blood_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag == null) {
                                stack.set(DataReg.tag, new CompoundTag());
                            }
                            if (compoundTag != null) {
                                if (compoundTag.getIntOr(time, 0) > 0) {
                                    event.setNewDamage(event.getNewDamage()*1.3f);
                                    player.heal(event.getNewDamage() * 0.2f);
                                    compoundTag.putInt(time,compoundTag.getIntOr(time,0)+20);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void tick(ItemStackTickEvent  event){
        if (event.player instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory != null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.Blood_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag == null) {
                                stack.set(DataReg.tag, new CompoundTag());
                            }
                            if (compoundTag != null) {
                                if (compoundTag.getIntOr(time, 0) > 0) {
                                    compoundTag.putInt(time,compoundTag.getIntOr(time,0)-1);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.blood.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.translatable("item.chest_item.blood.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.translatable("item.chest_item.blood.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.accept(Component.translatable("item.chest_item.blood.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
    }
    @Override
    public Identifier img(ItemStack stack) {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/celestial/blood.png");
    }

    @Override
    public int soulColor(ItemStack stack) {
        return Light.ARGB.color(255,255,80,80);
    }
}
