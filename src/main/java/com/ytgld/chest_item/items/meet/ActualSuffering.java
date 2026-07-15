package com.ytgld.chest_item.items.meet;

import com.google.common.collect.Multimap;
import com.ytgld.chest_item.config.ConfigPlugin;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.renderer.MRender;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.function.Consumer;

/**
 * 实质之痛苦
 * <p>
 * 通过吸收其他护盾来获得伤痛
 * <p>
 * 伤痛会带来极大的加成和防御
 */
public class ActualSuffering extends ItemBase implements Meat, IBlackLight , ITextColor {
    public ActualSuffering(Properties properties) {
        super(properties);
    }
    @ConfigPlugin
     public static class ConfigItem implements RegisterItemConfig {
        @Override
        public String theCategory() {
            return "Meat";
        }
        public static ModConfigSpec.DoubleValue intValue ;
        @Override
        public void config(ModConfigSpec.Builder builder) {
            builder.push("ActualSuffering");
            intValue =  builder.translation("chest_item.config.ActualSuffering")
                    .defineInRange("number",10f,0,Integer.MAX_VALUE);
            builder.pop();
        }

        @Override
        public List<CIString> theLanguageProvider() {
            return List.of(new CIString("ActualSuffering",
                    "实质之痛苦","最大创伤之痕"));
        }
    }


    @Override
    public void text(ItemStack stack, Consumer<Component> tooltipComponents, TooltipFlag flag) {
        tooltipComponents.accept(Component.translatable("item.chest_item.actual_suffering.string.0").withStyle(ChatFormatting.GOLD));
        tooltipComponents.accept(Component.translatable("item.chest_item.actual_suffering.string.1").withStyle(ChatFormatting.GOLD));

    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        var attribute  =super.doAttribute(stack, player);
        attribute.put(AttReg.painShield_number,
                new AttributeModifier(Identifier.parse(this.getDescriptionId()),
                        ConfigItem.intValue.get().floatValue(), AttributeModifier.Operation.ADD_VALUE));
        attribute.put(AttReg.painShield_res,
                new AttributeModifier(Identifier.parse(this.getDescriptionId()),
                        0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        attribute.put(AttReg.painShield_speed,
                new AttributeModifier(Identifier.parse(this.getDescriptionId()),
                        0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return attribute;
    }

    @Override
    public DoBlack colorBlack() {
        return new DoBlack(50 ,100,0,0, MRender.RenderPs.GUI_TEXTURED);
    }

    @Override
    public int colorText() {
        return 16755200;
    }
}
