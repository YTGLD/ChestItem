package com.ytgld.chest_item.items.black.soul;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.TheImprintOfTheSoul;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

import java.util.List;

/**
 *
 * 癫狂理论
 * <p>
 * <p>
 * 你说2+2不等于4？那等于几？
 * <p>
 * 攻击生物有15%的概率掉落经验球，蕴含的经验是死亡时的20%
 * <p>
 * 拾取经验球对附近的生物造成伤害
 * <p>
 * 瞬间吸取大范围内的经验球
 * <p>
 * <p>
 * 等于3！
 * <p>
 * 掉落的经验球其蕴含的学识减半
 * <p>
 * 你不再可以汲取经验球内的任何能量
 */
public class MadnessTheory extends TheImprintOfTheSoul {
    public MadnessTheory(Properties properties) {
        super(properties);
    }
    public static void attackEXP(LivingDamageEvent.Pre event){
        if (event.getSource().getEntity() instanceof Player player) {
            LivingEntity living = event.getEntity();
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()&&player.level() instanceof ServerLevel serverLevel) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.MadnessTheory_)) {
                            if (Mth.nextInt(RandomSource.create(), 1, 100) <= 15) {
                                ExperienceOrb orb = new ExperienceOrb(living.level(), living.getX(),
                                        living.getY(), living.getZ(),
                                        living.getExperienceReward(serverLevel,player));

                                living.level().addFreshEntity(orb);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public static void attackEXP(LivingExperienceDropEvent event){
        if (event.getAttackingPlayer() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.MadnessTheory_)) {
                            event.setDroppedExperience(event.getDroppedExperience()/2);
                            break;
                        }
                    }
                }
            }
        }
    }
    public static void attackEXP(PlayerXpEvent.PickupXp event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.MadnessTheory_)) {
                            Vec3 playerPos = player.position();
                            int range = 8;
                            List<LivingEntity> entities = player.level().getEntitiesOfClass(LivingEntity.class,
                                    new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range,
                                            playerPos.x + range, playerPos.y + range, playerPos.z + range));
                            for (LivingEntity living : entities) {
                                if (player.getLastHurtMob()!=null) {
                                    if (!living.is(player) && !living.is(player.getLastHurtMob())) {
                                        living.hurt(living.damageSources().magic(), event.getOrb().getValue() / 2f);
                                        event.getOrb().setValue(0);
                                        break;
                                    }else {
                                        if (!living.is(player)) {
                                            living.hurt(living.damageSources().magic(), event.getOrb().getValue() / 2f);
                                            event.getOrb().setValue(0);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void  expOrb(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (player!=null) {
            if (!player.level().isClientSide()) {
                for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                    ItemStack stack = chestInventory.getItem(i);
                    if (stack.is(InitItems.MadnessTheory_)) {
                        Vec3 playerPos = player.position();
                        int range = 8;
                        List<ExperienceOrb> entities = player.level().getEntitiesOfClass(ExperienceOrb.class,
                                new AABB(playerPos.x - range, playerPos.y - range, playerPos.z - range,
                                        playerPos.x + range, playerPos.y + range, playerPos.z + range));
                        for (ExperienceOrb experienceOrb : entities) {
                            player.takeXpDelay = 0;
                            experienceOrb.setPos(playerPos);
                            break;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
    
        tooltipComponents.accept(Component.translatable("item.chest_item.madness_theory.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.translatable("item.chest_item.madness_theory.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.madness_theory.string.3").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.madness_theory.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.madness_theory.string.5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.translatable("item.chest_item.madness_theory.string.6").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
        tooltipComponents.accept(Component.translatable("item.chest_item.madness_theory.string.7").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0Xff8040ff))));
    }
    @Override
    public Identifier Identifier() {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/madness_theory.png");
    }

    @Override
    public int soulColor() {
        return Light.ARGB.color(255,200 ,100 ,255);
    }
}
