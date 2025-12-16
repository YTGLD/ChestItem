package com.ytgld.chest_item.items.condensebone;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class QualitativeComponents extends ItemBone {
    public QualitativeComponents(Properties properties) {
        super(properties);
    }
    @Override
    public GUILight guiLight() {
        int lightNumber = 4;
        Map<Integer,Integer> listGUIColor = new HashMap<>();
        listGUIColor.put(0, Light.ARGB.color(100,120,255,80));
        listGUIColor.put(1,Light.ARGB.color(100,120,255,80));


        listGUIColor.put(2,Light.ARGB.color(60,80,120,255));


        listGUIColor.put(3,Light.ARGB.color(120,80,120,255));
        Map<Integer, Vec2> listPosOffset = new HashMap<>();
        listPosOffset.put(0,new Vec2(4,-2));
        listPosOffset.put(1,new Vec2(-4,-2));


        listPosOffset.put(2,new Vec2(0,0));


        listPosOffset.put(3,new Vec2(0,4));
        Map<Integer, ResourceLocation> listImg = new HashMap<>();



        for (int i = 0; i < lightNumber; i++) {
            ResourceLocation identifier = ResourceLocation.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png");
            listImg.put(i,identifier);
        }

        return new GUILight(listGUIColor,listPosOffset,listImg,true,lightNumber);
    }
    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.QualitativeComponents_)) {
                    player.getAttributes().addTransientAttributeModifiers(attributeModifierMultimap());
                    break;
                } else {
                    player.getAttributes().removeAttributeModifiers(attributeModifierMultimap());
                }
            }
        }
    }
    public static Multimap<Holder<Attribute>, AttributeModifier> attributeModifierMultimap() {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttReg.more_speed, new AttributeModifier(ResourceLocation.parse(Chestitem.MODID +
                InitItems.QualitativeComponents_.asItem().getDescriptionId()),
                0.1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }
    @Nullable
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> muAttribute(Player player,ItemStack stack) {
        return attributeModifierMultimap();
    }
}
