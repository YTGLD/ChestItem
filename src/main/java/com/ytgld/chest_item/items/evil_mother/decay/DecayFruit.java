package com.ytgld.chest_item.items.evil_mother.decay;

import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.AttributeDataType;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ytgld.chest_item.event.loot.ReinforcedLoot.lListItems;

public class DecayFruit extends DecayItem{
    public DecayFruit(Properties properties) {
        super(properties);
        lListItems.add(this);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "EvilMother";
        }
        public static ModConfigSpec.ConfigValue<List<String>> intValue ;
        public static ModConfigSpec.DoubleValue intValue2 ;
        public static ModConfigSpec.DoubleValue intValue3 ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("DecayFruit");
            intValue =  builder.translation("chest_item.config.DecayFruit")
                    .define("number",new ArrayList<>(List.of("")));
            intValue2 =  builder.translation("chest_item.config.DecayFruit2")
                    .defineInRange("number2",0.01f,0,10000f);
            intValue3 =  builder.translation("chest_item.config.DecayFruit3")
                    .defineInRange("number3",0.05f,0,10000f);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(new CIString("DecayFruit",
                    "万灵尸香果","属性黑名单"),
                    new CIString("DecayFruit2",
                            "万灵尸香果2","属性最小值"),
                    new CIString("DecayFruit3",
                            "万灵尸香果3","属性最大值")

            );
        }
    }
    public static boolean overrideStacked(ItemStack fruit, ItemStack me, Player player, boolean org) {
        if (!me.isEmpty()) {
            if (fruit.is(InitItems.DecayFruit_.asItem())) {
                AttributeDataType attributeDataType = me.get(DataReg.attributeType);
                if (attributeDataType == null) {
                    player.level().playSound(null, player.blockPosition(), SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.PLAYERS, 1, 1);
                    AttributeDataType doIt = new AttributeDataType(List.of());
                    me.set(DataReg.attributeType, addAttributeType(player, doIt));
                    fruit.shrink(1);
                    return true;
                }
            }
        }
        return org;
    }
    public static AttributeDataType addAttributeType(Player player, AttributeDataType attributeDataType){
        Set<String> blacklist = new HashSet<>();
        for (String aaa : ConfigItem.intValue.get()) {
            if (!aaa.isEmpty()) {
                String[] parts = aaa.split(":");
                if (parts.length > 0) {
                    blacklist.add(parts[0] + ":" + parts[1]);
                }
            }
        }
        int random = player.getRandom().nextInt(BuiltInRegistries.ATTRIBUTE.asHolderIdMap().size());
        Holder<Attribute> attributeReference = BuiltInRegistries.ATTRIBUTE.asHolderIdMap().byId(random);
        if (attributeReference !=null) {
            Identifier attributeId = BuiltInRegistries.ATTRIBUTE.getKey(attributeReference.value());
            if (attributeId != null && !blacklist.contains(attributeId.toString())) {
                return attributeDataType.builder().add(attributeReference,
                                new AttributeModifier(Identifier.fromNamespaceAndPath(
                                        Chestitem.MODID, "decay_fruit_attriubte"
                                ), addNumber(player), AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                        .build();
            }else {
                return attributeDataType.builder().add(Attributes.ATTACK_DAMAGE,
                                new AttributeModifier(Identifier.fromNamespaceAndPath(
                                        Chestitem.MODID, "decay_fruit_attriubte"
                                ), addNumber(player), AttributeModifier.Operation.ADD_MULTIPLIED_BASE))
                        .build();
            }
        }
        return new AttributeDataType(List.of());
    }

    private static float addNumber(Player player) {
        float value = (float) getSanValue(player);
        float base = (float) getSanValueBase(player);
        float c = base - value;
        c += 1;
        float add = (float) Math.sqrt(c);

        if (add > 5) {
            add = 5;
        }

        float min = (float) ConfigItem.intValue2.getAsDouble() * add;
        float max = (float) ConfigItem.intValue3.getAsDouble() * add;
        return Mth.nextFloat(player.getRandom(), min, max);
    }

    @Override
    public int getSanity() {
        return 0;
    }
}
