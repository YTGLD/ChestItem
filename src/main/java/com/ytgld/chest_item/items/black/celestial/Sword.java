package com.ytgld.chest_item.items.black.celestial;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
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

public class Sword  extends TheCelestial{
    public Sword(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("Sword");
            intValue =  builder.translation("chest_item.config.Sword")
                    .defineInRange("number",0.2f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(new CIString("Sword",
                    "剑体","属性"));
        }
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Sword_.asItem().getDescriptionId()),
                ConfigItem.intValue.get().floatValue(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.Sword_.asItem().getDescriptionId()),
                ConfigItem.intValue.get().floatValue(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }

    @Override
    public Identifier img(ItemStack stack) {
        return Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/gui/soul/celestial/sword.png");
    }

    @Override
    public int soulColor(ItemStack stack) {
        return Light.ARGB.color(255,60,100,255);
    }
}

