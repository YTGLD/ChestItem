package com.ytgld.chest_item.items.evil_mother;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.GlStateManager;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.IBlackLight;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_item.renderer.CIStateShardsHasBlack;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class EvilMother extends ItemBase implements IBlackLight , IEvil {
    public EvilMother(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
    public static class ConfigItem implements RegisterItemConfig {
        public static ModConfigSpec.DoubleValue intValue;

        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("EvilMother");
            intValue = builder.translation("chest_item.config.EvilMother")
                    .defineInRange("number", 1f, 0, 10000);

            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(
                    new CIString("EvilMother",
                            "邪母", "邪母的”理智“所造成的属性倍率")
            );
        }
    }
    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        Component component = super.getName(stack);
        MutableComponent co = component.copy();
        co.setStyle(Style.EMPTY.withColor(TextColor.fromRgb(colorBlack().color())));
        return co;
    }
    public static void attrib(EntityTickEvent.Post event){
        if (event.getEntity() instanceof Player player){
            player.getAttributes().addTransientAttributeModifiers(theAttrib(player));
        }
    }

    /**
     *每降低 1 点理智：
     * <p>
     * +0.2  护甲
     * <p>
     * +0.2  伤害
     * <p>
     * -1.5%  治疗
     * <p>
     * -2.5%  经验掉落
     * <p>
     * -3.5%  移速
     */
    private static Multimap<Holder<Attribute>, AttributeModifier> theAttrib(Player player) {
        Multimap<Holder<Attribute> , AttributeModifier> attributeModifierMultimap = HashMultimap.create();
        ResourceLocation resourceLocation = ResourceLocation.parse(Chestitem.MODID + "evil_mother");
        float value = (float) getSanValue(player);
        float base = (float) getSanValueBase(player);
        float armor = 0 , damage = 0 ,heal = 0, xp = 0,speed = 0;
        float res = 0;
        if (value != base) {
            float c = base - value;
            armor = (float) (c * 0.2f * ConfigItem.intValue.getAsDouble());
            damage = (float) (c * 0.2f * ConfigItem.intValue.getAsDouble());
            heal = (float) (c * 1.5f / 100f * ConfigItem.intValue.getAsDouble());
            xp = (float) (c * 2.5f / 100f * ConfigItem.intValue.getAsDouble());
            speed = (float) (c * 3.5f / 100f * ConfigItem.intValue.getAsDouble());

            //c大于base自己，也就是意识为负数
            if (c > base) {
                float ss = (2 * c / 100f);
                if (ss < 0) {
                    ss = -ss;
                }
                res = (float) ((15f / 100f) + (ss * ConfigItem.intValue.getAsDouble()));
            }
        }
        attributeModifierMultimap.put(Attributes.ARMOR, new AttributeModifier(resourceLocation,
                armor, AttributeModifier.Operation.ADD_VALUE));

        attributeModifierMultimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(resourceLocation,
                damage, AttributeModifier.Operation.ADD_VALUE));

        attributeModifierMultimap.put(AttReg.heal, new AttributeModifier(resourceLocation,
                -heal, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        attributeModifierMultimap.put(AttReg.xp_drop, new AttributeModifier(resourceLocation,
                -xp, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        attributeModifierMultimap.put(AttReg.resistance, new AttributeModifier(resourceLocation,
                -res, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

        attributeModifierMultimap.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(resourceLocation,
                -speed, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));



        return attributeModifierMultimap;
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute> , AttributeModifier> attributeModifierMultimap = multimapAttribute(stack, player);
        if (getSanity() != 0) {
            attributeModifierMultimap.put(AttReg.theSanity, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                    this.asItem().getDescriptionId()),
                    getSanity(), AttributeModifier.Operation.ADD_VALUE));
        }
        return attributeModifierMultimap;
    }
    public abstract int getSanity();
    public Multimap<Holder<Attribute>, AttributeModifier> multimapAttribute(ItemStack stack, Player player){
        return HashMultimap.create();
    }
    public static double getSanValue(Player player){
        return player.getAttributeValue(AttReg.theSanity);
    }
    public static double getSanValueBase(Player player) {
        return player.getAttributeBaseValue(AttReg.theSanity);
    }
}
