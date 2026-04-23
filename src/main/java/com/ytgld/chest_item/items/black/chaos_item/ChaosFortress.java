package com.ytgld.chest_item.items.black.chaos_item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.items.black.ITheChaos;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 *混沌要塞之护
 * <p>
 * 启明之铅：累计受到15000以上点伤害获取
 * <p>
 * 	增加15%的抗性
 * <p>
 * 	获得55%基于护甲值的侵蚀装甲
 * <p>
 * 	杀死生物恢复10%的侵蚀装甲
 * <p>
 * <p>
 * 	受到伤害时对攻击者造成80%基于自身侵蚀装甲的虚空伤害
 * <p>
 * 	被攻击时有5%的概率获得3秒无敌
 * <p>
 * <p>
 * 	无效化低于2点的伤害
 * <p>
 * 	满生命的生物对你的伤害降低20%
 * <p>
 * <p>
 * 	不死图腾无法对你生效
 * <p>
 * 	受到的魔法伤害提高400%
 * <p>
 * 	侵蚀装甲难以自然恢复
 */
public class ChaosFortress extends ItemBlackShadow implements ITheChaos {
    public ChaosFortress(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        public static ModConfigSpec.DoubleValue intValue3 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("ChaosFortress");
            intValue =  builder.translation("chest_item.config.ChaosFortress")
                    .defineInRange("number",2f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.ChaosFortress2")
                    .defineInRange("number2",5f,0,Integer.MAX_VALUE);
            intValue3 =  builder.translation("chest_item.config.ChaosFortress2")
                    .defineInRange("number3",0.7f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("ChaosFortress",
                            "混沌要塞之护","免疫低于这个值的伤害"),
                    new CIString("ChaosFortress2",
                            "混沌要塞之护2","增加的魔法伤害"),
                    new CIString("ChaosFortress3",
                            "混沌要塞之护3","伤害倍率")
            );
        }
    }
    public static void isInvulnerableToBase(Player player,
                                            DamageSource damageSource,
                                            CallbackInfoReturnable<Boolean> cir) {
        //被攻击时有5%的概率获得3秒无敌
        if (player.getCooldowns().isOnCooldown(InitItems.ChaosFortress_.asItem().getDefaultInstance())) {
            cir.setReturnValue(true);
        }
    }
    public static void dieTotem(LivingUseTotemEvent event){
        //无效化低于2点的伤害
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.ChaosFortress_.asItem())) {
                event.setCanceled(true);
            }
        }
    }

    public static void hurtBy2(LivingIncomingDamageEvent event){
        //无效化低于2点的伤害
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.ChaosFortress_.asItem())) {
                if (event.getAmount() < ConfigItem.intValue.get().floatValue()) {
                    event.setCanceled(true);
                }
            }
        }
    }

    public static void hurtRes(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.ChaosFortress_.asItem())) {
                //增加15%的抗性
                event.setNewDamage(event.getNewDamage() *  ConfigItem.intValue3.get().floatValue());
                //被攻击时有5%的概率获得3秒无敌
                if (Mth.nextInt(RandomSource.create(), 0, 100) <= 5) {
                    player.getCooldowns().addCooldown(InitItems.ChaosFortress_.asItem().getDefaultInstance(),60);
                }
            }
        }
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.ChaosFortress_.asItem())) {
                //受到伤害时对攻击者造成80%基于自身侵蚀装甲的虚空伤害
                float damage = player.getData(AttReg.chaosWinds) * 0.8f + 5;
                //受到的魔法伤害提高400%
                if (event.getSource().is(DamageTypes.MAGIC)) {
                    event.setNewDamage(event.getNewDamage() * ConfigItem.intValue2.get().floatValue());
                }
                if (event.getSource().getEntity() instanceof LivingEntity entity) {
                    entity.hurt(entity.damageSources().dryOut(),damage);
                    //满生命的生物对你的伤害降低20%
                    if (entity.getHealth() >= player.getMaxHealth()){
                        event.setNewDamage(event.getNewDamage() * 0.75f);
                    }
                }
            }
        }
    }
    public static void killArmor(LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof Player player) {
            //杀死生物恢复10%的侵蚀装甲
            if (Handler.has(player, InitItems.ChaosFortress_.asItem())) {
                if (player.getData(AttReg.chaosWinds) < player.getAttributeValue(AttReg.chaos_armor)) {
                    Handler.setDataValue(AttReg.chaosWinds,player,
                            player.getData(AttReg.chaosWinds)
                                    + ((player.getData(AttReg.chaosWinds) * 0.1f + 1)));
                }
            }
        }
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        return attributeModifierMultimap(player);
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        //获得55%基于护甲值的侵蚀装甲
        int armor = (int) (player.getArmorValue() * 0.55f);
        modifiers.put(AttReg.chaos_armor, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.ChaosFortress_.asItem().getDescriptionId()),
                armor, AttributeModifier.Operation.ADD_VALUE));

        //侵蚀装甲难以自然恢复
        modifiers.put(AttReg.chaos_armor_speed, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.ChaosFortress_.asItem().getDescriptionId()),
                5000, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(0XFFFF0000)));
        return co;
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        CompoundTag compoundTag = stack.get(DataReg.tag);
        if (compoundTag != null) {
            if (!flag.hasShiftDown()) {
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorText()))));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.2").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorText()))));
            }else {
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.3",ConfigItem.intValue3.get().floatValue()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipComponents.accept(Component.literal(""));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.6").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.7").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipComponents.accept(Component.literal(""));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.8",ConfigItem.intValue.get().floatValue()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.9").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipComponents.accept(Component.literal(""));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.10").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.11",ConfigItem.intValue2.get().floatValue()).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
                tooltipComponents.accept(Component.translatable("item.chest_item.chaos_fortress.string.12").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            }
        }else {
            tooltipComponents.accept((Component.translatable("item.chest_item.chaos_fortress.string.0")).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorText()))));
        }
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return attributeModifierMultimap(player);
    }

}
