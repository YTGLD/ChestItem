package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DryBones extends ItemBlackShadow {
    @ConfigPlugin
     public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "BlackShadow";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("DryBones");
            intValue =  builder.translation("chest_item.config.DryBones")
                    .defineInRange("number",0.2f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.DryBones2")
                    .defineInRange("number2",0.2f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("DryBones",
                            "腐枯尊骨","伤害和攻速"),
                    new CIString("DryBones2",
                            "腐枯尊骨2","护甲和速度")
            );
        }
    }

    public DryBones(Properties properties) {
        super(properties);
    }

    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        float hyperplasia = player.getData(AttReg.hyperplasiaATTACHMENT_TYPES);
        float shadow_shield = player.getData(AttReg.shadow_shield_ATTACHMENT_TYPES);
        float a =0;
        float s =0;
        float d =0;
        float f =0;
        float axe = 0f;
        if (player.getMainHandItem().is(InitItems.WallowAxe_.asItem())) {
            axe = 0.2f;
        }
        modifiers.put(AttReg.heal, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.WallowAxe_.asItem().getDescriptionId()),
                axe, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ARMOR, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.WallowAxe_.asItem().getDescriptionId()),
                axe, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.WallowAxe_.asItem().getDescriptionId()),
                axe, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.WallowAxe_.asItem().getDescriptionId()),
                axe, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.WallowAxe_.asItem().getDescriptionId()),
                axe, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        AttributeInstance attributeInstance_hyperplasia = player.getAttribute(AttReg.hyperplasia);
        AttributeInstance attributeInstance_shadow_shield = player.getAttribute(AttReg.shadow_shield);
        if (attributeInstance_hyperplasia != null && attributeInstance_shadow_shield != null) {
            float hV = (float) attributeInstance_hyperplasia.getValue() - 1;
            float vS = (float)attributeInstance_shadow_shield.getValue() - 1;

            if (hyperplasia >= hV){
                if (hyperplasia!=0 && hV != 0) {
                    a = ConfigItem.intValue2.get().floatValue();
                    s = ConfigItem.intValue2.get().floatValue();
                }
            }

            if (shadow_shield >= vS){
                if (shadow_shield!=0 && vS != 0) {
                    d = ConfigItem.intValue.get().floatValue();
                    f = ConfigItem.intValue.get().floatValue();
                }
            }
        }

        if (player.getMainHandItem().is(InitItems.WallowAxe_.asItem())) {
            a =0;
            s =0;
            d =0;
            f =0;
        }

        modifiers.put(Attributes.ARMOR, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DryBones_.asItem().getDescriptionId()),
                a, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DryBones_.asItem().getDescriptionId()),
                s, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DryBones_.asItem().getDescriptionId()),
                d, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DryBones_.asItem().getDescriptionId()),
                f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }


    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack,player);
    }

    public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.dry_bones.string.1",ConfigItem.intValue.get().floatValue() * 100f).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        tooltipComponents.accept(Component.translatable("item.chest_item.dry_bones.string.2",ConfigItem.intValue2.get().floatValue() * 100f).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }
}
