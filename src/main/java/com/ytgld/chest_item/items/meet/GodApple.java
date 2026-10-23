package com.ytgld.chest_item.items.meet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 减少25%最大生命值
 * <P>
 * 减少40%生命恢复
 * <P>
 * <P>
 * 你最高受到5点伤害
 * <P>
 * 超过5点的伤害将转换成等数值的流血
 * <P>
 */
public class GodApple extends ItemBase implements Meat  {
    @ConfigPlugin
     public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Meat";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("GodApple");
            intValue =  builder.translation("chest_item.config.GodApple")
                    .defineInRange("number",5f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("GodApple",
                            "上帝的果实","最大伤害")
            );
        }
    }

    public static final int TIME = 10;

    private static final String lastDamage = "GodAppleLastDamage";
    private static final String damageTime = "GodAppleDamageTime";

    public GodApple(Properties properties) {
        super(properties);
    }

    public static void event(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.God_Apple.get())) {
                if (!event.getSource().is(DamageTypes.GENERIC_KILL)) {
                    CompoundTag compoundTag = player.getPersistentData();
                    float max = ConfigItem.intValue.get().floatValue();
                    if (event.getNewDamage() > max) {
                        float damage = event.getNewDamage() - max;

                        compoundTag.putFloat(lastDamage,
                                compoundTag.getIntOr(lastDamage,0) + damage);
                        compoundTag.putInt(damageTime,TIME);

                        event.setNewDamage(max);
                    }
                }
            }
        }
    }

    @Override
    public void tick(Player entity, ItemStack stack) {
        super.tick(entity, stack);

        CompoundTag compoundTag = entity.getPersistentData();

        if (entity.isDeadOrDying()) {
            compoundTag.putFloat(lastDamage, 0);
            compoundTag.putInt(damageTime, 0);
        }
        if (entity.tickCount % 20 == 1) {
            if (compoundTag.getFloatOr(lastDamage,0) > 0 && compoundTag.getIntOr(damageTime,0) > 0) {
                entity.setInvulnerableTime(0);
                entity.damageCooldownTime = 0;
                entity.hurt(entity.damageSources().genericKill(),compoundTag.getFloatOr(lastDamage,0) / TIME);
                compoundTag.putInt(damageTime,compoundTag.getIntOr(damageTime,0) - 1);
            }else {
                compoundTag.putInt(damageTime,0);
                compoundTag.putFloat(lastDamage,0);

            }
        }
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                -0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(AttReg.heal, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.God_Apple.asItem().getDescriptionId()),
                -0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        return modifiers;
    }
    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.god_apple.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.god_apple.string.3",ConfigItem.intValue.get().floatValue()).withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.god_apple.string.4",ConfigItem.intValue.get().floatValue()).withStyle(ChatFormatting.GOLD));

    }


    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,135,105);
    }

}

