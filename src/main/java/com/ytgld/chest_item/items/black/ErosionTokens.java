package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.*;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ErosionTokens extends ItemBlackShadow implements IGUILightList {





    public ErosionTokens(Properties properties) {
        super(properties);
    }


    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);
        modifiers.put(AttReg.chaos_armor_min, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.ErosionTokens_.asItem().getDescriptionId()),
                -0.75f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable("item.chest_item.erosion_tokens.string.1").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
    }

    @Override
    public int color(ItemStack stack) {
        return Light.ARGB.color(80,185,50,158);
    }


    @Override
    public GUILight guiLight(LivingEntity entity) {
        if (entity!=null) {
            int lightNumber = 3;

            float modify1 = (float) Math.sin(entity.tickCount / 5f);
            float modify = (float) Math.sin(entity.tickCount / 10f);
            float modify2 = (float) Math.sin(entity.tickCount / 15f);


            Map<Integer, Integer> listGUIColor = new HashMap<>();
            listGUIColor.put(0, Light.ARGB.color((int) (60 + modify2 * 60), (int) (200 + modify * 50), 50, (int) (60 + modify * 60)));
            listGUIColor.put(1, Light.ARGB.color((int) (80 + modify * 40), 222, 80, (int) (60 + modify * 20)));
            listGUIColor.put(2, Light.ARGB.color((int) (80 + modify1 * 20), 220, 220, (int) (180 + modify * 50)));


            Map<Integer, Vec2> listPosOffset = new HashMap<>();

            listPosOffset.put(0, new Vec2(-4, 4));
            listPosOffset.put(1, new Vec2(-5, 5));
            listPosOffset.put(2, new Vec2(-3, 3));


            Map<Integer, Identifier> listImg = new HashMap<>();
            for (int i = 0; i < lightNumber; i++) {
                Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID, "textures/item_glowing/all.png");
                listImg.put(i, identifier);
            }
            return new GUILight(listGUIColor,listPosOffset,listImg,true,lightNumber);
        }
        return null;
    }
}
