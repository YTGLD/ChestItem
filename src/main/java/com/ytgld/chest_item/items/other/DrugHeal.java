package com.ytgld.chest_item.items.other;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DrugHeal  extends ItemBase {
    public DrugHeal(Properties properties) {
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
            builder.push("DrugHeal");
            intValue =  builder.translation("chest_item.config.DrugHeal")
                    .defineInRange("number",0.05f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.DrugHeal2")
                    .defineInRange("number2",80,1,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("DrugHeal",
                            "治疗药壶","生命值加成"),
                    new CIString("DrugHeal2",
                            "治疗药壶2","生命值恢复时间")
            );
        }
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.Drug_Heal)) {
                    if (player.tickCount % ConfigItem.intValue2.get().intValue() == 0) {
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0, false, false), player);
                        break;
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

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Drug_Heal.asItem().getDescriptionId()),
                ConfigItem.intValue.get().floatValue(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }

    @Override
     public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.drug_heal.string.0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        tooltipComponents.accept(Component.literal(""));
        tooltipComponents.accept(Component.translatable("item.chest_item.drug_heal.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,0,255,20);
    }
}
