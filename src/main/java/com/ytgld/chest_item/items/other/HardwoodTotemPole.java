package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.items.ILight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;

/**
 * 硬木图腾柱
 * <p>
 * <p>
 * 攻击有概率造成虚弱
 * <p>
 * 攻击有概率恢复生命值
 * <p>
 * 攻击有概率有概率获得力量
 * <p>
 * 受伤有概率获得抗性
 * <p>
 * 受伤有概率获得伤害吸收
 * <p>
 * 受伤有概率反弹伤害
 */
public class HardwoodTotemPole extends ItemBase implements ILight {
    public HardwoodTotemPole(Properties properties) {
        super(properties);
    }
    public static void tick(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.HardwoodTotemPole_)) {
                            if (event.getEntity() instanceof LivingEntity living) {

                                if (Mth.nextInt(RandomSource.create(), 1, 100) <= 15) {
                                    living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,200,0));
                                }

                                    if (Mth.nextInt(RandomSource.create(), 1, 100) <= 15) {
                                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,200,0));
                                }

                                if (Mth.nextInt(RandomSource.create(), 1, 100) <= 15) {
                                    player.heal(2);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.HardwoodTotemPole_)) {
                            if (event.getSource().getEntity() instanceof LivingEntity living) {

                                if (Mth.nextInt(RandomSource.create(), 1, 100) <= 15) {
                                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,100,0));
                                }

                                if (Mth.nextInt(RandomSource.create(), 1, 100) <= 15) {
                                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,100,0));
                                }


                                if (Mth.nextInt(RandomSource.create(), 1, 100) <= 15) {
                                    living.hurt(living.damageSources().playerAttack(player), (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0/33F);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.hardwood_totem_pole.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.hardwood_totem_pole.string.2").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.hardwood_totem_pole.string.3").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.hardwood_totem_pole.string.4").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.hardwood_totem_pole.string.5").withStyle(ChatFormatting.GOLD));
        tooltipAdder.add(Component.translatable("item.chest_item.hardwood_totem_pole.string.6").withStyle(ChatFormatting.GOLD));
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255, 255, 100, 50);
    }

    @Override
    public boolean isWhirlpool() {
        return true;
    }
}
