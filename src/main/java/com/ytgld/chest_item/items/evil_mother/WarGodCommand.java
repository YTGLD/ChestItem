package com.ytgld.chest_item.items.evil_mother;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.condensebone.AlienationDiodes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleToIntFunction;

/**
 * 军神之令
 * <p>
 * 生命值最多自然恢复到%d%%
 * <p>
 * 饥饿值会在恢复到这个值时停止消耗
 * <p>
 * 损失生命值带来巨大的加成
 * <p>
 * 持有此令者，有%d%%的概率规避受到的必死伤害
 */
public class WarGodCommand extends EvilMother {
        public WarGodCommand(Properties properties) {
            super(properties);
        }

        @ConfigPlugin
        public static class ConfigItem implements RegisterItemConfig {
            public static ModConfigSpec.IntValue intValue;
            public static ModConfigSpec.DoubleValue intValue2;
            public static ModConfigSpec.IntValue intValue3;

            @Override
            public void config(ModConfigSpec.Builder builder) {
                builder.push("WarGodCommand");
                intValue = builder.translation("chest_item.config.WarGodCommand")
                        .defineInRange("number", 45, 0, 100);
                intValue2 = builder.translation("chest_item.config.WarGodCommand2")
                        .defineInRange("number2", 1F, 0, 100);
                intValue3 = builder.translation("chest_item.config.WarGodCommand3")
                        .defineInRange("number3", 25, 0, 100);
                builder.pop();
            }

            @Override
            public List<CIString> theLanguageProvider() {
                return List.of(
                        new CIString("WarGodCommand",
                                "军神之令", "生命值最多恢复到多少"),
                        new CIString("WarGodCommand2",
                                "军神之令1", "损失生命值带来其属性的倍率"),
                        new CIString("WarGodCommand3",
                                "军神之令2", "规避必死伤害的概率")
                );
            }
        }

        @Override
        public int getSanity() {
            return -5;
        }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.war_god_command.string.1",ConfigItem.intValue.getAsInt()).withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.war_god_command.string.2").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.literal("").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.war_god_command.string.3").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.war_god_command.string.4",ConfigItem.intValue3.getAsInt()).withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.literal("").withStyle(Style.EMPTY.withColor(color)));
        tooltipComponents.accept(Component.translatable("item.chest_item.war_god_command.string.5",ConfigItem.intValue3.getAsInt()).withStyle(Style.EMPTY.withColor(color)));
    }

    /**
         * 饥饿值会在恢复到这个值时停止消耗
         *
         * @param player 玩家
         * @param ci     取消调用
         */
        public static void cirFood(Player player, CallbackInfo ci) {
            if (Handler.has(player, InitItems.WarGodCommand_.asItem())) {
                if (isHealMax(player)) {
                    ci.cancel();
                    ;
                }
            }
        }

        /**
         * 生命值最多自然恢复到%d%%
         * <p>
         * 其实默认是50%
         *
         * @param event 治疗事件
         */
        public static void healOFf(LivingHealEvent event) {
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player player) {
                if (Handler.has(player, InitItems.WarGodCommand_.asItem())) {
                    float amount = event.getAmount();
                    float now = player.getHealth();
                    float max = player.getMaxHealth();
                    float doIt = max * (ConfigItem.intValue.getAsInt() / 100f);

                    if ((amount + now) >= doIt) {
                        float newAmount = doIt - now;
                        event.setAmount(newAmount);
                    }
                }
            }
        }

        /**
         * 损失生命值带来巨大的加成
         * @param stack 目标物品 = 军神之令
         * @param player 玩家
         * @return 返回属性
         */
        public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
            Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
            if (Handler.has(player, InitItems.DefeatTheArmy_.asItem())) {
                return modifiers;
            }
            float lv = player.getHealth() / player.getMaxHealth();
            lv *= 100;
            float now = (int) (100 - (lv));
            if (now < 0) {
                now = 0;
            }
            now /= 100f;

            float speed = 1.3f * now * ConfigItem.intValue2.get().floatValue();
            float attackSpeed = 0.85f * now * ConfigItem.intValue2.get().floatValue();
            float heal = 0.65f * now * ConfigItem.intValue2.get().floatValue();
            float res = 0.5f * now * ConfigItem.intValue2.get().floatValue();


            modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    InitItems.WarGodCommand_.asItem().getDescriptionId()),
                    speed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));


            modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    InitItems.WarGodCommand_.asItem().getDescriptionId()),
                    attackSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));


            modifiers.put(AttReg.heal, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    InitItems.WarGodCommand_.asItem().getDescriptionId()),
                    heal, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));


            modifiers.put(AttReg.resistance, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                    InitItems.WarGodCommand_.asItem().getDescriptionId()),
                    res, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

            return modifiers;
        }

    @Override
    public @Nullable Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player, ItemStack stack) {
        return this.doAttribute(stack, player);
    }

    public static void notDie(LivingDamageEvent.Pre event) {
            LivingEntity entity = event.getEntity();
            if (entity instanceof Player player) {
                if (Handler.has(player, InitItems.WarGodCommand_.asItem())){
                    if (event.getNewDamage() >= player.getHealth()) {
                        if (player.getRandom().nextInt(100) <= ConfigItem.intValue3.get()) {
                            event.setNewDamage(0);
                        }
                    }
                }
            }
        }

        private static boolean isHealMax(Player player) {
            float max = player.getMaxHealth();
            float now = player.getHealth();
            if (now >= max * (ConfigItem.intValue.getAsInt() / 100f)) {
                return true;
            }
            return false;
        }
    }
