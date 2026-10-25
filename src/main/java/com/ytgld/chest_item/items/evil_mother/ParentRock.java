package com.ytgld.chest_item.items.evil_mother;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.effect.Effects;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.function.Consumer;

/**
 *
 *异化母岩
 * <p>
 * 幽影护盾被击碎时对目标施加幽影侵蚀效果
 * <p>
 * 若目标免疫此状态，则使自身获得伤害吸收
 * <p>
 * 邪母护盾被击碎时对目标施加邪母之拒效果
 * <p>
 *  若目标免疫此状态，则使自身获得力量效果
 * <p>
 * -4理智
 */
public class ParentRock extends EvilMother{
    public ParentRock(Properties properties) {
        super(properties);
    }
    public static void event(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof LivingEntity living) {
            if (player.getCooldowns().isOnCooldown(InitItems.ParentRock_.asItem().getDefaultInstance())) {
                return;
            }
            int time = 600;
            int cooldown = 100;
            if (Handler.has(player,InitItems.ParentRock_.asItem())) {
                int shield = (int) (float) player.getData(AttReg.shadowShieldTypes);
                int san = (int) player.getAttributeValue(AttReg.theSanity);
                if (shield <= 0) {
                    if (san <= 0) {
                        if (!living.addEffect(new MobEffectInstance(Effects.EvilErosion, time, 0))) {
                            player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, time, 1));
                            player.getCooldowns().addCooldown(InitItems.ParentRock_.asItem().getDefaultInstance(),cooldown);
                        }
                    } else {
                        if (!living.addEffect(new MobEffectInstance(Effects.ShadowErosion_, time, 0))) {
                            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, time, 1));
                            player.getCooldowns().addCooldown(InitItems.ParentRock_.asItem().getDefaultInstance(),cooldown);
                        }
                    }
                }
            }
        }
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.shadowShieldCooldown, new AttributeModifier(identifier(),
                2, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }

    @Override
    public int getSanity() {
        return -4;
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.parent_rock.string.1").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.parent_rock.string.2").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.parent_rock.string.3").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.parent_rock.string.4").withStyle(Style.EMPTY.withColor(color)));
    }
}
