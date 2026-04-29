package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.IChestItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import org.jetbrains.annotations.UnknownNullability;

import java.util.List;

public class DoomAxe extends AxeItem implements IChestItem {
    public DoomAxe(Properties properties) {
        super(Tiers.IRON, properties);
    }

    // 额外增加150~300%的暴击（跳劈）伤害，幽影护盾比例越低伤害加成越高
    public static void onCritEvent(CriticalHitEvent event) {
        Player player = event.getEntity();
        float shadow_shield = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
        double shadow_shield_max = player.getAttributeValue(AttReg.shadow_shield);

        if (shadow_shield_max <= 0) return;

        float ratio = (float) (shadow_shield / shadow_shield_max);
        float multiplier = 1.5F + (1.0F - ratio) * 1.5F; // 护盾越少，伤害越高
        event.setDamageMultiplier(event.getDamageMultiplier() * multiplier);
    }

    // 但降低非暴击伤害，幽影护盾（已有翻译Shadow Shield）比例越高伤害减益越强，最高70%
    public static void onAttackEvent(LivingDamageEvent.@UnknownNullability Pre event) {
        if (event.getSource().getEntity() instanceof Player player) {
            float shadow_shield = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
            double shadow_shield_max = player.getAttributeValue(AttReg.shadow_shield);
            if (shadow_shield_max > 0) {
                float ratio = (float) (shadow_shield / shadow_shield_max);
                // 比例越高减益越强，最高70%减伤，即保留30%伤害
                // 减伤比例 = ratio * 0.7
                float damageMultiplier = 1.0F - (ratio * 0.7F);
                event.setNewDamage(event.getNewDamage() * damageMultiplier);
            }
        }
    }

    // 进行onChestAttack, onChestCrit, onChestHurt, onChestHeal的测试，分别写一个明显的效果
    @Override
    public void onChestAttack(ItemStack stack, Player player, LivingEntity target, ItemStackAttackEvent event) {
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));
    }
    @Override
    public void onChestCrit(ItemStack stack, Player player, LivingEntity target, CriticalHitEvent event) {
        target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
    }

    @Override
    public void onChestHurt(ItemStack stack, Player player, LivingIncomingDamageEvent event) {
        event.setAmount(event.getAmount() * 2.0F);
    }

    @Override
    public void onChestHeal(ItemStack stack, Player player, LivingHealEvent event) {
        event.setAmount(event.getAmount() * 2);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.doom_axe.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
    }
}
