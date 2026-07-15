package com.ytgld.chest_item.items.memory.items;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.memory.MemoryBase;
import com.ytgld.chest_item.items.memory.MemoryItems;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import org.jetbrains.annotations.UnknownNullability;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 渴求
 * <p>
 * 进食获得大量增益效果并恢复生命值
 * <p>
 * 饥饿值会快速消耗，进食之后可短时间缓解症状
 * <p>
 * 时长感到异常饥饿，进食时抵消感触并获得大量增益效果
 * <p>
 * 死亡并非因果之末
 */
public class Crave extends MemoryBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Memory";
        }
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Crave");
            intValue =  builder.translation("chest_item.config.Crave")
                    .defineInRange("number",20,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Crave2")
                    .defineInRange("number2",0.033f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Crave",
                            "信仰：渴求","每隔多少刻减少一次对异常饥饿的抗性"),
                    new CIString("Crave2",
                            "信仰：渴求2","每次触发异常饥饿所消耗的饥饿值和饱和度")
            );
        }
    }
    public Crave(Properties properties) {
        super(properties);
    }
    @Override
    public MemoryString memoryName() {
        return new MemoryString(Chestitem.MODID,"crave_tooltip");
    }

    @Override
    public Item name() {
        return MemoryItems.CraveTooltip_.asItem();
    }

    @Override
    public void doText(ItemStack stack, @UnknownNullability List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("item.chest_item.crave.string.1").withStyle(ChatFormatting.GRAY));
    }
    @Override
    public boolean isHasActivated() {
        return true;
    }

    @Override
    public void doTextGive(ItemStack stack, List<Component> tooltipComponents) {
        tooltipComponents.add(Component.translatable("item.chest_item.crave.string.give").withStyle(ChatFormatting.GRAY));
    }
    public static class  CraveTooltip extends BaseTooltip {
        public static final String cooldown = "CraveTooltipCooldown";
        public CraveTooltip(Properties properties) {
            super(properties);
        }
        @Override
        public int color() {
            return Light.ARGB.color(255,25,200,25);
        }
        @Override
        public void doText(ItemStack stack, @UnknownNullability List<Component> tooltipComponents) {
            tooltipComponents.add(Component.translatable("item.chest_item.crave_tooltip.string.1").setStyle(Style.EMPTY.withColor(color())));
            tooltipComponents.add(Component.translatable("item.chest_item.crave_tooltip.string.2").setStyle(Style.EMPTY.withColor(color())));
        }
        @Override
        public Component doTextOne() {
            return Component.translatable("item.chest_item.crave_tooltip.string.0").setStyle(Style.EMPTY.withColor(color()));
        }
        public static void tickGive(ItemStackTickEvent event){
            Player player = event.getPlayer();
            if (MemoryBase.isHasEnabled(player, "chest_item:crave_tooltip")) {
                if (!player.isAlive()) {
                    MemoryBase.clearCounter(player, "chest_item:crave_tooltip");
                }
                if (!player.level().isClientSide()) {
                    if (player.tickCount % 20 ==1) {
                        MemoryBase.addCounter(player, "chest_item:crave_tooltip", 1);
                    }
                }
                if (MemoryBase.getCounter(player,"chest_item:crave_tooltip") >= 60 * 20 * 3) {
                    MemoryBase.addMemoryIt(player, "chest_item:crave_tooltip");
                    MemoryBase.clearCounter(player, "chest_item:crave_tooltip");
                    MemoryBase.clearEnabledMemory(player, "chest_item:crave_tooltip");
                }
            }
        }
        public static void eatNot(LivingEntityUseItemEvent.Finish event) {
            if (event.getEntity() instanceof Player player) {
                if (MemoryBase.isHasEnabled(player, "chest_item:crave_tooltip")) {
                    if (event.getItem().getUseAnimation() == UseAnim.EAT) {
                        MemoryBase.clearCounter(player, "chest_item:crave_tooltip");
                    }
                }
            }
        }
        public static void craveCauseFood(ItemStackTickEvent event){
            Player player = event.getPlayer();
            if (MemoryBase.hasMemory(player,"chest_item:crave_tooltip")){
                tickCooldown(player, ConfigItem.intValue.getAsInt());
                if (!hasCooldown(player)) {
                    if (!player.level().isClientSide()) {
                        player.causeFoodExhaustion((float) ConfigItem.intValue2.getAsDouble());
                    }
                }
            }
        }
        public static void craveCauseFood(LivingEntityUseItemEvent.Finish event){
            if (event.getEntity() instanceof Player player) {
                if (MemoryBase.hasMemory(player, "chest_item:crave_tooltip")) {
                    if (event.getItem().getUseAnimation() == UseAnim.EAT) {
                        if (!player.level().isClientSide()) {
                            addCooldown(player, 30);
                            player.heal(20);
                            addEffect(player, MobEffects.ABSORPTION, 300, 1, 4);
                            addEffect(player, MobEffects.DAMAGE_BOOST, 600, 0, 1);
                            addEffect(player, MobEffects.MOVEMENT_SPEED, 400, 0, 2);
                            addEffect(player, MobEffects.DIG_SPEED, 820, 0, 2);
                            addEffect(player, MobEffects.DAMAGE_RESISTANCE, 300, 0, 1);
                            addEffect(player, MobEffects.REGENERATION, 500, 0, 1);
                        }
                    }
                }
            }
        }

        private static void addEffect(Player player, Holder<MobEffect> effect,
                                      int initialTime ,
                                      int initialLevel, int maxLevel){
            int maxTime = maxLevel * initialTime;
            player.addEffect(new MobEffectInstance(effect,initialTime,initialLevel,false,false));
            @Nullable MobEffectInstance mobEffectInstance = player.getEffect(effect);
            if (mobEffectInstance != null) {
                if (mobEffectInstance.getAmplifier()< maxLevel) {
                    player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),
                            mobEffectInstance.getDuration()+initialTime,
                            mobEffectInstance.getAmplifier() + 1,
                            false,false));
                }else if (mobEffectInstance.getDuration() < maxTime){
                    player.addEffect(new MobEffectInstance(mobEffectInstance.getEffect(),maxTime,
                            maxLevel + 1,false,false));
                }
            }
        }
        private static void tickCooldown(Player player,int time){
            CompoundTag compoundTag = player.getPersistentData();
            if (player.tickCount % time == 1) {
                if (hasCooldown(player)) {
                    compoundTag.putInt(cooldown,compoundTag.getInt(cooldown) - 1);
                }
            }
        }
        private static void addCooldown(Player player,int value){
            CompoundTag compoundTag = player.getPersistentData();
            compoundTag.putInt(cooldown,value);
        }

        private static boolean hasCooldown(Player player){
            CompoundTag compoundTag = player.getPersistentData();
            if (compoundTag.getInt(cooldown) > 1) {
                return true;
            }
            return false;
        }
    }
}
