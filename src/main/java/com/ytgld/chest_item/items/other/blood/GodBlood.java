package com.ytgld.chest_item.items.other.blood;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackAttackEvent;
import com.ytgld.chest_item.items.IGUILight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 攻击会附加缓慢效果
 * <p>
 * 增加20%速度
 * <p>
 *增加20%攻速
 */

public class  GodBlood extends ItemBase implements IGUILight {
    public GodBlood(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Other";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("GodBlood");
            intValue =  builder.translation("chest_item.config.GodBlood")
                    .defineInRange("number",0.1f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.GodBlood2")
                    .defineInRange("number2",100,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("GodBlood",
                            "神速力","基础加成"),
                    new CIString("GodBlood2",
                            "神速力2","缓慢时间（施加）")
            );
        }
    }
    public static void attack(ItemStackAttackEvent event){
        LivingIncomingDamageEvent livingIncomingDamageEvent = event.event;
        Player player = event.player;
        LivingEntity target = livingIncomingDamageEvent.getEntity();
        ChestInventory chestInventory = event.chestInventory;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.God_blood)) {
                        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, ConfigItem.intValue2.get().intValue(), 1), player);
                }
            }
        }
    }

    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.God_blood.asItem().getDescriptionId()),
                ConfigItem.intValue.get().floatValue(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID + InitItems.God_blood.asItem().getDescriptionId()),
                ConfigItem.intValue.get().floatValue(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipAdder, flag);
        tooltipAdder.add(Component.translatable("item.chest_item.god_blood.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipAdder.add(Component.literal(""));
        tooltipAdder.add(Component.translatable("item.chest_item.god_blood.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,100,100,255);
    }

    @Override
    public int guiColor(ItemStack stack) {
        return Light.ARGB.color(150,120,80,255);
    }

    @Override
    public Vec2 posOffset() {
        return new Vec2(0,4);
    }
}
