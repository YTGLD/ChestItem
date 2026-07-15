package com.ytgld.chest_item.items.evil_mother.decay;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
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
import java.util.function.Consumer;

/**
 *
 * 古朽三角
 * <p>
 * 古朽三角
 * <p>
 * 增加30%伤害
 * <p>
 * 受到伤害时减少2%奖励
 * <p>
 * 最少减少至-30%，随时间恢复
 */
public class Triangle extends DecayItem {
    public Triangle(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
     public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "EvilMother";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        public static ModConfigSpec.DoubleValue intValue3 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Triangle");
            intValue =  builder.translation("chest_item.config.Triangle")
                    .defineInRange("number",30f,0, Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Triangle2")
                    .defineInRange("number2",2,0, Integer.MAX_VALUE);
            intValue3 =  builder.translation("chest_item.config.Triangle3")
                    .defineInRange("number3",10f,0, Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Triangle",
                            "古朽三角","基础伤害"),
                    new CIString("Triangle2",
                            "古朽三角2","每次受伤减少的伤害"),
                    new CIString("Triangle3",
                            "古朽三角3","伤害最低减少的值")
            );
        }
    }
    private static final String thePlayerHurtSize = "TrianglePlayerHurtSize";

    public static void hurtSize(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (Handler.has(player, InitItems.Triangle_.asItem())) {
                if (player.level().isClientSide()) {
                    return;
                }
                CompoundTag compoundTag = player.getPersistentData();
                int hurtSize = compoundTag.getIntOr(thePlayerHurtSize,0);
                int size = (int) ((ConfigItem.intValue.get().floatValue() + ConfigItem.intValue3.get().floatValue()) / ConfigItem.intValue2.get());

                if (hurtSize < size) {
                    compoundTag.putInt(thePlayerHurtSize,hurtSize + 1);
                }
            }
        }
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        var attribute = super.doAttribute(stack, player);
        CompoundTag compoundTag = player.getPersistentData();
        int hurtSize = compoundTag.getIntOr(thePlayerHurtSize,0);
        float max = ConfigItem.intValue.get().floatValue();
        float min = ConfigItem.intValue3.get().floatValue();
        min = -min;
        float value = max;
        value -= (hurtSize * ConfigItem.intValue2.get());
        if (value < min) {
            value = min;
        }
        value /= 100f;

        attribute.put(Attributes.ATTACK_DAMAGE,new AttributeModifier(Identifier.parse(this.descriptionId),value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        return attribute;
    }
    @Override
    public void tick(Player player, ItemStack stack) {
        super.tick(player, stack);
        CompoundTag compoundTag = player.getPersistentData();
        int hurtSize = compoundTag.getIntOr(thePlayerHurtSize,0);
        if (hurtSize > 0) {
            if (player.tickCount % 60 == 1) {
                compoundTag.putInt(thePlayerHurtSize, hurtSize - 1);
            }
        }
    }

    @Override
    public @Nullable Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player, ItemStack stack) {
        return HashMultimap.create();
    }

    @Override
    public int getSanity() {
        return -0;
    }

    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        super.text(stack, tooltipComponents, flag);
        tooltipComponents.accept(Component.translatable("item.chest_item.triangle.string.1",(int)(ConfigItem.intValue.get().floatValue() )).setStyle(Style.EMPTY.withColor(theColor())));
        tooltipComponents.accept(Component.translatable("item.chest_item.triangle.string.2",(int)(ConfigItem.intValue2.get().floatValue() )).setStyle(Style.EMPTY.withColor(theColor())));
        tooltipComponents.accept(Component.translatable("item.chest_item.triangle.string.3",(int)(ConfigItem.intValue3.get().floatValue() )).setStyle(Style.EMPTY.withColor(theColor())));
    }
}
