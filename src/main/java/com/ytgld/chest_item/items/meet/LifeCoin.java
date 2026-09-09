package com.ytgld.chest_item.items.meet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LifeCoin  extends ItemBase implements Meat {

    @ConfigPlugin
     public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Meat";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("LifeCoin");
            intValue =  builder.translation("chest_item.config.LifeCoin")
                    .defineInRange("number",1f,0,Integer.MAX_VALUE);
            intValue2 =  builder.translation("chest_item.config.LifeCoin2")
                    .defineInRange("number2",0.3f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("LifeCoin",
                            "鲜活硬币","最大增生组织"),
                    new CIString("LifeCoin2",
                            "鲜活硬币2","增生组织的强度减少值")
            );
        }
    }
    public LifeCoin(Properties properties) {
        super(properties);
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.hyperplasia, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.LifeCoin_.asItem().getDescriptionId()),
                ConfigItem.intValue.get().floatValue(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        modifiers.put(AttReg.hyperplasia_stronger, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.LifeCoin_.asItem().getDescriptionId()),
                -ConfigItem.intValue2.get().floatValue(), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack, player);
    }
}

