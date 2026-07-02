package com.ytgld.chest_item.items.other;

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

public class Stone extends ItemBase {
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.IntValue intValue ;
        public static ModConfigSpec.IntValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Stone");
            intValue =  builder.translation("chest_item.config.Stone")
                    .defineInRange("number",5,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.Stone2")
                    .defineInRange("number2",5,1,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("Stone",
                            "重石","护甲"),
                    new CIString("Stone2",
                            "重石2","防击退")
            );
        }
    }
    public Stone(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Stone_.asItem().getDescriptionId()),
                ConfigItem.intValue2.getAsInt(), AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ARMOR, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Stone_.asItem().getDescriptionId()),
                ConfigItem.intValue.getAsInt(), AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(Identifier.parse(Chestitem.MODID + InitItems.Stone_.asItem().getDescriptionId()),
                2, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,255,255,0);
    }
}
