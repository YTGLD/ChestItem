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

    public static final int TIME = 10 * 20;

    public static final String bloodTime = "bloodTime";
    public static final String bloodDamage = "bloodDamage";

    public GodApple(Properties properties) {
        super(properties);
    }

    public static void event(LivingDamageEvent.Pre event){
        if (!event.getSource().is(DamageTypes.GENERIC_KILL)) {
            if (event.getEntity() instanceof Player player) {
                if (!player.level().isClientSide()) {
                    ChestInventory chestInventory = Handler.getItem(player);
                    if (chestInventory != null) {
                        for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                            ItemStack stack = chestInventory.getItem(i);
                            if (stack.is(InitItems.God_Apple.get())) {
                                CompoundTag compoundTag = stack.get(DataReg.tag);
                                if (compoundTag != null) {
                                    float s = event.getNewDamage() - ConfigItem.intValue.get().floatValue();
                                    if (s > ConfigItem.intValue.get().floatValue()) {
                                        compoundTag.putFloat(bloodDamage, compoundTag.getFloatOr(bloodDamage,0) + s);
                                        compoundTag.putInt(bloodTime, TIME);
                                        event.setNewDamage(ConfigItem.intValue.get().floatValue());
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
    public static void event(ItemStackTickEvent event) {
        Player player = event.player;
        ChestInventory chestInventory = event.chestInventory;
        if (!player.level().isClientSide()&&chestInventory!=null){
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.God_Apple.get())) {
                    CompoundTag compoundTag = stack.get(DataReg.tag);
                    if (compoundTag != null) {
                        if (compoundTag.getIntOr(bloodTime,0) <= 0) {
                            compoundTag.putFloat(bloodDamage, 0);
                        }
                        if (compoundTag.getIntOr(bloodDamage,0) <= 0) {
                            compoundTag.putFloat(bloodTime, 0);
                        }
                        if (player.isDeadOrDying()) {
                            compoundTag.putFloat(bloodDamage, 0);
                            compoundTag.putFloat(bloodTime, 0);
                        }

                        if (!player.level().isClientSide()
                                && player.tickCount % 20 == 1) {
                            if (compoundTag.getIntOr(bloodTime,0) > 0 && compoundTag.getFloatOr(bloodDamage,0) > 0) {
                                compoundTag.putInt(bloodTime, compoundTag.getIntOr(bloodTime,0) - TIME / 10);
                                float dmg = compoundTag.getFloatOr(bloodDamage,0) / 10f;
                                compoundTag.putFloat(bloodDamage, compoundTag.getFloatOr(bloodDamage,0) - dmg);
                                player.hurt(player.damageSources().genericKill(), dmg);
                            }
                        }
                        break;
                    }else {
                        stack.set(DataReg.tag,new CompoundTag());
                    }
                }
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

