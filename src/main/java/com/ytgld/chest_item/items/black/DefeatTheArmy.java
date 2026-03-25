package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DefeatTheArmy extends ItemBlackShadow implements IBlackLight {
    public DefeatTheArmy(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("DefeatTheArmy");
            intValue =  builder.translation("chest_item.config.DefeatTheArmy")
                    .defineInRange("number",1f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("DefeatTheArmy",
                            "破军","属性倍率")
            );
        }
    }
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack,Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();

        float lv = player.getHealth() / player.getMaxHealth();
        lv *= 100;
        float now = (int) (100 - (lv));
        if (now < 0) {
            now = 0;
        }
        now /= 100f;

        float speed = 1.0f * now * ConfigItem.intValue.get().floatValue();
        float damage = 0.5f * now * ConfigItem.intValue.get().floatValue();
        float attSpeed = 0.8f * now * ConfigItem.intValue.get().floatValue();
        float chaosArmorSpeed = 0.8f * now * ConfigItem.intValue.get().floatValue();
        chaosArmorSpeed = -chaosArmorSpeed;


        modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                speed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                damage, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(Attributes.ATTACK_SPEED, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                attSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(AttReg.chaos_armor_speed, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                chaosArmorSpeed, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        modifiers.put(AttReg.chaos_armor, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.DefeatTheArmy_.asItem().getDescriptionId()),
                8, AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }

    public void text(ItemStack stack,java.util.function.Consumer<Component> tooltipComponents,TooltipFlag flag){
        tooltipComponents.accept(Component.translatable("item.chest_item.defeat_the_army.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }

    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return doAttribute(stack,player);
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(255,185,50,158);
    }

}
