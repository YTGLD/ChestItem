package com.ytgld.chest_item.items.evil_mother;

import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.renderer.particle.other.Particles;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;
import net.neoforged.neoforge.event.entity.player.SweepAttackEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.List;

/**
 * 苍戮
 * <p>
 * 攻击有%d%%的概率造成%d次的连续斩击
 *
 */
public class TheKill extends EvilMother{
    public TheKill(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "EvilMother";
        }
        public static ModConfigSpec.IntValue intValue;
        public static ModConfigSpec.IntValue intValue2;

        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("TheKill");
            intValue = builder.translation("chest_item.config.TheKill")
                    .defineInRange("number", 50, 0, 100);
            intValue2 = builder.translation("chest_item.config.TheKill2")
                    .defineInRange("number2", 4, 0, Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("TheKill",
                            "苍戮", "连续斩击的触发概率"),
                    new CIString("TheKill2",
                            "苍戮2", "连斩次数")
            );
        }
    }
    public static void attack(SweepAttackEvent event){
        if (!event.isSweeping()) {
            return;
        }
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.TheKill_.asItem())) {
                if (!player.getCooldowns().isOnCooldown(InitItems.TheKill_.asItem())){
                    if (Mth.nextInt(RandomSource.create(),0,100) <= ConfigItem.intValue.getAsInt()){
                        Entity entity = event.getTarget();
                        if (entity instanceof LivingEntity living) {
                            int s = (int)(float)living.getData(AttReg.slashing.get());
                            living.setData(AttReg.slashing.get(),(float)ConfigItem.intValue2.getAsInt());
                            player.getCooldowns().addCooldown(InitItems.TheKill_.asItem(),ConfigItem.intValue2.getAsInt() * 20);
                        }
                    }
                }

            }
        }
    }

    @Override
    public void text(ItemStack stack, List<Component> tooltipAdder, TooltipFlag flag) {
        super.text(stack, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.the_kill.string.0",ConfigItem.intValue.getAsInt(),ConfigItem.intValue2.getAsInt()).withStyle(Style.EMPTY.withColor(this.theColor())));
        tooltipAdder.add(Component.translatable("item.chest_item.the_kill.string.1").withStyle(Style.EMPTY.withColor(this.theColor())));
    }

    @Override
    public int getSanity() {
        return -3;
    }
}
