package com.ytgld.chest_item.items.black;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.Chestitem;
import com.ytgld.chest_item.Handler;
import com.ytgld.chest_item.event.activated.ci.ItemStackTickEvent;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.IGUILightList;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBlackShadow;
import com.ytgld.chest_item.other.ChestInventory;
import com.ytgld.chest_item.other.DataReg;
import com.ytgld.chest_item.renderer.light.GUILight;
import com.ytgld.chest_item.renderer.light.Light;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 *混沌构造器
 * <p>
 * <p>
 * 	连续受到伤害时逐渐提高抗性
 * <p>
 * 	每次提升1%，但不超过30%
 * <p>
 */
public class ChaosConstructor extends ItemBlackShadow  implements IGUILightList {
    @Override
    public GUILight guiLight(LivingEntity entity) {
        int lightNumber = 5;
        Map<Integer,Integer> listGUIColor = new HashMap<>();
        listGUIColor.put(0, Light.ARGB.color(60,255,100,150));
        listGUIColor.put(1, Light.ARGB.color(60,255,100,150));

        listGUIColor.put(2, Light.ARGB.color(100,255,210,180));


        listGUIColor.put(3, Light.ARGB.color(40,200,100,150));
        listGUIColor.put(4, Light.ARGB.color(40,200,100,150));

        Map<Integer, Vec2> listPosOffset = new HashMap<>();
        listPosOffset.put(0, new Vec2(4,-3));
        listPosOffset.put(1, new Vec2(-4,-3));

        listPosOffset.put(2, new Vec2(0,4));

        listPosOffset.put(3, new Vec2(5,3));
        listPosOffset.put(4, new Vec2(-5,3));


        Map<Integer, Identifier> listImg = new HashMap<>();
        for (int i = 0; i < lightNumber; i++) {
            Identifier identifier = Identifier.fromNamespaceAndPath(Chestitem.MODID,"textures/item_glowing/all.png");
            listImg.put(i,identifier);
        }

        return new GUILight(listGUIColor,listPosOffset,listImg,true,lightNumber);
    }
    public static final String leadHurtSize = "leadHurtSize";

    public ChaosConstructor(Properties properties) {
        super(properties);
    }

    public static void hurtOfBlood(LivingDamageEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            ChestInventory chestInventory = Handler.getItem(player);
            if (chestInventory!=null) {
                if (!player.level().isClientSide()) {
                    for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                        ItemStack stack = chestInventory.getItem(i);
                        if (stack.is(InitItems.ChaosConstructor_)) {
                            CompoundTag compoundTag = stack.get(DataReg.tag);
                            if (compoundTag != null) {

                                if (compoundTag.getIntOr(leadHurtSize,0) < 30) {
                                    compoundTag.putInt(leadHurtSize,compoundTag.getIntOr(leadHurtSize,0)+1);
                                }
                                float s =((float)(compoundTag.getIntOr(leadHurtSize,0))*0.01f);

                                event.setNewDamage(event.getNewDamage()*(1-s));

                                break;
                            }else {
                                stack.set(DataReg.tag,new CompoundTag());
                            }
                        }
                    }
                }
            }
        }
    }

    public static void tick(ItemStackTickEvent event){
        ChestInventory chestInventory = event.chestInventory;
        Player player = event.player;
        if (!player.level().isClientSide()) {
            for (int i = 0; i < chestInventory.getContainerSize(); i++) {
                ItemStack stack = chestInventory.getItem(i);
                if (stack.is(InitItems.ChaosConstructor_)) {
                    CompoundTag compoundTag = stack.get(DataReg.tag);
                    if (compoundTag != null) {
                        if (player.tickCount%40==1) {
                            if (compoundTag.getIntOr(leadHurtSize,0) > 0) {
                                compoundTag.putInt(leadHurtSize, compoundTag.getIntOr(leadHurtSize,0) - 1);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> doAttribute(ItemStack stack, Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.doAttribute(stack, player);
        modifiers.put(AttReg.chaos_armor, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.ChaosConstructor_.asItem().getDescriptionId()),
                20, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttReg.chaos_armor_damage, new AttributeModifier(Identifier.parse(Chestitem.MODID +
                InitItems.ChaosConstructor_.asItem().getDescriptionId()),
                0.5f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
        return modifiers;
    }

    @Override
    public void text(ItemStack stack,Consumer<Component> tooltipAdder,TooltipFlag flag){
        if (flag.hasShiftDown()) {
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_constructor.string.7").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80ff5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_constructor.string.8").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X80ff5ACD))));
        }else {
            tooltipAdder.accept(Component.translatable("options.key.hold").append(Component.translatable("key.keyboard.left.shift")).withStyle(ChatFormatting.GOLD));
            tooltipAdder.accept(Component.literal(""));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_constructor.string.1").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_constructor.string.2").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_constructor.string.3").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_constructor.string.4").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_constructor.string.5").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
            tooltipAdder.accept(Component.translatable("item.chest_item.chaos_constructor.string.6").withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0X806A5ACD))));
        }
    }
}
