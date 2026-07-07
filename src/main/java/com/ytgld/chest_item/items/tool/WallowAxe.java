package com.ytgld.chest_item.items.tool;

import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.black.ITheChaos;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

import java.util.List;
import java.util.function.Predicate;

/**
 *沉沦战斧
 * <p>
 * 2倍暴击伤害，非暴击伤害较小
 * */
public class WallowAxe extends AxeItem implements IBlackLight , ITheChaos {
    public WallowAxe(Properties properties) {
        super(new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,5000,
                9,
                4,
                9,
                () -> Ingredient.of(Items.CRYING_OBSIDIAN)),
                properties.attributes(
                        AxeItem.createAttributes(Tiers.WOOD,
                        10f,
                                -3.4F)));
    }

    @Override
    public Component getName(ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD)));
        return co;
    }

    public static void cit(CriticalHitEvent event){
        if (event.isCriticalHit()) {
            if (event.getEntity().getMainHandItem().is(InitItems.WallowAxe_.get())) {
                event.setDamageMultiplier(event.getDamageMultiplier() * 2);
                WallowAxe.hurtEnemy(event.getTarget(),event.getEntity());
            }
        }else {
            if (event.getEntity().getMainHandItem().is(InitItems.WallowAxe_.get())) {
                event.setDamageMultiplier(event.getDamageMultiplier() * 0.25f);
            }
        }
    }
    public static void hurtEnemy(Entity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayer serverplayer) {
            ServerLevel serverlevel = (ServerLevel) attacker.level();
            if (serverplayer.isIgnoringFallDamageFromCurrentImpulse() && serverplayer.currentImpulseImpactPos != null) {
                if (serverplayer.currentImpulseImpactPos.y > serverplayer.position().y) {
                    serverplayer.currentImpulseImpactPos = serverplayer.position();
                }
            } else {
                serverplayer.currentImpulseImpactPos = serverplayer.position();
            }
            serverplayer.setIgnoreFallDamageFromCurrentImpulse(true);
            serverplayer.connection.send(new ClientboundSetEntityMotionPacket(serverplayer));
            serverplayer.setSpawnExtraParticlesOnFall(true);
            knockback(serverlevel, serverplayer, target);
        }
    }
    private static void knockback(Level level, Player player, Entity entity) {
        level.levelEvent(2013, entity.getOnPos(), 750);
        level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate((double)3.5F), knockbackPredicate(player, entity)).forEach((p_347296_) -> {
            Vec3 vec3 = p_347296_.position().subtract(entity.position());
            double d0 = getKnockbackPower(player, p_347296_, vec3);
            Vec3 vec31 = vec3.normalize().scale(d0);
            if (d0 > (double)0.0F) {
                p_347296_.push(vec31.x, (double)0.7F, vec31.z);
            }
        });
    }
    private static Predicate<LivingEntity> knockbackPredicate(Player player, Entity entity) {
        return (p_344407_) -> {
            boolean flag;
            boolean flag1;
            boolean flag2;
            boolean flag6;
            label62: {
                flag = !p_344407_.isSpectator();
                flag1 = p_344407_ != player && p_344407_ != entity;
                flag2 = !player.isAlliedTo(p_344407_);
                if (p_344407_ instanceof TamableAnimal tamableanimal) {
                    if (tamableanimal.isTame() && player.getUUID().equals(tamableanimal.getOwnerUUID())) {
                        flag6 = true;
                        break label62;
                    }
                }

                flag6 = false;
            }

            boolean flag3;
            label55: {
                flag3 = !flag6;
                if (p_344407_ instanceof ArmorStand armorstand) {
                    if (armorstand.isMarker()) {
                        flag6 = false;
                        break label55;
                    }
                }

                flag6 = true;
            }

            boolean flag5 = entity.distanceToSqr(p_344407_) <= Math.pow((double)3.5F, (double)2.0F);
            return flag && flag1 && flag2 && flag3 && flag6 && flag5;
        };
    }
    private static double getKnockbackPower(Player player, LivingEntity entity, Vec3 entityPos) {
        return ((double)3.5F - entityPos.length()) * (double)0.7F * (double)(player.fallDistance > 5.0F ? 2 : 1) * ((double)1.0F - entity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        tooltipComponents.add(Component.translatable("item.chest_item.wallow_axe.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }
    @Override
    public int getBarColor(ItemStack stack) {
        return TextColor.fromRgb(0X806A5ACD).getValue();
    }
}
