package com.ytgld.chest_item.items.gold;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PainRune extends ItemBase implements IGold {
    public PainRune(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("PainRune");
            intValue =  builder.translation("chest_item.config.PainRune")
                    .defineInRange("number",0.25F,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.PainRune2")
                    .defineInRange("number2",0.25F,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("PainRune",
                            "痛苦符石","加成"),
                    new CIString("PainRune2",
                            "痛苦符石2","减益")
            );
        }
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Pain_Rune.asItem().getDescriptionId()),
                -ConfigItem.intValue2.get(), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Pain_Rune.asItem().getDescriptionId()),
                ConfigItem.intValue.get(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }
    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,255,20);
    }
}



