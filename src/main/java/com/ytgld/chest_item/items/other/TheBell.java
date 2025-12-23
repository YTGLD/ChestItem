package com.ytgld.chest_item.items.other;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Consumer;

/**
 *末路震钟
 * <p>
 * <p>
 *  生命值低于50%时对16格内生物造成
 * <p>
 *  基于自身100%最大生命值，50%攻击属性，50%护甲的伤害
 * <p>
 *  并且造成击飞
 * <p>
 *  冷却300秒
 *
 * <p>


 */
public class TheBell extends ItemBase {
    public TheBell(Properties properties) {
        super(properties);
    }

    public static void ItemStackTickEvent(ItemStackTickEvent event){
        Player player = event.player;
        if (!player.level().isClientSide()) {
            if (Handler.has(player, InitItems.TheBell_.asItem())) {
                if (player.getHealth() <= player.getMaxHealth() * 0.5f) {
                    if (!player.getCooldowns().isOnCooldown(InitItems.TheBell_.asItem().getDefaultInstance())) {
                        Vec3 playerPos = player.position();
                        int range = 16;
                        List<LivingEntity> livingEntities = player.level().getEntitiesOfClass(LivingEntity.class, new AABB(
                                playerPos.x - range, playerPos.y - range, playerPos.z - range,
                                playerPos.x + range, playerPos.y + range, playerPos.z + range));
                        for (int i = 0; i < 5; i++) {
                            player.level().playSound(null, player.blockPosition(), SoundEvents.BELL_BLOCK, SoundSource.AMBIENT, 1, 1);
                        }
                        for (LivingEntity living : livingEntities) {
                            if (!living.is(player)) {
                                float damage = (float) (player.getAttributeValue(Attributes.MAX_HEALTH)
                                        + player.getAttributeValue(Attributes.ARMOR) * 0.5f
                                        + player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.5f);
                                living.hurt(living.damageSources().playerAttack(player), damage);
                                living.setDeltaMovement(0, 1, 0);
                            }
                        }
                        player.getCooldowns().addCooldown(InitItems.TheBell_.asItem().getDefaultInstance(), 300 * 20);
                    }
                }
            }
        }
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,120,130,150);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.the_bell.string.1").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_bell.string.2").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_bell.string.3").withStyle(ChatFormatting.GOLD));
        tooltipAdder.accept(Component.translatable("item.chest_item.the_bell.string.4").withStyle(ChatFormatting.GOLD));
    }

}
